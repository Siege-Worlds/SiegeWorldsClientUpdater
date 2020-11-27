package com.gamesinteractive;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.lang.reflect.Method;

public class Misc {

	public static void setTransparency(Graphics g, float value) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, value));
	}
	
	public static void Rect(Graphics g, int x, int y, int w, int h, Color c) {
		//Misc.setTransparency(g, transparency);
		g.setColor(c);
		g.fillRect(x, y, w, h);
		//Misc.setTransparency(g, 1.0f);
	}
	
	public static void launchURL(String url) { 
		String osName = System.getProperty("os.name"); 
		try { 
			if (osName.startsWith("Mac OS")) { 
				Class fileMgr = Class.forName("com.apple.eio.FileManager"); 
				Method openURL = fileMgr.getDeclaredMethod("openURL", new Class[] {String.class}); 
				openURL.invoke(null, new Object[] {url});
			} else if (osName.startsWith("Windows")) 
				Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + url); 
			else { //assume Unix or Linux
				String[] browsers = { "firefox", "opera", "konqueror", "epiphany", "mozilla", "netscape", "safari" }; 
			String browser = null; 
			for (int count = 0; count < browsers.length && browser == null; count++) 
				if (Runtime.getRuntime().exec(new String[] {"which", browsers[count]}).waitFor() == 0)
					browser = browsers[count]; 
			if (browser == null) {
				 throw new Exception("Could not find web browser"); 
			} else
				 Runtime.getRuntime().exec(new String[] {browser, url});
			}
		} catch (Exception e) { 
			//pushMessage("Failed to open URL.", 0, "");
		}
	}
}
