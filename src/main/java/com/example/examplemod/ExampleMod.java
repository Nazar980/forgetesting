package com.example.examplemoddasd;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;

@Mod("examplemoddasd")
@Mod.EventBusSubscriber(modid = "examplemoddasd", value = Dist.CLIENT)
public class ExampleMod {

    public ExampleMod() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public static void onKey(InputEvent.Key event) {

        // если не кнопка C - до свидания
        if (event.getKey() != GLFW.GLFW_KEY_C || event.getAction() != GLFW.GLFW_PRESS) return;

        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.level == null) return;

        // луч игрока
        HitResult result = mc.player.pick(20, 0, false);

        // если мимо блока – хуйня
        if (result.getType() != HitResult.Type.BLOCK) {
            mc.player.sendSystemMessage(Component.literal("нет блока под прицелом"));
            return;
        }

        BlockHitResult bhr = (BlockHitResult) result;
        BlockState state = mc.level.getBlockState(bhr.getBlockPos());

        // отправляем BlockState игроку
        mc.player.sendSystemMessage(
                Component.literal("state → " + state.toString())
        );
    }
}
