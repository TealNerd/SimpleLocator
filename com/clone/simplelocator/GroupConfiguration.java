package com.clone.simplelocator;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

public class GroupConfiguration {
	private ArrayList<String> usernames = new ArrayList();
	private int maxViewDistanceOverride = 2147483647;
	private int expirationTimeOverride = 1800000;
	private boolean trackOnline = false;
	private boolean enableSound = false;
	private Color groupColor = null;
	private String groupName = "Group";
	private String updateURL = "";

	public ArrayList<String> getUsernames() {
		if (this.usernames == null) {
			this.usernames = new ArrayList();
		}
		return this.usernames;
	}

	public int getMaxViewDistanceOverride() {
		return this.maxViewDistanceOverride;
	}

	public void setMaxViewDistanceOverride(int value) {
		this.maxViewDistanceOverride = value;
	}

	public int getExpirationTimeOverride() {
		return this.expirationTimeOverride;
	}

	public void setExpirationTimeOverride(int value) {
		this.expirationTimeOverride = value;
	}

	public boolean isSoundEnabled() {
		return this.enableSound;
	}

	public void setSoundEnabled(boolean sound) {
		this.enableSound = sound;
	}

	public boolean isTrackingOnline() {
		return this.trackOnline;
	}

	public void setTrackingOnline(boolean value) {
		this.trackOnline = value;
	}

	public Color getColor() {
		return this.groupColor;
	}

	public void setColor(Color groupColor) {
		this.groupColor = groupColor;
	}

	public void randomizeColor() {
		Random random = new Random();
		this.groupColor = LocatorSettings.defaultColors[random
				.nextInt(LocatorSettings.defaultColors.length)];
	}

	public String getUpdateURL() {
		return this.updateURL;
	}

	public void setUpdateURL(String url) {
		this.updateURL = url;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getGroupName() {
		return this.groupName;
	}

	public String getName() {
		String shortenedName = this.groupName.length() > 20 ? this.groupName.substring(0, 18) + "..." : this.groupName;
		return shortenedName;
	}

	public String getConfigSummary()
	/* :0: */{
		/* :1:94 */String shortenedURL = this.updateURL.length() > 20 ? this.updateURL
				.substring(0, 18) + "..."
				: this.updateURL;
		/* :2: */
		/* :3:96 */return shortenedURL;
		/* :4: */}
	/* :5: */
}

/*
 * Location: C:\Users\Will\Documents\SimpleLocator\SimpleLocator_v2_2.jar
 * 
 * Qualified Name: shadowjay1.forge.simplelocator.GroupConfiguration
 * 
 * JD-Core Version: 0.7.0.1
 */