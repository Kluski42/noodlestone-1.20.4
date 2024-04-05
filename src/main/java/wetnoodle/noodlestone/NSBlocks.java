package wetnoodle.noodlestone;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.MapColor;
import net.minecraft.block.Oxidizable;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import wetnoodle.noodlestone.block.PowerDropperBlock;
import wetnoodle.noodlestone.block.fan.FanBlock;
import wetnoodle.noodlestone.block.fan.OxidizableFanBlock;

public class NSBlocks {
    public static final Block POWER_DROPPER = registerBlock("power_dropper",
            new PowerDropperBlock(FabricBlockSettings.copyOf(Blocks.DROPPER)), ItemGroups.REDSTONE);

    public static final Block COPPER_FAN = registerBlock("copper_fan",
            new OxidizableFanBlock(Oxidizable.OxidationLevel.UNAFFECTED, FabricBlockSettings.create()
                    .mapColor(Blocks.COPPER_BLOCK.getDefaultMapColor()).strength(3.0f, 6.0f)
                    .sounds(BlockSoundGroup.COPPER_BULB).requiresTool().solidBlock(Blocks::never).nonOpaque()));
    public static final Block EXPOSED_COPPER_FAN = registerBlock("exposed_copper_fan", new OxidizableFanBlock(Oxidizable.OxidationLevel.EXPOSED, FabricBlockSettings.copyOf(COPPER_FAN).mapColor(MapColor.TERRACOTTA_LIGHT_GRAY)));
    public static final Block WEATHERED_COPPER_FAN = registerBlock("weathered_copper_fan", new OxidizableFanBlock(Oxidizable.OxidationLevel.WEATHERED, FabricBlockSettings.copyOf(COPPER_FAN).mapColor(MapColor.DARK_AQUA)));
    public static final Block OXIDIZED_COPPER_FAN = registerBlock("oxidized_copper_fan", new OxidizableFanBlock(Oxidizable.OxidationLevel.OXIDIZED, FabricBlockSettings.copyOf(COPPER_FAN).mapColor(MapColor.TEAL)));
    public static final Block WAXED_COPPER_FAN = registerBlock("waxed_copper_fan", new FanBlock(FabricBlockSettings.copyOf(COPPER_FAN)));
    public static final Block WAXED_EXPOSED_COPPER_FAN = registerBlock("waxed_exposed_copper_fan", new FanBlock(FabricBlockSettings.copyOf(EXPOSED_COPPER_FAN)));
    public static final Block WAXED_WEATHERED_COPPER_FAN = registerBlock("waxed_weathered_copper_fan", new FanBlock(FabricBlockSettings.copyOf(WEATHERED_COPPER_FAN)));
    public static final Block WAXED_OXIDIZED_COPPER_FAN = registerBlock("waxed_oxidized_copper_fan", new FanBlock(FabricBlockSettings.copyOf(OXIDIZED_COPPER_FAN)));


    @SafeVarargs
    private static Block registerBlock(String name, Block block, RegistryKey<ItemGroup>... itemGroups) {
        registerBlockItem(name, block, itemGroups);
        return Registry.register(Registries.BLOCK, new Identifier(Noodlestone.MOD_ID, name), block);
    }

    private static Block registerBlockWithoutItem(String name, Block block) {
        return Registry.register(Registries.BLOCK, new Identifier(Noodlestone.MOD_ID, name), block);
    }

    private static Item registerBlockItem(String name, Block block, RegistryKey<ItemGroup>... itemGroups) {
        for (RegistryKey<ItemGroup> itemGroup : itemGroups) {
            ItemGroupEvents.modifyEntriesEvent(itemGroup).register(content -> content.add(block));
        }
        return Registry.register(Registries.ITEM, new Identifier(Noodlestone.MOD_ID, name),
                new BlockItem(block, new FabricItemSettings()));
    }

    public static void registerModBlocks() {
        Noodlestone.LOGGER.debug("Registering Blocks for " + Noodlestone.MOD_ID);
    }
}
