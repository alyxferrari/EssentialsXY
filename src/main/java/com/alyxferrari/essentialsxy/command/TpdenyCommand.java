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
public class TpdenyCommand {
	private TpdenyCommand() {}
	public static final LiteralArgumentBuilder<CommandSource> builder = Commands.literal("tpdeny").executes(context -> execute(context.getSource()));
	public static int execute(CommandSource source) throws CommandSyntaxException {
		ServerPlayerEntity player = source.asPlayer();
		for (int x = 0; x < EssentialsMod.players.size(); x++) {
			if (player.getEntityId() == EssentialsMod.players.get(x).getEntityId()) {
				TeleportRequest request = TeleportManager.requests.get(x);
				if (request == null) {
					player.sendMessage(new StringTextComponent(MinecraftColors.DARK_RED + "Error: " + MinecraftColors.RED + "You do not have a pending request."), player.getUniqueID());
					return Command.SINGLE_SUCCESS;
				}
				for (int y = 0; y < EssentialsMod.players.size(); y++) {
					if (request.getRequesterEntityID() == EssentialsMod.players.get(y).getEntityId()) {
						player.sendMessage(new StringTextComponent(MinecraftColors.GOLD + "Teleport request denied."), player.getUniqueID());
						EssentialsMod.players.get(y).sendMessage(new StringTextComponent(MinecraftColors.GOLD + "Teleport request denied."), EssentialsMod.players.get(y).getUniqueID());
						TeleportManager.requests.set(x, null);
						return Command.SINGLE_SUCCESS;
					}
				}
			}
		}
		return Command.SINGLE_SUCCESS;
	}
}