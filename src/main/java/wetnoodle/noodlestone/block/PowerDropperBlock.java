package wetnoodle.noodlestone.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.DropperBlock;
import net.minecraft.block.dispenser.DispenserBehavior;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.DispenserBlockEntity;
import net.minecraft.block.entity.HopperBlockEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import wetnoodle.noodlestone.Noodlestone;
import wetnoodle.noodlestone.block.dispenser.StackDispenserBehavior;

public class PowerDropperBlock extends DropperBlock {
    private static final DispenserBehavior BEHAVIOR = new StackDispenserBehavior();

    public PowerDropperBlock(Settings settings) {
        super(settings);
    }

    protected void dispense(ServerWorld world, BlockState state, BlockPos pos) {
        DispenserBlockEntity dispenserBlockEntity = world.getBlockEntity(pos, BlockEntityType.DROPPER).orElse(null);
        if (dispenserBlockEntity == null) {
            Noodlestone.LOGGER.warn("Ignoring dispensing attempt for Power Dropper without matching block entity at {}", pos);
        } else {
            BlockPointer blockPointer = new BlockPointer(world, pos, state, dispenserBlockEntity);
            int i = dispenserBlockEntity.chooseNonEmptySlot(world.random);
            if (i < 0) {
                world.syncWorldEvent(1001, pos, 0);
            } else {
                ItemStack itemStack = dispenserBlockEntity.getStack(i);
                if (!itemStack.isEmpty()) {
                    Direction direction = world.getBlockState(pos).get(FACING);
                    Inventory inventory = HopperBlockEntity.getInventoryAt(world, pos.offset(direction));
                    ItemStack itemStack2;
                    if (inventory == null) {
                        itemStack2 = BEHAVIOR.dispense(blockPointer, itemStack);
                    } else {
                        itemStack2 = HopperBlockEntity.transfer(dispenserBlockEntity, inventory, itemStack.copyAndEmpty(), direction.getOpposite());
                        if (itemStack2.isEmpty()) {
                            itemStack2 = itemStack.copy();
                            itemStack2.setCount(0);
                        } else {
                            itemStack2 = itemStack.copy();
                        }
                    }

                    dispenserBlockEntity.setStack(i, itemStack2);
                }
            }
        }
    }
}
