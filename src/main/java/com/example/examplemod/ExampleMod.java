package com.example.examplemod;

import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod(ExampleMod.MODID)
@Mod.EventBusSubscriber(modid = ExampleMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ExampleMod {
    public static final String MODID = "examplemod";

    // Регистрация предметов
    private static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, MODID);

    public static final RegistryObject<Item> DIAMOND_SWORD_300 =
            ITEMS.register("diamond_sword_300", () -> new Item(new Item.Properties().stacksTo(1)));

    public ExampleMod() {
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    // Добавляем в креативное меню
    @SubscribeEvent
    public static void onBuildContents(CreativeModeTabEvent.BuildContents event) {
        if (event.getTab() == CreativeModeTabs.COMBAT) {
            event.accept(getDiamondSword300());
        }
    }

    // Создаём ItemStack с прочностью 300
    public static ItemStack getDiamondSword300() {
        ItemStack stack = new ItemStack(Items.DIAMOND_SWORD);
        int desiredDurability = 300;
        int max = stack.getMaxDamage(); // у алмазного меча 1561
        int damage = Math.max(0, max - desiredDurability);
        stack.setDamageValue(damage);
        stack.getOrCreateTag().putBoolean("examplemod_display", true);
        stack.setHoverName(net.minecraft.network.chat.Component.literal("Алмазный меч (прочность 300)"));
        return stack;
    }
}
