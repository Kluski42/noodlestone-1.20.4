package wetnoodle.noodlestone.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import wetnoodle.noodlestone.block.FanBlock;

import java.util.List;

public class FanBlockEntity
        extends BlockEntity {

    private static final int TEMP_STR = 15;
    private static final double PUSH_CONST = 0.05;

    public FanBlockEntity(BlockPos pos, BlockState state) {
        super(NSBlockEntityType.FAN_BLOCK_ENTITY, pos, state);
    }

    public static void clientTick(World world, BlockPos pos, BlockState state, FanBlockEntity blockEntity) {

    }

    public static void serverTick(World world, BlockPos pos, BlockState state, FanBlockEntity blockEntity) {
        if (state.get(FanBlock.BLOWING)) {
            pushEntities(world, pos, state, blockEntity);
        }
    }

    // Main actions:
    private static void pushEntities(World world, BlockPos pos, BlockState state, FanBlockEntity blockEntity) {
        Direction facing = state.get(FanBlock.FACING);
        Box box = getWindBox(pos, facing, TEMP_STR);
        List<Entity> entities = world.getNonSpectatingEntities(Entity.class, box);
        if (entities.isEmpty()) return;
        for (Entity entity : entities) {
            entity.addVelocity(pushForce(pos, facing, entity));
        }
    }

    // Helper methods:
    private static Box getWindBox(BlockPos pos, Direction facing, int strength) {
        Vec3d start = new Vec3d(pos.getX(), pos.getY(), pos.getZ());
        Vec3d end = start.add(Vec3d.of(facing.getVector().multiply(strength)));
        return new Box(start, end.add(1, 1, 1));
    }

    /**
     * Returns the distance of the entity from the block position on the given axis.
     *
     * @return distance of the entity from the block position on the given axis.
     */
    private static double getDistance(BlockPos pos, Direction.Axis axis, Entity entity) {
        Vec3d distance = entity.getPos().subtract(pos.toCenterPos());
        if (axis.equals(Direction.Axis.X)) return Math.abs(distance.getX());
        if (axis.equals(Direction.Axis.Y)) return Math.abs(distance.getY());
        return Math.abs(distance.getZ());
    }

    private static Vec3d pushForce(BlockPos pos, Direction facing, Entity entity) {
        double distance = getDistance(pos, facing.getAxis(), entity);
        double force = PUSH_CONST * TEMP_STR / (distance + 2);
        return Vec3d.of(facing.getVector()).multiply(force);
    }
}
