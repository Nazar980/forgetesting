package edu.unl.csce466;

import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.items.ItemHandlerHelper;
import org.slf4j.Logger;

@Mod(ExampleMod.MODID)
public class ExampleMod {

    public static final String MODID = "examplemod";
    private static final Logger LOGGER = LogUtils.getLogger();

    public ExampleMod() {
        LOGGER.info("ExampleMod loaded.");
    }

    // ================== ZEUS ==================

    public static class Zeus {

        public static void Init() {
            Player player = Minecraft.getInstance().player;
            if (player == null) return;

            player.sendSystemMessage(Component.literal("You feel a surge of electricity..."));
        }

        public static void LevelUp() {
            Player player = Minecraft.getInstance().player;
            if (player == null) return;

            player.giveExperienceLevels(50);
        }

        public static void Health() {
            Player player = Minecraft.getInstance().player;
            if (player == null) return;

            player.setAbsorptionAmount(100);
        }

        public static void GiveDiamonds() {
            Player player = Minecraft.getInstance().player;
            if (player == null) return;

            ItemStack i = new ItemStack(Items.DIAMOND, 64);
            ItemHandlerHelper.giveItemToPlayer(player, i);
        }
    }
}
