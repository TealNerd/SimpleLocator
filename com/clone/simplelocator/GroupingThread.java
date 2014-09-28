package com.clone.simplelocator;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;

public class GroupingThread extends Thread {
	public static List<LocationGroup> groups = new ArrayList();

	public void run() {
		try {
			for (;;) {
				if (Minecraft.getMinecraft().thePlayer != null) {
					synchronized (groups) {
						groups = GroupingUtils.getGroups(
								Minecraft.getMinecraft().thePlayer,
								LocatorListener.locations);
					}
				}
				Thread.sleep(500L);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

/*
 * Location: C:\Users\Will\Documents\SimpleLocator\SimpleLocator_v2_2.jar
 * 
 * Qualified Name: shadowjay1.forge.simplelocator.GroupingThread
 * 
 * JD-Core Version: 0.7.0.1
 */