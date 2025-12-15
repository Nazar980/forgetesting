package com.example.examplemod;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.MouseInputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

@Mod("examplemod")
public class ExampleMod {
    public ExampleMod() {
        System.out.println("Мод загружен!");
    }

    @Mod.EventBusSubscriber(modid = "examplemod", value = Dist.CLIENT)
    public static class ClientEvents {

        @SubscribeEvent
        public static void onMouseInput(MouseInputEvent event) {
            Minecraft mc = Minecraft.getInstance();

            if (mc.player != null && mc.player.containerMenu != null) {
                // Подменяем carried stack на алмаз при любом клике мыши
                mc.player.containerMenu.setCarried(new ItemStack(Items.DIAMOND, 1));
            }
        }
    }
}
