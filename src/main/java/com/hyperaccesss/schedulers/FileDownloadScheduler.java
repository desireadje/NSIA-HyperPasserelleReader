package com.hyperaccesss.schedulers;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.hyperaccesss.entities.Passerelle;
import com.hyperaccesss.entities.Sms;
import com.hyperaccesss.repositories.PasserelleRepository;
import com.hyperaccesss.repositories.SmsRepository;

@Component
public class FileDownloadScheduler {

	@Autowired
	private SmsRepository smsRepos;

	@Autowired
	private PasserelleRepository passerelleRepos;

	@Value("${disk.log}")
	private String diskLog; // C:/

	@Value("${dir.app}")
	private String dirApp; // hyperPasserelleReader/

	@Value("${dir.downlaod}")
	private String dirDownlaod; // TELECHARGEMENT/

	String file_ext = ".txt";

	Date date = new Date();
	String data_now = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(date);
	SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

	// cette fonction permert de recuperer les cdr sur la passerelle
	@Scheduled(fixedDelay = 5000)
	public void FileDownload() throws MalformedURLException, IOException {

		List<Passerelle> passerelles = null;

		// recuperation de la liste des passerelles
		passerelles = passerelleRepos.findAllPasserelle();

		if (passerelles != null) {

			String dateformat = new SimpleDateFormat("yyyyMMdd").format(date);

			// je parcours la liste de passerelles
			for (Passerelle passerelle : passerelles) {

				String ip = passerelle.getIp_pass();

				String file_name = "SMS_IN_" + dateformat + ".log";

				// je creer un nouveau repertoire de telechargement
				final File logDir = new File(diskLog);

				File appDir = new File(logDir, dirApp);
				File downloadDir = new File(appDir, dirDownlaod);
				File ipDir = new File(downloadDir, ip);

				assertFalse(appDir.exists());
				assertFalse(downloadDir.exists());
				assertFalse(ipDir.exists());
				assertFalse(ipDir.mkdir());

				// je formate mon url de telechargement
				String url_pass = passerelle.getUrl_pass();

				String url_ip = url_pass.replace("[IP]", ip);
				String url = url_ip.replace("[FILE_NAME]", file_name);

				// tester un ping sur la paessrelle ip (chercher comment faire un ping en Spring
				// boot)
				HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
				connection.setRequestMethod("HEAD");
				int responseCode = connection.getResponseCode();

				if (responseCode == 200) {
					try (BufferedInputStream inputStream = new BufferedInputStream(new URL(url).openStream());

							FileOutputStream fileOS = new FileOutputStream(
									diskLog + dirApp + dirDownlaod + ip + "/" + file_name + file_ext)) {
						byte data[] = new byte[1024];
						int byteContent;

						while ((byteContent = inputStream.read(data, 0, 1024)) != -1) {
							fileOS.write(data, 0, byteContent);
						}
						System.out.println(data_now + " Recuperation des SMS de la passerelle " + ip);
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					System.err.println(data_now + " Aucun SMS récupéré sur la passerelle " + ip);
				}
			}
		} else {
			System.err.println(data_now + " Aucune passerelle n'a été trouvé");
		}
	}

	// cette fonction permet de recuperer les sms des fichier vers la bd avec un
	// code
	@Scheduled(fixedDelay = 7000)
	public void ReadFile() throws ParseException {

		// Mes declarations locales
		Sms findSmsInByCode = null;
		List<Passerelle> passerelles = null;
		List<String> allLines = null;

		// Je récupère ma liste des passerelle
		passerelles = passerelleRepos.findAllPasserelle();

		// si la liste n'est pas vide
		if (passerelles != null) {

			// Je parcours ma liste de passerelle
			for (Passerelle passerelle : passerelles) {

				String ip = passerelle.getIp_pass();

				String dateformat = new SimpleDateFormat("yyyyMMdd").format(date);

				String file_name = "SMS_IN_" + dateformat + ".log" + file_ext;

				// fichier a lire :
				// C:/hyperPasserelleReader/TELECHARGEMENT/192.168.9.2/SMS_IN_20200720.log.txt
				String fileReader = diskLog + dirApp + dirDownlaod + ip + "/" + file_name;

				if (Files.exists(Paths.get(fileReader))) {
					try {
						// lecture des lignes du fichier
						allLines = Files.readAllLines(Paths.get(fileReader));

						if (allLines != null) {

							for (String line : allLines) {

								// foramatge du code du sms de la ligne
								String codesms = DigestUtils.md5Hex(dateformat + line);

								// verification si le code existe ou pas
								findSmsInByCode = smsRepos.findSmsInByCode(codesms);

								if (findSmsInByCode == null) {

									// je split chaque line recuperée
									String delimiter = "\\|";
									String[] parts = line.split(delimiter);
									System.err.println("=>>>>>>>=>>>>>>>><" + parts.length);

									Date dateRecep = formatter.parse(parts[0]);
									String expediteur = parts[3];
									String message = parts[4];

									// enregistrement dans la base de donnees
									Sms sms = new Sms();

									sms.setCodeSms(codesms);
									sms.setExpediteurSms(expediteur);
									sms.setMessage(message);
									sms.setDateReception(dateRecep);
									sms.setDateInsertion(date);

									sms.setPasserelle(passerelle);
									sms.setEtatSms(-1);

									smsRepos.save(sms);
								}
							}

						} else {
							System.out.println(data_now + " fichier " + fileReader + "introuable");
						}

					} catch (FileNotFoundException e) {
						System.err.println(data_now + " Unable to find the file: " + fileReader);
					} catch (IOException e) {
						System.err.println(data_now + " Unable to read the file: " + fileReader);
					}
				} else {
					System.err.println(
							data_now + " Impossible de lire le fichier " + fileReader + " de la passerelle " + ip);
				}

				System.out.println(data_now + " Enregistrement dans la BD des SMS de la passerelle " + ip);
			}
		} else {
			System.err.println(data_now + " Aucune passerelle n'a été trouvé");
		}

	}

	// cette fonction prmet d'aller lire dans la bd et mettre les fichier a
	// disposition
	@Scheduled(fixedDelay = 5000)
	public void Exporte() throws IOException, ParseException {
		// mes valiables local
		List<Passerelle> passerelles = null;
		List<Sms> allSms = null;

		passerelles = passerelleRepos.findAllPasserelle();

		if (passerelles != null) {
			for (Passerelle passerelle : passerelles) {

				String ip = passerelle.getIp_pass();

				// selection des sms du jour de la passerelle du jour
				allSms = smsRepos.findAllSmsByIpPasserelle(ip);

				if (allSms != null) {
					for (Sms sms : allSms) {

						String dateformat = new SimpleDateFormat("yyyyMMdd").format(date);

						String fileReader = "SMS_IN_" + dateformat + ".log" + file_ext;

						// String file_name = diskLog + dirApp + "IN/" + ip + "/" + fileReader; //
						// C:/hyperPasserelleReader/IN/192.168.9.2
						String file_name = diskLog + dirApp + "IN/" + fileReader; // C:/hyperPasserelleReader/IN

						File file = new File(file_name);

						// je verifie si le fichier existe deja
						if (!file.exists()) {
							// creation du fichier
							Path newFilePath = Paths.get(file_name);
							Files.createFile(newFilePath);
						}

						// j'ajoute une nouvelle ligne dans le fichier
						try (FileWriter fw = new FileWriter(file_name, true);
								BufferedWriter bw = new BufferedWriter(fw);
								PrintWriter pw = new PrintWriter(bw);) {

							String content = sms.getCodeSms() + "|" + sms.getMessage() + "|" + sms.getExpediteurSms();

							pw.println(content);

							// mise a jour de l'etat dans la bd a 0
							sms.setEtatSms(0);

							smsRepos.save(sms);

						} catch (IOException i) {
							i.printStackTrace();
						}
					}
					System.out.println(data_now + " Mise a disposition des SMS de la passerelle " + ip);
				} else {
					System.err.println(data_now + " Liste de SMS vide");
				}
			}
		} else {
			System.err.println(data_now + " Aucune passerelle n'a été trouvé");
		}
	}

	private void assertTrue(boolean mkdir) {
		// TODO Auto-generated method stub
	}

	private void assertFalse(boolean exists) {
		// TODO Auto-generated method stub
	}
}
