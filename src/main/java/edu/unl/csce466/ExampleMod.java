package edu.unl.csce466;

import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;

@Mod(ExampleMod.MODID)
@Mod.EventBusSubscriber(modid = ExampleMod.MODID, value = Dist.CLIENT)
public class ExampleMod {

    public static final String MODID = "examplemod";
    private static final Logger LOGGER = LogUtils.getLogger();

    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, MODID);

    public static final RegistryObject<Block> EXAMPLE_BLOCK =
            BLOCKS.register("example_block",
                    () -> new Block(BlockBehaviour.Properties.of(Material.STONE)));

    public static final RegistryObject<Item> EXAMPLE_BLOCK_ITEM =
            ITEMS.register("example_block",
                    () -> new BlockItem(EXAMPLE_BLOCK.get(), new Item.Properties()));

    public ExampleMod() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::addCreative);

        BLOCKS.register(modEventBus);
        ITEMS.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        LOGGER.info("HELLO FROM COMMON SETUP");
        LOGGER.info("DIRT BLOCK >> {}", ForgeRegistries.BLOCKS.getKey(Blocks.DIRT));
    }

    // ✅ Creative Tab 1.19.2
    private void addCreative(CreativeModeTabEvent.BuildContents event) {
        if (event.getTab() == CreativeModeTab.TAB_BUILDING_BLOCKS) {
            event.accept(EXAMPLE_BLOCK_ITEM.get());
        }
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        LOGGER.info("HELLO from server starting");
    }

    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {

        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            LOGGER.info("HELLO FROM CLIENT SETUP");
            LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
        }
    }

    @SubscribeEvent
    public void onKeyInput(InputEvent.Key event) {
        if (Minecraft.getInstance().player == null) return;
        if (Minecraft.getInstance().screen != null) return;

        if (event.getKey() == GLFW.GLFW_KEY_L && event.getAction() == GLFW.GLFW_PRESS) {
            Player player = Minecraft.getInstance().player;
            player.sendSystemMessage(Component.literal("Клавиша L нажата!"));
            LOGGER.info("L pressed");
        }
    }

    // ===== ТВОИ СПОСОБНОСТИ =====

    public static class Zeus {

        public void Init() {
            Player player = Minecraft.getInstance().player;
            if (player == null) return;

            player.sendSystemMessage(Component.literal("You feel a surge of electricity..."));
        }

        public void LevelUp() {
            Player player = Minecraft.getInstance().player;
            if (player == null) return;

            player.giveExperienceLevels(50);
        }

        public void Health() {
            Player player = Minecraft.getInstance().player;
            if (player == null) return;

            player.setAbsorptionAmount(100);
        }

        public void GiveDiamonds() {
            Player player = Minecraft.getInstance().player;
            if (player == null) return;

            ItemStack i = new ItemStack(Items.DIAMOND, 64);
            ItemHandlerHelper.giveItemToPlayer(player, i);
        }

        public void Stick() {
            Player player = Minecraft.getInstance().player;
            if (player == null) return;

            ItemStack i = new ItemStack(Items.STICK, 1);
            i.enchant(Enchantments.SHARPNESS, 5);
            ItemHandlerHelper.giveItemToPlayer(player, i);
        }
    }
}
