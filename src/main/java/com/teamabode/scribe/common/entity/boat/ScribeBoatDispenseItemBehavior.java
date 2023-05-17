package com.teamabode.scribe.common.entity.boat;

import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.core.Direction;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;

public class ScribeBoatDispenseItemBehavior extends DefaultDispenseItemBehavior {
    private final DefaultDispenseItemBehavior behavior;
    private final ScribeBoatType type;
    private final boolean isChestBoat;

    public ScribeBoatDispenseItemBehavior(ScribeBoatType type) {
        this(type, false);
    }

    public ScribeBoatDispenseItemBehavior(ScribeBoatType type, boolean isChestBoat) {
        this.type = type;
        this.isChestBoat = isChestBoat;
        this.behavior = new DefaultDispenseItemBehavior();
    }

    public ItemStack execute(BlockSource source, ItemStack stack) {
        Direction direction = source.getBlockState().getValue(DispenserBlock.FACING);
        Level level = source.getLevel();
        double spawnX = source.x() + (double)((float)direction.getStepX() * 1.125F);
        double spawnY = source.y() + (double)((float)direction.getStepY() * 1.125F);
        double spawnZ = source.z() + (double)((float)direction.getStepZ() * 1.125F);
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
        Boat boat = this.isChestBoat ? new ScribeChestBoat(level, spawnX, spawnY + yOffset, spawnZ) : new ScribeBoat(level, spawnX, spawnY + yOffset, spawnZ);
        ((ScribeBoatAccessor) boat).setBoatType(this.type);

        level.addFreshEntity(boat);
        stack.shrink(1);
        return stack;
    }

    protected void playSound(BlockSource source) {
        source.getLevel().levelEvent(1000, source.getPos(), 0);
    }
}
