package com.clone.simplelocator.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import java.util.ArrayList;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSlot;
import net.minecraft.client.renderer.Tessellator;

import org.lwjgl.opengl.GL11;

import com.clone.simplelocator.GroupConfiguration;
import com.clone.simplelocator.GroupList;

@SideOnly(Side.CLIENT)
class GuiGroupSlot extends GuiSlot 
{
	final GuiGroups parentGui;
	private Minecraft minecraft;
	private FontRenderer fontRenderer;

	public GuiGroupSlot(GuiGroups par1GuiMultiplayer) 
	{
		super(Minecraft.getMinecraft(), par1GuiMultiplayer.width,par1GuiMultiplayer.height, 32, par1GuiMultiplayer.height - 64,	36);
		this.parentGui = par1GuiMultiplayer;
		this.minecraft = Minecraft.getMinecraft();
		this.fontRenderer = this.minecraft.fontRenderer;
	}

	protected int getSize() 
	{
		return GuiGroups.getGroupList(this.parentGui).countGroups();
	}

	protected void elementClicked(int par1, boolean par2, int par3, int par4) 
	{
		if (par1 < GuiGroups.getGroupList(this.parentGui).countGroups()) 
		{
			int j = GuiGroups.getSelectedGroup(this.parentGui);
			GuiGroups.getAndSetSelectedGroup(this.parentGui, par1);
			boolean flag1 = (GuiGroups.getSelectedGroup(this.parentGui) >= 0) && (GuiGroups.getSelectedGroup(this.parentGui) < getSize());
			boolean flag2 = GuiGroups.getSelectedGroup(this.parentGui) < GuiGroups.getGroupList(this.parentGui).countGroups();
			GuiGroups.getButtonEditMembers(this.parentGui).enabled = flag1;
			GuiGroups.getButtonEdit(this.parentGui).enabled = flag2;
			GuiGroups.getButtonDelete(this.parentGui).enabled = flag2;
			if ((par2) && (flag1)) 
			{
				GuiGroups.editGroup(this.parentGui, par1);
			} 
			else if ((flag2) && (GuiScreen.isShiftKeyDown()) && (j >= 0) && (j < GuiGroups.getGroupList(this.parentGui).countGroups())) 
			{
				GuiGroups.getGroupList(this.parentGui).swapGroups(j, GuiGroups.getSelectedGroup(this.parentGui));
			}
		}
	}

	protected boolean isSelected(int par1) {
		return par1 == GuiGroups.getSelectedGroup(this.parentGui);
	}

	protected int getContentHeight() {
		return getSize() * 36;
	}

	protected void drawBackground() {
	}

	protected void drawSlot(int par1, int par2, int par3, int par4, Tessellator par5Tessellator, int par5, int par6) 
	{
		if (par1 < GuiGroups.getGroupList(this.parentGui).countGroups()) 
		{
			GroupConfiguration group = (GroupConfiguration) GuiGroups.getGroupList(this.parentGui).get(par1);

			String sizeInfo = group.getUsernames().size() + (group.getUsernames().size() != 1 ? " members" : " member");
			this.parentGui.drawString(this.fontRenderer, group.getName(), par2 + 2, par3 + 1, 16777215);
			this.parentGui.drawString(this.fontRenderer, group.getConfigSummary(), par2 + 2, par3 + 12, 8421504);
			this.parentGui.drawString(this.fontRenderer, sizeInfo, par2 + 215 - this.fontRenderer.getStringWidth(sizeInfo), par3 + 12, 8421504);
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
 * Qualified Name: shadowjay1.forge.simplelocator.gui.GuiGroupSlot
 * 
 * JD-Core Version: 0.7.0.1
 */