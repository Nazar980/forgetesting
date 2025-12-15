package com.example.examplemod;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ScreenEvent.MouseClickedEvent;
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

    // Подписываемся на события только на клиенте
    @Mod.EventBusSubscriber(modid = "examplemod", value = Dist.CLIENT)
    public static class ClientEvents {

        @SubscribeEvent
        public static void onMouseClick(MouseClickedEvent.Pre event) {
            Minecraft mc = Minecraft.getInstance();

            // Проверяем, что игрок есть и что у него открыт контейнер
            if (mc.player != null && mc.player.containerMenu != null) {
                // Здесь можно добавить проверку на конкретный экран IC2
                // Например: if (mc.screen.getClass().getName().contains("FilterScreen")) { ... }

                // Подменяем "carried stack" (то, что игрок держит курсором) на алмаз
                mc.player.containerMenu.setCarried(new ItemStack(Items.DIAMOND, 1));
            }
        }
    }
}
