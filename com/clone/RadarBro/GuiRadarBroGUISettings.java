/*  1:   */ package com.clone.RadarBro;
/*  2:   */ 
/*  3:   */ /*  5:   */ import java.awt.Color;
import java.util.Random;

import net.minecraft.client.gui.GuiButton;
/*  6:   */ import net.minecraft.client.gui.GuiScreen;
/*  7:   */ import net.minecraft.util.StringTranslate;

/*  8:   */ import org.lwjgl.input.Keyboard;

import com.clone.simplelocator.LocatorSettings;
import com.clone.simplelocator.SimpleLocator;
import com.clone.simplelocator.gui.GuiColoredButton;
/*  4:   */ 
/*  9:   */ 
/* 10:   */ public class GuiRadarBroGUISettings
/* 11:   */   extends GuiScreen
/* 12:   */ {
/* 13:   */   private GuiScreen parentScreen;
			  private GuiButton autoRotate;
			  private GuiButton coords;
			  private GuiButton terrain;
			  private GuiColoredButton radarColor;
			  private GuiButton deleteColor;
			  LocatorSettings settings = SimpleLocator.settings;
/* 14:   */   
/* 15:   */   public GuiRadarBroGUISettings(GuiScreen guiscreen)
/* 16:   */   {
/* 17:14 */     this.parentScreen = guiscreen;
/* 18:   */   }
/* 19:   */   
/* 20:   */   public void initGui()
/* 21:   */   {
/* 22:19 */     StringTranslate stringTranslate = new StringTranslate();
/* 23:20 */     Keyboard.enableRepeatEvents(true);
/* 24:21 */     this.buttonList.clear();
/* 25:22 */     this.autoRotate = new GuiButton(0, this.width / 2 - 100, this.height / 4 - 16, "");
/* 26:23 */     this.coords = new GuiButton(1, this.width / 2 - 100, this.height / 4 + 8, "");
/* 27:24 */     this.terrain = new GuiButton(2, this.width / 2 - 100, this.height / 4 + 32, "");
				this.radarColor = new GuiColoredButton(5, this.width / 2 - 100, this.height / 4 + 56, 99, 20, "Color Dat Radar");
				this.deleteColor = new GuiButton(6, this.width / 2 + 1,this.height / 2 + 80, 99, 20, "Delete color");
				
/* 28:25 */     
				updateButtons();
				
				this.buttonList.add(new GuiButton(3, this.width / 2 - 100, this.height / 4 + 104, "Reposition Radar..."));
/* 29:26 */     this.buttonList.add(new GuiButton(4, this.width / 2 - 100, this.height / 4 + 128, stringTranslate.translateKey("gui.done")));
				this.buttonList.add(this.autoRotate);
				this.buttonList.add(this.coords);
				this.buttonList.add(this.terrain);
				this.buttonList.add(this.radarColor);
				this.buttonList.add(this.deleteColor);
/* 30:   */   }
/* 31:   */   
/* 32:   */   public void onGuiClosed()
/* 33:   */   {
/* 34:31 */     Keyboard.enableRepeatEvents(false);
/* 35:   */   }
/* 36:   */   
/* 37:   */   protected void actionPerformed(GuiButton guibutton)
/* 38:   */   {
/* 39:36 */     if (!guibutton.enabled) {
/* 40:38 */       return;
/* 41:   */     }
/* 42:40 */     if (guibutton.id == 0)
/* 43:   */     {
/* 44:41 */       settings.setRadarAutoRotate(!settings.getRadarAutoRotate());
/* 45:42 */       this.autoRotate.displayString = ("Auto Rotate: " + (settings.getRadarAutoRotate() ? "ON" : "OFF"));
				  updateButtons();
/* 46:   */     }
/* 47:44 */     if (guibutton.id == 1)
/* 48:   */     {
/* 49:46 */       settings.setRadarCoordinates(!settings.getRadarCoordinates());
/* 50:47 */       this.coords.displayString = ("Display Coordinates: " + (settings.getRadarCoordinates() ? "ON" : "OFF"));
				  updateButtons();
/* 51:   */     }
/* 52:49 */     if (guibutton.id == 2)
/* 53:   */     {
/* 54:51 */       settings.setRadarTerrain(!settings.getRadarTerrain());
/* 55:52 */       this.terrain.displayString = ("Terrain: " + (settings.getRadarTerrain() ? "ON" : "OFF"));
				  updateButtons();
/* 56:   */     }
/* 57:54 */     if (guibutton.id == 3) {
/* 58:56 */       this.mc.displayGuiScreen(new GuiRepositionRadarBro(this.mc));
/* 59:   */     }
/* 60:58 */     if (guibutton.id == 4) {
/* 61:60 */       this.mc.displayGuiScreen(this.parentScreen);
/* 62:   */     }
				if(guibutton.id == 5) {
					Random random = new Random();
					GuiRadarBro.color = LocatorSettings.defaultColors[random
							.nextInt(LocatorSettings.defaultColors.length)];
				}
				if(guibutton.id == 6) {
					GuiRadarBro.color = null;
					radarColor.setColor(Color.black);
				}
/* 63:   */   }
/* 64:   */   
			  private void updateButtons()
			  {
				  this.autoRotate.displayString = ("Auto Rotate: " + (settings.getRadarAutoRotate() ? "ON" : "OFF"));
				  this.coords.displayString = ("Display Coordinates: " + (settings.getRadarCoordinates() ? "ON" : "OFF"));
				  this.terrain.displayString = ("Show Terrain: " + (settings.getRadarTerrain() ? "ON" : "OFF"));
			  }
/* 65:   */   protected void mouseClicked(int i, int j, int k)
/* 66:   */   {
/* 67:66 */     super.mouseClicked(i, j, k);
/* 68:   */   }

/* 70:   */   public void drawScreen(int i, int j, float f)
/* 71:   */   {
/* 72:71 */     drawDefaultBackground();
/* 73:72 */     drawCenteredString(this.fontRendererObj, "RadarBro GUI Settings", this.width / 2, this.height / 4 - 60 + 20, 16777215);
/* 74:73 */     super.drawScreen(i, j, f);
/* 75:   */   }
/* 76:   */ }


/* Location:           C:\Users\Will\Documents\RadarBro.jar
 * Qualified Name:     com.calialec.radarbro.GuiRadarBroGUISettings
 * JD-Core Version:    0.7.0.1
 */