package com.clone.simplelocator;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundCategory;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiPlayerInfo;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;

import org.lwjgl.opengl.GL11;

import com.clone.simplelocator.gui.GuiLocatorSettings;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
//import sun.audio.AudioPlayer;
//import sun.audio.AudioStream;
import cpw.mods.fml.common.gameevent.TickEvent.RenderTickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Type;

public class LocatorListener extends Gui {
	private static Pattern snitch = Pattern.compile("([a-zA-Z0-9_]+) (?:entered|logged out in|logged in to) snitch at \\S* \\[([-]?[0-9]+) ([-]?[0-9]+) ([-]?[0-9]+)\\]");
	private static Pattern broadcast = Pattern.compile("(?:[a-zA-Z0-9_]+): held by ([a-zA-Z0-9_]+) at \\S+ ([-]?[0-9]+) ([-]?[0-9]+) ([-]?[0-9]+)");
	//private static Pattern groupcoords = Pattern.compile("\\[simplelocator\\] ([a-zA-Z0-9_]+): \\[([-]?[0-9]+) ([-]?[0-9]+) ([-]?[0-9]+)\\] > Tagged by: ([a-zA-Z0-9_]+)");
	private static Pattern groupcoords = Pattern.compile("([a-zA-Z0-9_]+) > \\[([-]?[0-9]+) ([-]?[0-9]+) ([-]?[0-9]+)\\] > Tagged by: ([a-zA-Z0-9_]+)");
	private static Pattern currentTag = Pattern.compile("You are in combat for ([0-9]+) seconds.");
	private static Pattern pearlTag = Pattern.compile("Pearl in ([0-9]+) seconds. Combat Tag in ([0-9]+) seconds.");
	private static Pattern startTag = Pattern.compile("\\[CombatTag\\] You have been hit by ([a-zA-Z0-9_]+). Type /ct to check your remaining tag time.");
	private static Pattern coordString = Pattern.compile("NAME: ([A-Za-z0-9]+?), X: ([-0-9.]+?), Z: ([-0-9.]+?), Y: ([0-9.]+?);");
	private static Minecraft mc;
	LocatorSettings settings = SimpleLocator.settings;
	public static HashMap<String, LocatorLocation> locations = new HashMap();
	private static final int segments = 20;
	private int loop = 0;
	private int locateloop = 0;
	private int sendLoop = 0;
	private int tagTime;
	private String tagger = "";
	public boolean combatTagged = false;
	SoundCategory catagory;
	private int pearlTimer;
	String gotlocations = null;
	final int TICKS = 30;
	private int pearlTimeRemaining;

	
	public LocatorListener() {
		mc = Minecraft.getMinecraft();
		
	}
	/** little buggy rn gonna wait on this
	@SubscribeEvent
	public void renderTimers(RenderGameOverlayEvent e) {
	if(e.type != ElementType.EXPERIENCE) {
		return;
	}
		FontRenderer fontrenderer = this.mc.fontRenderer;
		ScaledResolution sr = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
		int ctX = sr.getScaledWidth() / 2;
		int ctY = sr.getScaledHeight() / 2 + 7;
		int ptX = sr.getScaledWidth() / 2;
		int ptY = sr.getScaledHeight() / 2 + 15;
		int hX = sr.getScaledWidth() / 2;
		int hY = sr.getScaledHeight() / 2 + 24;
		int hpColor = 0xff0000;
		int ptColor = 0x0000ff;
		int ctColor = 0x00ff00;
		
		if(combatTagged) {
			int time = tagTime / TICKS;
			String s = "YOU ARE TAGGED FOR " + time + " SECONDS";
			GL11.glPushMatrix();
			GL11.glEnable(GL11.GL_BLEND);
			OpenGlHelper.glBlendFunc(770, 771, 1, 0);
			centeredString(s, ctX, ctY, ctColor);
			GL11.glDisable(GL11.GL_BLEND);
			GL11.glPopMatrix();
		} else {
			GL11.glPushMatrix();
			GL11.glEnable(GL11.GL_BLEND);
			OpenGlHelper.glBlendFunc(770, 771, 1, 0);
			centeredString("YOU ARE NOT IN COMBAT", ctX, ctY, ctColor);
			GL11.glDisable(GL11.GL_BLEND);
			GL11.glPopMatrix();
		}
		pearlTimeRemaining = pearlTimer / TICKS;
		String ps = "PEARL IN " + pearlTimeRemaining + " SECONDS";
		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_BLEND);
		OpenGlHelper.glBlendFunc(770, 771, 1, 0);
		centeredString(ps, ptX, ptY, ptColor);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glPopMatrix();
	} **/
	
