package com.clone.simplelocator.gui;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;

import org.lwjgl.input.Keyboard;

import com.clone.simplelocator.GroupConfiguration;
import com.clone.simplelocator.LocatorSettings;
import com.clone.simplelocator.MemberList;
import com.clone.simplelocator.SimpleLocator;

public class GuiRenameGroup extends GuiScreen {

	private String screenTitle = "Rename Group";
	private GuiScreen parent;
	private GuiTextField groupName;
	private GroupConfiguration group;

	public GuiRenameGroup(GuiScreen parent, GroupConfiguration group) {
		this.parent = parent;
		this.group = group;
	}

	public void initGui() {
		Keyboard.enableRepeatEvents(true);

		this.groupName = new GuiTextField(this.fontRendererObj,
				this.width / 2 - 100, this.height / 2, 200, 20);
		this.groupName.setFocused(true);
		this.buttonList.add(new GuiButton(100, this.width / 2 - 100,
				this.height / 2 + 88, 98, 20, "Done"));
		this.buttonList.add(new GuiButton(101, this.width / 2 + 2,
				this.height / 2 + 88, 98, 20, "Cancel"));
	}

	public void onGuiClosed() {
		Keyboard.enableRepeatEvents(false);
	}

	protected void keyTyped(char par1, int par2) {
		if (this.groupName.isFocused()) {
			this.groupName.textboxKeyTyped(par1, par2);
		}
		if (par2 == 28) {
			if (!this.groupName.getText().trim().isEmpty()) {
				group.setGroupName(this.groupName.getText().trim());
			}
			this.mc.displayGuiScreen(this.parent);
		}
		SimpleLocator.saveConfiguration();
	}

	public void updateScreen() {
		this.groupName.updateCursorCounter();
	}

	protected void actionPerformed(GuiButton par1GuiButton) {
		LocatorSettings settings = SimpleLocator.settings;
		if (par1GuiButton.enabled) {
			if (par1GuiButton.id == 100) {
				if (!this.groupName.getText().trim().isEmpty()) {
					group.setGroupName(this.groupName.getText().trim());
				}
				this.mc.displayGuiScreen(this.parent);
			} else if (par1GuiButton.id == 101) {
				this.mc.displayGuiScreen(this.parent);
			}
			SimpleLocator.saveConfiguration();
		}
	}

	protected void mouseClicked(int par1, int par2, int par3) {
		super.mouseClicked(par1, par2, par3);

		this.groupName.mouseClicked(par1, par2, par3);
	}

	public void drawScreen(int par1, int par2, float par3) {
		drawDefaultBackground();
		this.groupName.drawTextBox();
		drawCenteredString(this.fontRendererObj, this.screenTitle,
				this.width / 2, 15, 16777215);
		super.drawScreen(par1, par2, par3);
	}
}
