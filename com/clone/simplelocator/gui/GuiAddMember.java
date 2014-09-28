package com.clone.simplelocator.gui;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;

import org.lwjgl.input.Keyboard;

import com.clone.simplelocator.LocatorSettings;
import com.clone.simplelocator.MemberList;
import com.clone.simplelocator.SimpleLocator;

public class GuiAddMember extends GuiScreen {
	private String screenTitle = "Add member";
	private GuiScreen parent;
	private MemberList memberList;
	private GuiTextField username;

	public GuiAddMember(GuiScreen parent, MemberList memberList) {
		this.parent = parent;
		this.memberList = memberList;
	}

	public void initGui() {
		Keyboard.enableRepeatEvents(true);

		this.username = new GuiTextField(this.fontRendererObj,
				this.width / 2 - 100, this.height / 2, 200, 20);
		this.username.setFocused(true);

		this.buttonList.add(new GuiButton(100, this.width / 2 - 100,
				this.height / 2 + 88, 200, 20, "Done"));
	}

	public void onGuiClosed() {
		Keyboard.enableRepeatEvents(false);
	}

	protected void keyTyped(char par1, int par2) {
		if (this.username.isFocused()) {
			this.username.textboxKeyTyped(par1, par2);
		}
		if (par2 == 28) {
			if (!this.username.getText().trim().isEmpty()) {
				this.memberList.add(this.username.getText().trim());
			}
			this.mc.displayGuiScreen(this.parent);
		}
	}

	public void updateScreen() {
		this.username.updateCursorCounter();
	}

	protected void actionPerformed(GuiButton par1GuiButton) {
		LocatorSettings settings = SimpleLocator.settings;
		if (par1GuiButton.enabled) {
			if (par1GuiButton.id == 100) {
				if (!this.username.getText().trim().isEmpty()) {
					this.memberList.add(this.username.getText().trim());
				}
				this.mc.displayGuiScreen(this.parent);
			}
			SimpleLocator.saveConfiguration();
		}
	}

	protected void mouseClicked(int par1, int par2, int par3) {
		super.mouseClicked(par1, par2, par3);

		this.username.mouseClicked(par1, par2, par3);
	}

	public void drawScreen(int par1, int par2, float par3) {
		drawDefaultBackground();
		this.username.drawTextBox();
		drawCenteredString(this.fontRendererObj, this.screenTitle,
				this.width / 2, 15, 16777215);
		super.drawScreen(par1, par2, par3);
	}
}

/*
 * Location: C:\Users\Will\Documents\SimpleLocator\SimpleLocator_v2_2.jar
 * 
 * Qualified Name: shadowjay1.forge.simplelocator.gui.GuiAddMember
 * 
 * JD-Core Version: 0.7.0.1
 */