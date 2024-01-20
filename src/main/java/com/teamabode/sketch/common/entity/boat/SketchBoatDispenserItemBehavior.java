package com.teamabode.sketch.common.entity.boat;

import com.teamabode.sketch.core.registry.SketchEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.core.Direction;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;

public class SketchBoatDispenserItemBehavior extends DefaultDispenseItemBehavior {
    private final DefaultDispenseItemBehavior behavior;
    private final SketchBoatType type;
    private final boolean isChestBoat;

    public SketchBoatDispenserItemBehavior(SketchBoatType type) {
        this(type, false);
    }

    public SketchBoatDispenserItemBehavior(SketchBoatType type, boolean isChestBoat) {
        this.type = type;
        this.isChestBoat = isChestBoat;
        this.behavior = new DefaultDispenseItemBehavior();
    }

    public ItemStack execute(BlockSource source, ItemStack stack) {
        Direction direction = source.getBlockState().getValue(DispenserBlock.FACING);
        Level level = source.getLevel();
        double boatWidth = 0.5625 + SketchEntities.SKETCH_BOAT.getWidth() / 2.0d;

        double spawnX = source.x() + ((float)direction.getStepX() * boatWidth);
        double spawnY = source.y() + (double)((float)direction.getStepY() * 1.125f);
        double spawnZ = source.z() + ((float)direction.getStepZ() * boatWidth);
        BlockPos pos = source.getPos().relative(direction);
        double yOffset;

        if (level.getFluidState(pos).is(FluidTags.WATER)) {
            yOffset = 1.0;
        } else {
            if (!level.getBlockState(pos).isAir() || !level.getFluidState(pos.below()).is(FluidTags.WATER)) {
                return this.behavior.dispense(source, stack);
            }
            yOffset = 0.0;
        }
        Boat boat = this.isChestBoat ? new SketchChestBoat(level, spawnX, spawnY + yOffset, spawnZ) : new SketchBoat(level, spawnX, spawnY + yOffset, spawnZ);
        ((SketchBoatAccessor) boat).setBoatType(this.type);
        boat.setYRot(direction.toYRot());

        level.addFreshEntity(boat);
        stack.shrink(1);
        return stack;
    }

    protected void playSound(BlockSource source) {
        source.getLevel().levelEvent(1000, source.getPos(), 0);
    }
}
