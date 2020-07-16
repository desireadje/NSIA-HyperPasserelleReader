package com.hyperaccesss.schedulers;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.net.URL;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class FileDownloadScheduler {

	@Scheduled(fixedDelay = 10000)
	public void FileDownload() {

		try (BufferedInputStream inputStream = new BufferedInputStream(
				new URL("http://192.168.9.2/cb/LCR-CDRs.php?password=admin&action=get&filename=SMS_IN_20200716.log&filetype=cdr&internal=0&format=text").openStream());
				FileOutputStream fileOS = new FileOutputStream("C:/Users/HYPERACCESS SYSTEMS/Desktop/passerelle/passerelle.txt")) {
			byte data[] = new byte[1024];
			int byteContent;
			while ((byteContent = inputStream.read(data, 0, 1024)) != -1) {
				fileOS.write(data, 0, byteContent);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
