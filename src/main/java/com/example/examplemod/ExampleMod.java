package com.example.examplemod;

import net.minecraftforge.fml.common.Mod;
import org.spongepowered.asm.mixin.MixinEnvironment;

@Mod("examplemod")
public class ExampleMod {
    public ExampleMod() {
        // Важно для Mixin в Forge
        MixinEnvironment.getDefaultEnvironment()
            .setSide(MixinEnvironment.Side.CLIENT);
    }
}
