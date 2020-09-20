package com.alyxferrari.essentialsxy.command;
import com.alyxferrari.essentialsxy.EssentialsMod;
import com.alyxferrari.essentialsxy.MinecraftColors;
import com.alyxferrari.essentialsxy.managers.TeleportManager;
import com.alyxferrari.essentialsxy.types.TeleportRequest;
import com.alyxferrari.essentialsxy.types.TeleportRequestType;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.StringTextComponent;
public class TpacceptCommand {
	private TpacceptCommand() {}
	public static final LiteralArgumentBuilder<CommandSource> builder = Commands.literal("tpaccept").executes(context -> execute(context.getSource()));
	public static int execute(CommandSource source) throws CommandSyntaxException {
		ServerPlayerEntity requestee = source.asPlayer();
		for (int x = 0; x < EssentialsMod.players.size(); x++) {
			if (requestee.getEntityId() == EssentialsMod.players.get(x).getEntityId()) {
				TeleportRequest request = TeleportManager.requests.get(x);
				if (request == null || request.isOvertime()) {
					requestee.sendMessage(new StringTextComponent(MinecraftColors.DARK_RED + "Error: " + MinecraftColors.RED + "You do not have a pending request."), requestee.getUniqueID());
					return Command.SINGLE_SUCCESS;
				}
				PlayerEntity requester = null;
				for (int y = 0; y < EssentialsMod.players.size(); y++) {
					if (request.getRequesterEntityID() == EssentialsMod.players.get(y).getEntityId()) {
						requester = EssentialsMod.players.get(y);
						break;
					}
				}
				if (requester == null) {
					requestee.sendMessage(new StringTextComponent(MinecraftColors.DARK_RED + "Error: " + MinecraftColors.RED + "You do not have a pending request."), requestee.getUniqueID());
					return Command.SINGLE_SUCCESS;
				}
				requestee.sendMessage(new StringTextComponent(MinecraftColors.GOLD + "Teleporting..."), requestee.getUniqueID());
				requester.sendMessage(new StringTextComponent(MinecraftColors.GOLD + "Teleporting..."), requester.getUniqueID());
				if (request.getTeleportRequestType() == TeleportRequestType.TPA) {
					Vector3d pos = requestee.getPositionVec();
					requester.setPosition(pos.getX(), pos.getY(), pos.getZ());
					return Command.SINGLE_SUCCESS;
				}
				Vector3d pos = requester.getPositionVec();
				requestee.setPosition(pos.getX(),pos.getY(), pos.getZ());
				return Command.SINGLE_SUCCESS;
			}
		}
		return Command.SINGLE_SUCCESS;
	}
}