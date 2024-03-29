package wetnoodle.noodlestone.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class FanBlockEntity
        extends BlockEntity {
    public FanBlockEntity(BlockPos pos, BlockState state) {
        super(NSBlockEntityType.FAN_BLOCK_ENTITY, pos, state);
    }

    public static void clientTick(World world, BlockPos pos, BlockState state, FanBlockEntity blockEntity) {

    }

    public static void serverTick(World world, BlockPos pos, BlockState state, FanBlockEntity blockEntity) {

    }

    // Main actions:
    private static void pushEntities(World world, BlockPos pos, BlockState state, FanBlockEntity blockEntity) {

    }

    // Helper methods:
    private static Box getWindBox(BlockPos pos, Direction facing, int strength) {
        Vec3d start = new Vec3d(pos.getX(), pos.getY(), pos.getZ());
        Vec3d end = start.add(Vec3d.of(facing.getVector().multiply(strength)));
        return new Box(start, end.add(1, 1, 1));
    }
}
