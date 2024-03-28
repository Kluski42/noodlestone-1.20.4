package wetnoodle.noodlestone;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import wetnoodle.noodlestone.block.FanBlock;
import wetnoodle.noodlestone.block.PowerDropperBlock;

public class NSBlocks {
    public static final Block POWER_DROPPER = registerBlock("power_dropper",
            new PowerDropperBlock(FabricBlockSettings.copyOf(Blocks.DROPPER)), ItemGroups.REDSTONE);

    public static final Block FAN_BLOCK = registerBlock("copper_fan",
            new FanBlock(FabricBlockSettings.copyOf(Blocks.QUARTZ_BLOCK).nonOpaque()), ItemGroups.REDSTONE);

    private static Block registerBlock(String name, Block block, RegistryKey<ItemGroup> itemGroup) {
        registerBlockItem(name, block, itemGroup);
        return Registry.register(Registries.BLOCK, new Identifier(Noodlestone.MOD_ID, name), block);
    }

    private static Block registerBlockWithoutItem(String name, Block block) {
        return Registry.register(Registries.BLOCK, new Identifier(Noodlestone.MOD_ID, name), block);
    }

    private static Item registerBlockItem(String name, Block block, RegistryKey<ItemGroup> itemGroup) {
        ItemGroupEvents.modifyEntriesEvent(itemGroup).register(content -> content.add(block));
        return Registry.register(Registries.ITEM, new Identifier(Noodlestone.MOD_ID, name),
                new BlockItem(block, new FabricItemSettings()));
    }

    public static void registerModBlocks() {
        Noodlestone.LOGGER.debug("Registering Blocks for " + Noodlestone.MOD_ID);
    }
}
