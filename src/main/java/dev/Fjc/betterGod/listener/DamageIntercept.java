package dev.Fjc.betterGod.listener;

import dev.Fjc.betterGod.data.Keys;
import dev.Fjc.betterGod.data.pdc.ContxContainer;
import dev.Fjc.betterGod.depend.Mythic;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.persistence.PersistentDataContainer;

public class DamageIntercept implements Listener {

    @EventHandler
    public void onDamageTaken(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;

        PersistentDataContainer container = player.getPersistentDataContainer();
        var value = container.get(Keys.invinceKey, new ContxContainer());
        switch (value) {
            case FULL -> event.setCancelled(true);
            case COMBAT -> {
                if (event instanceof EntityDamageByEntityEvent)
                    event.setCancelled(true);
            }
            case ENV -> {
                if (event instanceof EntityDamageByBlockEvent ||
                isEnvSource(event)) event.setCancelled(true);
            }
            case VANILLA -> {
                if (!isCustom(event) && !Mythic.isMythicMobInRange(player.getLocation(), 45))
                    event.setCancelled(true);
            }
            case null, default -> {}
        }
    }

    private boolean isEnvSource(EntityDamageEvent event) {
        switch (event.getCause()) {
            case FALL, FIRE, FIRE_TICK, FREEZE, SUFFOCATION,
                 MELTING, LAVA, DROWNING, BLOCK_EXPLOSION,
                 ENTITY_EXPLOSION, LIGHTNING, MAGIC, FALLING_BLOCK,
                 FLY_INTO_WALL, CRAMMING, HOT_FLOOR, CAMPFIRE
                -> {return true;}
            default -> {return false;}
        }
    }

    private boolean isCustom(EntityDamageEvent event) {
        Entity damager = event.getDamageSource().getDirectEntity();
        if (damager != null) return Mythic.isMythicMob(damager);

        return event.getCause() == EntityDamageEvent.DamageCause.CUSTOM;
    }
}
