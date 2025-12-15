package com.example.examplemod;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
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
        public static void onMouseInput(InputEvent.MouseInputEvent event) {
            Minecraft mc = Minecraft.getInstance();

            if (mc.player != null) {
                mc.submit(() -> {
                    mc.player.inventory.setCarried(new ItemStack(Items.DIAMOND, 1));
                });
            }
        }
    }
}
