package com.alyxferrari.essentialsxy.command;
import com.alyxferrari.essentialsxy.MinecraftColors;
import com.alyxferrari.essentialsxy.managers.TeleportManager;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.text.StringTextComponent;
public class TpaCommand {
	private TpaCommand() {}
	public static final LiteralArgumentBuilder<CommandSource> builder = Commands.literal("tpa").then(Commands.argument("target", EntityArgument.player()).executes(context -> execute(context.getSource(), EntityArgument.getPlayer(context, "target"))));
	public static int execute(CommandSource source, ServerPlayerEntity player) throws CommandSyntaxException {
		ServerPlayerEntity sourcePlayer = source.asPlayer();
		if (sourcePlayer.getEntityId() == player.getEntityId()) {
			sourcePlayer.sendMessage(new StringTextComponent(MinecraftColors.DARK_RED + "Error: " + MinecraftColors.RED + "Cannot request to teleport to yourself."), sourcePlayer.getUniqueID());
			return Command.SINGLE_SUCCESS;
		}
		TeleportManager.initiateTeleportToRequest(sourcePlayer.getEntityId(), player.getEntityId());
		return Command.SINGLE_SUCCESS;
	}
}