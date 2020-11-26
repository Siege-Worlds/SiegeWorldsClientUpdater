//import java.awt.*;
//import java.io.*;
//import java.net.*;
//import java.util.zip.ZipEntry;
//import java.util.zip.ZipInputStream;
//
//import javax.swing.*;
//
//import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
//import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
//import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;
//
//public class Loader extends JFrame {
//
//	private static final long serialVersionUID = -6236121847194890547L;
//
//	/**
//	 * Needed variables
//	 */
//
//	public static final String GAME_CLIENT = "http://77.68.78.218/SW/sw_windows_client.tar.gz";
//	public static final String ZIP_FILE = System.getProperty("user.home") + "/.siegeworlds/sw_windows_client.tar.gz";
//
//	public static final String VERSION_URL = "http://77.68.78.218/SW/version.txt";
//	public static final String VERSION_FILE = System.getProperty("user.home") + "/.siegeworlds/version.txt";
//	
//	public static final String GAME_DIRECTORY = System.getProperty("user.home") + "/.siegeworlds/";
//	public static final String GAME_EXE = System.getProperty("user.home") + "/.siegeworlds/SWClient.exe";
//	
//	/**
//	 * Detect errors
//	 */
//	public boolean error = false;
//
//	/**
//	 * Methods
//	 */
//	void clearDirectory(File dir) {
//		if (!dir.exists())
//			return;
//	    for (File file: dir.listFiles()) {
//	        if (file.isDirectory())
//	        	clearDirectory(file);
//	        file.delete();
//	    }
//	}
//
//	public int getCurrentVersion() {
//		try {
//			return Integer
//					.parseInt(new BufferedReader(new InputStreamReader(new FileInputStream(VERSION_FILE))).readLine());
//		} catch (Exception e) {
//			return 0;
//		}
//	}
//
//	public int getNewestVersion() {
//		try {
//			URL tmp = new URL(VERSION_URL);
//			BufferedReader br = new BufferedReader(new InputStreamReader(tmp.openStream()));
//			return Integer.parseInt(br.readLine());
//		} catch (Exception e) {
//			error = true;
//			return -1;
//		}
//	}
//
//	public void download() {
//		try {
//			clearDirectory(new File(GAME_DIRECTORY));
//			saveUrl(ZIP_FILE, GAME_CLIENT);
//		} catch (MalformedURLException e1) {
//			error = true;
//			e1.printStackTrace();
//		} catch (IOException e1) {
//			error = true;
//			e1.printStackTrace();
//		}
//	}
//
//	public void update() throws IOException {
//		File file = new File(ZIP_FILE);
//		int newest = getNewestVersion();
//		int currentVersion = getCurrentVersion();
//		System.out.println("Current version installed: " + currentVersion);
//		System.out.println("Newest version: " + newest);
//		boolean downloaded = false;
//		if (!file.exists()) {
//			download();
//			downloaded = true;
//		} else if (newest > this.getCurrentVersion()) {
//			OutputStream out;
//			try {
//				out = new FileOutputStream(VERSION_FILE);
//				out.write(String.valueOf(newest).getBytes());
//				file.delete();
//				download();
//				downloaded = true;
//			} catch (FileNotFoundException e) {
//				error = true;
//				e.printStackTrace();
//			} catch (IOException e2) {
//				e2.printStackTrace();
//				error = true;
//			}
//		}
//		if (downloaded) {
//			extractTarGZ(new FileInputStream(new File(ZIP_FILE)));
//		}
//		
//		
//		
//		try {
//			Runtime.getRuntime().exec(GAME_EXE);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
//
//	/**
//	 * Components
//	 */
//
//	JPanel panel;
//	JLabel label;
//
//	/**
//	 * Constructor, loads JFrame and components
//	 */
//	public Loader() {
//		super("Siege Worlds Loader");
//		setSize(350, 150);
//		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
//		setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
//		//setUndecorated(true);
//		setVisible(true);
//		
//		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
//		
//		setForeground(Color.BLACK);
//		setFont(new Font("Arial", 24, 24));
//		repaint();
//
//		/**
//		 * Load Components
//		 */
//
//		panel = new JPanel();
//		label = new JLabel();
//
//		/**
//		 * Component Settings
//		 */
//
//		panel.setDoubleBuffered(true);
//		label.setText("Siege worlds is downloading. 0%");
//
//		/**
//		 * Add components
//		 */
//
//		panel.add(label);
//		add(panel);
//
//		/**
//		 * Download and load client
//		 */
//
//		try {
//			update();
//		} catch (IOException e2) {
//			e2.printStackTrace();
//		}
//		
//		if (error) {
//			label.setText("Error: Try re-downloading game launcher.");
//			repaint();
//			while (true) {
//				try {
//					Thread.sleep(50);
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//		}
//		
//		dispose();
//	}
//
//	/**
//	 * Starts client loader
//	 * 
//	 * @param args unused
//	 */
//	public static void main(String[] args) {
//		System.out.println("Siege Worlds client launcher.");
//		new Loader();
//	}
//
//	/**
//	 * Downloads file from url and places in desired directory
//	 * 
//	 * @param filename  downloaded file name and path
//	 * @param urlString file URL
//	 * @throws MalformedURLException malformed url exception
//	 * @throws IOException           IOException
//	 */
//	public void saveUrl(String filename, String urlString) throws MalformedURLException, IOException {
//		BufferedInputStream in = null;
//		FileOutputStream out = null;
//		try {
//			in = new BufferedInputStream(new URL(urlString).openStream());
//			new File(GAME_DIRECTORY).mkdirs();
//			out = new FileOutputStream(filename);
//			byte data[] = new byte[1024];
//			int count;
//			long curr = 0;
//			long max = new URL(urlString).openConnection().getContentLength();
//			while ((count = in.read(data, 0, 1024)) != -1) {
//				out.write(data, 0, count);
//				curr += count;
//				int pc = (int)((curr * 100) / max);
//				label.setText("Siege worlds is downloading. "+pc+"%");
//				repaint();
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			error = true;
//		}
//		finally {
//			if (in != null)
//				in.close();
//			if (out != null)
//				out.close();
//		}
//	}
//	
//	public void extractTarGZ(InputStream in) throws IOException {
//		label.setText("Extracting game files.");
//		repaint();
//	    GzipCompressorInputStream gzipIn = new GzipCompressorInputStream(in);
//	    try (TarArchiveInputStream tarIn = new TarArchiveInputStream(gzipIn)) {
//	        TarArchiveEntry entry;
//
//	        while ((entry = (TarArchiveEntry) tarIn.getNextEntry()) != null) {
//	            /** If the entry is a directory, create the directory. **/
//	            if (entry.isDirectory()) {
//	                File f = new File(GAME_DIRECTORY + entry.getName());
//	                boolean created = f.mkdir();
//	                if (!created) {
//	                    System.out.printf("Unable to create directory '%s', during extraction of archive contents.\n",
//	                            f.getAbsolutePath());
//	                }
//	            } else {
//	                int count;
//	                byte data[] = new byte[1024];
//	                FileOutputStream fos = new FileOutputStream(GAME_DIRECTORY + entry.getName(), false);
//	                try (BufferedOutputStream dest = new BufferedOutputStream(fos, 1024)) {
//	                    while ((count = tarIn.read(data, 0, 1024)) != -1) {
//	                        dest.write(data, 0, count);
//	                    }
//	                }
//	            }
//	        }
//
//	        System.out.println("Untar completed successfully!");
//	    } catch (Exception e) {
//	    	error = true;
//	    	e.printStackTrace();
//	    }
//	}
//	
//	
//}