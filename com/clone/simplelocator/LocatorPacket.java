package com.clone.simplelocator;

import java.util.HashMap;
import java.util.Map;

public class LocatorPacket {
	private String sessionId;
	private Action action;
	private String ip;
	private int port;
	private LocatorLocation ownLocation = null;
	private HashMap<String, LocatorLocation> activeLocations;
	private HashMap<String, LocatorLocation> exactLocations;
	private long localTime;

	public LocatorPacket(String sessionId, String ip, int port,
			HashMap<String, LocatorLocation> activeLocations,
			HashMap<String, LocatorLocation> exactLocations) {
		this.sessionId = sessionId;
		this.action = Action.REQUEST;
		this.ip = ip;
		this.port = port;
		this.activeLocations = activeLocations;
		this.exactLocations = exactLocations;
	}

	public Action getAction() {
		return this.action;
	}

	public String getSessionId() {
		return this.sessionId;
	}

	public void setOwnLocation(LocatorLocation location) {
		this.ownLocation = location;
	}

	public Map<String, LocatorLocation> getActiveLocations() {
		if (this.activeLocations == null) {
			return new HashMap();
		}
		return this.activeLocations;
	}

	public Map<String, LocatorLocation> getPlayerLocations() {
		if (this.exactLocations == null) {
			return new HashMap();
		}
		return this.exactLocations;
	}

	public long getLocalTime() {
		return this.localTime;
	}

	public String toString() {
		return "LocatorPacket{id:" + this.sessionId + "; action:"
				+ this.action.toString() + "}";
	}

	public static enum Action {
		REQUEST, RESPONSE, DENY;

		private Action() {
		}
	}
}

/*
 * Location: C:\Users\Will\Documents\SimpleLocator\SimpleLocator_v2_2.jar
 * 
 * Qualified Name: shadowjay1.forge.simplelocator.LocatorPacket
 * 
 * JD-Core Version: 0.7.0.1
 */