package wetnoodle.noodlestone.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.RecipeProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import wetnoodle.noodlestone.NSBlocks;

public class NSRecipeProvider extends FabricRecipeProvider {
    private static final Item BREEZE_ROD = Items.BLAZE_ROD;

    public NSRecipeProvider(FabricDataOutput output) {
        super(output);
    }

    /**
     * Implement this method and then use the range of methods in {@link RecipeProvider} or from one of the recipe json factories such as {@link ShapedRecipeJsonBuilder} or {@link ShapelessRecipeJsonBuilder}.
     */
    @Override
    public void generate(RecipeExporter exporter) {
        // Copper Fans
        generateCopperFanRecipe(NSBlocks.COPPER_FAN, Blocks.COPPER_GRATE, Blocks.COPPER_BLOCK, exporter);
        generateCopperFanRecipe(NSBlocks.EXPOSED_COPPER_FAN, Blocks.EXPOSED_COPPER_GRATE, Blocks.EXPOSED_COPPER, exporter);
        generateCopperFanRecipe(NSBlocks.WEATHERED_COPPER_FAN, Blocks.WEATHERED_COPPER_GRATE, Blocks.WEATHERED_COPPER, exporter);
        generateCopperFanRecipe(NSBlocks.OXIDIZED_COPPER_FAN, Blocks.OXIDIZED_COPPER_GRATE, Blocks.OXIDIZED_COPPER, exporter);
        generateCopperFanRecipe(NSBlocks.WAXED_COPPER_FAN, Blocks.WAXED_COPPER_GRATE, Blocks.WAXED_COPPER_BLOCK, exporter);
        generateCopperFanRecipe(NSBlocks.WAXED_EXPOSED_COPPER_FAN, Blocks.WAXED_EXPOSED_COPPER_GRATE, Blocks.WAXED_EXPOSED_COPPER, exporter);
        generateCopperFanRecipe(NSBlocks.WAXED_WEATHERED_COPPER_FAN, Blocks.WAXED_WEATHERED_COPPER_GRATE, Blocks.WAXED_WEATHERED_COPPER, exporter);
        generateCopperFanRecipe(NSBlocks.WAXED_OXIDIZED_COPPER_FAN, Blocks.WAXED_OXIDIZED_COPPER_GRATE, Blocks.WAXED_OXIDIZED_COPPER, exporter);
//        generateWaxedFanRecipe(NSBlocks.WAXED_COPPER_FAN, NSBlocks.COPPER_FAN, exporter);
//        generateWaxedFanRecipe(NSBlocks.WAXED_EXPOSED_COPPER_FAN, NSBlocks.EXPOSED_COPPER_FAN, exporter);
//        generateWaxedFanRecipe(NSBlocks.WAXED_WEATHERED_COPPER_FAN, NSBlocks.WEATHERED_COPPER_FAN, exporter);
//        generateWaxedFanRecipe(NSBlocks.WAXED_OXIDIZED_COPPER_FAN, NSBlocks.OXIDIZED_COPPER_FAN, exporter);

    }

    private void generateCopperFanRecipe(Block fan, Block grate, Block copperBlock, RecipeExporter exporter) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.REDSTONE, fan).pattern("gig").pattern("cbc").pattern("crc")
                .input('g', grate).input('c', copperBlock).input('i', Items.IRON_INGOT).input('b', BREEZE_ROD).input('r', Items.REDSTONE)
                .criterion(FabricRecipeProvider.hasItem(BREEZE_ROD), FabricRecipeProvider.conditionsFromItem(BREEZE_ROD))
                .offerTo(exporter);
    }

    private void generateWaxedFanRecipe(Block waxedFan, Block fan, RecipeExporter exporter) {
        ShapelessRecipeJsonBuilder.create(RecipeCategory.REDSTONE, waxedFan).input(fan).input(Items.HONEYCOMB)
                .criterion(FabricRecipeProvider.hasItem(waxedFan), FabricRecipeProvider.conditionsFromItem(waxedFan))
                .criterion(FabricRecipeProvider.hasItem(fan), FabricRecipeProvider.conditionsFromItem(fan))
                .offerTo(exporter);


//        ShapelessRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, SIMPLE_ITEM).input(SIMPLE_BLOCK)
//                .criterion(FabricRecipeProvider.hasItem(SIMPLE_ITEM), FabricRecipeProvider.conditionsFromItem(SIMPLE_ITEM))
//                .criterion(FabricRecipeProvider.hasItem(SIMPLE_BLOCK), FabricRecipeProvider.conditionsFromItem(SIMPLE_BLOCK))
//                .offerTo(exporter);
    }

}
