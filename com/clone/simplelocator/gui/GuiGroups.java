package com.clone.simplelocator.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

import org.lwjgl.input.Keyboard;

import com.clone.simplelocator.GroupConfiguration;
import com.clone.simplelocator.GroupList;
import com.clone.simplelocator.LocatorSettings;
import com.clone.simplelocator.MemberList;
import com.clone.simplelocator.SimpleLocator;

@SideOnly(Side.CLIENT)
public class GuiGroups extends GuiScreen {
	private GuiScreen parentScreen;
	private GuiGroupSlot groupSlotContainer;
	private GroupList groupList;
	private GroupConfiguration group;
	private int selectedGroup = -1;
	private GuiButton editMembersButton;
	private GuiButton renameGroupButton;
	private GuiButton editButton;
	private GuiButton deleteButton;
	private int ticksOpened;

	public GuiGroups(GuiScreen par1GuiScreen) {
		this.parentScreen = par1GuiScreen;
	}

	public void initGui() {
		this.groupList = SimpleLocator.settings.getGroups();

		Keyboard.enableRepeatEvents(true);
		this.buttonList.clear();

		this.groupSlotContainer = new GuiGroupSlot(this);

		initGuiControls();
	}

	public void initGuiControls() {
		this.buttonList.add(this.editMembersButton = new GuiButton(13,
				this.width / 2, this.height - 52, 143, 20, "Edit members"));
		this.buttonList
				.add(this.renameGroupButton = new GuiButton(15,
						this.width / 2 - 143, this.height - 52, 143, 20,
						"Rename Group"));
		this.buttonList.add(new GuiButton(10, this.width / 2 - 143,
				this.height - 28, 70, 20, "Add"));
		this.buttonList.add(this.editButton = new GuiButton(11,
				this.width / 2 - 71, this.height - 28, 70, 20, "Edit"));
		this.buttonList.add(this.deleteButton = new GuiButton(12,
				this.width / 2 + 1, this.height - 28, 70, 20, "Delete"));
		this.buttonList.add(new GuiButton(14, this.width / 2 + 73,
				this.height - 28, 70, 20, "Cancel"));
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
				if ((this.selectedGroup > -1)
						&& (this.selectedGroup < this.groupList.size())) {
					this.groupList.remove(this.selectedGroup);
				}
				this.selectedGroup = -1;
			} else if (par1GuiButton.id == 10) {
				this.groupList.add(new GroupConfiguration());
				this.selectedGroup = (this.groupList.size() - 1);
			} else if (par1GuiButton.id == 11) {
				if ((this.selectedGroup > -1)
						&& (this.selectedGroup < this.groupList.size())) {
					Minecraft.getMinecraft().displayGuiScreen(
							new GuiGroupSettings(this,
									(GroupConfiguration) this.groupList
											.get(this.selectedGroup)));
				}
			} else if (par1GuiButton.id == 13) {
				if ((this.selectedGroup > -1)
						&& (this.selectedGroup < this.groupList.size())) {
					Minecraft.getMinecraft().displayGuiScreen(new GuiMembers(this, new MemberList(((GroupConfiguration) this.groupList.get(this.selectedGroup)).getUsernames())));
				}
			} else if (par1GuiButton.id == 14) {
				this.mc.displayGuiScreen(this.parentScreen);
			} else if (par1GuiButton.id == 15) {
				if ((this.selectedGroup > -1)
						&& (this.selectedGroup < this.groupList.size())) {
					Minecraft.getMinecraft().displayGuiScreen(
							new GuiRenameGroup(this,
									(GroupConfiguration) this.groupList
											.get(this.selectedGroup)));
				}
			} else {
				this.groupSlotContainer.actionPerformed(par1GuiButton);
			}
			updateButtons();
			SimpleLocator.saveConfiguration();
		}
	}

	protected void keyTyped(char par1, int par2) {
		int j = this.selectedGroup;
		if ((isShiftKeyDown()) && (par2 == 200)) {
			if ((j > 0) && (j < this.groupList.countGroups())) {
				this.groupList.swapGroups(j, j - 1);
				this.selectedGroup -= 1;
				if (j < this.groupList.countGroups() - 1) {
					this.groupSlotContainer.scrollBy(-this.groupSlotContainer
							.getSlotHeight());
				}
			}
		} else if ((isShiftKeyDown()) && (par2 == 208)) {
			if (((j >= 0 ? 1 : 0) & (j < this.groupList.countGroups() - 1 ? 1
					: 0)) != 0) {
				this.groupList.swapGroups(j, j + 1);
				this.selectedGroup += 1;
				if (j > 0) {
					this.groupSlotContainer.scrollBy(this.groupSlotContainer
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
		this.groupSlotContainer.drawScreen(par1, par2, par3);
		drawCenteredString(this.fontRendererObj, "Edit groups", this.width / 2,
				20, 16777215);
		super.drawScreen(par1, par2, par3);
		updateButtons();
	}

	private void joinServer(int par1) {
		if (par1 >= this.groupList.countGroups()) {
			par1 -= this.groupList.countGroups();
		}
	}

	public void updateButtons() {
		boolean flag = (this.selectedGroup >= 0)
				&& (this.selectedGroup < SimpleLocator.settings.getGroups()
						.size());
		this.editMembersButton.enabled = flag;
		this.renameGroupButton.enabled = flag;
		this.editButton.enabled = flag;
		this.deleteButton.enabled = flag;
	}

	static GroupList getGroupList(GuiGroups guiGroups) {
		return guiGroups.groupList;
	}

	static int getSelectedGroup(GuiGroups guiGroups) {
		return guiGroups.selectedGroup;
	}

	static int getAndSetSelectedGroup(GuiGroups guiGroups, int par1) {
		return guiGroups.selectedGroup = par1;
	}

	static GuiButton getButtonEditMembers(GuiGroups guiGroups) {
		return guiGroups.editMembersButton;
	}

	static GuiButton getButtonRenameGroups(GuiGroups guiGroups) {
		return guiGroups.renameGroupButton;
	}

	static GuiButton getButtonEdit(GuiGroups guiGroups) {
		return guiGroups.editButton;
	}

	static GuiButton getButtonDelete(GuiGroups guiGroups) {
		return guiGroups.deleteButton;
	}

	static void editGroup(GuiGroups guiGroups, int par1) {
		if ((par1 > -1) && (par1 < guiGroups.groupList.size())) {
			Minecraft.getMinecraft()
					.displayGuiScreen(
							new GuiGroupSettings(guiGroups,
									(GroupConfiguration) guiGroups.groupList
											.get(par1)));
		}
	}

	static int getTicksOpened(GuiGroups guiGroups) {
		return guiGroups.ticksOpened;
	}
}

/*
 * Location: C:\Users\Will\Documents\SimpleLocator\SimpleLocator_v2_2.jar
 * 
 * Qualified Name: shadowjay1.forge.simplelocator.gui.GuiGroups
 * 
 * JD-Core Version: 0.7.0.1
 */