package com.example.examplemod;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.registration.IIngredientManager;
import mezz.jei.api.registration.IIngredientRegistration;
import mezz.jei.api.runtime.IJeiRuntime;
import mezz.jei.api.runtime.IJeiHelpers;
import mezz.jei.api.constants.VanillaTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.List;

public class JEIPlugin implements IModPlugin {
    private static final ResourceLocation ID = new ResourceLocation("examplemod", "jei_plugin");

    @Override
    public ResourceLocation getPluginUid() {
        return ID;
    }

    @Override
    public void registerIngredients(IIngredientRegistration registration) {
        ItemStack sword = new ItemStack(Items.DIAMOND_SWORD);
        sword.setDamageValue(sword.getMaxDamage() - 300);
        registration.addIngredients(VanillaTypes.ITEM_STACK, List.of(sword));
    }
}
