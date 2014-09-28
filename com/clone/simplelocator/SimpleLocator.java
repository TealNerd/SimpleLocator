package com.clone.simplelocator;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.EventBus;
import cpw.mods.fml.relauncher.SideOnly;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;














//import javax.media.CannotRealizeException;
//import javax.media.Manager;
//import javax.media.NoPlayerException;
//import javax.media.Player;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.Mixer.Info;
import javax.sound.sampled.Port;
import javax.sound.sampled.UnsupportedAudioFileException;

//import sun.audio.AudioData;
//import sun.audio.AudioPlayer;
//import sun.audio.AudioStream;
//import sun.audio.ContinuousAudioDataStream;














import org.lwjgl.input.Keyboard;

import com.clone.RadarBro.GuiRadarBro;
import com.clone.RadarBro.GuiRepositionRadarBro;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundCategory;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.launchwrapper.LaunchClassLoader;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;

@Mod(modid = "clonedlocator", name = "ClonedLocator", version = "v5.1")
public class SimpleLocator {
	@Mod.Instance("ClonedLocator")
	public static final String version = "5.1";
	public static SimpleLocator instance;
	public static LocatorSettings settings;
	public static File configFile;
	static List<String> previousPlayerList = new ArrayList();
	public static RenderItem itemRenderer = new RenderItem();
	public static HashMap<String, BufferedImage> HeadIconCache = new HashMap();
	public static KeyBinding binding;
	public static Minecraft mc;
	
	public static Clip clip;
	public static InputStreamReader audioInputStream;
	public static FloatControl madeonVolume;
	public static SoundCategory catagory;
	
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		File directory = event.getModConfigurationDirectory();
		if (!directory.isDirectory()) {
			directory.mkdir();
		}
		configFile = new File(directory, "simplelocator.json");
		if (!configFile.isFile()) {
			try {
				configFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			settings = new LocatorSettings();
			settings.save(configFile);
		} else {
			settings = LocatorSettings.load(configFile);
			if (settings == null) {
				settings = new LocatorSettings();
			}
			settings.save(configFile);
		}
		try {
			LaunchClassLoader l = (LaunchClassLoader) getClass()
					.getClassLoader();

			Field field = LaunchClassLoader.class
					.getDeclaredField("classLoaderExceptions");

			field.setAccessible(true);
			Set<String> exclusions = (Set) field.get(l);

			exclusions.remove("org.apache.");
		} catch (Exception e) {
			e.printStackTrace();
		}

		for (GroupConfiguration group : settings.getGroups()) {
			if ((group.getUpdateURL() != null)
					&& (!group.getUpdateURL().trim().isEmpty())) {
				new GroupUpdateThread(group).start();
			}
		}
	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent event) 
	{
		GroupingThread groupingThread = new GroupingThread();
		groupingThread.start();

		this.mc = Minecraft.getMinecraft();
		
		GuiRepositionRadarBro.xOffset = settings.getRadarPosX();
		GuiRepositionRadarBro.yOffset = settings.getRadarPosY();
		
		LocatorListener listener = new LocatorListener();
		MinecraftForge.EVENT_BUS.register(new IngameInvList(mc));
		FMLCommonHandler.instance().bus().register(new IngameInvList(mc));
		MinecraftForge.EVENT_BUS.register(listener);
		MinecraftForge.EVENT_BUS.register(new
		GuiRadarBro(Minecraft.getMinecraft()));
		FMLCommonHandler.instance().bus().register(listener);

		ClientRegistry.registerKeyBinding(SimpleLocator.binding = new KeyBinding("Locator settings", 38, "SimpleLocator"));
		
			
	}

	public static void saveConfiguration() {
		settings.save(configFile);
	}

	public static BufferedImage getIconFromCache(String url) {
		BufferedImage bufferedimage = (BufferedImage) HeadIconCache.get(url);
		if (bufferedimage != null) {
			return bufferedimage;
		}
		return null;
	}

	public static String filterChatColors(String s) {
		return EnumChatFormatting.getTextWithoutFormattingCodes(s);
	}

	public static void onPlayerLeave(String player) {
		GroupConfiguration group = settings.getGroups().getByUsername(player);
		if ((group != null) && (group.isTrackingOnline())) {
			Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(new ChatComponentText(EnumChatFormatting.GREEN + "" + EnumChatFormatting.BOLD + player	+ EnumChatFormatting.RESET + EnumChatFormatting.YELLOW + " of group "	+ EnumChatFormatting.RED + ""	+ EnumChatFormatting.BOLD + group.getName()	+ EnumChatFormatting.LIGHT_PURPLE + " left"	+ EnumChatFormatting.RESET + EnumChatFormatting.YELLOW + " the server."));
			if(group.isSoundEnabled())
			{
				mc.thePlayer.playSound("note.base", 0.5f, 4.0f);
			}
		}
	}

	public static void onPlayerJoin(String player) {
		GroupConfiguration group = settings.getGroups().getByUsername(player);
		if ((group != null) && (group.isTrackingOnline())) {
			Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(new ChatComponentText(EnumChatFormatting.GREEN + "" + EnumChatFormatting.BOLD + player	+ EnumChatFormatting.RESET + EnumChatFormatting.YELLOW + " of group " + EnumChatFormatting.RED + ""	+ EnumChatFormatting.BOLD + group.getName()	+ EnumChatFormatting.BLUE + " joined" + EnumChatFormatting.RESET + EnumChatFormatting.YELLOW + " the server."));
			if(group.isSoundEnabled())
			{
				mc.thePlayer.playSound("note.base", 0.5f, 4.0f);
			}
		}
	}
}

/*
 * Location: C:\Users\Will\Documents\SimpleLocator\SimpleLocator_v2_2.jar
 * 
 * Qualified Name: shadowjay1.forge.simplelocator.SimpleLocator
 * 
 * JD-Core Version: 0.7.0.1
 */