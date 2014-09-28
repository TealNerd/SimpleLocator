package com.clone.simplelocator.gui;

import java.io.File;
import java.util.List;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import org.lwjgl.input.Keyboard;

import com.clone.RadarBro.GuiRadarBroSettings;
import com.clone.simplelocator.LocatorSettings;
import com.clone.simplelocator.SimpleLocator;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;

@SideOnly(Side.CLIENT)
public class GuiLocatorSettings extends GuiScreen {
	private String screenTitle = "Locator settings";
	private GuiButton worldPoliceButton = null;
	private GuiButton broadcastCoordsButton = null;
	private GuiButton renderButton = null;
	private GuiButton showOfflineButton = null;
	private GuiDistanceSlider renderDistance = null;
	private GuiExpirationSlider expirationTime = null;
	private GuiScaleSlider scale = null;
	private GuiOpacitySlider opacity = null;
	private GuiButton groupsButton = null;
	private GuiButton radarButton = null;
	private GuiColoredButton colorButton = null;
	private GuiButton deleteColorButton = null;
	private GuiButton altListButton = null;

	private static LocatorSettings settings = SimpleLocator.settings;
	
	public void initGui() {
		Keyboard.enableRepeatEvents(true);

		this.worldPoliceButton = new GuiButton(1, this.width / 2 - 205,	this.height / 2 - 60, 200, 20, "");
		this.broadcastCoordsButton = new GuiButton(2, this.width / 2 - 205, this.height / 2 - 36, 200, 20, "Broadcast Coords on Tag");
		this.renderButton = new GuiButton(3, this.width / 2 - 205, this.height / 2 - 12, 200, 20, "");
		this.showOfflineButton = new GuiButton(6, this.width / 2 - 205, this.height / 2 + 12, 200, 20, "");
		this.renderDistance = new GuiDistanceSlider(4, this.width / 2 + 5, this.height / 2 - 60, 15000.0D, true, "Max render distance",	1.0F);
		this.expirationTime = new GuiExpirationSlider(7, this.width / 2 + 5, this.height / 2 - 36, 60.0D, false, "Expiration time", 1.0F);
		this.scale = new GuiScaleSlider(8, this.width / 2 + 5, this.height / 2 - 12, 2.0D, false, "Scale", 1.0F);
		this.opacity = new GuiOpacitySlider(11, this.width / 2 + 5,	this.height / 2 + 12, 1.0F, false, "Opacity", 1.0F);
		this.colorButton = new GuiColoredButton(9, this.width / 2 - 205, this.height / 2 + 36, 200, 20, "Randomize default color");
		this.deleteColorButton = new GuiButton(10, this.width / 2 + 5, this.height / 2 + 36, 200, 20, "Delete default color");
		this.groupsButton = new GuiButton(5, this.width / 2 - 150, this.height / 2 + 62, 98, 20, "Group settings");
		this.radarButton = new GuiButton(12, this.width / 2 + 51, this.height / 2 + 62, 98, 20, "Radar Settings");
		this.altListButton = new GuiButton(13, this.width /2 - 49, this.height / 2 + 62, 98, 20, "Alt List");

		updateButtons();

		this.buttonList.add(this.worldPoliceButton);
		this.buttonList.add(this.broadcastCoordsButton);
		this.buttonList.add(this.renderButton);
		this.buttonList.add(this.showOfflineButton);
		this.buttonList.add(this.renderDistance);
		this.buttonList.add(this.expirationTime);
		this.buttonList.add(this.scale);
		this.buttonList.add(this.opacity);
		this.buttonList.add(this.groupsButton);
		this.buttonList.add(this.radarButton);
		this.buttonList.add(this.colorButton);
		this.buttonList.add(this.deleteColorButton);
		this.buttonList.add(this.altListButton);

		this.buttonList.add(new GuiButton(100, this.width / 2 - 100, this.height / 2 + 88, 200, 20, "Done"));
		
		this.altListButton.enabled = false;
		
}

	protected void actionPerformed(GuiButton par1GuiButton) {
		settings.load(SimpleLocator.configFile);
		if (par1GuiButton.enabled) {
			if (par1GuiButton.id == 1) {
				settings.setWorldPoliceEnabled(!settings.isWorldPoliceEnabled());
				updateButtons();
			}
			if (par1GuiButton.id == 2) {
				settings.setBroadcastCoords(!settings.getBroadcastCoords());
				updateButtons();
			}
			if (par1GuiButton.id == 3) {
				settings.setRenderEnabled(!settings.isRenderEnabled());
				updateButtons();
			}
			if (par1GuiButton.id == 6) {
				settings.setShowExactOffline(!settings.isShowExactOfflineEnabled());
				updateButtons();
			}
			if (par1GuiButton.id == 5) {
				Minecraft.getMinecraft().displayGuiScreen(new GuiGroups(null));
			}
			if (par1GuiButton.id == 9) {
				settings.randomizeDefaultColor();
				this.colorButton.setColor(settings.getDefaultColor());
			}
			if (par1GuiButton.id == 10) {
				settings.setDefaultColor(null);
				this.colorButton.setColor(settings.getDefaultColor());
			}
			if (par1GuiButton.id == 12) {
				 Minecraft.getMinecraft().displayGuiScreen(new
				 GuiRadarBroSettings(new GuiLocatorSettings()));
			}
			if (par1GuiButton.id == 13)
			{
				//Minecraft.getMinecraft().displayGuiScreen(new GuiAlts(null));
			}
			if (par1GuiButton.id == 100) {
				this.mc.displayGuiScreen(null);
			}
			SimpleLocator.saveConfiguration();
		}
	}

	private void updateButtons() {
		this.worldPoliceButton.displayString = ("World Police Siren: " + (settings.isWorldPoliceEnabled() ? "enabled" : "disabled"));
		this.broadcastCoordsButton.displayString = ("Broadcast Coords on Tag: " + (settings.getBroadcastCoords() ? "enabled" : "disabled"));
		this.renderButton.displayString = ("Render locations: " + (settings.isRenderEnabled() ? "enabled" : "disabled"));
		this.showOfflineButton.displayString = ("Exact offline locations: " + (settings.isShowExactOfflineEnabled() ? "enabled" : "disabled"));
		this.colorButton.setColor(settings.getDefaultColor());
	}

	protected void mouseClicked(int par1, int par2, int par3) {
		super.mouseClicked(par1, par2, par3);
	}

	public void drawScreen(int par1, int par2, float par3) {
		drawDefaultBackground();
		drawCenteredString(this.fontRendererObj, this.screenTitle, this.width / 2, 15, 16777215);
		super.drawScreen(par1, par2, par3);
	}
}

/*
 * Location: C:\Users\Will\Documents\SimpleLocator\SimpleLocator_v2_2.jar
 * 
 * Qualified Name: shadowjay1.forge.simplelocator.gui.GuiLocatorSettings
 * 
 * JD-Core Version: 0.7.0.1
 */