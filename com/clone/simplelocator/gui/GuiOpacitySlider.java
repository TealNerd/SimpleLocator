package com.clone.simplelocator.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

import org.lwjgl.opengl.GL11;

import com.clone.simplelocator.LocatorSettings;
import com.clone.simplelocator.SimpleLocator;

@SideOnly(Side.CLIENT)
public class GuiOpacitySlider extends GuiButton {
	public float sliderValue = 1.0F;
	public boolean dragging;
	private float maxOpacity;
	private boolean infinityAllowed;
	private String name;

	public GuiOpacitySlider(int par1, int par2, int par3, float maxOpacity,
			boolean infinityAllowed, String par5Str, float par6) {
		super(par1, par2, par3, 150, 20, par5Str);
		this.maxOpacity = maxOpacity;
		this.infinityAllowed = infinityAllowed;
		this.sliderValue = par6;
		this.name = par5Str;
		this.width = 200;

		adjustFromOpacity(SimpleLocator.settings.getOpacity());
		updateDisplayString();
	}

	public int getHoverState(boolean par1) {
		return 0;
	}

	protected void mouseDragged(Minecraft par1Minecraft, int par2, int par3) {
		if (this.visible) {
			if (this.dragging) {
				this.sliderValue = ((par2 - (float) (this.xPosition + 4)) / (float) (this.width - 8));
				if (this.sliderValue < 0.0F) {
					this.sliderValue = 0.0F;
				}
				if (this.sliderValue > 1.0F) {
					this.sliderValue = 1.0F;
				}
				updateDisplayString();
				SimpleLocator.settings.setOpacity(getOpacity());
			}
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			drawTexturedModalRect(this.xPosition
					+ (int) (this.sliderValue * (this.width - 8)),
					this.yPosition, 0, 66, 4, 20);
			drawTexturedModalRect(this.xPosition
					+ (int) (this.sliderValue * (this.width - 8)) + 4,
					this.yPosition, 196, 66, 4, 20);
		}
	}

	public boolean mousePressed(Minecraft par1Minecraft, int par2, int par3) {
		if (super.mousePressed(par1Minecraft, par2, par3)) {
			this.sliderValue = ((par2 - (float) (this.xPosition + 4)) / (float) (this.width - 8));
			if (this.sliderValue < 0.0F) {
				this.sliderValue = 0.0F;
			}
			if (this.sliderValue > 1.0F) {
				this.sliderValue = 1.0F;
			}
			updateDisplayString();
			SimpleLocator.settings.setOpacity(getOpacity());
			this.dragging = true;
			return true;
		}
		return false;
	}

	public void mouseReleased(int par1, int par2) {
		this.dragging = false;
		SimpleLocator.saveConfiguration();
	}

	private void updateDisplayString() {
		this.displayString = (this.name + ": " + ((this.sliderValue >= 1.0F)
				&& (this.infinityAllowed) ? "infinity"
				: new StringBuilder()
						.append(Math.round(this.maxOpacity * this.sliderValue
								* 100.0F)).append("%").toString()));
	}

	private void adjustFromOpacity(float opacity) {
		this.sliderValue = (opacity / this.maxOpacity);
		if (this.sliderValue > 1.0F) {
			this.sliderValue = 1.0F;
		} else if (this.sliderValue < 0.0F) {
			this.sliderValue = 0.0F;
		}
	}

	public float getOpacity() {
		if ((this.infinityAllowed) && (this.sliderValue >= 1.0F)) {
			return 3.4028235E+38F;
		}
		return Math.round(this.maxOpacity * this.sliderValue * 100.0F) / 100.0F;
	}
}

/*
 * Location: C:\Users\Will\Documents\SimpleLocator\SimpleLocator_v2_2.jar
 * 
 * Qualified Name: shadowjay1.forge.simplelocator.gui.GuiOpacitySlider
 * 
 * JD-Core Version: 0.7.0.1
 */