	@SubscribeEvent
	public void onTick(TickEvent.ClientTickEvent event) {
		
		if (event.phase == TickEvent.Phase.START) {
			
			if (mc.theWorld == null) {
				synchronized (locations) {
					if (!locations.isEmpty()) {
						locations.clear();
					}
				}
				SimpleLocator.previousPlayerList = new ArrayList();

				return;
			} else if (mc.theWorld != null) {
				
				if(mc.thePlayer.inventory.inventoryChanged && IngameInvList.CivCrap) {
					getInv();
					mc.thePlayer.inventory.inventoryChanged = false;
				}
				if(pearlTimer > 0) {
					pearlTimer--;
				}
				
				if(this.combatTagged == true)
				{
					this.tagTime--;
					
					if(settings.getBroadcastCoords())
					{
						if((this.tagTime % 150 == 0) && ((this.tagTime / 30) < 30) && ((this.tagTime / 30) > 0))
						{
							if(!this.tagger.matches(""))
							{
								mc.thePlayer.sendChatMessage("/g simplelocator " + mc.thePlayer.getDisplayName() + " > [" + (int)mc.thePlayer.posX + " " + (int)mc.thePlayer.posY + " " + (int)mc.thePlayer.posZ + "] > Tagged by: " + this.tagger);
							}
							else
							{
								mc.thePlayer.sendChatMessage("/g simplelocator " + mc.thePlayer.getDisplayName() + " > [" + (int)mc.thePlayer.posX + " " + (int)mc.thePlayer.posY + " " + (int)mc.thePlayer.posZ + "] > Tagged by: none");
							}
						}
					}
					
					if(this.tagTime == 0)
					{
						mc.thePlayer.sendChatMessage("/ct");
					}
				}				
				
				// World Police Siren
				LocatorSettings settings = SimpleLocator.settings;
				if (settings.isWorldPoliceEnabled()) {
					
					loop++;
					
					if (loop >= 210) {
						loop = 0;
						mc.thePlayer.sendChatMessage("Official World Police Business!");
					} else if (loop == 30 || loop == 90 || loop == 150) {
						mc.thePlayer.sendChatMessage("Weeeee");
					} else if (loop == 60 || loop == 120 || loop == 180) {
						mc.thePlayer.sendChatMessage("Wooooo");
					}} else {
						loop = 210;
					}
			}
			if ((SimpleLocator.binding.getIsKeyPressed()) && (mc.inGameHasFocus)) {
				mc.displayGuiScreen(new GuiLocatorSettings());
			}
			sendLoop++;		
			if (sendLoop >= 300) {
				for (Object o : mc.theWorld.playerEntities) {
					if ((o instanceof EntityOtherPlayerMP)) {
						EntityOtherPlayerMP player = (EntityOtherPlayerMP) o;
						LocatorLocation l = new LocatorLocation(player);
						String name = player.getCommandSenderName();
						double x = l.getX();
						double z = l.getZ();
						double y = l.getY();
						WebInterfacer.sendLocation(name, x, y, z);
						sendLoop = 0;
					}
				}
			}
			ArrayList<String> playerList = new ArrayList();
			List players = mc.thePlayer.sendQueue.playerInfoList;
			for (Object o : players) {
				if ((o instanceof GuiPlayerInfo)) {
					GuiPlayerInfo info = (GuiPlayerInfo) o;

					playerList.add(SimpleLocator.filterChatColors(info.name));
				}
			}
			ArrayList<String> temp = (ArrayList) playerList.clone();
			playerList.removeAll(SimpleLocator.previousPlayerList);
			SimpleLocator.previousPlayerList.removeAll(temp);
			for (String player : SimpleLocator.previousPlayerList) {
				SimpleLocator.onPlayerLeave(player);
			}
			for (String player : playerList) {
				SimpleLocator.onPlayerJoin(player);
			}
			SimpleLocator.previousPlayerList = temp;
		}
	}

