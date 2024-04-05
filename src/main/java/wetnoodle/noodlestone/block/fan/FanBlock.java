package wetnoodle.noodlestone.block.fan;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import wetnoodle.noodlestone.NSBlocks;
import wetnoodle.noodlestone.block.entity.FanBlockEntity;
import wetnoodle.noodlestone.block.entity.NSBlockEntityType;

public class FanBlock
        extends BlockWithEntity {
    public static final MapCodec<FanBlock> CODEC = createCodec(FanBlock::new);
    public static final DirectionProperty FACING = FacingBlock.FACING;
    public static final BooleanProperty POWERED = Properties.POWERED;
    public static final BooleanProperty BLOWING = BooleanProperty.of("blowing");

    public FanBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(FACING, Direction.NORTH).with(POWERED, false).with(BLOWING, false));
    }

    @Override
    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        if (oldState.getBlock() != state.getBlock() && world instanceof ServerWorld serverWorld) {
            this.update(state, serverWorld, pos);
        }
    }

    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, BlockPos sourcePos, boolean notify) {
        if (world instanceof ServerWorld serverWorld) {
            this.update(state, serverWorld, pos);
        }
    }

    public void update(BlockState state, ServerWorld world, BlockPos pos) {
        boolean bl = world.isReceivingRedstonePower(pos);
        if (bl != state.get(POWERED)) {
            BlockState blockState = state;
            if (!(Boolean) state.get(POWERED)) {
                blockState = state.cycle(BLOWING);
                world.playSound((PlayerEntity) null, pos,
                        blockState.get(BLOWING) ? SoundEvents.BLOCK_COPPER_BULB_TURN_ON : SoundEvents.BLOCK_COPPER_BULB_TURN_OFF, SoundCategory.BLOCKS);
            }
            world.setBlockState(pos, blockState.with(POWERED, bl), 3);
        }
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getPlayerLookDirection().getOpposite()).with(POWERED, false).with(BLOWING, false);
    }

    @Override
    protected MapCodec<? extends FanBlock> getCodec() {
        return CODEC;
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, POWERED, BLOWING);
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
        int str = 0;
        if (state.isOf(NSBlocks.COPPER_FAN)) str = 15;
        if (state.isOf(NSBlocks.EXPOSED_COPPER_FAN)) str = 12;
        if (state.isOf(NSBlocks.WEATHERED_COPPER_FAN)) str = 8;
        if (state.isOf(NSBlocks.OXIDIZED_COPPER_FAN)) str = 4;
        FanBlockEntity fan = new FanBlockEntity(pos, state);
        fan.setStrength(str);
        return fan;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return FanBlock.validateTicker(type, NSBlockEntityType.FAN_BLOCK_ENTITY, FanBlockEntity::tick);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {    
        return BlockRenderType.MODEL;
    }
}
