package com.example.examplemod.mixin;

import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractContainerScreen.class)
public class ContainerScreenMixin {
    
    @Inject(method = "slotClicked", at = @At("HEAD"))
    private void onSlotClicked(int slotId, int button, net.minecraft.world.inventory.ClickType clickType, CallbackInfo ci) {
        // Этот метод вызывается при клике по любому слоту
        System.out.println("Слот " + slotId + " был кликнут!");
        // Здесь вы можете добавить свою логику
    }
}
