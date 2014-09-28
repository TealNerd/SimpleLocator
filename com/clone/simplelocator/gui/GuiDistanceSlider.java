package com.clone.simplelocator.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

import org.lwjgl.opengl.GL11;

import com.clone.simplelocator.LocatorSettings;
import com.clone.simplelocator.SimpleLocator;

@SideOnly(Side.CLIENT)
public class GuiDistanceSlider extends GuiButton {
	public float sliderValue = 1.0F;
	public boolean dragging;
	private double maxDistance;
	private boolean infinityAllowed;
	private String name;

	public GuiDistanceSlider(int par1, int par2, int par3, double maxDistance,
			boolean infinityAllowed, String par5Str, float par6) {
		super(par1, par2, par3, 150, 20, par5Str);
		this.maxDistance = maxDistance;
		this.infinityAllowed = infinityAllowed;
		this.sliderValue = par6;
		this.name = par5Str;
		this.width = 200;

		adjustFromDistance(SimpleLocator.settings.getMaxViewDistance());
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
				} else if (this.sliderValue > 1.0F) {
					this.sliderValue = 1.0F;
				}
				updateDisplayString();
				SimpleLocator.settings.setMaxViewDistance(getDistance());
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
			} else if (this.sliderValue > 1.0F) {
				this.sliderValue = 1.0F;
			}
			updateDisplayString();
			SimpleLocator.settings.setMaxViewDistance(getDistance());
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
				&& (this.infinityAllowed) ? "infinity" : Long.valueOf(Math
				.round(this.maxDistance * this.sliderValue / 500.0D) * 500L)));
	}

	private void adjustFromDistance(int distance) {
		this.sliderValue = (distance / (float) this.maxDistance);
		if (this.sliderValue > 1.0F) {
			this.sliderValue = 1.0F;
		} else if (this.sliderValue < 0.0F) {
			this.sliderValue = 0.0F;
		}
	}

	public int getDistance() {
		if ((this.infinityAllowed) && (this.sliderValue >= 1.0F)) {
			return 2147483647;
		}
		return (int) (Math.round(this.maxDistance * this.sliderValue / 500.0D) * 500L);
	}
}

/*
 * Location: C:\Users\Will\Documents\SimpleLocator\SimpleLocator_v2_2.jar
 * 
 * Qualified Name: shadowjay1.forge.simplelocator.gui.GuiDistanceSlider
 * 
 * JD-Core Version: 0.7.0.1
 */