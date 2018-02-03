package edu.ung.phys.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ZippedTextReader {
	
		public ZipFile zipFile;
		public ZipEntry zipEntry;
		public InputStream inputStream;
		public BufferedReader bufferedReader;
		
		public ZippedTextReader(String zipFileFullPath) throws IOException {
			this.zipFile = new ZipFile(zipFileFullPath);
			Enumeration<? extends ZipEntry> entries = zipFile.entries();
			zipEntry = entries.nextElement();
			inputStream = zipFile.getInputStream(zipEntry);
			bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
		}
		
		public void close() throws IOException {
			zipFile.close();
			bufferedReader.close();
		}
		
		public String readLine() throws IOException {
			return bufferedReader.readLine();
		}

}