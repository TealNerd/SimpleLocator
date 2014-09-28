package com.clone.simplelocator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;

public class IngameInvList extends Gui{
	
	Minecraft mc = Minecraft.getMinecraft();
	Pattern invPattern = Pattern.compile("name: ([A-Za-z0-9]+?), health: ([0-9]+?), helm: ([0-9]+?), chest: ([0-9]+?), legs: ([0-9]+?), boots: ([0-9]+?), second: (0|1), pearls: ([0-9]+?), healths: ([0-9]+?), fire: ([0-9]+?), speed: ([0-9]+?), strength: ([0-9]+?), regen: ([0-9]+?)");
	String lowHealth;
	String lowPots;
	String lowDura;
	String lowPearls;
	int[] lowPearlsVal = new int[12];
	int[] lowPotsVal = new int[12];
	int[] lowDuraVal = new int[12];
	int[] lowHealthVal = new int[12];
	boolean dataIsReady = false;
	int loop = 300;
	
	public IngameInvList(Minecraft mc) {
		super();
		this.mc = mc;
	}
	
	@SubscribeEvent
	public void onRenderInvList(RenderGameOverlayEvent e) {
		if(e.type != ElementType.PLAYER_LIST) {
			return;
		}
		ScaledResolution res = e.resolution;
		
		loop++;		
			if (loop >= 300) {
				readyData();
				loop = 0;
			}
		
		drawPlayerList(res.getScaledWidth(), res.getScaledHeight());
		
	}
	
