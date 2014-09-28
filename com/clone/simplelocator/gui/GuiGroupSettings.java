package com.clone.simplelocator.gui;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;

import org.lwjgl.input.Keyboard;

import com.clone.simplelocator.GroupConfiguration;
import com.clone.simplelocator.GroupUpdateThread;
import com.clone.simplelocator.LocatorSettings;
import com.clone.simplelocator.SimpleLocator;

public class GuiGroupSettings extends GuiScreen {
	private String screenTitle = "Group settings";
	private GuiScreen parent;
	private GroupConfiguration group;
	private GuiButton trackOnlineButton = null;
	private GuiDistanceOverrideSlider renderDistance = null;
	private GuiExpirationOverrideSlider expirationTime = null;
	private GuiColoredButton groupColorButton = null;
	private GuiButton deleteColorButton = null;
	private GuiTextField updateURLField = null;
	private GuiButton updateNowButton = null;
	private GuiButton enableSound = null;

	public GuiGroupSettings(GuiScreen parent, GroupConfiguration group) {
		this.parent = parent;
		this.group = group;
	}

	public void initGui() {
		Keyboard.enableRepeatEvents(true);

		this.trackOnlineButton = new GuiButton(1, this.width / 2 - 100,
				this.height / 2 - 95, 200, 20, "");
		this.enableSound = new GuiButton(7, this.width / 2 - 100,
				this.height / 2 - 72, 200, 20, "");
		this.renderDistance = new GuiDistanceOverrideSlider(this.group, 2,
				this.width / 2 - 100, this.height / 2 - 48, 15000.0D, true,
				"Max render distance", 1.0F);
		this.expirationTime = new GuiExpirationOverrideSlider(this.group, 6,
				this.width / 2 - 100, this.height / 2 - 24, 60.0D, false,
				"Expiration time", 1.0F);
		this.groupColorButton = new GuiColoredButton(3, this.width / 2 - 100,
				this.height / 2, 99, 20, "Randomize color");
		this.deleteColorButton = new GuiButton(4, this.width / 2 + 1,
				this.height / 2, 99, 20, "Delete color");
		this.updateURLField = new GuiTextField(this.fontRendererObj,
				this.width / 2 - 100, this.height / 2 + 48, 128, 20);
		this.updateURLField.setMaxStringLength(200);
		String url = this.group.getUpdateURL();
		if (url != null) {
			this.updateURLField.setText(url);
		}
		this.updateNowButton = new GuiButton(5, this.width / 2 + 30,
				this.height / 2 + 48, 70, 20, "Update now");

		this.groupColorButton.setColor(this.group.getColor());

		updateButtons();

		this.buttonList.add(this.trackOnlineButton);
		this.buttonList.add(this.enableSound);
		this.buttonList.add(this.renderDistance);
		this.buttonList.add(this.expirationTime);
		this.buttonList.add(this.groupColorButton);
		this.buttonList.add(this.deleteColorButton);
		this.buttonList.add(this.updateNowButton);

		this.buttonList.add(new GuiButton(100, this.width / 2 - 100,
				this.height / 2 + 88, 200, 20, "Done"));
	}

	public void onGuiClosed() {
		Keyboard.enableRepeatEvents(false);
	}

	protected void keyTyped(char par1, int par2) {
		if (this.updateURLField.isFocused()) {
			this.updateURLField.textboxKeyTyped(par1, par2);

			this.group.setUpdateURL(this.updateURLField.getText());
		}
	}

	public void updateScreen() {
		this.updateURLField.updateCursorCounter();
	}

	protected void actionPerformed(GuiButton par1GuiButton) {
		LocatorSettings settings = SimpleLocator.settings;
		if (par1GuiButton.enabled) {
			if (par1GuiButton.id == 1) {
				this.group.setTrackingOnline(!this.group.isTrackingOnline());
				updateButtons();
			}
			if (par1GuiButton.id == 3) {
				this.group.randomizeColor();
				this.groupColorButton.setColor(this.group.getColor());
			}
			if (par1GuiButton.id == 4) {
				this.group.setColor(null);
				this.groupColorButton.setColor(this.group.getColor());
			}
			if (par1GuiButton.id == 5) {
				new GroupUpdateThread(this.group).start();
			}
			if (par1GuiButton.id == 100) {
				this.mc.displayGuiScreen(this.parent);
			}
			if (par1GuiButton.id == 7) {
				this.group.setSoundEnabled(!this.group.isSoundEnabled());
				updateButtons();
			}
			SimpleLocator.saveConfiguration();
		}
	}

	protected void mouseClicked(int par1, int par2, int par3) {
		super.mouseClicked(par1, par2, par3);

		this.updateURLField.mouseClicked(par1, par2, par3);
	}

	private void updateButtons() {
		LocatorSettings settings = SimpleLocator.settings;

		this.trackOnlineButton.displayString = ("Track log-ins/log-outs: " + (this.group
				.isTrackingOnline() ? "enabled" : "disabled"));
		this.enableSound.displayString = ("Enable Sound on Snitch: " + (this.group
				.isSoundEnabled() ? "enabled" : "disabled"));
	}

	public void drawScreen(int par1, int par2, float par3) {
		drawDefaultBackground();
		drawCenteredString(this.fontRendererObj, this.screenTitle,
				this.width / 2, 15, 16777215);
		super.drawScreen(par1, par2, par3);
		String s = "Auto-update URL";
		this.fontRendererObj.drawString(s, this.width / 2
				- this.fontRendererObj.getStringWidth(s) / 2,
				this.height / 2 + 7 + 24, -1);
		this.updateURLField.drawTextBox();
	}
}

/*
 * Location: C:\Users\Will\Documents\SimpleLocator\SimpleLocator_v2_2.jar
 * 
 * Qualified Name: shadowjay1.forge.simplelocator.gui.GuiGroupSettings
 * 
 * JD-Core Version: 0.7.0.1
 */