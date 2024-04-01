package wetnoodle.noodlestone.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.block.Block;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.ModelIds;
import net.minecraft.data.client.VariantsBlockStateSupplier;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import wetnoodle.noodlestone.NSBlocks;

public class NSModelProvider extends FabricModelProvider {


    public NSModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        blockStateModelGenerator.registerDispenserLikeOrientable(NSBlocks.POWER_DROPPER);
        registerFacingPoweredBlock(blockStateModelGenerator, NSBlocks.FAN_BLOCK);
    }

    private void registerFacingPoweredBlock(BlockStateModelGenerator blockStateModelGenerator, Block block) {
        Identifier offIdentifier = ModelIds.getBlockModelId(block);
        Identifier onIdentifier = ModelIds.getBlockSubModelId(block, "_on");
        blockStateModelGenerator.blockStateCollector.accept(VariantsBlockStateSupplier.create(block)
                .coordinate(BlockStateModelGenerator.createBooleanModelMap(Properties.POWERED, onIdentifier, offIdentifier))
//                .coordinate(BlockStateModelGenerator.createBooleanModelMap(FanBlock.BLOWING, onIdentifier, offIdentifier))
                .coordinate(BlockStateModelGenerator.createNorthDefaultRotationStates()));
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
    }
}