	@SubscribeEvent
	public void onChat(ClientChatReceivedEvent event) {
		
		System.out.println(event.message.getFormattedText());
		System.out.println(event.message.getUnformattedText());

		if(event.message.getUnformattedText().matches("You have been Combat Tagged on Login"))
		{
			combatTagged = true;
			tagTime = 30 * TICKS;
		}
		Matcher startTagMatcher = startTag.matcher(event.message.getFormattedText());
		if(startTagMatcher.find())
		{
			combatTagged = true;
			tagTime = 30 * TICKS;
			tagger = startTagMatcher.group(1);
		}
		Matcher pearlTagMatcher = pearlTag.matcher(event.message.getFormattedText());
		if(pearlTagMatcher.find())
		{
			if(combatTagged == false)
			{
				return;
			}
			combatTagged = true;
				int p = Integer.parseInt(pearlTagMatcher.group(1));
				pearlTimer = p * TICKS;
				int remainingTime = Integer.parseInt(pearlTagMatcher.group(2));
				tagTime = remainingTime * TICKS;
		}
		Matcher currentTagMatcher = currentTag.matcher(event.message.getFormattedText());
		if(currentTagMatcher.find())
		{
			event.setCanceled(true);
				int remainingTime = Integer.parseInt(currentTagMatcher.group(1));
				this.tagTime = remainingTime * TICKS;
		}
		if(event.message.getUnformattedText().matches("You are not currently in combat!"))
		{
			this.combatTagged = false;
			this.tagger = "";
		}
		
		Matcher snitchMatcher = snitch.matcher(event.message.getFormattedText());
		if (snitchMatcher.find()) {
			try {
				String username = snitchMatcher.group(1);
				int x = Integer.parseInt(snitchMatcher.group(2));
				int y = Integer.parseInt(snitchMatcher.group(3));
				int z = Integer.parseInt(snitchMatcher.group(4));
				//send to server 
				WebInterfacer.sendLocation(username, x, y, z);

				synchronized (locations) {
					LocatorLocation newLocation = new LocatorLocation(x, y, z, LocationType.SNITCH);
					LocatorLocation location = locations.get(username);

					boolean use = true;
					if ((location != null) && (newLocation.compareTo(location) <= 0)) {
						use = false;
					}
					if (use) {
						locations.put(username, newLocation);

						if (settings.getGroups().getByUsername(username).isSoundEnabled()) {
							mc.thePlayer.playSound("note.pling", .5F, 4.0F);
						}
					}
				}
				return;
			} catch (Exception e) {
			}
		}
		Matcher broadcastMatcher = broadcast.matcher(event.message.getFormattedText());
		if (broadcastMatcher.find()) {
			try {
				String username = broadcastMatcher.group(1);
				int x = Integer.parseInt(broadcastMatcher.group(2));
				int y = Integer.parseInt(broadcastMatcher.group(3));
				int z = Integer.parseInt(broadcastMatcher.group(4));
				//send location
				WebInterfacer.sendLocation(username, x, y, z);
				return;
			} catch (Exception e) {}
		}
		
		/**
		Matcher groupCoordsMatcher = groupcoords.matcher(event.message.getUnformattedText());
		if (groupCoordsMatcher.find()) 
		{
			try {
				String username = groupCoordsMatcher.group(1);
				//username = username.substring(1);
				int x = Integer.parseInt(groupCoordsMatcher.group(2));
				int y = Integer.parseInt(groupCoordsMatcher.group(3));
				int z = Integer.parseInt(groupCoordsMatcher.group(4));
				//send location
				WebInterfacer.sendLocation(username, x, y, z);

					synchronized (locations) {
						LocatorLocation newLocation = new LocatorLocation(x, y, z, LocationType.GROUPCOORDS);
						LocatorLocation location = locations.get(username);

						boolean use = true;
						if ((location != null)
								&& (newLocation.compareTo(location) <= 0)) {
							use = false;
						}
						if (use) {
							locations.put(username, newLocation);
						}
					
					return;
				}
			} catch (Exception e) {
			}
		} **/
	}

