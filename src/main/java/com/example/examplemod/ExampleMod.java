package com.example.examplemoddasd;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

@Mod("examplemoddasd")
@Mod.EventBusSubscriber(modid = "examplemoddasd", value = Dist.CLIENT)
public class ExampleMod {

    public ExampleMod() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public static void onKey(InputEvent.Key event) {
        if (event.getKey() != GLFW.GLFW_KEY_C || event.getAction() != GLFW.GLFW_PRESS) return;

        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;
        if (player == null || mc.level == null) return;

        HitResult result = player.pick(20, 0, false);

        if (result.getType() != HitResult.Type.BLOCK) {
            player.sendSystemMessage(Component.literal("§cНет блока под прицелом"));
            return;
        }

        BlockHitResult bhr = (BlockHitResult) result;
        BlockPos pos = bhr.getBlockPos();
        Level level = mc.level;

        BlockState blockState = level.getBlockState(pos);
        Block block = blockState.getBlock();
        BlockEntity blockEntity = level.getBlockEntity(pos);
        FluidState fluidState = blockState.getFluidState();

        // Создаем подробное сообщение
        StringBuilder info = new StringBuilder();
        info.append("\n§6===== ИНФОРМАЦИЯ О БЛОКЕ =====\n");

        // 1. Основная информация
        info.append("§e§lОсновная информация:\n");
        info.append("§fКоординаты: §7").append(pos.getX()).append(", ").append(pos.getY()).append(", ").append(pos.getZ()).append("\n");
        
        ResourceLocation blockId = BuiltInRegistries.BLOCK.getKey(block);
        info.append("§fID блока: §a").append(blockId).append("\n");
        info.append("§fЛокализованное имя: §b").append(block.getName().getString()).append("\n");
        info.append("§fСветимость: §3").append(blockState.getLightEmission()).append("\n");
        info.append("§fСопротивление взрыву: §3").append(blockState.getExplosionResistance(level, pos, null)).append("\n");
        info.append("§fТвердость: §3").append(blockState.getDestroySpeed(level, pos)).append("\n");
        info.append("§fНепрозрачность: §3").append(blockState.isSolidRender(level, pos)).append("\n");
        info.append("§fЗаполнение: §3").append(blockState.getShape(level, pos).bounds()).append("\n");

        // 2. Свойства блока
        if (!blockState.getProperties().isEmpty()) {
            info.append("\n§e§lСвойства блока:\n");
            for (Property<?> property : blockState.getProperties()) {
                String propName = property.getName();
                Comparable<?> value = blockState.getValue(property);
                info.append("§f").append(propName).append(": §a").append(value).append("\n");
            }
        } else {
            info.append("\n§e§lСвойства блока: §7нет\n");
        }

        // 3. Теги блока
        Collection<ResourceLocation> tags = blockState.getTags().map(t -> t.location()).collect(Collectors.toList());
        if (!tags.isEmpty()) {
            info.append("\n§e§lТеги блока:\n");
            for (ResourceLocation tag : tags) {
                info.append("§7- §f").append(tag).append("\n");
            }
        } else {
            info.append("\n§e§lТеги блока: §7нет\n");
        }

        // 4. Информация о BlockEntity (если есть)
        if (blockEntity != null) {
            info.append("\n§e§lBlockEntity информация:\n");
            info.append("§fТип: §a").append(blockEntity.getType()).append("\n");
            
            // Получаем NBT данные
            CompoundTag nbt = blockEntity.saveWithFullMetadata();
            if (nbt != null && !nbt.isEmpty()) {
                info.append("§fNBT данные:\n");
                printNBT(nbt, info, 1);
            } else {
                info.append("§fNBT данные: §7пусто\n");
            }
        } else {
            info.append("\n§e§lBlockEntity: §7отсутствует\n");
        }

        // 5. Информация о жидкости
        if (!fluidState.isEmpty()) {
            info.append("\n§e§lЖидкость:\n");
            info.append("§fТип: §a").append(fluidState.getType()).append("\n");
            info.append("§fУровень: §3").append(fluidState.getAmount()).append("\n");
            info.append("§fИсточник: §3").append(fluidState.isSource()).append("\n");
        }

        // 6. Красный камень
        info.append("\n§e§lСигнал красного камня:\n");
        info.append("§fСила сигнала: §c").append(blockState.getSignal(level, pos, bhr.getDirection())).append("\n");
        info.append("§fПрямая мощность: §c").append(blockState.getDirectSignal(level, pos, bhr.getDirection())).append("\n");

        // 7. Дополнительная информация
        info.append("\n§e§lДополнительно:\n");
        info.append("§fМожно ли ставить блок: §3").append(blockState.canBeReplaced()).append("\n");
        info.append("§fВоздух: §3").append(blockState.isAir()).append("\n");
        info.append("§fВода: §3").append(blockState.liquid()).append("\n");
        info.append("§fГорючесть: §3").append(blockState.isFlammable(level, pos, bhr.getDirection())).append("\n");
        info.append("§fМожет гореть: §3").append(blockState.ignitedByLava()).append("\n");
        info.append("§fТребует инструмента: §3").append(!blockState.requiresCorrectToolForDrops()).append("\n");

        // Отправляем все части
        String[] lines = info.toString().split("\n");
        for (String line : lines) {
            player.sendSystemMessage(Component.literal(line));
        }
    }

