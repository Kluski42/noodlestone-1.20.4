package wetnoodle.noodlestone.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

import java.util.concurrent.CompletableFuture;

public class NSBlockTagProvider extends FabricTagProvider.BlockTagProvider {

    public static final TagKey<Block> WIND_PASSABLE = TagKey.of(RegistryKeys.BLOCK, new Identifier("noodlestone:wind_passable"));

    public NSBlockTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    /**
     * Implement this method and then use {@link FabricTagProvider#getOrCreateTagBuilder} to get and register new tag builders.
     *
     * @param arg
     */
    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg) {

        getOrCreateTagBuilder(WIND_PASSABLE)
                .add(Blocks.COPPER_GRATE).add(Blocks.EXPOSED_COPPER_GRATE).add(Blocks.WEATHERED_COPPER_GRATE).add(Blocks.OXIDIZED_COPPER_GRATE)
                .add(Blocks.WAXED_COPPER_GRATE).add(Blocks.WAXED_EXPOSED_COPPER_GRATE).add(Blocks.WAXED_OXIDIZED_COPPER_GRATE).add(Blocks.WAXED_WEATHERED_COPPER_GRATE)
                .addOptionalTag(BlockTags.REPLACEABLE)
                .addOptionalTag(BlockTags.LEAVES);


        getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE);

        getOrCreateTagBuilder(BlockTags.NEEDS_STONE_TOOL);

        getOrCreateTagBuilder(BlockTags.NEEDS_IRON_TOOL);

        getOrCreateTagBuilder(BlockTags.NEEDS_DIAMOND_TOOL);
    }
}
