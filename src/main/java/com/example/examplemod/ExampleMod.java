package com.example.examplemod;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod(ExampleMod.MODID)
public class ExampleMod {
    public static final String MODID = "examplemod";

    public ExampleMod() {
        // пустой конструктор, регистрация не нужна
    }

    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            // Создаём ванильный алмазный меч с прочностью 300
            ItemStack sword = new ItemStack(Items.DIAMOND_SWORD);
            int max = sword.getMaxDamage(); // 1561
            sword.setDamageValue(max - 300);

            // Теперь этот ItemStack можно добавить в список фильтра IC2
            // Обычно это делается через API IC2 или JEI — например:
            // IC2Filters.addDisplayItem(sword);

            // Для теста можно просто вывести в лог
            System.out.println("Добавлен меч для фильтра: " + sword);
        }
    }
}
