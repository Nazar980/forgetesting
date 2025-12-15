package com.example.examplemod.mixin;

import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.Slot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractContainerScreen.class)
public class ContainerMixin {

    @Inject(
        method = "m_97755_(Lnet/minecraft/world/inventory/Slot;IILnet/minecraft/world/inventory/ClickType;)V",
        at = @At("HEAD")
    )
    private void onSlotClicked(
            Slot slot,
            int slotId,
            int mouseButton,
            ClickType clickType,
            CallbackInfo ci
    ) {
        System.out.println("Слот " + slotId + " был кликнут!");
    }
}
