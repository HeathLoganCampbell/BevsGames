package games.bevs.library.commons.utils;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ActionBarUtils {
	
	private static String nmsVersion;
	private static boolean useOldMethods = false;
	
	static
	{
		nmsVersion = Bukkit.getServer().getClass().getPackage().getName();
		nmsVersion = nmsVersion.substring(nmsVersion.lastIndexOf(".") + 1);
		
		if ((nmsVersion.equalsIgnoreCase("v1_8_R1")) || (nmsVersion.equalsIgnoreCase("v1_7_")))
			useOldMethods = true;

	}

//	public static void sendActionBar(Player player, String message) {
//		if(nmsVersion == null) {
//			nmsVersion = (nmsVersion = Bukkit.getServer().getClass().getPackage().getName()).substring(nmsVersion.lastIndexOf(".") + 1);
//		}
//		try {
//			Class<?> c1 = Class.forName("org.bukkit.craftbukkit." + nmsVersion + ".entity.CraftPlayer");
//			Object p = c1.cast(player);
//			Object ppoc;
//			Class<?> c2, c3,
//					c4 = Class.forName("net.minecraft.server." + nmsVersion + ".PacketPlayOutChat"),
//					c5 = Class.forName("net.minecraft.server." + nmsVersion + ".Packet");
//			Object o;
//			
//			
//			
//			if ((nmsVersion.equalsIgnoreCase("v1_8_R1") || !nmsVersion.startsWith("v1_8_")) && !nmsVersion.startsWith("v1_9_")) {
//				c2 = Class.forName("net.minecraft.server." + nmsVersion + ".ChatSerializer");
//				c3 = Class.forName("net.minecraft.server." + nmsVersion + ".IChatBaseComponent");
//				Method m3 = c2.getDeclaredMethod("a", String.class);
//				o = c3.cast(m3.invoke(c2, "{\"text\": \"" + message + "\"}"));
//			} else if() {
//				
//			}else {
//				c2 = Class.forName("net.minecraft.server." + nmsVersion + ".ChatComponentText");
//				c3 = Class.forName("net.minecraft.server." + nmsVersion + ".IChatBaseComponent");
//				o = c2.getConstructor(new Class<?>[]{String.class}).newInstance(message);
//			}
//			
//			
//			ppoc = c4.getConstructor(new Class<?>[]{c3, byte.class}).newInstance(o, (byte) 2);
//			Method m1 = c1.getDeclaredMethod("getHandle");
//			Object h = m1.invoke(p);
//			Field f1 = h.getClass().getDeclaredField("playerConnection");
//			Object pc = f1.get(h);
//			Method m5 = pc.getClass().getDeclaredMethod("sendPacket", c5);
//			m5.invoke(pc, ppoc);
//		} catch (Exception ex) {
//			ex.printStackTrace();
//		}
//	}
	
	public static void sendActionBar(Player player, String message) {
	    try
	    {
	      Class<?> c1 = Class.forName("org.bukkit.craftbukkit." + nmsVersion + ".entity.CraftPlayer");
	      Object p = c1.cast(player);

	      Class<?> c4 = Class.forName("net.minecraft.server." + nmsVersion + ".PacketPlayOutChat");
	      Class<?> c5 = Class.forName("net.minecraft.server." + nmsVersion + ".Packet");
	      Object ppoc;
	      if (useOldMethods) {
	        Class<?> c2 = Class.forName("net.minecraft.server." + nmsVersion + ".ChatSerializer");
	        Class<?> c3 = Class.forName("net.minecraft.server." + nmsVersion + ".IChatBaseComponent");
	        Method m3 = c2.getDeclaredMethod("a", new Class[] { String.class });
	        Object cbc = c3.cast(m3.invoke(c2, new Object[] { "{\"text\": \"" + message + "\"}" }));
	        ppoc = c4.getConstructor(new Class[] { c3, Byte.TYPE }).newInstance(new Object[] { cbc, (byte) 2 });
	      } else {
	        Class<?> c2 = Class.forName("net.minecraft.server." + nmsVersion + ".ChatComponentText");
	        Class<?> c3 = Class.forName("net.minecraft.server." + nmsVersion + ".IChatBaseComponent");
	        Object o = c2.getConstructor(new Class[] { String.class }).newInstance(new Object[] { message });
	        ppoc = c4.getConstructor(new Class[] { c3, Byte.TYPE }).newInstance(new Object[] { o, (byte) 2 });
	      }
	      Method m1 = c1.getDeclaredMethod("getHandle", new Class[0]);
	      Object h = m1.invoke(p, new Object[0]);
	      Field f1 = h.getClass().getDeclaredField("playerConnection");
	      Object pc = f1.get(h);
	      Method m5 = pc.getClass().getDeclaredMethod("sendPacket", new Class[] { c5 });
	      m5.invoke(pc, new Object[] { ppoc });
	    } catch (Exception ex) {
	      ex.printStackTrace();
	    }
	  }
	
}