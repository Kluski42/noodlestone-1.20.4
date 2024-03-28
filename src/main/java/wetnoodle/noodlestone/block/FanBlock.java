package wetnoodle.noodlestone.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FacingBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class FanBlock
        extends FacingBlock {
    public static final MapCodec<FanBlock> CODEC = createCodec(FanBlock::new);
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
}