	public void drawPlayerList(int width, int height) {
		FontRenderer fr = mc.fontRenderer;
		int columns = 4;
		int rows = 14;
		int columnWidth = 100;
		int left = (width - columns * columnWidth) / 2 + 1;
		byte border = 5;
		drawRect(left - 1, border - 1, left + columnWidth * columns, border + 9 * rows, Integer.MIN_VALUE);
		for(int i = 0; i < 56; i++) {
			int xPos = left + i % columns * columnWidth;
			int yPos = border + i / columns * 9;
			drawRect(xPos, yPos, xPos + columnWidth - 1, yPos + 8, 553648127);
			if(i == 0) {
				String s = lowHealth;
				fr.drawString(s, xPos + 1, yPos, 16777215);
			}
			if(i == 1) {
				fr.drawString(lowPots, xPos + 1, yPos, 16777215);
			}
			if(i == 2) {
				fr.drawString(lowDura, xPos + 1, yPos, 16777215);
			}
			if(i == 3) {
				fr.drawString(lowPearls, xPos + 1, yPos, 16777215);
			}
			if(i == 4) {
				fr.drawString("Health: " + lowHealthVal[0], xPos + 1, yPos, 16777215);
			}
			if(i == 5) {
				fr.drawString("Health: " + lowPotsVal[0], xPos + 1, yPos, 16777215);
			}
			if(i == 6) {
				fr.drawString("Health: " + lowDuraVal[0], xPos + 1, yPos, 16777215);
			}
			if(i == 7) {
				fr.drawString("Health: " + lowPearlsVal[0], xPos + 1, yPos, 16777215);
			}
			if(i == 8) {
				fr.drawString("Helm: " + lowHealthVal[1], xPos + 1, yPos, 16777215);
			}
			if(i == 9) {
				fr.drawString("Helm: " + lowPotsVal[1], xPos + 1, yPos, 16777215);
			}
			if(i == 10) {
				fr.drawString("Helm: " + lowDuraVal[1], xPos + 1, yPos, 16777215);
			}
			if(i == 11) {
				fr.drawString("Helm: " + lowPearlsVal[1], xPos + 1, yPos, 16777215);
			}
			if(i == 12) {
				fr.drawString("Chestplate: " + lowHealthVal[2], xPos + 1, yPos, 16777215);
			}
			if(i == 13) {
				fr.drawString("Chestplate: " + lowPotsVal[2], xPos + 1, yPos, 16777215);
			}
			if(i == 14) {
				fr.drawString("Chestplate: " + lowDuraVal[2], xPos + 1, yPos, 16777215);
			}
			if(i == 15) {
				fr.drawString("Chestplate: " + lowPearlsVal[2], xPos + 1, yPos, 16777215);
			}
			if(i == 16) {
				fr.drawString("Legs: " + lowHealthVal[3], xPos + 1, yPos, 16777215);
			}
			if(i == 17) {
				fr.drawString("Legs: " + lowPotsVal[3], xPos + 1, yPos, 16777215);
			}
			if(i == 18) {
				fr.drawString("Legs: " + lowDuraVal[3], xPos + 1, yPos, 16777215);
			}
			if(i == 19) {
				fr.drawString("Legs: " + lowPearlsVal[3], xPos + 1, yPos, 16777215);
			}
			if(i == 20) {
				fr.drawString("Boots: " + lowHealthVal[4], xPos + 1, yPos, 16777215);
			}
			if(i == 21) {
				fr.drawString("Boots: " + lowPotsVal[4], xPos + 1, yPos, 16777215);
			}
			if(i == 22) {
				fr.drawString("Boots: " + lowDuraVal[4], xPos + 1, yPos, 16777215);
			}
			if(i == 23) {
				fr.drawString("Boots: " + lowPearlsVal[4], xPos + 1, yPos, 16777215);
			}
			if(i == 24) {
				boolean b = false;
				if(lowHealthVal[5] == 1) {
					b = true;
				}
				fr.drawString("Second: " + b, xPos + 1, yPos, 16777215);
			}
			if(i == 25) {
				boolean b = false;
				if(lowPotsVal[5] == 1) {
					b = true;
				}
				fr.drawString("Second: " + b, xPos + 1, yPos, 16777215);
			}
			if(i == 26) {
				boolean b = false;
				if(lowDuraVal[5] == 1) {
					b = true;
				}
				fr.drawString("Second: " + b, xPos + 1, yPos, 16777215);
			}
			if(i == 27) {
				boolean b = false;
				if(lowPearlsVal[5] == 1) {
					b = true;
				}
				fr.drawString("Second: " + b, xPos + 1, yPos, 16777215);
			}
			if(i == 28) {
				fr.drawString("Pearls: " + lowHealthVal[6], xPos + 1, yPos, 16777215);
			}
			if(i == 29) {
				fr.drawString("Pearls: " + lowPotsVal[6], xPos + 1, yPos, 16777215);
			}
			if(i == 30) {
				fr.drawString("Pearls: " + lowDuraVal[6], xPos + 1, yPos, 16777215);
			}
			if(i == 31) {
				fr.drawString("Pearls: " + lowPearlsVal[6], xPos + 1, yPos, 16777215);
			}
			if(i == 32) {
				fr.drawString("Health Pots: " + lowHealthVal[7], xPos + 1, yPos, 16777215);
			}
			if(i == 33) {
				fr.drawString("Health Pots: " + lowPotsVal[7], xPos + 1, yPos, 16777215);
			}
			if(i == 34) {
				fr.drawString("Health Pots: " + lowDuraVal[7], xPos + 1, yPos, 16777215);
			}
			if(i == 35) {
				fr.drawString("Health Pots: " + lowPearlsVal[7], xPos + 1, yPos, 16777215);
			}
			if(i == 36) {
				fr.drawString("Fire Res: " + lowHealthVal[8], xPos + 1, yPos, 16777215);
			}
			if(i == 37) {
				fr.drawString("Fire Res: " + lowPotsVal[8], xPos + 1, yPos, 16777215);
			}
			if(i == 38) {
				fr.drawString("Fire Res: " + lowDuraVal[8], xPos + 1, yPos, 16777215);
			}
			if(i == 39) {
				fr.drawString("Fire Res: " + lowPearlsVal[8], xPos + 1, yPos, 16777215);
			}
			if(i == 40) {
				fr.drawString("Speed Pots: " + lowHealthVal[9], xPos + 1, yPos, 16777215);
			}
			if(i == 41) {
				fr.drawString("Speed Pots: " + lowPotsVal[9], xPos + 1, yPos, 16777215);
			}
			if(i == 42) {
				fr.drawString("Speed Pots: " + lowDuraVal[9], xPos + 1, yPos, 16777215);
			}
			if(i == 43) {
				fr.drawString("Speed Pots: " + lowPearlsVal[9], xPos + 1, yPos, 16777215);
			}
			if(i == 44) {
				fr.drawString("Strength Pots: " + lowHealthVal[10], xPos + 1, yPos, 16777215);
			}
			if(i == 45) {
				fr.drawString("Strength Pots: " + lowPotsVal[10], xPos + 1, yPos, 16777215);
			}
			if(i == 46) {
				fr.drawString("Strength Pots: " + lowDuraVal[10], xPos + 1, yPos, 16777215);
			}
			if(i == 47) {
				fr.drawString("Strength Pots: " + lowPearlsVal[10], xPos + 1, yPos, 16777215);
			}
			if(i == 48) {
				fr.drawString("Regen Pots: " + lowHealthVal[11], xPos + 1, yPos, 16777215);
			}
			if(i == 49) {
				fr.drawString("Regen Pots: " + lowPotsVal[11], xPos + 1, yPos, 16777215);
			}
			if(i == 50) {
				fr.drawString("Regen Pots: " + lowDuraVal[11], xPos + 1, yPos, 16777215);
			}
			if(i == 51) {
				fr.drawString("Regen Pots: " + lowPearlsVal[11], xPos + 1, yPos, 16777215);
			}
		}
	}
	
