package com.example.examplemod;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod(ExampleMod.MODID)
public class ExampleMod {
    public static final String MODID = "examplemod";

    // Регистрируем предметы
    private static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, MODID);

    public static final RegistryObject<Item> DIAMOND_SWORD_300 =
            ITEMS.register("diamond_sword_300", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_COMBAT)));

    public ExampleMod() {
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    // Создаём ItemStack алмазного меча с прочностью 300
    public static ItemStack getDiamondSword300() {
        ItemStack stack = new ItemStack(Items.DIAMOND_SWORD);
        int desiredDurability = 300;
        int max = stack.getMaxDamage(); // у алмазного меча 1561
        int damage = Math.max(0, max - desiredDurability);
        stack.setDamageValue(damage);
        stack.setHoverName(Component.literal("Алмазный меч (прочность 300)"));
        return stack;
    }
}
