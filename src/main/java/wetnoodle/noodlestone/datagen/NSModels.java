package wetnoodle.noodlestone.datagen;

import net.minecraft.block.Block;
import net.minecraft.data.client.Model;
import net.minecraft.data.client.TextureKey;
import net.minecraft.data.client.TextureMap;
import net.minecraft.util.Identifier;
import wetnoodle.noodlestone.Noodlestone;

import java.util.Optional;

public class NSModels {


    public static TextureMap sideFrontBack(Block block) {
        return new TextureMap().put(TextureKey.SIDE, TextureMap.getSubId(block, "_side")).put(TextureKey.FRONT, TextureMap.getSubId(block, "_front")).put(TextureKey.BACK, TextureMap.getSubId(block, "_back"));
    }


    private static Model block(String parent, TextureKey... requiredTextureKeys) {
        return new Model(Optional.of(new Identifier(Noodlestone.MOD_ID, "block/" + parent)), Optional.empty(), requiredTextureKeys);
    }

    public static Model item(String parent) {
        return new Model(Optional.of(new Identifier(Noodlestone.MOD_ID, "item/" + parent)), Optional.empty());
    }
}
