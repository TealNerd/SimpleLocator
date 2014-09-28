package com.clone.simplelocator;

public enum LocationType {
	EXACT("-"), SNITCH("~"), PPBROADCAST("o"), GROUPCOORDS("|");

	private String indicator;

	private LocationType(String indicator) {
		this.indicator = indicator;
	}

	public String getIndicator() {
		return this.indicator;
	}

	public boolean isExact() {
		return true;
	}

	public boolean isRemote() {
		return false;
	}
}

/*
 * Location: C:\Users\Will\Documents\SimpleLocator\SimpleLocator_v2_2.jar
 * 
 * Qualified Name: shadowjay1.forge.simplelocator.LocationType
 * 
 * JD-Core Version: 0.7.0.1
 */