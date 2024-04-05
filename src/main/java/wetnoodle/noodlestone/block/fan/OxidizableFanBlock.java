package wetnoodle.noodlestone.block.fan;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.block.Oxidizable;
import net.minecraft.block.PistonExtensionBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import org.jetbrains.annotations.Nullable;
import wetnoodle.noodlestone.block.entity.FanBlockEntity;

public class OxidizableFanBlock
        extends FanBlock
        implements Oxidizable {
    public static final MapCodec<OxidizableFanBlock> CODEC = RecordCodecBuilder.mapCodec((instance) -> {
        return instance.group(OxidationLevel.CODEC.fieldOf("weathering_state").forGetter(OxidizableFanBlock::getDegradationLevel), createSettingsCodec()).apply(instance, OxidizableFanBlock::new);
    });

    private final OxidationLevel oxidationLevel;

    protected MapCodec<OxidizableFanBlock> getCodec() {
        return CODEC;
    }

    public OxidizableFanBlock(OxidationLevel oxidationLevel, Settings settings) {
        super(settings);
        this.oxidationLevel = oxidationLevel;
    }

    /**
     * {@return a new fan block entity instance}
     *
     * @param pos
     * @param state
     * @implNote While this is marked as nullable, in practice this should never return
     * {@code null}. {@link PistonExtensionBlock} is the only block in vanilla that
     * returns {@code null} inside the implementation.
     */
    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        int str = switch (getDegradationLevel()) {
            case UNAFFECTED -> 15;
            case EXPOSED -> 12;
            case WEATHERED -> 8;
            case OXIDIZED -> 4;
        };
        FanBlockEntity fan = new FanBlockEntity(pos, state);
        fan.setStrength(str);
        return fan;
    }

    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        this.tickDegradation(state, world, pos, random);
    }

    public boolean hasRandomTicks(BlockState state) {
        return Oxidizable.getIncreasedOxidationBlock(state.getBlock()).isPresent();
    }

//    @Nullable
//    @Override
//    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
//        return FanBlock.validateTicker(type, NSBlockEntityType.FAN_BLOCK_ENTITY, FanBlockEntity::tick);
//    }

    @Override
    public OxidationLevel getDegradationLevel() {
        return oxidationLevel;
    }
}
