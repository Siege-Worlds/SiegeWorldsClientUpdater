import java.awt.*;
import java.io.*;
import java.net.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.swing.*;

public class Loader extends JFrame {

	private static final long serialVersionUID = -6236121847194890547L;

	/**
	 * Needed variables
	 */

	public static final String GAME_CLIENT = "http://www.gamesinteractive.co.uk/SW/client.zip";
	public static final String ZIP_FILE = System.getProperty("user.home") + "/SW/SWClient.zip";

	public static final String VERSION_URL = "http://www.gamesinteractive.co.uk/SW/version.txt";
	public static final String VERSION_FILE = System.getProperty("user.home") + "/SW/version.txt";
	
	public static final String GAME_DIRECTORY = System.getProperty("user.home") + "/SW/";
	public static final String GAME_EXE = System.getProperty("user.home") + "/SW/SWClient.exe";

	/**
	 * Methods
	 */
	void clearDirectory(File dir) {
		if (!dir.exists())
			return;
	    for (File file: dir.listFiles()) {
	        if (file.isDirectory())
	        	clearDirectory(file);
	        file.delete();
	    }
	}

	public int getCurrentVersion() {
		try {
			return Integer
					.parseInt(new BufferedReader(new InputStreamReader(new FileInputStream(VERSION_FILE))).readLine());
		} catch (Exception e) {
			return 0;
		}
	}

	public int getNewestVersion() {
		try {
			URL tmp = new URL(VERSION_URL);
			BufferedReader br = new BufferedReader(new InputStreamReader(tmp.openStream()));
			return Integer.parseInt(br.readLine());
		} catch (Exception e) {
			return -1;
		}
	}

	public void download() {
		try {
			clearDirectory(new File(GAME_DIRECTORY));
			saveUrl(ZIP_FILE, GAME_CLIENT);
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	public void update() throws IOException {
		File file = new File(ZIP_FILE);
		int newest = getNewestVersion();
		int currentVersion = getCurrentVersion();
		System.out.println("Current version installed: " + currentVersion);
		System.out.println("Newest version: " + newest);
		if (!file.exists()) {
			download();
		}
		if (newest > this.getCurrentVersion()) {
			OutputStream out;
			try {
				out = new FileOutputStream(VERSION_FILE);
				out.write(String.valueOf(newest).getBytes());
				file.delete();
				download();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e2) {
			}
		}
		
		unZip(new File(ZIP_FILE));
		
		try {
			Runtime.getRuntime().exec(GAME_EXE);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Components
	 */

	JPanel panel;
	JLabel label;

	/**
	 * Constructor, loads JFrame and components
	 */
	public Loader() {
		super("Siege Worlds Loader");
		setSize(350, 70);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		//setUndecorated(true);
		setVisible(true);
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		setForeground(Color.BLACK);
		setFont(new Font("Arial", 24, 24));
		repaint();

		/**
		 * Load Components
		 */

		panel = new JPanel();
		label = new JLabel();

		/**
		 * Component Settings
		 */

		panel.setDoubleBuffered(true);
		label.setText("Siege worlds is downloading. 0%");

		/**
		 * Add components
		 */

		panel.add(label);
		add(panel);

		/**
		 * Download and load client
		 */

		try {
			update();
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		dispose();
	}

	/**
	 * Starts client loader
	 * 
	 * @param args unused
	 */
	public static void main(String[] args) {
		new Loader();
	}

	/**
	 * Downloads file from url and places in desired directory
	 * 
	 * @param filename  downloaded file name and path
	 * @param urlString file URL
	 * @throws MalformedURLException malformed url exception
	 * @throws IOException           IOException
	 */
	public void saveUrl(String filename, String urlString) throws MalformedURLException, IOException {
		BufferedInputStream in = null;
		FileOutputStream out = null;
		try {
			in = new BufferedInputStream(new URL(urlString).openStream());
			new File(GAME_DIRECTORY).mkdir();
			out = new FileOutputStream(filename);
			byte data[] = new byte[1024];
			int count;
			long curr = 0;
			long max = new URL(urlString).openConnection().getContentLength();
			while ((count = in.read(data, 0, 1024)) != -1) {
				out.write(data, 0, count);
				curr += count;
				int pc = (int)((curr * 100) / max);
				label.setText("Siege worlds is downloading. "+pc+"%");
				repaint();
			}
		} finally {
			if (in != null)
				in.close();
			if (out != null)
				out.close();
		}
	}
	
	private void unZip(File clientZip) {
		try {
			label.setText("un-zipping game files.");
			repaint();
			unZipFile(clientZip,new File(GAME_DIRECTORY));
			clientZip.delete();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void unZipFile(File zipFile,File outFile) throws IOException{
		ZipInputStream zin = new ZipInputStream(new BufferedInputStream(new FileInputStream(zipFile)));
		ZipEntry e;
		long max = 0;
		long curr = 0;
		while((e = zin.getNextEntry()) != null)
			max += e.getSize();
		zin.close();
		ZipInputStream in = new ZipInputStream(new BufferedInputStream(new FileInputStream(zipFile)));
		while((e = in.getNextEntry()) != null){
			if(e.isDirectory())
				new File(outFile,e.getName()).mkdirs();
			else{
				FileOutputStream out = new FileOutputStream(new File(outFile,e.getName()));
				byte[] b = new byte[1024];
				int len;
				while((len = in.read(b,0,b.length)) > -1){
					curr += len;
						out.write(b, 0, len);
						//int complete = (int)((curr * 100) / max));
				}
				out.flush();
				out.close();
			}
		}
	}
}