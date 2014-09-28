package com.clone.simplelocator.gui;

import java.awt.Color;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.texture.TextureManager;

import org.lwjgl.opengl.GL11;

import com.clone.simplelocator.LocatorSettings;

public class GuiColoredButton extends GuiButton {
	private Color color;
	LocatorSettings settings = new LocatorSettings();

	public GuiColoredButton(int par1, int par2, int par3, int par4, int par5,
			String par6Str) {
		super(par1, par2, par3, par4, par5, par6Str);
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public void drawButton(Minecraft mc, int par1, int par2) {
		if (this.visible) {
			FontRenderer fontrenderer = mc.fontRenderer;
			mc.getTextureManager().bindTexture(buttonTextures);
			if (this.color != null) {
				GL11.glColor3f(this.color.getRed() / 255.0F,
						this.color.getGreen() / 255.0F,
						this.color.getBlue() / 255.0F);
			} else {
				GL11.glColor3f(1.0F, 1.0F, 1.0F);
			}
			this.field_146123_n = ((par1 >= this.xPosition)
					&& (par2 >= this.yPosition)
					&& (par1 < this.xPosition + this.width) && (par2 < this.yPosition
					+ this.height));
			int k = getHoverState(this.field_146123_n);
			GL11.glEnable(3042);
			OpenGlHelper.glBlendFunc(770, 771, 1, 0);
			GL11.glBlendFunc(770, 771);
			drawTexturedModalRect(this.xPosition, this.yPosition, 0,
					46 + k * 20, this.width / 2, this.height);
			drawTexturedModalRect(this.xPosition + this.width / 2,
					this.yPosition, 200 - this.width / 2, 46 + k * 20,
					this.width / 2, this.height);
			mouseDragged(mc, par1, par2);
			int l = 14737632;
			if (this.packedFGColour != 0) {
				l = this.packedFGColour;
			} else if (!this.enabled) {
				l = 10526880;
			} else if (this.field_146123_n) {
				l = 16777120;
			}
			drawCenteredString(fontrenderer, this.displayString, this.xPosition
					+ this.width / 2, this.yPosition + (this.height - 8) / 2, l);
		}
	}
}

/*
 * Location: C:\Users\Will\Documents\SimpleLocator\SimpleLocator_v2_2.jar
 * 
 * Qualified Name: shadowjay1.forge.simplelocator.gui.GuiColoredButton
 * 
 * JD-Core Version: 0.7.0.1
 */