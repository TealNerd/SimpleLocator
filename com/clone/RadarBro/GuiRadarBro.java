/*   1:    */ package com.clone.RadarBro;
/*   2:    */ 
/*   3:    */ import cpw.mods.fml.common.eventhandler.SubscribeEvent;

import java.awt.Color;
/*   4:    */ import java.awt.Graphics;
/*   5:    */ import java.awt.image.BufferedImage;
/*   6:    */ import java.io.IOException;
/*   7:    */ import java.net.MalformedURLException;
/*   8:    */ import java.net.URL;
/*   9:    */ import java.util.ArrayList;
/*  10:    */ import java.util.HashMap;
/*  11:    */ import java.util.List;

/*  12:    */ import javax.imageio.ImageIO;

/*  13:    */ import net.minecraft.client.Minecraft;
/*  14:    */ import net.minecraft.client.entity.AbstractClientPlayer;
/*  15:    */ import net.minecraft.client.entity.EntityClientPlayerMP;
/*  16:    */ import net.minecraft.client.entity.EntityOtherPlayerMP;
/*  17:    */ import net.minecraft.client.gui.FontRenderer;
/*  18:    */ import net.minecraft.client.gui.GuiScreen;
/*  19:    */ import net.minecraft.client.gui.ScaledResolution;
/*  20:    */ import net.minecraft.client.multiplayer.WorldClient;
/*  21:    */ import net.minecraft.client.renderer.entity.RenderItem;
/*  22:    */ import net.minecraft.client.renderer.texture.DynamicTexture;
/*  23:    */ import net.minecraft.client.renderer.texture.TextureManager;
/*  24:    */ import net.minecraft.client.resources.IResource;
/*  25:    */ import net.minecraft.client.resources.IResourceManager;
/*  26:    */ import net.minecraft.entity.Entity;
/*  27:    */ import net.minecraft.entity.boss.EntityDragon;
/*  28:    */ import net.minecraft.entity.boss.EntityWither;
/*  29:    */ import net.minecraft.entity.item.EntityBoat;
/*  30:    */ import net.minecraft.entity.item.EntityItem;
/*  31:    */ import net.minecraft.entity.item.EntityMinecart;
/*  32:    */ import net.minecraft.entity.item.EntityPainting;
/*  33:    */ import net.minecraft.entity.item.EntityXPOrb;
/*  34:    */ import net.minecraft.entity.monster.EntityBlaze;
/*  35:    */ import net.minecraft.entity.monster.EntityCaveSpider;
/*  36:    */ import net.minecraft.entity.monster.EntityCreeper;
/*  37:    */ import net.minecraft.entity.monster.EntityEnderman;
/*  38:    */ import net.minecraft.entity.monster.EntityGhast;
/*  39:    */ import net.minecraft.entity.monster.EntityIronGolem;
/*  40:    */ import net.minecraft.entity.monster.EntityMagmaCube;
/*  41:    */ import net.minecraft.entity.monster.EntityPigZombie;
/*  42:    */ import net.minecraft.entity.monster.EntitySilverfish;
/*  43:    */ import net.minecraft.entity.monster.EntitySkeleton;
/*  44:    */ import net.minecraft.entity.monster.EntitySlime;
/*  45:    */ import net.minecraft.entity.monster.EntitySnowman;
/*  46:    */ import net.minecraft.entity.monster.EntitySpider;
/*  47:    */ import net.minecraft.entity.monster.EntityWitch;
/*  48:    */ import net.minecraft.entity.monster.EntityZombie;
/*  49:    */ import net.minecraft.entity.passive.EntityBat;
/*  50:    */ import net.minecraft.entity.passive.EntityChicken;
/*  51:    */ import net.minecraft.entity.passive.EntityCow;
/*  52:    */ import net.minecraft.entity.passive.EntityMooshroom;
/*  53:    */ import net.minecraft.entity.passive.EntityOcelot;
/*  54:    */ import net.minecraft.entity.passive.EntityPig;
/*  55:    */ import net.minecraft.entity.passive.EntitySheep;
/*  56:    */ import net.minecraft.entity.passive.EntitySquid;
/*  57:    */ import net.minecraft.entity.passive.EntityVillager;
/*  58:    */ import net.minecraft.entity.passive.EntityWolf;
/*  59:    */ import net.minecraft.entity.player.EntityPlayer;
/*  60:    */ import net.minecraft.entity.projectile.EntityArrow;
/*  61:    */ import net.minecraft.item.ItemStack;
/*  62:    */ import net.minecraft.tileentity.TileEntity;
/*  63:    */ import net.minecraft.tileentity.TileEntityChest;
/*  64:    */ import net.minecraft.tileentity.TileEntityDispenser;
/*  65:    */ import net.minecraft.tileentity.TileEntityDropper;
/*  66:    */ import net.minecraft.tileentity.TileEntityEnderChest;
/*  67:    */ import net.minecraft.tileentity.TileEntityMobSpawner;
/*  68:    */ import net.minecraft.util.ResourceLocation;
/*  69:    */ import net.minecraftforge.client.event.RenderGameOverlayEvent;
/*  70:    */ import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;

