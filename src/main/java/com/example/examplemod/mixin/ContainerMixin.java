package com.example.examplemod.mixin;

import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractContainerMenu.class) // ИЛИ конкрентный класс
public class ContainerMixin {
    
    @Inject(method = "getCarried", at = @At("HEAD"), cancellable = true)
    private void fakeGetCarried(CallbackInfoReturnable<ItemStack> cir) {
        // Условие срабатывания
        boolean shouldFakeItem = true; // Или твоя логика
        
        if (shouldFakeItem) {
            cir.setReturnValue(new ItemStack(Items.DIAMOND, 64));
            cir.cancel(); // Отменяем оригинальный метод
        }
    }
}