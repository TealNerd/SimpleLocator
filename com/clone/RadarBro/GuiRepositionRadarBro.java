/*  1:   */ package com.clone.RadarBro;
/*  2:   */ 
/*  3:   */ import net.minecraft.client.Minecraft;
/*  4:   */ import net.minecraft.client.gui.GuiScreen;
/*  5:   */ import net.minecraft.client.gui.ScaledResolution;
/*  6:   */ import net.minecraft.util.MouseHelper;

/*  7:   */ import org.lwjgl.input.Keyboard;
/*  8:   */ import org.lwjgl.input.Mouse;
/*  9:   */ import org.lwjgl.opengl.GL11;

import com.clone.simplelocator.LocatorSettings;
import com.clone.simplelocator.SimpleLocator;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
/* 10:   */ 
			@SideOnly(Side.CLIENT)
/* 11:   */ public class GuiRepositionRadarBro
/* 12:   */   extends GuiScreen
/* 13:   */ {
/* 14:   */   public MouseHelper mouseHelper;
/* 15:   */   public static Minecraft mc;
			  LocatorSettings settings = SimpleLocator.settings;
			  
/* 86:87 */   public static boolean isDraggable = false;
/* 87:88 */   public static boolean dragging = false;
/* 88:   */   public static int xPos;
/* 89:   */   public static int yPos;
/* 90:   */   public static int xDisplacement;
/* 91:   */   public static int yDisplacement;
/* 92:   */   public static int xEndDisplacement;
/* 93:   */   public static int yEndDisplacement;
/* 94:   */   public static int xOffset;
/* 95:   */   public static int yOffset;
/* 16:   */   
/* 17:   */   public GuiRepositionRadarBro(Minecraft minecraft)
/* 18:   */   {
/* 19:20 */     mc = minecraft;
/* 20:   */   }
/* 21:   */   
/* 22:   */   public void initGui()
/* 23:   */   {
/* 24:25 */     Keyboard.enableRepeatEvents(true);
/* 25:   */   }
/* 26:   */   
/* 27:   */   public void onGuiClosed()
/* 28:   */   {
/* 29:30 */     Keyboard.enableRepeatEvents(false);
				settings.setRadarPosX(xOffset);
				settings.setRadarPosY(yOffset);
				SimpleLocator.saveConfiguration();
/* 31:   */   }
/* 32:   */   
/* 33:   */   public void drawScreen(int i, int j, float f)
/* 34:   */   {
/* 35:36 */     ScaledResolution sr = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
/* 36:37 */     int i3 = sr.getScaledWidth();
/* 37:38 */     drawCenteredString(this.fontRendererObj, "Click and drag the radar to reposition", this.width / 2, this.height / 4 - 60 + 20, 16777215);
/* 38:39 */     GL11.glScalef(0.5F, 0.5F, 0.5F);
/* 39:40 */     drawString(this.fontRendererObj, "Press Esc when you're finished", this.width / 2 - 210, this.height / 4 - 60 + 2, 10526880);
/* 40:41 */     GL11.glScalef(2.0F, 2.0F, 2.0F);
/* 41:42 */     handleMouseDrag();
/* 42:43 */     xOffset = xDisplacement + xEndDisplacement;
/* 43:44 */     yOffset = yDisplacement + yEndDisplacement;
/* 44:45 */     super.drawScreen(i, j, f);
/* 45:   */   }
/* 46:   */   
/* 47:   */   private void handleMouseDrag()
/* 48:   */   {
/* 49:50 */     if (!Mouse.isButtonDown(0))
/* 50:   */     {
/* 51:52 */       dragging = false;
/* 52:53 */       xEndDisplacement += xDisplacement;
/* 53:54 */       yEndDisplacement += yDisplacement;
/* 54:55 */       xDisplacement = 0;
/* 55:56 */       yDisplacement = 0;
/* 56:   */     }
/* 57:58 */     if (dragging)
/* 58:   */     {
/* 59:60 */       int x = Mouse.getEventX() * this.width / mc.displayWidth;
/* 60:61 */       int y = this.height - Mouse.getEventY() * this.height / mc.displayHeight - 1;
/* 61:62 */       xDisplacement = x - xPos;
/* 62:63 */       yDisplacement = y - yPos;
/* 63:   */     }
/* 64:   */   }
/* 65:   */   
/* 66:   */   protected void mouseMovedOrUp(int i, int j, int k)
/* 67:   */   {
/* 68:69 */     if ((i >= this.width - 135 + xOffset) && (i <= this.width + xOffset) && (j >= yOffset) && (j <= 130 + yOffset)) {
/* 69:70 */       isDraggable = true;
/* 70:   */     } else {
/* 71:72 */       isDraggable = false;
/* 72:   */     }
/* 73:   */   }
/* 74:   */   
/* 75:   */   protected void mouseClicked(int i, int j, int k)
/* 76:   */   {
/* 77:78 */     if ((i >= this.width - 135 + xOffset) && (i <= this.width + xOffset) && (j >= yOffset) && (j <= 130 + yOffset))
/* 78:   */     {
/* 79:80 */       xPos = Mouse.getEventX() * this.width / mc.displayWidth;
/* 80:81 */       yPos = this.height - Mouse.getEventY() * this.height / mc.displayHeight - 1;
/* 81:82 */       dragging = true;
/* 82:   */     }
/* 83:84 */     super.mouseClicked(i, j, k);
/* 84:   */   }
/* 85:   */   
/* 96:   */ }


/* Location:           C:\Users\Will\Documents\RadarBro.jar
 * Qualified Name:     com.calialec.radarbro.GuiRepositionRadarBro
 * JD-Core Version:    0.7.0.1
 */