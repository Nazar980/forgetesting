package com.example.examplemoddasd;

import net.minecraft.commands.Commands;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod("examplemoddasd")
@Mod.EventBusSubscriber(modid = "examplemoddasd", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ExampleMod {

    public ExampleMod() {
        System.out.println("ExampleMod loaded!");
    }

    @SubscribeEvent
    public static void onCommandRegister(RegisterCommandsEvent event) {

        event.getDispatcher().register(
                Commands.literal("checkinfo")
                        .executes(ctx -> {

                            ServerPlayer player = ctx.getSource().getPlayerOrException();

                            HitResult hit = player.pick(20, 0, false);

                            if (hit.getType() != HitResult.Type.BLOCK) {
                                player.sendSystemMessage(
                                        Component.literal("НЕТ БЛОКА ПОД ПРИЦЕЛОМ")
                                );
                                return 1;
                            }

                            BlockHitResult bhr = (BlockHitResult) hit;
                            BlockPos pos = bhr.getBlockPos();

                            BlockState state = player.level.getBlockState(pos);

                            String name = state.getBlock().getName().getString();

                            player.sendSystemMessage(
                                    Component.literal(
                                            "Блок: " + name +
                                                    "\nX: " + pos.getX() +
                                                    " Y: " + pos.getY() +
                                                    " Z: " + pos.getZ()
                                    )
                            );

                            return 1;
                        })
        );

    }
}
