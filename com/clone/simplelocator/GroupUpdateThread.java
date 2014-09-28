package com.clone.simplelocator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class GroupUpdateThread extends Thread {
	private GroupConfiguration group;

	public GroupUpdateThread(GroupConfiguration group) {
		this.group = group;
	}

	public void run() {
		String sURL = this.group.getUpdateURL();
		try {
			URL url = new URL(sURL);
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					url.openStream()));

			ArrayList<String> members = this.group.getUsernames();
			members.clear();
			String line;
			while ((line = reader.readLine()) != null) {
				line = line.trim();
				if (!line.isEmpty()) {
					members.add(line);
				}
			}
			reader.close();
		} catch (MalformedURLException e) {
			this.group.setUpdateURL(null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

/*
 * Location: C:\Users\Will\Documents\SimpleLocator\SimpleLocator_v2_2.jar
 * 
 * Qualified Name: shadowjay1.forge.simplelocator.GroupUpdateThread
 * 
 * JD-Core Version: 0.7.0.1
 */