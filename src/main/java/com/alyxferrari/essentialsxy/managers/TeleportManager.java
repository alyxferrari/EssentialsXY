package com.alyxferrari.essentialsxy.managers;
import java.util.ArrayList;
import java.util.UUID;

import com.alyxferrari.essentialsxy.EssentialsMod;
import com.alyxferrari.essentialsxy.MinecraftColors;
import com.alyxferrari.essentialsxy.types.TeleportRequest;
import com.alyxferrari.essentialsxy.types.TeleportRequestType;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedOutEvent;
public class TeleportManager {
	private TeleportManager() {}
	private static boolean init = false;
	public static final ArrayList<TeleportRequest> requests = new ArrayList<TeleportRequest>();
	public static void init() {
		if (!init) {
			for (int i = 0; i < 300; i++) {
				requests.add(null);
			}
		}
		init = true;
	}
	public static void initiateTeleportToRequest(int requesterEntityID, int requesteeEntityID) {
		for (int x = 0; x < EssentialsMod.players.size(); x++) {
			if (EssentialsMod.players.get(x).getEntityId() == requesteeEntityID) {
				requests.set(x, new TeleportRequest(requesterEntityID, TeleportRequestType.TPA));
				String requester = null;
				for (int y = 0; y < EssentialsMod.players.size(); y++) {
					if (EssentialsMod.players.get(y).getEntityId() == requesterEntityID) {
						requester = EssentialsMod.players.get(y).getName().getString();
					}
				}
				if (requester == null) {
					requests.set(x, null);
				} else {
					PlayerEntity player = EssentialsMod.players.get(x);
					UUID uuid = player.getUniqueID();
					player.sendMessage(new StringTextComponent(MinecraftColors.GREEN + requester + MinecraftColors.GOLD + " has requested to teleport to you."), uuid);
					player.sendMessage(new StringTextComponent(MinecraftColors.GOLD + "To teleport, type " + MinecraftColors.RED + "/tpaccept" + MinecraftColors.GOLD + "."), uuid);
					player.sendMessage(new StringTextComponent(MinecraftColors.GOLD + "To deny this request, type " + MinecraftColors.RED + "/tpdeny" + MinecraftColors.GOLD + "."), uuid);
					player.sendMessage(new StringTextComponent(MinecraftColors.GOLD + "This request will timeout after " + MinecraftColors.RED + "120 seconds" + MinecraftColors.GOLD + "."), uuid);
					for (int y = 0; y < EssentialsMod.players.size(); y++) {
						if (EssentialsMod.players.get(y).getEntityId() == requesterEntityID) {
							PlayerEntity player2 = EssentialsMod.players.get(y);
							UUID uuid2 = player2.getUniqueID();
							player2.sendMessage(new StringTextComponent(MinecraftColors.GOLD + "Request sent to " + MinecraftColors.RED + EssentialsMod.players.get(x).getName().getString() + MinecraftColors.GOLD + "."), uuid2);
							player2.sendMessage(new StringTextComponent(MinecraftColors.GOLD + "To cancel this request, type " + MinecraftColors.RED + "/tpacancel" + MinecraftColors.GOLD + "."), uuid2);
							return;
						}
					}
				}
			}
		}
	}
	public static void initiateTeleportHereRequest(int requesterEntityID, int requesteeEntityID) {
		for (int x = 0; x < EssentialsMod.players.size(); x++) {
			if (EssentialsMod.players.get(x).getEntityId() == requesteeEntityID) {
				requests.set(x, new TeleportRequest(requesterEntityID, TeleportRequestType.TPAHERE));
				String requester = null;
				for (int y = 0; y < EssentialsMod.players.size(); y++) {
					if (EssentialsMod.players.get(y).getEntityId() == requesterEntityID) {
						requester = EssentialsMod.players.get(y).getName().getString();
					}
				}
				if (requester == null) {
					requests.set(x, null);
					return;
				}
				PlayerEntity player = EssentialsMod.players.get(x);
				UUID uuid = player.getUniqueID();
				player.sendMessage(new StringTextComponent(MinecraftColors.GREEN + requester + MinecraftColors.GOLD + " has requested that you teleport to them."), uuid);
				player.sendMessage(new StringTextComponent(MinecraftColors.GOLD + "To teleport, type " + MinecraftColors.RED + "/tpaccept" + MinecraftColors.GOLD + "."), uuid);
				player.sendMessage(new StringTextComponent(MinecraftColors.GOLD + "To deny this request, type " + MinecraftColors.RED + "/tpdeny" + MinecraftColors.GOLD + "."), uuid);
				player.sendMessage(new StringTextComponent(MinecraftColors.GOLD + "This request will timeout after " + MinecraftColors.RED + "120 seconds" + MinecraftColors.GOLD + "."), uuid);
				for (int y = 0; y < EssentialsMod.players.size(); y++) {
					if (EssentialsMod.players.get(y).getEntityId() == requesterEntityID) {
						PlayerEntity player2 = EssentialsMod.players.get(y);
						UUID uuid2 = player2.getUniqueID();
						player2.sendMessage(new StringTextComponent(MinecraftColors.GOLD + "Request sent to " + MinecraftColors.RED + EssentialsMod.players.get(x).getName().getString() + MinecraftColors.GOLD + "."), uuid2);
						player2.sendMessage(new StringTextComponent(MinecraftColors.GOLD + "To cancel this request, type " + MinecraftColors.RED + "/tpacancel" + MinecraftColors.GOLD + "."), uuid2);
						return;
					}
				}
			}
		}
	}
	public static void onPlayerDisconnect(PlayerLoggedOutEvent event) {
		for (int i = 0; i < EssentialsMod.players.size(); i++) {
			if (EssentialsMod.players.get(i).getEntityId() == event.getPlayer().getEntityId()) {
				requests.set(i, null);
				return;
			}
		}
	}
}