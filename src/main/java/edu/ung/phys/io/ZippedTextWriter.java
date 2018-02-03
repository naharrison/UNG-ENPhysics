package edu.ung.phys.io;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZippedTextWriter {

	public File file;
	public ZipOutputStream zipOutputStream;
	public ZipEntry zipEntry;
	
	// filename w/ more than one "." should still work, but won't get desired output filename
	public ZippedTextWriter(String zipFileFullPath) throws IOException {
		file = new File(zipFileFullPath);
		zipOutputStream = new ZipOutputStream(new FileOutputStream(file));
		String[] fullPathComponents = zipFileFullPath.split("/");
		String basenameWithExtension = fullPathComponents[fullPathComponents.length-1];
		String basenameWithoutExtension = basenameWithExtension.split("\\.")[0];
		String outName = basenameWithoutExtension + ".txt";
		zipEntry = new ZipEntry(outName);
		zipOutputStream.putNextEntry(zipEntry);
	}
	
	public void close() throws IOException {
		zipOutputStream.closeEntry();
		zipOutputStream.close();
	}
	
	public void write(String string) throws IOException {
		StringBuilder sb = new StringBuilder();
		sb.append(string);
		byte[] data = sb.toString().getBytes();
		zipOutputStream.write(data, 0, data.length);
	}
	
	public void writeLine(String string) throws IOException {
		write(string);
	    write(System.getProperty("line.separator"));
	}

}