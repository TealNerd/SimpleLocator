/*  1:   */ package com.clone.RadarBro;
/*  2:   */ 
/*  3:   */ import java.util.List;

/*  4:   */ import net.minecraft.client.Minecraft;
/*  5:   */ import net.minecraft.client.gui.GuiButton;
/*  6:   */ import net.minecraft.client.gui.GuiScreen;
/*  7:   */ import net.minecraft.util.StringTranslate;

/*  8:   */ import org.lwjgl.input.Keyboard;

import com.clone.simplelocator.LocatorSettings;
import com.clone.simplelocator.SimpleLocator;
/*  9:   */ 
/* 10:   */ public class GuiRadarBroSettings
/* 11:   */   extends GuiScreen
/* 12:   */ {
/* 13:   */   private GuiScreen parentScreen;
			  LocatorSettings settings = SimpleLocator.settings;
/* 14:   */   
/* 15:   */   public GuiRadarBroSettings(GuiScreen guiscreen)
/* 16:   */   {
/* 17:14 */     this.parentScreen = guiscreen;
/* 18:   */   }
/* 19:   */   
/* 20:   */   public void initGui()
/* 21:   */   {
/* 22:19 */     StringTranslate stringTranslate = new StringTranslate();
/* 23:20 */     Keyboard.enableRepeatEvents(true);
/* 24:21 */     this.buttonList.clear();
/* 25:22 */     this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 - 16, "Edit Radar Icons..."));
/* 28:25 */     this.buttonList.add(new GuiButton(3, this.width / 2 - 100, this.height / 4 + 8, "Radar: " + (settings.getRadarEnabled() ? "Enabled" : "Disabled")));
/* 29:26 */     this.buttonList.add(new GuiButton(4, this.width / 2 - 100, this.height / 4 + 32, "GUI Settings..."));
/* 30:27 */     this.buttonList.add(new GuiButton(5, this.width / 2 - 100, this.height / 4 + 58, "Multiplayer Settings..."));
/* 31:28 */     this.buttonList.add(new GuiButton(6, this.width / 2 - 100, this.height / 4 + 128, stringTranslate.translateKey("gui.back")));
/* 32:   */   }
/* 33:   */   
/* 34:   */   public void onGuiClosed()
/* 35:   */   {
/* 36:33 */     Keyboard.enableRepeatEvents(false);
/* 37:   */   }
/* 38:   */   
/* 39:   */   protected void actionPerformed(GuiButton guibutton)
/* 40:   */   {
/* 41:38 */     if (!guibutton.enabled) {
/* 42:40 */       return;
/* 43:   */     }
/* 44:42 */     if (guibutton.id == 0) {
/* 45:43 */       this.mc.displayGuiScreen(new GuiRadarBroIconSettings(this));
/* 46:   */     }
/* 53:53 */     if (guibutton.id == 3)
/* 54:   */     {
/* 55:55 */       settings.setRadarEnabled(!settings.getRadarEnabled());
/* 56:56 */       ((GuiButton)this.buttonList.get(1)).displayString = ("Radar: " + (settings.getRadarEnabled() ? "Enabled" : "Disabled"));
/* 57:   */     }
/* 58:58 */     if (guibutton.id == 4) {
/* 59:60 */       this.mc.displayGuiScreen(new GuiRadarBroGUISettings(this));
/* 60:   */     }
/* 61:62 */     if (guibutton.id == 5) {
/* 62:64 */       this.mc.displayGuiScreen(new GuiRadarBroMultiplayerSettings(this));
/* 63:   */     }
/* 64:66 */     if (guibutton.id == 6) {
/* 65:68 */       this.mc.displayGuiScreen(this.parentScreen);
/* 66:   */     }
/* 67:   */   }
/* 68:   */   
/* 69:   */   protected void mouseClicked(int i, int j, int k)
/* 70:   */   {
/* 71:74 */     super.mouseClicked(i, j, k);
/* 72:   */   }
/* 73:   */   
/* 74:   */   public void drawScreen(int i, int j, float f)
/* 75:   */   {
/* 76:79 */     drawDefaultBackground();
/* 77:80 */     drawCenteredString(this.fontRendererObj, "RadarBro Settings", this.width / 2, this.height / 4 - 60 + 20, 16777215);
/* 78:81 */     super.drawScreen(i, j, f);
/* 79:   */   }
/* 80:   */ }


/* Location:           C:\Users\Will\Documents\RadarBro.jar
 * Qualified Name:     com.calialec.radarbro.GuiRadarBroSettings
 * JD-Core Version:    0.7.0.1
 */