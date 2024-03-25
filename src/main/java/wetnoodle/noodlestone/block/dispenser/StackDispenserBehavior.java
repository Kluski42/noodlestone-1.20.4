package wetnoodle.noodlestone.block.dispenser;

import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.ItemDispenserBehavior;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Position;

public class StackDispenserBehavior extends ItemDispenserBehavior {

    @Override
    protected ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
        Direction direction = pointer.state().get(DispenserBlock.FACING);
        Position position = DispenserBlock.getOutputLocation(pointer);
        ItemStack itemStack = stack.copyAndEmpty();
        // Maybe change the dispensing speed? That sounds fun. Default is 6
        spawnItem(pointer.world(), itemStack, 60, direction, position);
        return stack;
    }

    //      Direction direction = (Direction)pointer.state().get(DispenserBlock.FACING);
    //        Position position = DispenserBlock.getOutputLocation(pointer);
    //        ItemStack itemStack = stack.split(1);
    //        spawnItem(pointer.world(), itemStack, 6, direction, position);
    //        return stack;
}