    private static void printNBT(CompoundTag nbt, StringBuilder builder, int depth) {
        String indent = "  ".repeat(depth);
        
        for (String key : nbt.getAllKeys()) {
            switch (nbt.getTagType(key)) {
                case CompoundTag.TAG_COMPOUND:
                    builder.append(indent).append("§f").append(key).append(": §9{\n");
                    printNBT(nbt.getCompound(key), builder, depth + 1);
                    builder.append(indent).append("§9}\n");
                    break;
                    
                case CompoundTag.TAG_LIST:
                    builder.append(indent).append("§f").append(key).append(": §6[\n");
                    ListTag list = nbt.getList(key, 10); // 10 = CompoundTag
                    for (int i = 0; i < list.size(); i++) {
                        builder.append(indent).append("  §7[Элемент ").append(i).append("]\n");
                        printNBT(list.getCompound(i), builder, depth + 2);
                    }
                    builder.append(indent).append("§6]\n");
                    break;
                    
                case CompoundTag.TAG_STRING:
                    String value = nbt.getString(key);
                    builder.append(indent).append("§f").append(key).append(": §a\"").append(value).append("\"\n");
                    break;
                    
                case CompoundTag.TAG_INT:
                    builder.append(indent).append("§f").append(key).append(": §3").append(nbt.getInt(key)).append("\n");
                    break;
                    
                case CompoundTag.TAG_LONG:
                    builder.append(indent).append("§f").append(key).append(": §3").append(nbt.getLong(key)).append("\n");
                    break;
                    
                case CompoundTag.TAG_DOUBLE:
                    builder.append(indent).append("§f").append(key).append(": §3").append(nbt.getDouble(key)).append("\n");
                    break;
                    
                case CompoundTag.TAG_FLOAT:
                    builder.append(indent).append("§f").append(key).append(": §3").append(nbt.getFloat(key)).append("\n");
                    break;
                    
                case CompoundTag.TAG_BYTE:
                    builder.append(indent).append("§f").append(key).append(": §3").append(nbt.getByte(key)).append("\n");
                    break;
                    
                case CompoundTag.TAG_SHORT:
                    builder.append(indent).append("§f").append(key).append(": §3").append(nbt.getShort(key)).append("\n");
                    break;
                    
                case CompoundTag.TAG_BYTE_ARRAY:
                case CompoundTag.TAG_INT_ARRAY:
                case CompoundTag.TAG_LONG_ARRAY:
                    builder.append(indent).append("§f").append(key).append(": §3[массив данных]\n");
                    break;
                    
                case CompoundTag.TAG_BOOLEAN:
                    builder.append(indent).append("§f").append(key).append(": §3").append(nbt.getBoolean(key)).append("\n");
                    break;
                    
                default:
                    builder.append(indent).append("§f").append(key).append(": §7[неизвестный тип]\n");
            }
        }
    }
}
