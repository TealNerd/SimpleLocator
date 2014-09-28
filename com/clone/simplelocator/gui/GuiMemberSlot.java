package com.clone.simplelocator.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSlot;
import net.minecraft.client.renderer.Tessellator;

import org.lwjgl.opengl.GL11;

import com.clone.simplelocator.MemberList;

@SideOnly(Side.CLIENT)
class GuiMemberSlot extends GuiSlot {
	final GuiMembers parentGui;
	private Minecraft minecraft;
	private FontRenderer fontRenderer;

	public GuiMemberSlot(GuiMembers par1GuiMultiplayer) {
		super(Minecraft.getMinecraft(), par1GuiMultiplayer.width, par1GuiMultiplayer.height, 32, par1GuiMultiplayer.height - 64, 16);
		this.parentGui = par1GuiMultiplayer;
		this.minecraft = Minecraft.getMinecraft();
		this.fontRenderer = this.minecraft.fontRenderer;
	}

	protected int getSize() {
		return GuiMembers.getMemberList(this.parentGui).countGroups();
	}

	protected void elementClicked(int par1, boolean par2, int par3, int par4) {
		if (par1 < GuiMembers.getMemberList(this.parentGui).countGroups()) {
			int j = GuiMembers.getSelectedGroup(this.parentGui);
			GuiMembers.getAndSetSelectedGroup(this.parentGui, par1);
			boolean flag1 = (GuiMembers.getSelectedGroup(this.parentGui) >= 0)
					&& (GuiMembers.getSelectedGroup(this.parentGui) < getSize());
			boolean flag2 = GuiMembers.getSelectedGroup(this.parentGui) < GuiMembers
					.getMemberList(this.parentGui).countGroups();
			GuiMembers.getButtonDelete(this.parentGui).enabled = flag2;
			if ((!par2) || (!flag1)) {
				if ((flag2)
						&& (GuiScreen.isShiftKeyDown())
						&& (j >= 0)
						&& (j < GuiMembers.getMemberList(this.parentGui)
								.countGroups())) {
					GuiMembers.getMemberList(this.parentGui).swapGroups(j,
							GuiMembers.getSelectedGroup(this.parentGui));
				}
			}
		}
	}

	protected boolean isSelected(int par1) {
		return par1 == GuiMembers.getSelectedGroup(this.parentGui);
	}

	protected int getContentHeight() {
		return getSize() * this.slotHeight;
	}

	protected void drawBackground() {
	}

	protected void drawSlot(int par1, int par2, int par3, int par4,
			Tessellator par5Tessellator, int par5, int par6) {
		if (par1 < GuiMembers.getMemberList(this.parentGui).countGroups()) {
			String username = GuiMembers.getMemberList(this.parentGui).get(par1);

			this.parentGui.drawString(this.fontRenderer, username, par2 + 5,
					par3 + 2, 16777215);
		}
	}

	public void drawContainerBackground(Tessellator t) {
		GL11.glBlendFunc(770, 771);
		GL11.glEnable(3042);
		GL11.glDisable(3553);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		float height = 32.0F;
		t.startDrawingQuads();
		t.setColorRGBA(0, 0, 0, 200);
		t.addVertexWithUV(this.left, this.bottom, 0.0D, this.left / height,
				(this.bottom + getAmountScrolled()) / height);
		t.addVertexWithUV(this.right, this.bottom, 0.0D, this.right / height,
				(this.bottom + getAmountScrolled()) / height);
		t.addVertexWithUV(this.right, this.top, 0.0D, this.right / height,
				(this.top + getAmountScrolled()) / height);
		t.addVertexWithUV(this.left, this.top, 0.0D, this.left / height,
				(this.top + getAmountScrolled()) / height);
		t.draw();
		GL11.glEnable(3553);
	}
}

/*
 * Location: C:\Users\Will\Documents\SimpleLocator\SimpleLocator_v2_2.jar
 * 
 * Qualified Name: shadowjay1.forge.simplelocator.gui.GuiMemberSlot
 * 
 * JD-Core Version: 0.7.0.1
 */