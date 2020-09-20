package com.alyxferrari.essentialsxy.command;
import com.alyxferrari.essentialsxy.managers.AfkManager;
import com.alyxferrari.essentialsxy.EssentialsMod;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.text.StringTextComponent;
public class AfkCommand {
	private AfkCommand() {}
	public static final LiteralArgumentBuilder<CommandSource> builder = Commands.literal("afk").executes(context -> AfkCommand.execute(context.getSource()));
	public static int execute(CommandSource source) throws CommandSyntaxException {
		ServerPlayerEntity player = source.asPlayer();
		for (int x = 0; x < EssentialsMod.players.size(); x++) {
			if (EssentialsMod.players.get(x).getEntityId() == player.getEntityId()) {
				if (AfkManager.isAfk.get(x)) {
					AfkManager.isAfk.set(x, false);
					for (int y = 0; y < EssentialsMod.players.size(); y++) {
						EssentialsMod.players.get(y).sendMessage(new StringTextComponent("\u00A77* \u00A73" + EssentialsMod.players.get(y).getName().getString() + "\u00A77 is no longer AFK."), EssentialsMod.players.get(y).getUniqueID());
					}
					return Command.SINGLE_SUCCESS;
				}
				AfkManager.isAfk.set(x, true);
				AfkManager.ticksAfk.set(x, AfkManager.afkDefinition-2);
				return Command.SINGLE_SUCCESS;
			}
		}
		return Command.SINGLE_SUCCESS;
	}
}