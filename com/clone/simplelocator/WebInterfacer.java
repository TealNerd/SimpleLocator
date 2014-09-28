package com.clone.simplelocator;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import net.minecraft.client.Minecraft;

public class WebInterfacer {
	
	protected static String playerName = Minecraft.getMinecraft().thePlayer.getCommandSenderName();
	
	public static void getLocations() {
		post("http://dydoisbutts.info/getlocations.php", "?name=" + playerName);
	}
	
	public static void sendLocation(String name, double x, double y, double z) {
		post("http://dydoisbutts.info/locate.php", "?name=" + playerName + "&located=" + name + "&x=" + x + "&y=" + y +"&z=" + z);
	}
	
	public static void sendInvData(float health, int helm, int chest, int legs, int boots, boolean second, int pearls, int healths, int fire, int speed, int str, int regen) {
		post("http://dydoisbutts.info/inv.php", "?name=" + playerName + "&health=" + health + "&helm=" + helm + "&chest=" + chest + "&legs=" + legs + "&boots=" + boots + "&second=" + second + "&pearls=" + pearls + "&healths=" + healths + "&fire=" + fire + "&speed=" + speed + "&str=" + str + "&regen=" + regen);
	}
	
	public static String getInvData() {
		return post("http://dydoisbutts.info/getinv.php", "?name=" + playerName);
	}
	
	public static void sendCT(boolean tagged) {
		post("http://dydoisbutts.info/combat.php", "?action=" + playerName + "&tagged=" + tagged);
	}
	
	public static void getCT() {
		post("http://dydoisbutts.info/combat.php", "?action=get");
	}
	
	public static String post(String urlString, String params) {
		HttpURLConnection conn = null;
		try {
			URL url = new URL(urlString + params);
			conn = (HttpURLConnection)url.openConnection();
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("Content-Length", "" + Integer.toString(params.getBytes().length));
			conn.setRequestProperty("Content-Language", "en-US");
			conn.setUseCaches(false);
			conn.setDoInput(true);
			conn.setDoOutput(true);

			DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
			wr.writeBytes(params);
			wr.flush();
			wr.close();
			
			InputStream is = conn.getInputStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(is));
			String line;
			StringBuffer response = new StringBuffer();
			while((line = rd.readLine()) != null) {
				response.append(line);
				response.append('\r');
			}
			rd.close();
			return response.toString();
			
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		finally {
			if(conn != null) {
				conn.disconnect();
			}
		}
	}
}
