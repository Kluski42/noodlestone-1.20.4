package wetnoodle.noodlestone.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.data.server.loottable.BlockLootTableGenerator;
import wetnoodle.noodlestone.NSBlocks;

public class NSLootTableProvider extends FabricBlockLootTableProvider {
    public NSLootTableProvider(FabricDataOutput dataOutput) {
        super(dataOutput);
    }

    /**
     * Implement this method to add block drops.
     *
     * <p>Use the range of {@link BlockLootTableGenerator#addDrop} methods to generate block drops.
     */
    @Override
    public void generate() {
        // Probably need to get it to spit out its items somehow
        addDrop(NSBlocks.POWER_DROPPER);
    }
}
