package com.hyperaccesss.schedulers;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
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

	@Value("${dir.cdr}")
	private String dirCdr; // C:/

	@Value("${dir.app}")
	private String dirApp;

	String FILE_EXT = ".txt";

	// @Scheduled(fixedDelay = 10000)
	public void FileDownload() {

		Date date = new Date();
		String dateformat = new SimpleDateFormat("yyyyMMdd").format(date);

		String FILE_NAME = "SMS_IN_" + dateformat + ".log";

		// create new directory IN
		final File TEMP_DIRECTORY = new File(dirCdr);

		File newDirectory = new File(TEMP_DIRECTORY, dirApp);
		File nestedDirectory = new File(newDirectory, "IN");

		assertFalse(newDirectory.exists());
		assertFalse(nestedDirectory.exists());
		assertFalse(nestedDirectory.mkdir());

		try (BufferedInputStream inputStream = new BufferedInputStream(
				new URL("http://192.168.9.2/cb/LCR-CDRs.php?password=admin&action=get&filename=" + FILE_NAME
						+ "&filetype=cdr&internal=0&format=text").openStream());
				FileOutputStream fileOS = new FileOutputStream(dirCdr + dirApp + "/IN/" + FILE_NAME + FILE_EXT)) {
			byte data[] = new byte[1024];
			int byteContent;
			while ((byteContent = inputStream.read(data, 0, 1024)) != -1) {
				fileOS.write(data, 0, byteContent);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Scheduled(fixedDelay = 5000)
	public void ReadFile() throws ParseException {

		Sms findSmsInByCode = null;

		// recuperation de la liste des passerelle
		List<Passerelle> passerelles = passerelleRepos.findAllPasserelle();

		for (Passerelle passerelle : passerelles) {

			Date date = new Date();
			String dateformat = new SimpleDateFormat("yyyyMMdd").format(date);

			String FILE_NAME = "SMS_IN_" + dateformat + ".log" + FILE_EXT;

			String fileReader = dirCdr + dirApp + passerelle.getIp_pass() + "/IN/" + FILE_NAME;

			try {
				List<String> allLines = Files.readAllLines(Paths.get(fileReader));
				for (String line : allLines) {
					// code de la ligne
					String codesms = DigestUtils.md5Hex(dateformat + line);

					findSmsInByCode = smsRepos.findSmsInByCode(codesms);
					System.out.println(findSmsInByCode);

					if (findSmsInByCode != null) {
						System.out.println("Jai trouver quelque chose");
					} else {
						// je split chaque line recuperée
						String delimiter = "\\|";
						String[] parts = line.split(delimiter);

						SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

						Date dateRecep = formatter.parse(parts[0]);
						String expediteur = parts[3];
						String message = parts[4];

						// enregistrement dans la base de donnees
						Sms sms = new Sms();

						sms.setCodeSms(codesms);
						sms.setExpediteurSms(expediteur);
						sms.setMessage(message);
						sms.setDateReception(dateRecep);

						sms.setDateInsertion(new Date());
						sms.setEtatSms(0);

						Passerelle pass = new Passerelle();
						pass.setIp_pass(passerelle.getIp_pass());
						System.out.println("===> pass = " + pass);

						// sms.setPasserelle(pass);

						smsRepos.save(sms);
					}
				}
			} catch (FileNotFoundException e) {
				System.err.println("Unable to find the file: " + fileReader);
			} catch (IOException e) {
				System.err.println("Unable to read the file: " + fileReader);
			}

		}

	}

	private void assertTrue(boolean mkdir) {
		// TODO Auto-generated method stub

	}

	private void assertFalse(boolean exists) {
		// TODO Auto-generated method stub

	}
}
