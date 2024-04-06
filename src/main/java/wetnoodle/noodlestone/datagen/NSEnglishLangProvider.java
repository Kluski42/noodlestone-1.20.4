package wetnoodle.noodlestone.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import wetnoodle.noodlestone.NSBlocks;

public class NSEnglishLangProvider extends FabricLanguageProvider {
    public NSEnglishLangProvider(FabricDataOutput dataGenerator) {
        super(dataGenerator, "en_us");
    }

    /**
     * Implement this method to register languages.
     *
     * <p>Call {@link TranslationBuilder#add(String, String)} to add a translation.
     *
     * @param translationBuilder
     */
    @Override
    public void generateTranslations(TranslationBuilder translationBuilder) {
        translationBuilder.add(NSBlocks.COPPER_FAN, "Copper Fan");
        translationBuilder.add(NSBlocks.WAXED_COPPER_FAN, "Copper Fan");
        translationBuilder.add(NSBlocks.EXPOSED_COPPER_FAN, "Exposed Copper Fan");
        translationBuilder.add(NSBlocks.WAXED_EXPOSED_COPPER_FAN, "Waxed Exposed Copper Fan");
        translationBuilder.add(NSBlocks.WEATHERED_COPPER_FAN, "Weathered Copper Fan");
        translationBuilder.add(NSBlocks.WAXED_WEATHERED_COPPER_FAN, "Waxed Weathered Copper Fan");
        translationBuilder.add(NSBlocks.OXIDIZED_COPPER_FAN, "Oxidized Copper Fan");
        translationBuilder.add(NSBlocks.WAXED_OXIDIZED_COPPER_FAN, "Waxed Oxidized Copper Fan");
    }
}
