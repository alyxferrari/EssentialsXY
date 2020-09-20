package com.alyxferrari.essentialsxy;
import java.util.ArrayList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.alyxferrari.essentialsxy.command.AfkCommand;
import com.alyxferrari.essentialsxy.command.TpaCommand;
import com.alyxferrari.essentialsxy.command.TpacancelCommand;
import com.alyxferrari.essentialsxy.command.TpacceptCommand;
import com.alyxferrari.essentialsxy.command.TpahereCommand;
import com.alyxferrari.essentialsxy.command.TpdenyCommand;
import com.alyxferrari.essentialsxy.managers.AfkManager;
import com.alyxferrari.essentialsxy.managers.TeleportManager;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.command.CommandSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedOutEvent;
import net.minecraftforge.fml.common.Mod;
@Mod(EssentialsMod.MODID)
public class EssentialsMod {
	public static final String MODID = "essentialsxy";
	public static final Logger logger = LogManager.getLogger(EssentialsMod.MODID);
	public static final ArrayList<PlayerEntity> players = new ArrayList<PlayerEntity>();
	public EssentialsMod() {
		TeleportManager.init();
		MinecraftForge.EVENT_BUS.addListener(EssentialsMod::registerCommands);
		MinecraftForge.EVENT_BUS.addListener(EssentialsMod::onPlayerConnect);
		MinecraftForge.EVENT_BUS.addListener(EssentialsMod::onPlayerDisconnect);
		MinecraftForge.EVENT_BUS.addListener(AfkManager::onPlayerConnect);
		MinecraftForge.EVENT_BUS.addListener(AfkManager::onPlayerDisconnect);
		MinecraftForge.EVENT_BUS.addListener(AfkManager::onChat);
		MinecraftForge.EVENT_BUS.addListener(AfkManager::onServerTick);
		MinecraftForge.EVENT_BUS.addListener(TeleportManager::onPlayerDisconnect);
	}
	public static void onPlayerConnect(PlayerLoggedInEvent event) {
		players.add(event.getPlayer());
	}
	public static void onPlayerDisconnect(PlayerLoggedOutEvent event) {
		for (int i = 0; i < players.size(); i++) {
			if (players.get(i).getEntityId() == event.getPlayer().getEntityId()) {
				players.remove(i);
				return;
			}
		}
	}
	public static void registerCommands(RegisterCommandsEvent event) {
		CommandDispatcher<CommandSource> dispatcher = event.getDispatcher();
		dispatcher.register(AfkCommand.builder);
		dispatcher.register(TpaCommand.builder);
		dispatcher.register(TpacceptCommand.builder);
		dispatcher.register(TpahereCommand.builder);
		dispatcher.register(TpdenyCommand.builder);
		dispatcher.register(TpacancelCommand.builder);
	}
}