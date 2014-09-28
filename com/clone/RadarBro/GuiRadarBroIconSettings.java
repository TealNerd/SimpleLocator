/*   1:    */ package com.clone.RadarBro;
/*   2:    */ 
/*   3:    */ import java.util.List;

/*   4:    */ import net.minecraft.client.Minecraft;
/*   5:    */ import net.minecraft.client.gui.GuiButton;
/*   6:    */ import net.minecraft.client.gui.GuiScreen;
/*   7:    */ import net.minecraft.client.renderer.texture.TextureManager;
/*   8:    */ import net.minecraft.util.ResourceLocation;
/*   9:    */ import net.minecraft.util.StringTranslate;

/*  10:    */ import org.lwjgl.input.Keyboard;
/*  11:    */ import org.lwjgl.opengl.GL11;
import org.apache.commons.io.filefilter.IOFileFilter;

import com.clone.simplelocator.LocatorSettings;
import com.clone.simplelocator.SimpleLocator;
/*  12:    */ 
/*  13:    */ public class GuiRadarBroIconSettings
/*  14:    */   extends GuiScreen
/*  15:    */ {
/*  16:    */   private GuiScreen parentScreen;
/*  18:    */   private TextureManager textureManager;
				LocatorSettings settings = SimpleLocator.settings;
/*  19:    */   
/*  20:    */   public GuiRadarBroIconSettings(GuiScreen guiscreen)
/*  21:    */   {
/*  22: 22 */     this.parentScreen = guiscreen;
/*  23: 23 */     this.textureManager = SimpleLocator.mc.getTextureManager();
/*  24:    */   }
/*  25:    */   
/*  26:    */   public void updateScreen() {}
/*  27:    */   
/*  28:    */   public void initGui()
/*  29:    */   {
/*  30: 32 */     StringTranslate stringTranslate = new StringTranslate();
/*  31: 33 */     Keyboard.enableRepeatEvents(true);
/*  32: 34 */     this.buttonList.clear();
/*  33:    */     
/*  34:    */ 
/*  35:    */ 
/*  36: 38 */     this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 2 - 60, 200, 20, "Friendly Mobs: " + (settings.getRadarFriendlyMobs() ? "On" : "Off")));
/*  37: 39 */     this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 2 - 36, 200, 20, "Hostile Mobs: " + (settings.getRadarHostileMobs() ? "On" : "Off")));
/*  38: 40 */     this.buttonList.add(new GuiButton(2, this.width / 2 - 100, this.height / 2 - 12, 200, 20, "Neutral Mobs: " + (settings.getRadarNeutralMobs() ? "On" : "Off")));
/*  39: 41 */     this.buttonList.add(new GuiButton(3, this.width / 2 - 100, this.height / 2 + 12, 200, 20, "Items: " + (settings.getRadarItems() ? "On" : "Off")));
/*  40: 42 */     this.buttonList.add(new GuiButton(4, this.width / 2 - 100, this.height / 2  + 36, 200, 20, "Players: " + (settings.getRadarPlayers() ? "On" : "Off")));
/*  41: 43 */     
/*  91: 93 */     this.buttonList.add(new GuiButton(39, this.width / 2 - 100, this.height / 2 + 90, 200, 20, stringTranslate.translateKey("gui.done")));
/*  92:    */   }
/*  93:    */   
/*  94:    */   public void onGuiClosed()
/*  95:    */   {
/*  96: 99 */     Keyboard.enableRepeatEvents(false);
/*  97:    */   }
/*  98:    */   
/*  99:    */   protected void actionPerformed(GuiButton guibutton)
/* 100:    */   {
/* 101:104 */     if (!guibutton.enabled) {
/* 102:106 */       return;
/* 103:    */     }
/* 104:108 */     if (guibutton.id == 0)
/* 105:    */     {
/* 106:110 */       settings.setRadarFriendlyMobs(!settings.getRadarFriendlyMobs());
/* 107:111 */       ((GuiButton)this.buttonList.get(0)).displayString = ("Friendly Mobs: " + (settings.getRadarFriendlyMobs() ? "On" : "Off"));
/* 108:    */     }
/* 109:113 */     if (guibutton.id == 1)
/* 110:    */     {
/* 111:115 */       settings.setRadarHostileMobs(!settings.getRadarHostileMobs());
/* 112:116 */       ((GuiButton)this.buttonList.get(1)).displayString = ("Hostile Mobs: " + (settings.getRadarHostileMobs() ? "On" : "Off"));
/* 113:    */     }
/* 114:118 */     if (guibutton.id == 2)
/* 115:    */     {
/* 116:119 */       settings.setRadarNeutralMobs(!settings.getRadarNeutralMobs());
/* 117:120 */       ((GuiButton)this.buttonList.get(2)).displayString = ("Neutral Mobs: " + (settings.getRadarNeutralMobs() ? "On" : "Off"));
/* 118:    */     }
/* 119:123 */     if (guibutton.id == 3)
/* 120:    */     {
/* 121:125 */       settings.setRadarItems(!settings.getRadarItems());
/* 122:126 */       ((GuiButton)this.buttonList.get(3)).displayString = ("Items: " + (settings.getRadarItems() ? "On" : "Off"));
/* 123:    */     }
/* 124:128 */     if (guibutton.id == 4)
/* 125:    */     {
/* 126:130 */       settings.setRadarPlayers(!settings.getRadarPlayers());
/* 127:131 */       ((GuiButton)this.buttonList.get(4)).displayString = ("Players: " + (settings.getRadarPlayers() ? "On" : "Off"));
/* 128:    */     }
/* 129:133 */     
/* 299:306 */     if (guibutton.id == 39) {
/* 300:308 */       this.mc.displayGuiScreen(this.parentScreen);
/* 301:    */     }
				  SimpleLocator.saveConfiguration();
/* 303:    */   }
/* 304:    */   
/* 305:    */   protected void mouseClicked(int i, int j, int k)
/* 306:    */   {
/* 307:316 */     super.mouseClicked(i, j, k);
/* 308:    */   }
/* 309:    */   
/* 310:    */   public void drawIcon(int x, int y, int x2, int y2)
/* 311:    */   {
/* 312:320 */     GL11.glPushMatrix();
/* 313:321 */     GL11.glScalef(0.5F, 0.5F, 0.5F);
/* 314:322 */     GL11.glTranslatef(x, y, 0.0F);
/* 315:323 */     drawTexturedModalRect(x, y, x2, y2, 16, 16);
/* 316:324 */     GL11.glScalef(2.0F, 2.0F, 2.0F);
/* 317:325 */     GL11.glPopMatrix();
/* 318:    */   }
/* 319:    */   
/* 320:    */   public void drawScreen(int i, int j, float f)
/* 321:    */   {
/* 322:330 */     GL11.glDisable(2929);
/* 323:331 */     drawDefaultBackground();
/* 324:    */     
/* 325:333 */     this.textureManager.bindTexture(GuiRadarBro.radaricons);
/* 326:334 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 327:    */     
/* 328:    */ 
/* 329:    */ 
/* 330:    */ 	 
/* 459:467 */     drawCenteredString(this.fontRendererObj, "RadarBro Icon Settings", this.width / 2, this.height / 4 - 72 + 20, 16777215);
/* 460:    */     
/* 511:519 */     super.drawScreen(i, j, f);
/* 512:    */   }
/* 513:    */ }


/* Location:           C:\Users\Will\Documents\RadarBro.jar
 * Qualified Name:     com.calialec.radarbro.GuiRadarBroIconSettings
 * JD-Core Version:    0.7.0.1
 */