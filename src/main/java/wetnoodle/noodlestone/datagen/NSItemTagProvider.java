package wetnoodle.noodlestone.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class NSItemTagProvider extends FabricTagProvider.ItemTagProvider {


    /**
     * Construct an {@link ItemTagProvider} tag provider <b>without</b> an associated {@link BlockTagProvider} tag provider.
     *
     * @param output            The {@link FabricDataOutput} instance
     * @param completableFuture
     */
    public NSItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
        super(output, completableFuture);
    }

    /**
     * Implement this method and then use {@link FabricTagProvider#getOrCreateTagBuilder} to get and register new tag builders.
     *
     * @param arg
     */
    @Override
    public void configure(RegistryWrapper.WrapperLookup arg) {

    }
}