	public void readyData() {
		System.out.println("Readying Data");
		String data = WebInterfacer.getInvData();
		if(data != null) {
			String[] invArray = data.split("<br>");
			int players = invArray.length;
			String[] names = new String[players];
			int[] health = new int[players];
			int[] pots = new int[players];
			int[] dura = new int[players];
			int[] pearls = new int[players];
			String g2 = null;
			String g9 = null;
			String g3 = null;
			String g4 = null;
			String g5 = null;
			String g6 = null;
			String g7 = null;
			String g8 = null;
			for(int i = 0; i < players; i++) {
				String s = invArray[i];
				Matcher invMatcher = invPattern.matcher(s);
				while(invMatcher.find()) {
					g2 = invMatcher.group(2);
					g3 = invMatcher.group(3);
					g4 = invMatcher.group(4);
					g5 = invMatcher.group(5);
					g6 = invMatcher.group(6);
					g7 = invMatcher.group(7);
					g8 = invMatcher.group(8);
					g9 = invMatcher.group(9);
					names[i] = invMatcher.group(1);
				}
				health[i] = Integer.parseInt(g2);
				pots[i] = Integer.parseInt(g9);
				pearls[i] = Integer.parseInt(g8);
				int helm = Integer.parseInt(g3);
				int chest = Integer.parseInt(g4);
				int legs = Integer.parseInt(g5);
				int boots = Integer.parseInt(g6);
				int total = helm + chest + boots + legs;
				dura[i] = total;
			}
			int healthIndex = getLowest(health);
			int potsIndex = getLowest(pots);
			int duraIndex = getLowest(dura);
			int pearlIndex = getLowest(pearls);
			
			lowHealth = names[healthIndex];
			lowPots = names[potsIndex];
			lowDura = names[duraIndex];
			lowPearls = names[pearlIndex];
			
			String lowHealthValString = invArray[healthIndex];
			String lowPotsValString = invArray[potsIndex];
			String lowDuraValString = invArray[duraIndex];
			String lowPearlValString = invArray[pearlIndex];
			
			Matcher healthMatcher = invPattern.matcher(lowHealthValString);
			Matcher potsMatcher = invPattern.matcher(lowPotsValString);
			Matcher duraMatcher = invPattern.matcher(lowDuraValString);
			Matcher pearlMatcher = invPattern.matcher(lowPearlValString);
				while(healthMatcher.find()) {
					for(int i = 0; i < 12; i++) {
						lowHealthVal[i] = Integer.parseInt(healthMatcher.group(i + 2));
					}
				}
				while(potsMatcher.find()) {
					for(int i = 0; i < 12; i++) {
						lowPotsVal[0] = Integer.parseInt(potsMatcher.group(2));
					}
				}
				while(duraMatcher.find()) {
					for(int i = 0; i < 12; i++) {
						lowDuraVal[0] = Integer.parseInt(duraMatcher.group(2));
					}
				}
				while(pearlMatcher.find()) {
					for(int i = 0; i < 12; i++) {
						lowPearlsVal[0] = Integer.parseInt(pearlMatcher.group(2));	
					}
				}
			}	
	}
	
	public int getLowest(int[] array) {
		int index = 0;
		int prevint = 500000;
		for(int i = 0; i < array.length; i++) {
			if(array[i] < prevint) {
				prevint = array[i];
				index = i;
			}
		}
		return index;
	}
}