	@SubscribeEvent
	public void onRenderWorldLast(RenderWorldLastEvent event) {
		LocatorSettings settings = SimpleLocator.settings;
		if (settings.isRenderEnabled()) {
			locations = prepLocations();
			float opacity = settings.getOpacity();
			float guiScale = settings.getScale();
			int color = settings.getDefaultColor() != null ? settings
					.getDefaultColor().getRGB() : -1;

			int maxDistance = settings.getMaxViewDistance();
			HashMap<String, LocatorLocation> locationsClone;
			synchronized (locations) {
				locationsClone = (HashMap) locations.clone();
			}
			List<LocationGroup> groupsClone = new ArrayList();
			synchronized (GroupingThread.groups) {
				groupsClone.addAll(GroupingThread.groups);
			}
			for (LocationGroup group : groupsClone) {
				List<String> message = new ArrayList();

				Iterator<String> usernameIterator = group.iterator();
				while (usernameIterator.hasNext()) {
					String username = usernameIterator.next();

					LocatorLocation location = locationsClone.remove(username);
					if (location != null) {
						GroupConfiguration groupConfiguration = settings.getGroups().getByUsername(username);
						int maxDistUser = groupConfiguration != null ? groupConfiguration.getMaxViewDistanceOverride() : maxDistance;
						int maxAge = groupConfiguration != null ? groupConfiguration.getExpirationTimeOverride() : settings.getExpirationTime();
						color = -1;
						if(groupConfiguration != null)
						{
							if(groupConfiguration.getColor() != null)
							{
								color = groupConfiguration.getColor().getRGB();
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

						if (location.getAge() > maxAge) {
							synchronized (locations) {
								locations.remove(username);
							}
						} else if ((!isVisible(username))
								&& ((settings.isShowExactOfflineEnabled())
										|| (!location.getType().isExact()) || (isOnline(username)))) {
							double xLength = location.getX()
									- mc.thePlayer.posX;
							double yLength = location.getY()
									- mc.thePlayer.posY;
							double zLength = location.getZ()
									- mc.thePlayer.posZ;

							double distance = Math.sqrt(xLength * xLength
									+ yLength * yLength + zLength * zLength);
							if (distance <= maxDistUser) {
								boolean showTime = !location.getType()
										.isRemote();

								message.add(username
										+ " ("
										+ Math.round(distance)
										+ "m)"
										+ (showTime ? " ("
												+ formatTime(location.getAge())
												+ ")" : "") + " "
										+ location.getType().getIndicator());
							}
						}
					}
				}
				double xLength = group.getX() - mc.thePlayer.posX;
				double yLength = group.getY() - mc.thePlayer.posY;
				double zLength = group.getZ() - mc.thePlayer.posZ;

				double distance = Math.sqrt(xLength * xLength + yLength
						* yLength + zLength * zLength);

				double scale = 50.0D;

				xLength /= distance;
				yLength /= distance;
				zLength /= distance;

				xLength *= scale;
				yLength *= scale;
				zLength *= scale;

				renderText(message.toArray(new String[0]),
						(float) (mc.thePlayer.posX + xLength),
						(float) (mc.thePlayer.posY + yLength),
						(float) (mc.thePlayer.posZ + zLength), color, true,
						0.0F, guiScale, opacity);
			}
			Object names = locationsClone.keySet().iterator();
			while (((Iterator) names).hasNext()) {
				String name = (String) ((Iterator) names).next();
				if (!isVisible(name)) {
					LocatorLocation loc = locationsClone.get(name);
					if (renderLocation(name, loc)) {
						double xLength = loc.getX() - mc.thePlayer.posX;
						double yLength = loc.getY() - mc.thePlayer.posY;
						double zLength = loc.getZ() - mc.thePlayer.posZ;

						double distance = Math.sqrt(xLength * xLength + yLength
								* yLength + zLength * zLength);

						boolean showTime = !loc.getType().isRemote();

						double scale = 50.0D;

						xLength /= distance;
						yLength /= distance;
						zLength /= distance;

						xLength *= scale;
						yLength *= scale;
						zLength *= scale;

						renderText(
								new String[] {
										name,
										"("
												+ Math.round(distance)
												+ "m)"
												+ (showTime ? " ("
														+ formatTime(loc.getAge())
														+ ")"
														: ""),
										loc.getType().getIndicator() },
								(float) (mc.thePlayer.posX + xLength),
								(float) (mc.thePlayer.posY + yLength),
								(float) (mc.thePlayer.posZ + zLength), color,
								true, 0.0F, guiScale, opacity);
					}
				}
			}
		}
	}

	@SubscribeEvent
	public void onRenderPlayer(RenderPlayerEvent.Post event) {
		if ((event.entity instanceof EntityOtherPlayerMP)) {
			EntityOtherPlayerMP e = (EntityOtherPlayerMP) event.entity;

			LocatorSettings settings = SimpleLocator.settings;

			GroupConfiguration group = settings.getGroups().getByUsername(
					SimpleLocator.filterChatColors(e.getCommandSenderName()));
			if (group != null) {
				Color c = group.getColor();
				if (c == null) {
					c = settings.getDefaultColor();
				}
				if (c != null) {
					render(-(RenderManager.renderPosX - e.posX),
							-(RenderManager.renderPosY - e.posY),
							-(RenderManager.renderPosZ - e.posZ), e, c);
				}
			}
		}
	}

	private void render(double x, double y, double z, EntityLivingBase e, Color c) {
		GL11.glBlendFunc(770, 771);
		GL11.glLineWidth(5.0F);
		GL11.glDisable(2896);
		GL11.glDisable(3553);
		GL11.glEnable(3042);
		GL11.glEnable(2848);
		GL11.glEnable(32925);
		GL11.glAlphaFunc(516, 0.09F);

		GL11.glPushMatrix();
		GL11.glTranslated(x, y, z);
		GL11.glRotatef(e.rotationYaw, 0.0F, (float) y, 0.0F);
		GL11.glTranslated(-x, -y, -z);
		GL11.glTranslated(0.0D, e.isSneaking() ? 0.2D : 0.1D, 0.0D);
		AxisAlignedBB bb = AxisAlignedBB.getBoundingBox(x - e.width, y, z
				- e.width, x + e.width, y + e.height, z + e.width);
		if (((e instanceof EntityPlayer)) || ((e instanceof EntityZombie))
				|| ((e instanceof EntitySkeleton))
				|| ((e instanceof EntityEnderman))) {
			bb = bb.contract(0.25D, 0.0D, 0.25D);
		}
		GL11.glColor3f(c.getRed() / 255.0F, c.getGreen() / 255.0F,
				c.getBlue() / 255.0F);

		drawCircle(bb);
		GL11.glPopMatrix();

		GL11.glDisable(2848);
		GL11.glDisable(3042);
		GL11.glEnable(3553);
		GL11.glDisable(32925);
		GL11.glEnable(2896);
	}

	public static void drawCircle(AxisAlignedBB bb) {
		Tessellator t = Tessellator.instance;

		double centerX = (bb.minX + bb.maxX) / 2.0D;
		double centerY = (bb.minY + bb.maxY) / 2.0D;
		double centerZ = (bb.minZ + bb.maxZ) / 2.0D;

		t.startDrawing(3);
		for (float i = 0.0F; i < 21.0F; i += 1.0F) {
			t.addVertex(Math.cos(i / 20.0F * 2.0F * 3.141592653589793D) * 0.6D + centerX, centerY, Math.sin(i / 20.0F * 2.0F * 3.141592653589793D) * 0.6D + centerZ);
		}
		t.draw();
	}

	public static void drawCrossedOutlinedBoundingBox(AxisAlignedBB bb) {
		Tessellator t = Tessellator.instance;
		t.startDrawing(3);
		t.addVertex(bb.minX, bb.minY, bb.minZ);
		t.addVertex(bb.maxX, bb.minY, bb.minZ);
		t.addVertex(bb.maxX, bb.minY, bb.maxZ);
		t.addVertex(bb.minX, bb.minY, bb.maxZ);
		t.addVertex(bb.minX, bb.minY, bb.minZ);
		t.draw();
		t.startDrawing(3);
		t.addVertex(bb.minX, bb.maxY, bb.minZ);
		t.addVertex(bb.maxX, bb.maxY, bb.minZ);
		t.addVertex(bb.maxX, bb.maxY, bb.maxZ);
		t.addVertex(bb.minX, bb.maxY, bb.maxZ);
		t.addVertex(bb.minX, bb.maxY, bb.minZ);
		t.draw();
		t.startDrawing(1);
		t.addVertex(bb.minX, bb.minY, bb.minZ);
		t.addVertex(bb.minX, bb.maxY, bb.minZ);
		t.addVertex(bb.maxX, bb.minY, bb.minZ);
		t.addVertex(bb.maxX, bb.maxY, bb.minZ);
		t.addVertex(bb.maxX, bb.minY, bb.maxZ);
		t.addVertex(bb.maxX, bb.maxY, bb.maxZ);
		t.addVertex(bb.minX, bb.minY, bb.maxZ);
		t.addVertex(bb.minX, bb.maxY, bb.maxZ);
		t.addVertex(bb.minX, bb.minY, bb.minZ);
		t.addVertex(bb.minX, bb.maxY, bb.maxZ);
		t.addVertex(bb.maxX, bb.minY, bb.minZ);
		t.addVertex(bb.maxX, bb.maxY, bb.maxZ);
		t.draw();
	}

	public static boolean isVisible(String username) {
		if (username.equals(mc.thePlayer.getCommandSenderName())) {
			return true;
		}
		for (Object o : mc.theWorld.playerEntities) {
			if (((o instanceof EntityOtherPlayerMP)) && (SimpleLocator.filterChatColors(((EntityOtherPlayerMP) o).getCommandSenderName()).equals(username))) {
				return true;
			}
		}
		return false;
	}

	public static boolean isOnline(String username) {
		username = SimpleLocator.filterChatColors(username);

		List players = mc.thePlayer.sendQueue.playerInfoList;
		for (Object o : players) {
			if ((o instanceof GuiPlayerInfo)) {
				GuiPlayerInfo info = (GuiPlayerInfo) o;
				if (SimpleLocator.filterChatColors(info.name).equals(username)) {
					return true;
				}
			}
		}
		return false;
	}

	public static void renderText(String[] text, float x, float y, float z,
			int color, boolean renderBlackBox, float partialTickTime,
			float guiScale, float opacity) {
		color = (int) (opacity * 255.0F) << 24 | color & 0xFFFFFF;

		RenderManager renderManager = RenderManager.instance;
		FontRenderer fontRenderer = mc.fontRenderer;

		float playerX = (float) (mc.thePlayer.lastTickPosX + (mc.thePlayer.posX - mc.thePlayer.lastTickPosX)
				* partialTickTime);
		float playerY = (float) (mc.thePlayer.lastTickPosY + (mc.thePlayer.posY - mc.thePlayer.lastTickPosY)
				* partialTickTime);
		float playerZ = (float) (mc.thePlayer.lastTickPosZ + (mc.thePlayer.posZ - mc.thePlayer.lastTickPosZ)
				* partialTickTime);

		float dx = x - playerX;
		float dy = y - playerY;
		float dz = z - playerZ;
		float distance = (float) Math.sqrt(dx * dx + dy * dy + dz * dz);
		float multiplier = distance / 120.0F;
		float scale = 0.45F * multiplier;
		scale *= guiScale;

		GL11.glPushMatrix();
		GL11.glTranslatef(dx, dy, dz);
		GL11.glRotatef(-renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
		GL11.glScalef(-scale, -scale, scale);
		GL11.glDisable(2896);
		GL11.glDepthMask(false);
		GL11.glDisable(2929);
		GL11.glEnable(3042);
		GL11.glBlendFunc(770, 771);

		int textWidth = 0;
		for (String line : text) {
			int current = fontRenderer.getStringWidth(line);
			if (current > textWidth) {
				textWidth = current;
			}
		}
		int lineHeight = 10;
		if (renderBlackBox) {
			Tessellator tessellator = Tessellator.instance;
			GL11.glDisable(3553);
			tessellator.startDrawingQuads();
			int stringMiddle = textWidth / 2;
			tessellator.setColorRGBA_F(0.0F, 0.0F, 0.0F, opacity);
			tessellator.addVertex(-stringMiddle - 1, -1.0D, 0.0D);
			tessellator.addVertex(-stringMiddle - 1, 8 + lineHeight
					* text.length - lineHeight, 0.0D);
			tessellator.addVertex(stringMiddle + 1, 8 + lineHeight
					* text.length - lineHeight, 0.0D);
			tessellator.addVertex(stringMiddle + 1, -1.0D, 0.0D);
			tessellator.draw();
			GL11.glEnable(3553);
		}
		int i = 0;
		for (String message : text) {
			int messageLength = fontRenderer.getStringWidth(message);
			fontRenderer.drawString(message, -messageLength / 2,
					i * lineHeight, color);
			i++;
		}
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glDepthMask(true);
		GL11.glEnable(2929);
		GL11.glPopMatrix();
	}

	private static String formatTime(long time) {
		if (time < 99000L) {
			return time / 1000L + " sec";
		}
		return time / 1000L / 60L + " min";
	}

	public static boolean renderLocation(String username,
			LocatorLocation location) {
		LocatorSettings settings = SimpleLocator.settings;

		GroupConfiguration groupConfiguration = settings.getGroups()
				.getByUsername(username);
		int maxDistUser = groupConfiguration != null ? groupConfiguration
				.getMaxViewDistanceOverride() : settings.getMaxViewDistance();
		int maxAge = groupConfiguration != null ? groupConfiguration
				.getExpirationTimeOverride() : settings.getExpirationTime();
		if (location.getAge() > maxAge) {
			synchronized (locations) {
				locations.remove(username);
			}
			return false;
		}
		if (isVisible(username)) {
			return false;
		}
		if ((!settings.isShowExactOfflineEnabled())
				&& (location.getType().isExact()) && (!isOnline(username))) {
			return false;
		}
		double xLength = location.getX() - mc.thePlayer.posX;
		double yLength = location.getY() - mc.thePlayer.posY;
		double zLength = location.getZ() - mc.thePlayer.posZ;

		double distance = Math.sqrt(xLength * xLength + yLength * yLength
				+ zLength * zLength);
		if (distance > maxDistUser) {
			return false;
		}
		return true;
	}
	
	public void centeredString(String s, int x, int y, int color) {
		int width = mc.fontRenderer.getStringWidth(s) / 2;
		mc.fontRenderer.drawStringWithShadow(s, x - width, y, color);
	}
	
	public void getInv() {
		float health = mc.thePlayer.getHealth();
		boolean second = false;
		boolean secondHelm = false;
		boolean secondChest = false;
		boolean secondLegs = false;
		boolean secondBoots = false;
		int helm = 0;
		int chest = 0;
		int legs = 0;
		int boots = 0;
		int pots = 0;
		int fire = 0;
		int speed = 0;
		int regen = 0;
		int str = 0;
		int pearls = 0;
		ItemStack[] stack = mc.thePlayer.inventory.mainInventory;
		for(ItemStack o : stack) {
			if(o != null) {
				Item i = o.getItem();
				int id = Item.getIdFromItem(o.getItem());
				int dv = o.getItemDamage();
				int damage = i.getMaxDamage() - o.getItemDamageForDisplay();
				if(id == 368) {
					pearls += o.stackSize;
				}
				if(id == 310 && damage > i.getMaxDamage() / 2) {
					secondHelm = true;
				}
				if(id == 311 && damage > i.getMaxDamage() / 2) {
					secondChest = true;
				}
				if(id == 312 && damage > i.getMaxDamage() / 2) {
					secondLegs = true;
				}
				if(id == 313 && damage > i.getMaxDamage() / 2) {
					secondBoots = true;
				}
				if(id == 373) {
					
					if(dv == 8257) {
						regen++;
					}
					if(dv == 8258 || dv == 8226) {
						speed++;
					}
					if(dv == 8259) {
						fire++;
					}
					if(dv == 8233 || dv == 8265) {
						str++;
					}
					if(dv == 16421) {
						pots++;
					}
				}
			}
		}
		
		ItemStack[] armor = mc.thePlayer.inventory.armorInventory;
		for(ItemStack i : armor) {
			if(i != null) {
				int damage = i.getItemDamageForDisplay();
				System.out.println(damage);
				int id = Item.getIdFromItem(i.getItem());
				if(id == 310) {
					helm = damage;
				}
				if(id == 311) {
					chest = damage;
				}
				if(id == 312) {
					legs = damage;
				}
				if(id == 313) {
					boots = damage;
				}
			}
		}
		if(secondHelm && secondChest && secondLegs && secondBoots) {
			second = true;
		}
		WebInterfacer.sendInvData(health, helm, chest, legs, boots, second, pearls, pots, fire, speed, str, regen);
	}
	
	private boolean ticked = false;
    private boolean firstload = true;

    @SubscribeEvent
    public void RenderTickEvent(RenderTickEvent event) {
        if ((event.type == Type.RENDER || event.type == Type.CLIENT) && event.phase == Phase.END) {
            Minecraft mc = Minecraft.getMinecraft();
            if (!ticked && mc.ingameGUI != null) {
                mc.ingameGUI = new IngameInvList(mc);
                ticked = true;
            }
        }
    }
    
    public HashMap<String, LocatorLocation> prepLocations() {
    	HashMap<String, LocatorLocation> locationMap = new HashMap();
    	locateloop++;		
		if (locateloop >= 300) {
			gotlocations = WebInterfacer.getLocations();
			locateloop = 0;
			String[] locationArray = gotlocations.split("<br>");
			int size = locationArray.length;
			String[] names = new String[size];
			double[] xArray = new double[size];
			double[] zArray = new double[size];
			double[] yArray = new double[size];
			
			for(int i = 0; i < size; i++) {
				Matcher locationMatcher = coordString.matcher(locationArray[i]);
				while(locationMatcher.find()) {
					names[i] = locationMatcher.group(1);
					try {
						xArray[i] = Integer.parseInt(locationMatcher.group(2));
						zArray[i] = Integer.parseInt(locationMatcher.group(3));
						yArray[i] = Integer.parseInt(locationMatcher.group(4));
					} catch (Exception e) { e.printStackTrace(); }
				}
			}
			for(int i = 0; i < size; i++) {
				String s = names[i];
				double x = xArray[i];
				double z = zArray[i];
				double y = yArray[i];
				locationMap.put(s, new LocatorLocation(x, y, z, LocationType.EXACT));
			}
			
		}
		return locationMap;
    }
}

/*
 * Location: C:\Users\Will\Documents\SimpleLocator\SimpleLocator_v2_2.jar
 * 
 * Qualified Name: shadowjay1.forge.simplelocator.LocatorListener
 * 
 * JD-Core Version: 0.7.0.1
 */
