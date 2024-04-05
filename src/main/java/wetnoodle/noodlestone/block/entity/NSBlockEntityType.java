package wetnoodle.noodlestone.block.entity;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import wetnoodle.noodlestone.NSBlocks;
import wetnoodle.noodlestone.Noodlestone;

public class NSBlockEntityType<T extends BlockEntity> {
    public static void registerModBlockEntities() {
        Noodlestone.LOGGER.debug("Registering Block Entities for " + Noodlestone.MOD_ID);
    }

    private static Block[] copperFans = new Block[]{NSBlocks.COPPER_FAN, NSBlocks.WAXED_COPPER_FAN,
            NSBlocks.EXPOSED_COPPER_FAN, NSBlocks.WAXED_EXPOSED_COPPER_FAN,
            NSBlocks.WEATHERED_COPPER_FAN, NSBlocks.WAXED_WEATHERED_COPPER_FAN,
            NSBlocks.OXIDIZED_COPPER_FAN, NSBlocks.WAXED_OXIDIZED_COPPER_FAN};

    public static final BlockEntityType<FanBlockEntity> FAN_BLOCK_ENTITY = Registry.register(
            Registries.BLOCK_ENTITY_TYPE,
            new Identifier(Noodlestone.MOD_ID, "fan_block_entity"),
            FabricBlockEntityTypeBuilder.create(FanBlockEntity::new,
                    NSBlocks.COPPER_FAN, NSBlocks.WAXED_COPPER_FAN,
                    NSBlocks.EXPOSED_COPPER_FAN, NSBlocks.WAXED_EXPOSED_COPPER_FAN,
                    NSBlocks.WEATHERED_COPPER_FAN, NSBlocks.WAXED_WEATHERED_COPPER_FAN,
                    NSBlocks.OXIDIZED_COPPER_FAN, NSBlocks.WAXED_OXIDIZED_COPPER_FAN
            ).build()
    );
}
