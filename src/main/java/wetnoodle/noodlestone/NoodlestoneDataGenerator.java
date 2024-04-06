package wetnoodle.noodlestone;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import wetnoodle.noodlestone.datagen.*;

public class NoodlestoneDataGenerator implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
        pack.addProvider(NSBlockTagProvider::new);
        pack.addProvider(NSLootTableProvider::new);
        pack.addProvider(NSItemTagProvider::new);
        pack.addProvider(NSModelProvider::new);
        pack.addProvider(NSRecipeProvider::new);
        pack.addProvider(NSEnglishLangProvider::new);
    }
}
