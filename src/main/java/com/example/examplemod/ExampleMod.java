package com.example.examplemod;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.server.level.ServerPlayer;

@Mod("examplemod")
public class ExampleMod {

    public ExampleMod() {
        System.out.println("ExampleMod loaded!");
    }

    @Mod.EventBusSubscriber(modid = "examplemod")
    public static class Events {

        @SubscribeEvent
        public static void onCommandsRegister(RegisterCommandsEvent event) {
            event.getDispatcher().register(
                    Commands.literal("checkinfo")
                            .executes(Events::checkInfo)
            );
        }

        private static int checkInfo(CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
            ServerPlayer player = ctx.getSource().getPlayerOrException();

            HitResult hit = player.pick(20.0D, 0.0F, false);

            if (hit.getType() != HitResult.Type.BLOCK) {
                player.sendSystemMessage(
                        net.minecraft.network.chat.Component.literal("§cТы не смотришь на блок!")
                );
                return Command.SINGLE_SUCCESS;
            }

            BlockHitResult blockHit = (BlockHitResult) hit;
            BlockPos pos = blockHit.getBlockPos();
            BlockState state = player.level().getBlockState(pos);

            String blockName = state.getBlock().getDescriptionId();
            String niceName = state.getBlock().getName().getString();

            player.sendSystemMessage(
                    net.minecraft.network.chat.Component.literal(
                            "§aБлок: §f" + niceName +
                                    "\n§aID: §f" + blockName +
                                    "\n§aКоординаты: §f" + pos.getX() + ", " + pos.getY() + ", " + pos.getZ() +
                                    "\n§aBlockState: §f" + state.toString()
                    )
            );

            return Command.SINGLE_SUCCESS;
        }
    }
}
