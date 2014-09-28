package com.clone.simplelocator;

import net.minecraft.entity.player.EntityPlayer;

public class LocatorLocation implements ILocation {
	private final double x;
	private final double y;
	private final double z;
	private final LocationType type;
	private final long creationTime;
	private final int dimension;

	public LocatorLocation(double x, double y, double z, LocationType type) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.type = type;
		this.creationTime = System.currentTimeMillis();
		this.dimension = 1;
	}

	public LocatorLocation(EntityPlayer p) {
		this.x = p.posX;
		this.y = p.posY;
		this.z = p.posZ;
		this.type = LocationType.EXACT;
		this.creationTime = System.currentTimeMillis();
		this.dimension = p.dimension;
	}

	private LocatorLocation(double x, double y, double z, LocationType type,
			long creationTime, int dimension) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.type = type;
		this.creationTime = creationTime;
		this.dimension = dimension;
	}

	public double getX() {
		return this.x;
	}

	public double getY() {
		return this.y;
	}

	public double getZ() {
		return this.z;
	}

	public LocationType getType() {
		return this.type;
	}

	public long getCreationTime() {
		return this.creationTime;
	}

	public long getAge() {
		return System.currentTimeMillis() - this.creationTime;
	}

	public int compareTo(LocatorLocation loc2) {
		long time1 = getEffectiveTime();
		long time2 = loc2.getEffectiveTime();
		if (time1 > time2) {
			return 1;
		}
		if (time1 < time2) {
			return -1;
		}
		return 0;
	}

	private long getEffectiveTime() {
		switch (this.type) {
		case EXACT:
			return this.creationTime;
		case SNITCH:
			return this.creationTime - 5000L;
		case PPBROADCAST:
			return this.creationTime - 4000L;
		case GROUPCOORDS:
			return this.creationTime - 4000L;
		}
		return this.creationTime;
	}

	public LocatorLocation setType(LocationType type) {
		return new LocatorLocation(this.x, this.y, this.z, type,
				this.creationTime, this.dimension);
	}

	public LocatorLocation setCreationTime(long time) {
		return new LocatorLocation(this.x, this.y, this.z, this.type, time,
				this.dimension);
	}

	public boolean equals(Object o) {
		if ((o instanceof LocatorLocation)) {
			LocatorLocation l2 = (LocatorLocation) o;

			return (this.x == l2.x) && (this.y == l2.y) && (this.z == l2.z)
					&& (this.type == l2.type);
		}
		return false;
	}
}

/*
 * Location: C:\Users\Will\Documents\SimpleLocator\SimpleLocator_v2_2.jar
 * 
 * Qualified Name: shadowjay1.forge.simplelocator.LocatorLocation
 * 
 * JD-Core Version: 0.7.0.1
 */