package com.clone.simplelocator.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

import org.lwjgl.input.Keyboard;

import com.clone.simplelocator.GroupList;
import com.clone.simplelocator.LocatorSettings;
import com.clone.simplelocator.MemberList;
import com.clone.simplelocator.SimpleLocator;

@SideOnly(Side.CLIENT)
public class GuiMembers extends GuiScreen {
	private GuiScreen parentScreen;
	private GuiMemberSlot memberSlotContainer;
	private int selectedMember = -1;
	private GuiButton deleteButton;
	private int ticksOpened;
	private MemberList memberList;

	public GuiMembers(GuiScreen par1GuiScreen, MemberList memberList) {
		this.parentScreen = par1GuiScreen;
		this.memberList = memberList;
	}

	public void initGui() {
		Keyboard.enableRepeatEvents(true);
		this.buttonList.clear();

		this.memberSlotContainer = new GuiMemberSlot(this);

		initGuiControls();
	}

	public void initGuiControls() {
		this.buttonList.add(new GuiButton(10, this.width / 2 - 107,
				this.height - 40, 70, 20, "Add"));
		this.buttonList.add(this.deleteButton = new GuiButton(12,
				this.width / 2 - 35, this.height - 40, 70, 20, "Delete"));
		this.buttonList.add(new GuiButton(14, this.width / 2 + 37,
				this.height - 40, 70, 20, "Cancel"));
		updateButtons();
	}

	public void updateScreen() {
		super.updateScreen();
		this.ticksOpened += 1;
	}

	public void onGuiClosed() {
		Keyboard.enableRepeatEvents(false);
	}

	protected void actionPerformed(GuiButton par1GuiButton) {
		if (par1GuiButton.enabled) {
			if (par1GuiButton.id == 12) {
				if ((this.selectedMember > -1)
						&& (this.selectedMember < this.memberList.countGroups())) {
					this.memberList.remove(this.selectedMember);
				}
				this.selectedMember = -1;
			} else if (par1GuiButton.id == 10) {
				this.mc.displayGuiScreen(new GuiAddMember(this, this.memberList));
				this.selectedMember = -1;
			} else if (par1GuiButton.id == 11) {
				if ((this.selectedMember <= -1)
						|| (this.selectedMember >= this.memberList
								.countGroups())) {
				}
			} else if (par1GuiButton.id == 14) {
				this.mc.displayGuiScreen(this.parentScreen);
			} else {
				this.memberSlotContainer.actionPerformed(par1GuiButton);
			}
			updateButtons();
			SimpleLocator.saveConfiguration();
		}
	}

	protected void keyTyped(char par1, int par2) {
		int j = this.selectedMember;
		if ((isShiftKeyDown()) && (par2 == 200)) {
			if ((j > 0) && (j < this.memberList.countGroups())) {
				this.memberList.swapGroups(j, j - 1);
				this.selectedMember -= 1;
				if (j < this.memberList.countGroups() - 1) {
					this.memberSlotContainer.scrollBy(-this.memberSlotContainer
							.getSlotHeight());
				}
			}
		} else if ((isShiftKeyDown()) && (par2 == 208)) {
			if (((j >= 0 ? 1 : 0) & (j < this.memberList.countGroups() - 1 ? 1
					: 0)) != 0) {
				this.memberList.swapGroups(j, j + 1);
				this.selectedMember += 1;
				if (j > 0) {
					this.memberSlotContainer.scrollBy(this.memberSlotContainer
							.getSlotHeight());
				}
			}
		} else if ((par2 != 28) && (par2 != 156)) {
			super.keyTyped(par1, par2);
		} else {
			actionPerformed((GuiButton) this.buttonList.get(2));
		}
	}

	public void drawScreen(int par1, int par2, float par3) {
		this.memberSlotContainer.drawScreen(par1, par2, par3);
		drawCenteredString(this.fontRendererObj, "Edit members",
				this.width / 2, 20, 16777215);
		super.drawScreen(par1, par2, par3);
	}

	private void joinServer(int par1) {
		if (par1 >= this.memberList.countGroups()) {
			par1 -= this.memberList.countGroups();
		}
	}

	public void updateButtons() {
		boolean flag = (this.selectedMember >= 0)
				&& (this.selectedMember < SimpleLocator.settings.getGroups()
						.size());
		this.deleteButton.enabled = flag;
	}

	static MemberList getMemberList(GuiMembers GuiMembers) {
		return GuiMembers.memberList;
	}

	static int getSelectedGroup(GuiMembers GuiMembers) {
		return GuiMembers.selectedMember;
	}

	static int getAndSetSelectedGroup(GuiMembers GuiMembers, int par1) {
		return GuiMembers.selectedMember = par1;
	}

	static GuiButton getButtonDelete(GuiMembers GuiMembers) {
		return GuiMembers.deleteButton;
	}

	static int getTicksOpened(GuiMembers GuiMembers) {
		return GuiMembers.ticksOpened;
	}
}

/*
 * Location: C:\Users\Will\Documents\SimpleLocator\SimpleLocator_v2_2.jar
 * 
 * Qualified Name: shadowjay1.forge.simplelocator.gui.GuiMembers
 * 
 * JD-Core Version: 0.7.0.1
 */