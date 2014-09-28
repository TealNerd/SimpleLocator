package com.clone.simplelocator;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import java.awt.Color;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Random;

import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class LocatorSettings {
	public static final Color[] defaultColors = { Color.BLACK, Color.BLUE, Color.CYAN, Color.DARK_GRAY, Color.GRAY, Color.GREEN, Color.LIGHT_GRAY, Color.MAGENTA, Color.ORANGE, Color.PINK,	Color.RED, Color.WHITE, Color.YELLOW };
	private boolean renderEnabled = true;
	private boolean worldPoliceEnabled = false;
	private boolean broadcastCoords = false;
	private boolean sendOthersEnabled;
	private boolean showExactOffline = true;
	private int maxViewDistance = 3000;
	private int expirationTime = 1800000;
	private float scale = 1.0F;
	private float opacity = 0.5F;
	private GroupList groups = new GroupList();
	//private AltList alts = new AltList();
	private Color defaultColor;
	private String debug = "/debug";

	private boolean RadarEnabled = true;
	private boolean RadarPlayerNames = true;
	private boolean RadarColorPlayerNames = true;
	private boolean RadarUsePlayerSkinTexture = false;
	private boolean RadarAutoRotate = true;
	private boolean RadarTerrain = false;
	private boolean RadarCoordinates = true;
	private boolean RadarFriendlyMobs = true;
	private boolean RadarHostileMobs = true;
	private boolean RadarNeutralMobs = true;
	private boolean RadarItems = true;
	private boolean RadarPlayers = true;
	
	private int RadarPosX;
	private int RadarPosY;


	public int getRadarPosX()
	{
		return this.RadarPosX;
	}
	public void setRadarPosX(int RadarPosX)
	{
		this.RadarPosX = RadarPosX;
	}
	
	public int getRadarPosY()
	{
		return this.RadarPosY;
	}
	public void setRadarPosY(int RadarPosY)
	{
		this.RadarPosY = RadarPosY;
	}
	public boolean getRadarEnabled() {
		return RadarEnabled;
	}

	public void setRadarEnabled(boolean RadarEnabled) {
		this.RadarEnabled = RadarEnabled;
	}

	public boolean getRadarPlayerNames() {
		return RadarPlayerNames;
	}

	public void setRadarPlayerNames(boolean RadarPlayerNames) {
		this.RadarPlayerNames = RadarPlayerNames;
	}

	public boolean getRadarColorPlayerNames() {
		return RadarColorPlayerNames;
	}

	public void setRadarColorPlayerNames(boolean RadarColorPlayerNames) {
		this.RadarColorPlayerNames = RadarColorPlayerNames;
	}

	public boolean getRadarUsePlayerSkinTexture() {
		return RadarUsePlayerSkinTexture;
	}

	public void setRadarUsePlayerSkinTexture(boolean RadarUsePlayerSkinTexture) {
		this.RadarUsePlayerSkinTexture = RadarUsePlayerSkinTexture;
	}

	public boolean getRadarAutoRotate() {
		return RadarAutoRotate;
	}

	public void setRadarAutoRotate(boolean RadarAutoRotate) {
		this.RadarAutoRotate = RadarAutoRotate;
	}

	public boolean getRadarTerrain() {
		return RadarTerrain;
	}

	public void setRadarTerrain(boolean RadarTerrain) {
		this.RadarTerrain = RadarTerrain;
	}

	public boolean getRadarCoordinates() {
		return RadarCoordinates;
	}

	public void setRadarCoordinates(boolean RadarCoordinates) {
		this.RadarCoordinates = RadarCoordinates;
	}

	public boolean getRadarFriendlyMobs() {
		return RadarFriendlyMobs;
	}

	public void setRadarFriendlyMobs(boolean RadarFriendlyMobs) {
		this.RadarFriendlyMobs = RadarFriendlyMobs;
	}

	public boolean getRadarHostileMobs() {
		return RadarHostileMobs;
	}

	public void setRadarHostileMobs(boolean RadarHostileMobs) {
		this.RadarHostileMobs = RadarHostileMobs;
	}

	public boolean getRadarNeutralMobs() {
		return RadarNeutralMobs;
	}

	public void setRadarNeutralMobs(boolean RadarNeutralMobs) {
		this.RadarNeutralMobs = RadarNeutralMobs;
	}

	public boolean getRadarItems() {
		return RadarItems;
	}

	public void setRadarItems(boolean RadarItems) {
		this.RadarItems = RadarItems;
	}

	public boolean getRadarPlayers() {
		return RadarPlayers;
	}

	public void setRadarPlayers(boolean RadarPlayers) {
		this.RadarPlayers = RadarPlayers;
	}

	public boolean getBroadcastCoords()
	{
		return this.broadcastCoords;
	}
	public void setBroadcastCoords(boolean broadcastCoords)
	{
		this.broadcastCoords = broadcastCoords;
	}
	
	public boolean isRenderEnabled() {
		return this.renderEnabled;
	}

	public void setRenderEnabled(boolean value) {
		this.renderEnabled = value;
	}

	public boolean isWorldPoliceEnabled() {
		return this.worldPoliceEnabled;
	}

	public void setWorldPoliceEnabled(boolean value) {
		this.worldPoliceEnabled = value;
	}

	public boolean isSendOthersEnabled() {
		return this.sendOthersEnabled;
	}

	public void setSendOthersEnabled(boolean value) {
		this.sendOthersEnabled = value;
	}

	public boolean isShowExactOfflineEnabled() {
		return this.showExactOffline;
	}

	public void setShowExactOffline(boolean value) {
		this.showExactOffline = value;
	}

	public int getExpirationTime() {
		return this.expirationTime;
	}

	public void setExpirationTime(int value) {
		this.expirationTime = value;
	}

	public int getMaxViewDistance() {
		return this.maxViewDistance;
	}

	public void setMaxViewDistance(int value) {
		this.maxViewDistance = value;
	}

	public float getScale() {
		return this.scale;
	}

	public void setScale(float value) {
		this.scale = value;
	}

	public float getOpacity() {
		return this.opacity;
	}

	public void setOpacity(float value) {
		this.opacity = value;
	}

	public GroupList getGroups() {
		if (this.groups == null) {
			this.groups = new GroupList();
		}
		return this.groups;
	}
	
	/*public AltList getAlts()
	{
		if(this.alts == null)
		{
			this.alts = new AltList();
		}
		return this.alts;
	}*/

	public Color getDefaultColor() {
		return this.defaultColor;
	}

	public void setDefaultColor(Color value) {
		this.defaultColor = value;
	}

	public void randomizeDefaultColor() {
		Random random = new Random();
		this.defaultColor = defaultColors[random.nextInt(defaultColors.length)];
	}

	public void save(File file) {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		try {
			String json = gson.toJson(this);

			FileWriter writer = new FileWriter(file);
			writer.write(json);
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static LocatorSettings load(File file) {
		Gson gson = new Gson();
		try {
			return (LocatorSettings) gson.fromJson(new FileReader(file), LocatorSettings.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new LocatorSettings();
	}
}

/*
 * Location: C:\Users\Will\Documents\SimpleLocator\SimpleLocator_v2_2.jar
 * 
 * Qualified Name: shadowjay1.forge.simplelocator.LocatorSettings
 * 
 * JD-Core Version: 0.7.0.1
 */