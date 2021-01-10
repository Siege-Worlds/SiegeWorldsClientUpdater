package com.gamesinteractive.games;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import com.gamesinteractive.FileDownloader;
import com.gamesinteractive.Misc;
import com.gamesinteractive.Sprite;

public class SiegeWorlds {

	/**
	 * Needed variables
	 */

	public static final String GAME_CLIENT = "http://77.68.78.218/SW/sw_windows_client.tar.gz";
	public static final String ZIP_FILE = System.getProperty("user.home") + "/.siegeworlds/sw_windows_client.tar.gz";

	public static final String VERSION_URL = "http://77.68.78.218/SW/version.txt";
	public static final String VERSION_FILE = System.getProperty("user.home") + "/.siegeworlds/version.txt";

	public static final String GAME_DIRECTORY = System.getProperty("user.home") + "/.siegeworlds/";
	public static final String GAME_EXE = System.getProperty("user.home") + "/.siegeworlds/SWClient.exe";

	public int getCurrentVersion() {
		try {
			int value = -1;
			FileInputStream fis = new FileInputStream(VERSION_FILE);
			InputStreamReader isr = new InputStreamReader(fis);
			BufferedReader b = new BufferedReader(isr);
			value = Integer.parseInt(b.readLine());
			fis.close();
			isr.close();
			b.close();
			return value;
			//return Integer.parseInt(new BufferedReader(new InputStreamReader(new FileInputStream(VERSION_FILE))).readLine());
		} catch (Exception e) {
			return -1;
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

	public void init() {
		playButton = new Sprite("play.png");
		playButtonH = new Sprite("playh.png");
		updateButton = new Sprite("update.png");
		updateButtonH = new Sprite("updateh.png");
		progressBar = new Sprite("progress.png");
		progressbg = new Sprite("progressbg.png");
		updatingButton = new Sprite("updating.png");
		shine = new Sprite("shine.png");
		website = new Sprite("website.png");
		websiteh = new Sprite("websiteh.png");
		telegram = new Sprite("telegram.png");
		telegramH = new Sprite("telegramh.png");
		discord = new Sprite("discord.png");
		discordH = new Sprite("discordH.png");
		
		facebook = new Sprite("facebook2.png");
		twitter = new Sprite("twitter.png");
		youtube = new Sprite("youtube.png");
		
		facebookH = new Sprite("facebook2h.png");
		twitterH = new Sprite("twitterh.png");
		youtubeH = new Sprite("youtubeh.png");
		
		clientVersion = getCurrentVersion();
		newestVersion = getNewestVersion();
		lastVersionCheck = System.currentTimeMillis();
		if (clientVersion < newestVersion) {
			updateRequired = true;
		}
		downloading = false;
		
		downloader = new FileDownloader(ZIP_FILE, GAME_CLIENT, GAME_DIRECTORY);
	}

	public void update() {
		if (System.currentTimeMillis() - lastVersionCheck > 60000) {
			clientVersion = getCurrentVersion();
			newestVersion = getNewestVersion();
			if (clientVersion < newestVersion) {
				updateRequired = true;
			}
		}
		if (downloading) {
			if (downloader.downloadComplete) {
				downloading = false;
				updateRequired = false;
			}
		}
	}

	public void draw(Graphics g) {
		g.setColor(Color.white);
		g.setFont(new Font("Arial", Font.PLAIN, 10));
		g.drawString("Current installed version: " + clientVersion, 230, 710);
		g.drawString("Latest Client version: " + newestVersion, 230, 725);
		g.drawString("Game Directory: " + GAME_DIRECTORY, 230, 740);
		progressbg.draw(g, 385, 652);
		
		website.draw(g, 985, 716);
		if (website.hover()) {
			websiteh.draw(g, 985-5, 716-5);
		}
		
		int x = 1024-106;
		facebook.draw(g, x, 43);
		if (facebook.hover()) {
			facebookH.draw(g,x,43);
		}
		x+= 53;
		twitter.draw(g, x, 43);
		if (twitter.hover()) {
			twitterH.draw(g,x,43);
		}
		x+=53;
		youtube.draw(g, x, 43);
		if (youtube.hover()) {
			youtubeH.draw(g,x,43);
		}
		x+=53;
		telegram.draw(g, x, 43);
		if (telegram.hover()) {
			telegramH.draw(g,x,43);
		}
		x+=53;
		discord.draw(g, x, 43);
		if (discord.hover()) {
			discordH.draw(g,x,43);
		}
		
		if (updateRequired) {
			updateButton.draw(g, 926, 640);
			if (updateButton.hover()) {
				updateButtonH.draw(g, 926, 640);
			}
			if (downloading) {
				updatingButton.draw(g, 926, 640);
			}
			float width = (500 / 100 ) * downloader.percent;
			progressBar.drawClipped(g, 386, 653, (int)width, 35);//(g, 386, 653);
			
			
			//draw progress bar shine
			g.clipRect(386, 653, (int)width, 35);
			shineX += 20;
			shine.draw(g, shineX, 660);
			if (shineX > 2500)
				shineX = 0;
			g.setClip(0, 0, 1200, 750);
			
			
			g.setFont(new Font("Arial", Font.PLAIN, 12));
			g.setColor(Color.white);
			g.drawString(downloader.message, 394, 675);
		} else {
			
			playButton.draw(g, 926, 640);
			if (playButton.hover()) {
				playButtonH.draw(g, 926, 640);
			}
			progressBar.draw(g, 386, 653);
		
			g.setFont(new Font("Arial", Font.PLAIN, 12));
			g.setColor(Color.white);
			g.drawString("Update Complete!", 394, 675);
		}
	}

	public void mouseReleased(MouseEvent click) {
		if (!updateRequired) {
			if (playButton.hover()) {
				try {
					Runtime.getRuntime().exec(GAME_EXE);
				} catch (IOException e) {
					e.printStackTrace();
					downloader.clearDirectory(new File(GAME_DIRECTORY));
					updateRequired = true;
				}
			}
		} else {
			if (updateButton.hover() && !downloading) {
				downloader.start();
				downloading = true;
			}
		}
		if (website.hover()) {
			Misc.launchURL("www.siegeworlds.com/");
		}
		if (facebook.hover()) {
			Misc.launchURL("www.facebook.com/Siege-Worlds-110346624000517/about/?ref=page_internal");
		}
		if (twitter.hover()) {
			Misc.launchURL("www.twitter.com/siege_worlds");
		}
		if (youtube.hover()) {
			Misc.launchURL("www.youtube.com/channel/UCS_czm1uJ5j79b9KzZtgViw");
		}
		if (telegram.hover()) {
			Misc.launchURL("www.t.me/siegeworlds");
		}
		if (discord.hover()) {
			Misc.launchURL("www.discord.gg/MaEqM6jYEX");
		}
	}
	
	boolean updateRequired;
	boolean downloading;
	
	long lastVersionCheck;

	int clientVersion;
	int newestVersion;
	
	int shineX = 0;
	
	//play / update butons with hover
	Sprite playButton;
	Sprite playButtonH;
	Sprite updateButton;
	Sprite updateButtonH;
	Sprite updatingButton;
	Sprite progressBar;
	Sprite progressbg;
	Sprite shine;
	Sprite website;
	Sprite websiteh;
	
	Sprite facebook, facebookH;
	Sprite twitter, twitterH;
	Sprite youtube, youtubeH;
	Sprite telegram, telegramH;
	Sprite discord, discordH;
	
	FileDownloader downloader;
	
}