/*  71:    */ import org.lwjgl.opengl.GL11;

import com.clone.simplelocator.GroupConfiguration;
import com.clone.simplelocator.LocatorSettings;
import com.clone.simplelocator.SimpleLocator;
/*  72:    */ 
/*  73:    */ public class GuiRadarBro
/*  74:    */   extends GuiScreen
/*  75:    */ {
/*  76:    */   private static Minecraft mc;
/*  77: 74 */   public static final ResourceLocation radaricons = new ResourceLocation("simplelocator/textures/radaricons.png");
/*  78:    */   private TextureManager textureManager;
				LocatorSettings settings = SimpleLocator.settings;
				static Color color = Color.CYAN;
/*  79:    */   
/*  80:    */   public GuiRadarBro(Minecraft minecraft)
/*  81:    */   {
/*  82: 77 */     mc = minecraft;
/*  83: 78 */     this.textureManager = mc.getTextureManager();
/*  84:    */   }
/*  85:    */   
/*  86:    */   @SubscribeEvent
/*  87:    */   public void onDrawRadarBro(RenderGameOverlayEvent event)
/*  88:    */   {
/*  89: 84 */     if ((event.isCancelable()) || (event.type != RenderGameOverlayEvent.ElementType.EXPERIENCE)) {
/*  90: 85 */       return;
/*  91:    */     }
/*  92: 87 */     if (settings.getRadarEnabled())
/*  93:    */     {
/*  94: 88 */       ScaledResolution sr = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
/*  95: 89 */       int i = sr.getScaledWidth();
/*  96: 90 */       GL11.glPushMatrix();
/*  97: 91 */       GL11.glTranslatef(i - 65 + (GuiRepositionRadarBro.xOffset), 65 + (GuiRepositionRadarBro.yOffset), 0.0F);
/*  98: 92 */       if (settings.getRadarCoordinates())
/*  99:    */       {
/* 100: 93 */         GL11.glScalef(0.5F, 0.5F, 0.5F);
/* 101: 94 */         mc.fontRenderer.drawString("(" + (int)mc.thePlayer.posX, -8 - mc.fontRenderer.getStringWidth("(" + (int)mc.thePlayer.posX), 134, 14737632);
/* 102: 95 */         mc.fontRenderer.drawString("," + (int)mc.thePlayer.posY + "," + (int)mc.thePlayer.posZ + ")", -8, 134, 14737632);
/* 103: 96 */         GL11.glScalef(2.0F, 2.0F, 2.0F);
/* 104:    */       }
/* 105: 98 */       if (settings.getRadarAutoRotate()) {
/* 106: 99 */         GL11.glRotatef(-mc.thePlayer.rotationYaw, 0.0F, 0.0F, 1.0F);
/* 107:    */       }
/* 108:    */       try
/* 109:    */       {
/* 110:103 */         if (settings.getRadarTerrain()) {
/* 111:104 */           renderTerrain();
/* 112:    */         }
/* 113:    */       }
/* 114:    */       catch (Exception e) {}
/* 115:109 */       drawCircle(0, 0, 63.0D, -2147483648, true);
/* 116:110 */       drawCircle(0, 0, 63.0D, -2140904094, false);
/* 117:111 */       drawCircle(0, 0, 43.0D, -2140904094, false);
/* 118:112 */       drawCircle(0, 0, 22.0D, -2140904094, false);
/* 119:113 */       GL11.glLineWidth(2.0F);
/* 120:114 */       GL11.glDisable(3553);
/* 121:115 */       GL11.glDisable(2896);
/* 122:116 */       GL11.glBegin(1);
/* 123:117 */       GL11.glVertex2d(0.0D, -63.0D);
/* 124:118 */       GL11.glVertex2d(0.0D, 63.0D);
/* 125:119 */       GL11.glVertex2d(-63.0D, 0.0D);
/* 126:120 */       GL11.glVertex2d(63.0D, 0.0D);
/* 127:121 */       GL11.glVertex2d(-44.5D, -44.5D);
/* 128:122 */       GL11.glVertex2d(44.5D, 44.5D);
/* 129:123 */       GL11.glVertex2d(-44.5D, 44.5D);
/* 130:124 */       GL11.glVertex2d(44.5D, -44.5D);
/* 131:125 */       GL11.glEnd();
/* 132:126 */       GL11.glDisable(3042);
/* 133:127 */       GL11.glEnable(3553);
/* 134:128 */       drawIconRadar();
/* 135:129 */       if (settings.getRadarAutoRotate()) {
/* 136:130 */         GL11.glRotatef(mc.thePlayer.rotationYaw, 0.0F, 0.0F, 1.0F);
/* 137:    */       }
/* 138:132 */       drawTriangle(0, 0, -4144960);
/* 139:133 */       GL11.glPopMatrix();
/* 140:    */     }
					
/* 141:    */   }
/* 142:    */   
/* 143:    */   public void drawIconRadar()
/* 144:    */   {
/* 179:169 */     if (settings.getRadarItems())
/* 180:    */     {
/* 181:170 */       List<TileEntity> tileEntities = mc.theWorld.loadedTileEntityList;
/* 182:171 */       for (int i = 0; i < tileEntities.size(); i++)
/* 183:    */       {
/* 184:172 */         TileEntity tileEntity = (TileEntity)tileEntities.get(i);
/* 185:173 */         int pPosX = (int)Math.round(mc.thePlayer.posX);
/* 186:174 */         int pPosY = (int)Math.round(mc.thePlayer.posY);
/* 187:175 */         int pPosZ = (int)Math.round(mc.thePlayer.posZ);
/* 188:176 */         int tileEntPosX = Math.round(tileEntity.xCoord);
/* 189:177 */         int tileEntPosY = Math.round(tileEntity.yCoord);
/* 190:178 */         int tileEntPosZ = Math.round(tileEntity.zCoord);
/* 191:179 */         int dPosX = pPosX - tileEntPosX;
/* 192:180 */         int dPosY = pPosY - tileEntPosY;
/* 193:181 */         int dPosZ = pPosZ - tileEntPosZ;
/* 194:182 */         if (Math.hypot(dPosX, dPosZ) < 130.0D)
/* 195:    */         {
/* 196:183 */           if ((tileEntity instanceof TileEntityChest)) {
/* 197:184 */             drawIconRadarIcon(dPosX, dPosZ, 16, 32);
/* 198:    */           }
/* 199:186 */           if ((tileEntity instanceof TileEntityEnderChest)) {
/* 200:187 */             drawIconRadarIcon(dPosX, dPosZ, 64, 32);
/* 201:    */           }
/* 202:189 */           if ((tileEntity instanceof TileEntityMobSpawner)) {
/* 203:190 */             drawIconRadarIcon(dPosX, dPosZ, 128, 32);
/* 204:    */           }
/* 205:192 */           if ((tileEntity instanceof TileEntityDispenser)) {
/* 206:193 */             drawIconRadarIcon(dPosX, dPosZ, 144, 32);
/* 207:    */           }
/* 208:195 */           if ((tileEntity instanceof TileEntityDropper)) {
/* 209:196 */             drawIconRadarIcon(dPosX, dPosZ, 160, 32);
/* 210:    */           }
/* 211:    */         }
/* 212:    */       }
/* 213:    */     }
/* 214:201 */     List<Entity> entities = mc.theWorld.loadedEntityList;
/* 215:202 */     for (int i = 0; i < entities.size(); i++)
/* 216:    */     {
/* 217:203 */       Entity entity = (Entity)entities.get(i);
/* 218:204 */       int pPosX = (int)Math.round(mc.thePlayer.posX);
/* 219:205 */       int pPosY = (int)Math.round(mc.thePlayer.posY);
/* 220:206 */       int pPosZ = (int)Math.round(mc.thePlayer.posZ);
/* 221:207 */       int entPosX = (int)Math.round(entity.posX);
/* 222:208 */       int entPosY = (int)Math.round(entity.posY);
/* 223:209 */       int entPosZ = (int)Math.round(entity.posZ);
/* 224:210 */       int dPosX = pPosX - entPosX;
/* 225:211 */       int dPosY = pPosY - entPosY;
/* 226:212 */       int dPosZ = pPosZ - entPosZ;
/* 227:213 */       if ((Math.hypot(dPosX, dPosZ) < 130.0D) && (entity != mc.thePlayer))
/* 228:    */       {
/* 229:214 */         if (((entity instanceof EntityBat)) && (settings.getRadarNeutralMobs())) {
/* 230:215 */           drawIconRadarIcon(dPosX, dPosZ, 80, 32);
/* 231:    */         }
/* 232:217 */         if (((entity instanceof EntityChicken)) && (settings.getRadarFriendlyMobs())) {
/* 233:218 */           drawIconRadarIcon(dPosX, dPosZ, 32, 0);
/* 234:    */         }
/* 235:220 */         if (((entity instanceof EntityCow)) && (settings.getRadarFriendlyMobs()) && (!(entity instanceof EntityMooshroom))) {
/* 236:221 */           drawIconRadarIcon(dPosX, dPosZ, 48, 0);
/* 237:    */         }
/* 238:223 */         if (((entity instanceof EntityMooshroom)) && (settings.getRadarFriendlyMobs()) && (!(entity instanceof EntityCow))) {
/* 239:224 */           drawIconRadarIcon(dPosX, dPosZ, 144, 0);
/* 240:    */         }
/* 241:226 */         if (((entity instanceof EntityOcelot)) && (settings.getRadarFriendlyMobs())) {
/* 242:227 */           drawIconRadarIcon(dPosX, dPosZ, 240, 16);
/* 243:    */         }
/* 244:229 */         if (((entity instanceof EntityPig)) && (settings.getRadarFriendlyMobs())) {
/* 245:230 */           drawIconRadarIcon(dPosX, dPosZ, 160, 0);
/* 246:    */         }
/* 247:232 */         if (((entity instanceof EntitySheep)) && (settings.getRadarFriendlyMobs())) {
/* 248:233 */           drawIconRadarIcon(dPosX, dPosZ, 176, 0);
/* 249:    */         }
/* 250:235 */         if (((entity instanceof EntitySnowman)) && (settings.getRadarFriendlyMobs())) {
/* 251:236 */           drawIconRadarIcon(dPosX, dPosZ, 240, 0);
/* 252:    */         }
/* 253:238 */         if (((entity instanceof EntitySquid)) && (settings.getRadarNeutralMobs())) {
/* 254:239 */           drawIconRadarIcon(dPosX, dPosZ, 16, 16);
/* 255:    */         }
/* 256:241 */         if (((entity instanceof EntityVillager)) && (settings.getRadarFriendlyMobs())) {
/* 257:242 */           drawIconRadarIcon(dPosX, dPosZ, 80, 16);
/* 258:    */         }
/* 259:244 */         if (((entity instanceof EntityBlaze)) && (settings.getRadarHostileMobs())) {
/* 260:245 */           drawIconRadarIcon(dPosX, dPosZ, 0, 0);
/* 261:    */         }
/* 262:247 */         if (((entity instanceof EntityCaveSpider)) && (settings.getRadarHostileMobs()) && (!(entity instanceof EntitySpider))) {
/* 263:248 */           drawIconRadarIcon(dPosX, dPosZ, 16, 0);
/* 264:    */         }
/* 265:250 */         if (((entity instanceof EntityCreeper)) && (settings.getRadarHostileMobs())) {
/* 266:251 */           drawIconRadarIcon(dPosX, dPosZ, 64, 0);
/* 267:    */         }
/* 268:253 */         if (((entity instanceof EntityDragon)) && (settings.getRadarHostileMobs())) {
/* 269:254 */           drawIconRadarIcon(dPosX, dPosZ, 80, 0);
/* 270:    */         }
/* 271:256 */         if (((entity instanceof EntityGhast)) && (settings.getRadarHostileMobs())) {
/* 272:257 */           drawIconRadarIcon(dPosX, dPosZ, 112, 0);
/* 273:    */         }
/* 274:259 */         if (((entity instanceof EntityMagmaCube)) && (settings.getRadarHostileMobs())) {
/* 275:260 */           drawIconRadarIcon(dPosX, dPosZ, 128, 0);
/* 276:    */         }
/* 277:262 */         if (((entity instanceof EntitySilverfish)) && (settings.getRadarHostileMobs())) {
/* 278:263 */           drawIconRadarIcon(dPosX, dPosZ, 192, 0);
/* 279:    */         }
/* 280:265 */         if (((entity instanceof EntitySkeleton)) && (settings.getRadarHostileMobs())) {
/* 281:266 */           drawIconRadarIcon(dPosX, dPosZ, 208, 0);
/* 282:    */         }
/* 283:268 */         if (((entity instanceof EntitySlime)) && (settings.getRadarHostileMobs()) && (!(entity instanceof EntityMagmaCube))) {
/* 284:269 */           drawIconRadarIcon(dPosX, dPosZ, 224, 0);
/* 285:    */         }
/* 286:271 */         if (((entity instanceof EntitySpider)) && (settings.getRadarHostileMobs()) && (!(entity instanceof EntityCaveSpider))) {
/* 287:272 */           drawIconRadarIcon(dPosX, dPosZ, 0, 16);
/* 288:    */         }
/* 289:274 */         if (((entity instanceof EntityWitch)) && (settings.getRadarHostileMobs())) {
/* 290:275 */           drawIconRadarIcon(dPosX, dPosZ, 96, 32);
/* 291:    */         }
/* 292:277 */         if (((entity instanceof EntityWither)) && (settings.getRadarHostileMobs())) {
/* 293:278 */           drawIconRadarIcon(dPosX, dPosZ, 112, 32);
/* 294:    */         }
/* 295:280 */         if (((entity instanceof EntityZombie)) && (settings.getRadarHostileMobs())) {
/* 296:281 */           drawIconRadarIcon(dPosX, dPosZ, 48, 16);
/* 297:    */         }
/* 298:283 */         if (((entity instanceof EntityEnderman)) && (settings.getRadarHostileMobs())) {
/* 299:284 */           drawIconRadarIcon(dPosX, dPosZ, 96, 0);
/* 300:    */         }
/* 301:286 */         if (((entity instanceof EntityIronGolem)) && (settings.getRadarFriendlyMobs())) {
/* 302:287 */           drawIconRadarIcon(dPosX, dPosZ, 0, 32);
/* 303:    */         }
/* 304:289 */         if (((entity instanceof EntityWolf)) && (settings.getRadarNeutralMobs())) {
/* 305:290 */           drawIconRadarIcon(dPosX, dPosZ, 32, 16);
/* 306:    */         }
/* 307:292 */         if (((entity instanceof EntityPigZombie)) && (settings.getRadarNeutralMobs())) {
/* 308:293 */           drawIconRadarIcon(dPosX, dPosZ, 64, 16);
/* 309:    */         }
/* 313:298 */         if (((entity instanceof EntityBoat)) && (settings.getRadarItems())) {
/* 314:299 */           drawIconRadarIcon(dPosX, dPosZ, 128, 16);
/* 315:    */         }
/* 316:301 */         if (((entity instanceof EntityItem)) && (settings.getRadarItems()))
/* 317:    */         {
/* 318:302 */           EntityItem entitem = (EntityItem)entities.get(i);
/* 319:303 */           drawIconRadarItemIcon(dPosX, dPosZ, entitem.getEntityItem());
/* 320:    */         }
/* 321:305 */         if (((entity instanceof EntityMinecart)) && (settings.getRadarItems())) {
/* 322:306 */           drawIconRadarIcon(dPosX, dPosZ, 160, 16);
/* 323:    */         }
/* 330:314 */         if ((entity instanceof EntityPlayer)) {
/* 331:    */           try
/* 332:    */           {
/* 333:316 */             EntityOtherPlayerMP eop = (EntityOtherPlayerMP)entities.get(i);
/* 348:331 */             if (settings.getRadarPlayers())
/* 349:    */             {
/* 350:332 */               if (settings.getRadarUsePlayerSkinTexture()) {
/* 351:333 */                 //drawPlayerHeadImage(generatePlayerHeadImage(eop.getDisplayName()), (dPosX + 5) / 2, (dPosZ + 5) / 2);
/* 352:    */               } else {
/* 353:335 */                 drawIconRadarIcon(dPosX, dPosZ, 96, 16);
/* 354:    */               }
/* 355:337 */               if (settings.getRadarPlayerNames()) {
/* 356:338 */                 drawRadarNames(dPosX, dPosZ, eop.getDisplayName());
/* 357:    */               }
/* 358:    */             }
/* 359:    */           }
/* 360:    */           catch (ClassCastException e) {}
/* 361:    */         }
/* 362:    */       }
/* 363:    */     }
/* 364:    */   }
/* 365:    */   
/* 366:    */   public static void drawCircle(int x, int y, double r, int c, boolean filled)
/* 367:    */   {
/* 368:349 */     float f = (c >> 16 & 0xFF) / 255.0F;
/* 369:350 */     float f1 = (c >> 8 & 0xFF) / 255.0F;
/* 370:351 */     float f2 = (c & 0xFF) / 255.0F;
/* 371:352 */     float f3 = (c >> 24 & 0xFF) / 255.0F;
/* 372:353 */     GL11.glEnable(3042);
/* 373:354 */     GL11.glDisable(3553);
/* 374:355 */     GL11.glEnable(2848);
/* 375:356 */     GL11.glBlendFunc(770, 771);
/* 376:357 */     GL11.glColor4f(f, f1, f2, f3);
				  GL11.glColor4f(0, 0, 128 / 255.0f, 128 / 255.0f);
				/**
				  int red = color.getRed();
				  int blue = color.getBlue();
				  int green = color.getRed();
				  GL11.glColor4f(0, red / 255.0f, green / 255.0f, blue / 255.0f); **/
/* 377:358 */     GL11.glBegin(filled ? 6 : 2);
/* 378:359 */     for (int i = 0; i <= 360; i++)
/* 379:    */     {
/* 380:361 */       double x2 = Math.sin(i * 3.141526D / 180.0D) * r;
/* 381:362 */       double y2 = Math.cos(i * 3.141526D / 180.0D) * r;
/* 382:363 */       GL11.glVertex2d(x + x2, y + y2);
/* 383:    */     }
/* 384:365 */     GL11.glEnd();
/* 385:366 */     GL11.glDisable(2848);
/* 386:367 */     GL11.glEnable(3553);
/* 387:368 */     GL11.glDisable(3042);
/* 388:    */   }
/* 389:    */   
/* 390:    */   public void drawTriangle(int cx, int cy, int c)
/* 391:    */   {
/* 392:373 */     if (!(settings.getRadarAutoRotate()))
/* 393:    */     {
/* 394:374 */       GL11.glPushMatrix();
/* 395:375 */       GL11.glTranslatef(0.0F, 0.0F, 0.0F);
/* 396:376 */       GL11.glRotatef(-mc.thePlayer.rotationYaw, 0.0F, 0.0F, 1.0F);
/* 397:    */     }
/* 398:378 */     GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
/* 399:379 */     float f = (c >> 24 & 0xFF) / 255.0F;
/* 400:380 */     float f1 = (c >> 16 & 0xFF) / 255.0F;
/* 401:381 */     float f2 = (c >> 8 & 0xFF) / 255.0F;
/* 402:382 */     float f3 = (c & 0xFF) / 255.0F;
/* 403:383 */     GL11.glColor4f(f1, f2, f3, f);
/* 404:384 */     GL11.glEnable(3042);
/* 405:385 */     GL11.glDisable(3553);
/* 406:386 */     GL11.glEnable(2848);
/* 407:387 */     GL11.glBlendFunc(770, 771);
/* 408:388 */     GL11.glBegin(4);
/* 409:389 */     GL11.glVertex2d(cx, cy + 3);
/* 410:390 */     GL11.glVertex2d(cx + 3, cy - 3);
/* 411:391 */     GL11.glVertex2d(cx - 3, cy - 3);
/* 412:392 */     GL11.glEnd();
/* 413:393 */     GL11.glDisable(2848);
/* 414:394 */     GL11.glEnable(3553);
/* 415:395 */     GL11.glDisable(3042);
/* 416:396 */     GL11.glRotatef(-180.0F, 0.0F, 0.0F, 1.0F);
/* 417:397 */     if (!(settings.getRadarAutoRotate())) {
/* 418:398 */       GL11.glPopMatrix();
/* 419:    */     }
/* 420:    */   }
/* 421:    */   
/* 422:    */   private void setColorizedColor(int color)
/* 423:    */   {
/* 424:403 */     float alpha = (color >> 24 & 0xFF) / 255.0F;
/* 425:404 */     float red = (color >> 16 & 0xFF) / 255.0F;
/* 426:405 */     float green = (color >> 8 & 0xFF) / 255.0F;
/* 427:406 */     float blue = (color & 0xFF) / 255.0F;
/* 428:407 */     GL11.glColor4f(red, green, blue, alpha);
/* 429:    */   }
/* 430:    */   
/* 431:    */   public void renderTerrain() {}
/* 432:    */   
/* 433:    */   public void drawIconRadarIcon(int x, int y, int x2, int y2)
/* 434:    */   {
/* 435:416 */     this.textureManager.bindTexture(radaricons);
/* 436:417 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 437:418 */     GL11.glEnable(3042);
/* 438:419 */     GL11.glPushMatrix();
/* 439:420 */     GL11.glScalef(0.5F, 0.5F, 0.5F);
/* 440:421 */     GL11.glTranslatef(x + 1, y + 1, 0.0F);
/* 441:422 */     if (settings.getRadarAutoRotate()) {
/* 442:423 */       GL11.glRotatef(mc.thePlayer.rotationYaw, 0.0F, 0.0F, 1.0F);
/* 443:    */     }
/* 444:424 */     drawTexturedModalRect(-8, -8, x2, y2, 16, 16);
/* 445:425 */     GL11.glTranslatef(-x - 1, -y - 1, 0.0F);
/* 446:426 */     GL11.glScalef(2.0F, 2.0F, 2.0F);
/* 447:427 */     GL11.glDisable(2896);
/* 448:428 */     GL11.glDisable(3042);
/* 449:429 */     GL11.glPopMatrix();
/* 450:    */   }
/* 451:    */   
/* 452:    */   public void drawIconRadarItemIcon(int x, int y, ItemStack is)
/* 453:    */   {
/* 454:433 */     GL11.glPushMatrix();
/* 455:434 */     GL11.glScalef(0.5F, 0.5F, 0.5F);
/* 456:435 */     GL11.glTranslatef(x + 1, y + 1, 0.0F);
/* 457:436 */     if (settings.getRadarAutoRotate()) {
/* 458:437 */       GL11.glRotatef(mc.thePlayer.rotationYaw, 0.0F, 0.0F, 1.0F);
/* 459:    */     }
/* 460:438 */     SimpleLocator.itemRenderer.renderItemIntoGUI(mc.fontRenderer, this.textureManager, is, -8, -8);
/* 461:439 */     GL11.glTranslatef(-x - 1, -y - 1, 0.0F);
/* 462:440 */     GL11.glScalef(2.0F, 2.0F, 2.0F);
/* 463:441 */     GL11.glDisable(2896);
/* 464:442 */     GL11.glPopMatrix();
/* 465:    */   }
/* 466:    */   
/* 467:    */   public void drawIconRadarWaypointIcon(int x, int y)
/* 468:    */   {
/* 469:446 */     this.textureManager.bindTexture(radaricons);
/* 470:447 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 471:448 */     GL11.glPushMatrix();
/* 472:449 */     GL11.glScalef(0.5F, 0.5F, 0.5F);
/* 473:450 */     GL11.glTranslatef(x + 1, y + 1, 0.0F);
/* 474:451 */     if (settings.getRadarAutoRotate()) {
/* 475:452 */       GL11.glRotatef(-mc.thePlayer.rotationYaw / 180.0F - 180.0F, 0.0F, 0.0F, 1.0F);
/* 476:    */     }
/* 477:453 */     drawTexturedModalRect(-8, 0, 48, 32, 16, 16);
/* 478:454 */     GL11.glTranslatef(-x - 1, -y - 1, 0.0F);
/* 479:455 */     GL11.glScalef(2.0F, 2.0F, 2.0F);
/* 480:456 */     GL11.glDisable(2896);
/* 481:457 */     GL11.glPopMatrix();
/* 482:    */   }
/* 483:    */   
/* 484:    */   public void drawRadarNames(int x, int y, String username)
/* 485:    */   {
				  GroupConfiguration group = settings.getGroups().getByUsername(username);
				  int color = -1;
				  if(group != null)
				  {
					  if(group.getColor() != null)
					  {
						  color = group.getColor().getRGB();
					  }
					  else
					  {
						  if(settings.getDefaultColor() != null)
						  {
							  color = settings.getDefaultColor().getRGB();
						  }
					  }
				  }
				  else
					{
						if(settings.getDefaultColor() != null)
						{
							color = settings.getDefaultColor().getRGB();
						}
					}
/* 486:461 */     GL11.glPushMatrix();
/* 487:462 */     GL11.glScalef(0.5F, 0.5F, 0.5F);
/* 488:463 */     GL11.glTranslatef(x, y, 0.0F);
/* 489:464 */     if (settings.getRadarAutoRotate()) {
/* 490:465 */       GL11.glRotatef(mc.thePlayer.rotationYaw, 0.0F, 0.0F, 1.0F);
/* 491:    */     }
/* 492:467 */     GL11.glTranslatef(-x, -y, 0.0F);
/* 493:468 */     FontRenderer fontrenderer = mc.fontRenderer;
				  if (settings.getRadarColorPlayerNames())
/* 503:    */     {
/* 504:476 */       drawCenteredString(fontrenderer, username, x, y - 18, color);
/* 505:    */     }
/* 506:    */     else
/* 507:    */     {
/* 508:478 */       if (username.contains("??")) {
/* 509:479 */         username = username.substring(2);
/* 510:    */       }
/* 511:481 */       drawCenteredString(fontrenderer, username, x, y - 18, 14737632);
/* 512:    */     }
/* 513:484 */     GL11.glScalef(2.0F, 2.0F, 2.0F);
/* 514:485 */     GL11.glPopMatrix();
/* 515:    */   }
/* 516:    */   
/* 517:    */   public void drawPlayerHeadImage(BufferedImage bufferedimage, int x, int y)
/* 518:    */   {
/* 519:489 */     DynamicTexture previewTexture = new DynamicTexture(bufferedimage);
/* 520:490 */     ResourceLocation resourceLocation = mc.getTextureManager().getDynamicTextureLocation("preivew", previewTexture);
/* 521:491 */     mc.getTextureManager().bindTexture(resourceLocation);
/* 522:492 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 523:493 */     GL11.glPushMatrix();
/* 524:494 */     drawTexturedModalRect(x, y, 0, 0, 32, 32);
/* 440:421 */     GL11.glTranslatef(x + 1, y + 1, 0.0F);
/* 441:422 */     if (settings.getRadarAutoRotate()) {
/* 442:423 */       GL11.glRotatef(mc.thePlayer.rotationYaw, 0.0F, 0.0F, 1.0F);
/* 443:    */     }
/* 444:424 */     //drawTexturedModalRect(-8, -8, x2, y2, 16, 16);
/* 445:425 */     GL11.glTranslatef(-x - 1, -y - 1, 0.0F);
/* 525:495 */     GL11.glPopMatrix();
/* 526:    */   }
/* 588:    */ }


/* Location:           C:\Users\Will\Documents\RadarBro.jar
 * Qualified Name:     com.calialec.radarbro.GuiRadarBro
 * JD-Core Version:    0.7.0.1
 */