package com.gamesinteractive;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;

public class FileDownloader implements Runnable {
	
	
	public FileDownloader(String fileName, String url, String directory) {
		this.fileName = fileName;
		this.url = url;
		this.directory = directory;
		message = "Update required.";
		downloadComplete = false;
	}
	
	public void start() {
		Thread t = new Thread(this);
		t.start();
	}
	
	private String fileName, url, directory, zipFile;
	
	public volatile String message;
	public volatile boolean downloadComplete;
	public volatile int percent;

	private void download(String fileName, String url, String directory) {
		try {
			clearDirectory(new File(directory));
			saveUrl(fileName, url, directory);
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	public void clearDirectory(File dir) {
		if (!dir.exists())
			return;
		for (File file : dir.listFiles()) {
			if (file.isDirectory())
				clearDirectory(file);
			file.delete();
		}
	}

	/**
	 * Downloads file from url and places in desired directory
	 * 
	 * @param filename  downloaded file name and path
	 * @param urlString file URL
	 * @throws MalformedURLException malformed url exception
	 * @throws IOException           IOException
	 */
	private void saveUrl(String filename, String urlString, String directory)
			throws MalformedURLException, IOException {
		BufferedInputStream in = null;
		FileOutputStream out = null;
		try {
			in = new BufferedInputStream(new URL(urlString).openStream());
			new File(directory).mkdirs();
			out = new FileOutputStream(filename);
			byte data[] = new byte[1024];
			int count;
			long curr = 0;
			long max = new URL(urlString).openConnection().getContentLength();
			while ((count = in.read(data, 0, 1024)) != -1) {
				out.write(data, 0, count);
				curr += count;
				int pc = (int) ((curr * 100) / max);
				this.percent = pc;
				message = "Downloading Game Files: " + pc + "%";
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (in != null)
				in.close();
			if (out != null)
				out.close();
		}
	}
	
	public void extractTarGZ(InputStream in, String directory) throws IOException {
		message = "Unpacking game files.";
		GzipCompressorInputStream gzipIn = new GzipCompressorInputStream(in);
		try (TarArchiveInputStream tarIn = new TarArchiveInputStream(gzipIn)) {
			TarArchiveEntry entry;

			while ((entry = (TarArchiveEntry) tarIn.getNextEntry()) != null) {
				/** If the entry is a directory, create the directory. **/
				if (entry.isDirectory()) {
					File f = new File(directory + entry.getName());
					boolean created = f.mkdir();
					if (!created) {
						System.out.printf("Unable to create directory '%s', during extraction of archive contents.\n",
								f.getAbsolutePath());
					}
				} else {
					int count;
					byte data[] = new byte[1024];
					FileOutputStream fos = new FileOutputStream(directory + entry.getName(), false);
					try (BufferedOutputStream dest = new BufferedOutputStream(fos, 1024)) {
						while ((count = tarIn.read(data, 0, 1024)) != -1) {
							dest.write(data, 0, count);
						}
					}
				}
			}

			System.out.println("Untar completed successfully!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		message = "Connecting to file server.";
		download(fileName, url, directory);
		try {
			extractTarGZ(new FileInputStream(new File(fileName)), directory);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			message = "Error extracting game files.";
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			message = "Error extracting game files.";
			e.printStackTrace();
		}
		downloadComplete = true;
	}

}
