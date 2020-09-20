package com.alyxferrari.essentialsxy.managers;
import java.util.ArrayList;
import com.alyxferrari.essentialsxy.EssentialsMod;
import com.alyxferrari.essentialsxy.types.Vec3;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.TickEvent.ServerTickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedOutEvent;
public class AfkManager {
	public static final int afkDefinition = 2400;
	public static final ArrayList<Vec3> previous = new ArrayList<Vec3>();
	public static final ArrayList<Integer> ticksAfk = new ArrayList<Integer>();
	public static final ArrayList<Boolean> isAfk = new ArrayList<Boolean>();
	private AfkManager() {}
	public static void onPlayerConnect(PlayerLoggedInEvent event) {
		previous.add(new Vec3(event.getPlayer().lastTickPosX, event.getPlayer().lastTickPosY, event.getPlayer().lastTickPosZ));
		ticksAfk.add(0);
		isAfk.add(false);
	}
	public static void onPlayerDisconnect(PlayerLoggedOutEvent event) {
		for (int i = 0; i < EssentialsMod.players.size(); i++) {
			if (EssentialsMod.players.get(i).getEntityId() == event.getPlayer().getEntityId()) {
				EssentialsMod.players.remove(i);
				previous.remove(i);
				ticksAfk.remove(i);
				isAfk.remove(i);
				return;
			}
		}
	}
	public static void onChat(ServerChatEvent event) {
		for (int x = 0; x < EssentialsMod.players.size(); x++) {
			if (EssentialsMod.players.get(x).getEntityId() == event.getPlayer().getEntityId()) {
				if (ticksAfk.get(x) > afkDefinition) {
					isAfk.set(x, false);
					for (int y = 0; y < EssentialsMod.players.size(); y++) {
						EssentialsMod.players.get(y).sendMessage(new StringTextComponent("\u00A77* \u00A73" + EssentialsMod.players.get(x).getName().getString() + "\u00A77 is no longer AFK."), EssentialsMod.players.get(y).getUniqueID());
					}
				}
				ticksAfk.set(x, 0);
				return;
			}
		}
	}
	public static void onServerTick(ServerTickEvent event) {
		for (int x = 0; x < EssentialsMod.players.size(); x++) {
			Vec3 pos = previous.get(x);
			PlayerEntity player = EssentialsMod.players.get(x);
			if (pos.x == player.lastTickPosX && pos.y == player.lastTickPosY && pos.z == player.lastTickPosZ) {
				ticksAfk.set(x, ticksAfk.get(x) + 1);
			} else {
				if (ticksAfk.get(x) > afkDefinition) {
					isAfk.set(x, false);
					for (int y = 0; y < EssentialsMod.players.size(); y++) {
						EssentialsMod.players.get(y).sendMessage(new StringTextComponent("\u00A77* \u00A73" + EssentialsMod.players.get(x).getName().getString() + "\u00A77 is no longer AFK."), EssentialsMod.players.get(y).getUniqueID());
					}
				}
				ticksAfk.set(x, 0);
			}
			if (ticksAfk.get(x) == afkDefinition) {
				isAfk.set(x, true);
				for (int y = 0; y < EssentialsMod.players.size(); y++) {
					EssentialsMod.players.get(y).sendMessage(new StringTextComponent("\u00A77* \u00A73" + EssentialsMod.players.get(x).getName().getString() + "\u00A77 is now AFK."), EssentialsMod.players.get(y).getUniqueID());
				}
			}
			previous.set(x, new Vec3(player.lastTickPosX, player.lastTickPosY, player.lastTickPosZ));
		}
	}
}