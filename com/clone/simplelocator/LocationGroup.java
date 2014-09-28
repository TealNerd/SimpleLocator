package com.clone.simplelocator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import net.minecraft.entity.player.EntityPlayer;

public class LocationGroup extends ArrayList<String> implements ILocation {
	private double x = 0.0D;
	private double y = 0.0D;
	private double z = 0.0D;
	private float yawFromPlayer = 0.0F;
	private float pitchFromPlayer = 0.0F;

	public double getX() {
		return this.x;
	}

	public double getY() {
		return this.y;
	}

	public double getZ() {
		return this.z;
	}

	public void calculateCenter() {
		this.x = 0.0D;
		this.y = 0.0D;
		this.z = 0.0D;

		int amount = size();
		if (amount == 0) {
			return;
		}
		Iterator<String> iterator = iterator();
		while (iterator.hasNext()) {
			LocatorLocation location = (LocatorLocation) LocatorListener.locations
					.get(iterator.next());
			if (location == null) {
				iterator.remove();
			} else {
				this.x += location.getX();
				this.y += location.getY();
				this.z += location.getZ();
			}
		}
		this.x /= amount;
		this.y /= amount;
		this.z /= amount;
	}

	public void calculateYawFromPlayer(EntityPlayer p) {
		this.yawFromPlayer = ((float) Math.atan2(this.z - p.posZ, this.x
				- p.posX));
	}

	public void calculatePitchFromPlayer(EntityPlayer p) {
		double xDist = this.x - p.posX;
		double zDist = this.z - p.posZ;
		double xzDist = Math.sqrt(xDist * xDist + zDist * zDist);
		this.pitchFromPlayer = ((float) Math.atan2(this.y - p.posY, xzDist));
	}

	public float getYawFromPlayer() {
		return this.yawFromPlayer;
	}

	public float getPitchFromPlayer() {
		return this.pitchFromPlayer;
	}
}

/*
 * Location: C:\Users\Will\Documents\SimpleLocator\SimpleLocator_v2_2.jar
 * 
 * Qualified Name: shadowjay1.forge.simplelocator.LocationGroup
 * 
 * JD-Core Version: 0.7.0.1
 */