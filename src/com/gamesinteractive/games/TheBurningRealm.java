package com.gamesinteractive.games;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;

import com.gamesinteractive.Misc;
import com.gamesinteractive.Sprite;

public class TheBurningRealm {
	
	public void init() {
		website = new Sprite("website.png");
		websiteh = new Sprite("websiteh.png");
		
		facebook = new Sprite("facebook2.png");
		twitter = new Sprite("twitter.png");
		youtube = new Sprite("youtube.png");
		
		facebookH = new Sprite("facebook2h.png");
		twitterH = new Sprite("twitterh.png");
		youtubeH = new Sprite("youtubeh.png");
	}
	
	public void update() {
		
	}
	
	public void draw(Graphics g) {
		website.draw(g, 645, 704);
		if (website.hover()) {
			websiteh.draw(g, 645-5, 704-5);
		}
		
		int x = 1024;
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
	}

	public void mouseReleased(MouseEvent click) {
		// TODO Auto-generated method stub
		if (website.hover()) {
			Misc.launchURL("www.theburningrealm.com/");
		}
		if (facebook.hover()) {
			Misc.launchURL("https://www.facebook.com/gaming/theburningrealm/");
		}
		if (twitter.hover()) {
			Misc.launchURL("https://twitter.com/TheBurningRealm");
		}
		if (youtube.hover()) {
			Misc.launchURL("https://www.youtube.com/channel/UCYFvxaYJkB2RJi0cViM6mWw/about");
		}
	}
	
	Sprite website;
	Sprite websiteh;
	
	Sprite facebook, facebookH;
	Sprite twitter, twitterH;
	Sprite youtube, youtubeH;

}
