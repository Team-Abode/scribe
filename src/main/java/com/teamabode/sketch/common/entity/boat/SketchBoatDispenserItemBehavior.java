package com.teamabode.sketch.common.entity.boat;

import com.teamabode.sketch.core.registry.SketchEntities;
import com.teamabode.sketch.core.registry.SketchRegistries;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;

public class SketchBoatDispenserItemBehavior extends DefaultDispenseItemBehavior {
    private final DefaultDispenseItemBehavior behavior;
    private final ResourceKey<SketchBoatType> type;
    private final boolean isChestBoat;

    public SketchBoatDispenserItemBehavior(ResourceKey<SketchBoatType> type) {
        this(type, false);
    }

    public SketchBoatDispenserItemBehavior(ResourceKey<SketchBoatType> type, boolean isChestBoat) {
        this.type = type;
        this.isChestBoat = isChestBoat;
        this.behavior = new DefaultDispenseItemBehavior();
    }

    public ItemStack execute(BlockSource source, ItemStack stack) {
        Direction direction = source.state().getValue(DispenserBlock.FACING);
        Level level = source.level();
        double boatWidth = 0.5625 + SketchEntities.SKETCH_BOAT.getWidth() / 2.0d;

        double spawnX = source.pos().getX() + ((float)direction.getStepX() * boatWidth);
        double spawnY = source.pos().getY() + (double)((float)direction.getStepY() * 1.125f);
        double spawnZ = source.pos().getZ() + ((float)direction.getStepZ() * boatWidth);
        BlockPos pos = source.pos().relative(direction);
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
        SketchBoatType.setTypeFromKey(level.registryAccess(), (SketchBoatAccessor) boat, this.type);
        boat.setYRot(direction.toYRot());
        level.addFreshEntity(boat);
        stack.shrink(1);
        return stack;
    }

    protected void playSound(BlockSource source) {
        source.level().levelEvent(1000, source.pos(), 0);
    }
}
