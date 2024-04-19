package com.teamabode.sketch.core.api.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;


public class ShieldEvents {

    public static final Event<Blocked> BLOCKED = EventFactory.createArrayBacked(Blocked.class, listeners -> (player, damageSource, damageAmount) -> {
        for (Blocked listener : listeners) {
            listener.blocked(player, damageSource, damageAmount);
        }
    });

    public static final Event<Disabled> DISABLED = EventFactory.createArrayBacked(Disabled.class, listeners -> (player, attacker) -> {
        for (Disabled listener : listeners) {
            listener.disabled(player, attacker);
        }
    });

    @FunctionalInterface
    public interface Blocked {
        void blocked(Player player, DamageSource damageSource, float damageAmount);
    }

    @FunctionalInterface
    public interface Disabled {
        void disabled(Player player, LivingEntity attacker);
    }
}
