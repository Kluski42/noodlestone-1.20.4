package wetnoodle.noodlestone.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import wetnoodle.noodlestone.block.fan.FanBlock;

import java.awt.geom.Rectangle2D;
import java.util.List;

public class FanBlockEntity
        extends BlockEntity {

    private static final double PUSH_CONST = 0.05;
    private int strength;

    public FanBlockEntity(BlockPos pos, BlockState state) {
        super(NSBlockEntityType.FAN_BLOCK_ENTITY, pos, state);
    }

    public static void tick(World world, BlockPos pos, BlockState state, FanBlockEntity blockEntity) {
        if (state.get(FanBlock.BLOWING)) {
            pushEntities(world, pos, state, blockEntity);
        }
    }

    // Main actions:
    private static void pushEntities(World world, BlockPos pos, BlockState state, FanBlockEntity blockEntity) {
        Direction facing = state.get(FanBlock.FACING);
        Box windBox = getWindBox(pos, facing, blockEntity.getStrength());
        List<Entity> entities = world.getNonSpectatingEntities(Entity.class, windBox);
        if (entities.isEmpty()) return;
        for (Entity entity : entities) {
            if (entity.isLogicalSideForUpdatingMovement()) {
                if (entity instanceof PlayerEntity playerEntity) {
                    if (playerEntity.getAbilities().flying) continue;
                }
                if (!isObstructed(world, pos, entity)) {
                    Vec3d force = pushForce(pos, facing, entity, windBox, blockEntity.getStrength());
                    entity.addVelocity(force);
                    entity.limitFallDistance();
                    // check if grounded
//                    if (!force.equals(Vec3d.ZERO) && entity.collidesWithStateAtPos(pos, state)) {
//                        entity.setOnGround(true, force);
//                    }
                }
            }
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

    private static Vec3d pushForce(BlockPos pos, Direction facing, Entity entity, Box windBox, int fanStr) {
        double distance = getDistance(pos, facing.getAxis(), entity);
        double percentageBlown = getPercentageBlown(entity, windBox, facing.getAxis());
        double force = PUSH_CONST * percentageBlown * fanStr / (distance + 2);
        // This allows entities to walk on weaker fans
        // This currently doesn't work when landing on a fan with an elytra
        if (force < 0.15 && entity.isOnGround()) {
            return Vec3d.ZERO;
        }
        return Vec3d.of(facing.getVector()).multiply(force);
    }

    private static boolean isObstructed(World world, BlockPos blockPos, Entity entity) {
        return false;
    }

    /**
     * As the windBox is always 1x1, the overlap area should <= 1.
     *
     * @return The area of the intersection between the wind column's face and the entity's nearest face.
     */
    private static double getOverlapArea(Rectangle2D entityFace, Box windBox, Direction.Axis axis) {
        Rectangle2D windFace = getFace(windBox, axis);
//        Rectangle2D entityFace = getFace(entity.getBoundingBox(), axis);
        Rectangle2D intersection = windFace.createIntersection(entityFace);
        return intersection.getWidth() * intersection.getHeight();
    }

    private static double getPercentageBlown(Entity entity, Box windBox, Direction.Axis axis) {
        Rectangle2D entityFace = getFace(entity.getBoundingBox(), axis);
        double overlapArea = getOverlapArea(entityFace, windBox, axis);
        double faceArea = entityFace.getWidth() * entityFace.getWidth();
        return overlapArea / faceArea;
    }

    /**
     * Gets the box's face on an axis
     *
     * @return box's face on an axis
     */
    private static Rectangle2D.Double getFace(Box box, Direction.Axis axis) {
        Direction.Axis[] axi;
        axi = switch (axis) {
            case X -> new Direction.Axis[]{Direction.Axis.Y, Direction.Axis.Z};
            case Y -> new Direction.Axis[]{Direction.Axis.X, Direction.Axis.Z};
            case Z -> new Direction.Axis[]{Direction.Axis.X, Direction.Axis.Y};
        };
        double length1 = box.getMax(axi[0]) - box.getMin(axi[0]);
        double length2 = box.getMax(axi[1]) - box.getMin(axi[1]);
        return new Rectangle2D.Double(box.getMin(axi[0]), box.getMin(axi[1]), length1, length2);
    }

    // NBT
    @Override
    protected void writeNbt(NbtCompound nbt) {
        // Save the current value of the strength to the nbt
        nbt.putInt("strength", strength);
        super.writeNbt(nbt);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);

        strength = nbt.getInt("strength");
    }

    // Getters setters
    private int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
        markDirty();
    }
}
