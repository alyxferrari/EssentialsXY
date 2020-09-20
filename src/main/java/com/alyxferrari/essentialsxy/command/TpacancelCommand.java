package com.alyxferrari.essentialsxy.command;
import com.alyxferrari.essentialsxy.EssentialsMod;
import com.alyxferrari.essentialsxy.MinecraftColors;
import com.alyxferrari.essentialsxy.managers.TeleportManager;
import com.alyxferrari.essentialsxy.types.TeleportRequest;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.text.StringTextComponent;
public class TpacancelCommand {
	private TpacancelCommand() {}
	public static final LiteralArgumentBuilder<CommandSource> builder = Commands.literal("tpacancel").executes(context -> execute(context.getSource()));
	public static int execute(CommandSource source) throws CommandSyntaxException {
		ServerPlayerEntity player = source.asPlayer();
		for (int x = 0; x < TeleportManager.requests.size(); x++) {
			TeleportRequest request = TeleportManager.requests.get(x);
			if (request != null && player.getEntityId() == request.getRequesterEntityID()) {
				EssentialsMod.players.get(x).sendMessage(new StringTextComponent(MinecraftColors.GOLD + "Teleport request cancelled by requester."), EssentialsMod.players.get(x).getUniqueID());
				player.sendMessage(new StringTextComponent(MinecraftColors.GOLD + "Teleport request cancelled."), player.getUniqueID());
			}
		}
		player.sendMessage(new StringTextComponent(MinecraftColors.DARK_RED + "Error: " + MinecraftColors.RED + "No pending teleport request found."), player.getUniqueID());
		return Command.SINGLE_SUCCESS;
	}
}