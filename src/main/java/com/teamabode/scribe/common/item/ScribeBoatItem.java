package com.teamabode.scribe.common.item;

import com.google.common.collect.Maps;
import com.teamabode.scribe.common.entity.boat.ScribeBoat;
import com.teamabode.scribe.common.entity.boat.ScribeBoatAccessor;
import com.teamabode.scribe.common.entity.boat.ScribeBoatType;
import com.teamabode.scribe.common.entity.boat.ScribeChestBoat;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public class ScribeBoatItem extends Item {
    public static final Map<ScribeBoatType, Item> TYPE_TO_BOAT_ITEM = Maps.newHashMap();
    public static final Map<ScribeBoatType, Item> TYPE_TO_CHEST_BOAT_ITEM = Maps.newHashMap();
    private static final Predicate<Entity> ENTITY_PREDICATE = EntitySelector.NO_SPECTATORS.and(Entity::isPickable);

    private final ScribeBoatType type;
    private final boolean hasChest;

    public ScribeBoatItem(Properties properties, ScribeBoatType type, boolean hasChest) {
        super(properties);
        this.type = type;
        this.hasChest = hasChest;

        if (!hasChest) TYPE_TO_BOAT_ITEM.put(type, this);
        else TYPE_TO_CHEST_BOAT_ITEM.put(type, this);
    }

    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        ItemStack itemStack = player.getItemInHand(usedHand);
        HitResult hitResult = getPlayerPOVHitResult(level, player, ClipContext.Fluid.ANY);
        if (hitResult.getType() == HitResult.Type.MISS) return InteractionResultHolder.pass(itemStack);

        Vec3 vec3 = player.getViewVector(1.0F);
        List<Entity> list = level.getEntities(player, player.getBoundingBox().expandTowards(vec3.scale(5.0)).inflate(1.0), ENTITY_PREDICATE);
        if (!list.isEmpty()) {
            Vec3 vec32 = player.getEyePosition();

            for (Entity entity : list) {
                AABB aABB = entity.getBoundingBox().inflate(entity.getPickRadius());
                if (aABB.contains(vec32)) {
                    return InteractionResultHolder.pass(itemStack);
                }
            }
        }
        if (hitResult.getType() == HitResult.Type.BLOCK) {
            Boat boat = this.getBoat(level, hitResult);
            ((ScribeBoatAccessor) boat).setBoatType(this.type);
            boat.setYRot(player.getYRot());
            if (!level.noCollision(boat, boat.getBoundingBox())) {
                return InteractionResultHolder.fail(itemStack);
            }
            if (!level.isClientSide) {
                level.addFreshEntity(boat);
                level.gameEvent(player, GameEvent.ENTITY_PLACE, hitResult.getLocation());
                if (!player.getAbilities().instabuild) {
                    itemStack.shrink(1);
                }
            }

            player.awardStat(Stats.ITEM_USED.get(this));
            return InteractionResultHolder.sidedSuccess(itemStack, level.isClientSide());
        }
        return InteractionResultHolder.pass(itemStack);
    }

    private Boat getBoat(Level level, HitResult hitResult) {
        ScribeBoat boat = new ScribeBoat(level, hitResult.getLocation().x, hitResult.getLocation().y, hitResult.getLocation().z);
        ScribeChestBoat chestBoat = new ScribeChestBoat(level, hitResult.getLocation().x, hitResult.getLocation().y, hitResult.getLocation().z);

        return this.hasChest ? chestBoat : boat;
    }
}
