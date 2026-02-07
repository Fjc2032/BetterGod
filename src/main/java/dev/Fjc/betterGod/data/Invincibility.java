package dev.Fjc.betterGod.data;

import dev.Fjc.betterGod.Util;
import dev.Fjc.betterGod.data.pdc.ContxContainer;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

import static dev.Fjc.betterGod.GodInstance.b;
import static dev.Fjc.betterGod.GodInstance.plugin;
import static dev.Fjc.betterGod.data.Keys.invinceKey;

/**
 * Represents a state of invincibility on a target. Even though {@link Player#setInvulnerable(boolean)} exists, you have
 * to constantly set it on or off for certain events where you don't want a player to be invincible. So I'm doing it myself.
 */
public class Invincibility {

    private final Player target;
    private final PersistentDataContainer container;

    public Invincibility(@NotNull Player player) {
        this.target = player;
        this.container = player.getPersistentDataContainer();
    }

    /**
     * Sets a player fully invincible to all sources of damage.
     * @return This class, for chaining
     */
    public Invincibility setInvincible() {
        container.set(invinceKey, new ContxContainer(), Context.FULL);
        b.putIfAbsent(target, true);

        return this;
    }

    /**
     * Sets a player invincible to a select source of damage.
     * @param context The context the invincibility should be present on
     * @return This class, for chaining
     */
    public Invincibility setInvincible(Context context) {
        container.set(invinceKey, new ContxContainer(), context);
        b.putIfAbsent(target, true);

        return this;
    }

    /**
     * Sets a player invincible to select sources of damage
     * @param contexts The contexts the invincibility should be present on
     * @return This class, for chaining
     */
    public Invincibility setInvincible(Context... contexts) {
        for (Context context : contexts) container.set(invinceKey, new ContxContainer(), context);
        b.putIfAbsent(target, true);

        return this;
    }

    /**
     * Sets a player invincible to select sources of damage for a specified duration.
     * @param context The context the invincibility should be present on
     * @param duration How long the invincibility should be active
     * @return This class, for chaining
     */
    public Invincibility setInvincible(Context context, Duration duration) {
        container.set(invinceKey, new ContxContainer(), context);

        plugin.getServer().getScheduler().runTaskLater(
                plugin,
                task -> removeInvincibility(),
                duration.get(ChronoUnit.SECONDS)
        );

        return this;
    }

    /**
     * Sends a message to the player regarding invincibility being toggled.
     * @param content The message to send. You can input any string, but mentioning the context of the
     *                invincibility is recommended.
     * @return This class, for chaining
     * @apiNote You must set an invincibility state on the player before calling this method.
     */
    public Invincibility announceToPlayer(String content) {
        if (!b.containsKey(target)) {
            Util.severe("Player was not set invincible first.");
            return null;
        }
        target.sendMessage(Util.nbt(content));

        return this;
    }

    /**
     * Removes invincibility from a player.
     * @return This class, for chaining
     */
    public Invincibility removeInvincibility() {
        container.remove(invinceKey);
        b.put(target, false);

        return this;
    }

    /**
     * Represents the context in which invincibility was activated. Context may determine what the player is
     * or isn't invincible against, and may also determine future behaviors.
     */
    public enum Context {
        /**
         * Immunity to all forms of damage.
         */
        FULL(4),
        /**
         * Immunity to any standard forms of damage. This excludes damage dealt by custom content
         * or anything that isn't part of the base game.
         */
        VANILLA(3),
        /**
         * Immunity to environment variables, such as kinetic energy, fall damage, and magic.
         */
        ENV(0),
        /**
         * Immunity to combat damage from all living entities, including players. Note that this does not block
         * indirect damage (e.g. an explosion triggered by a player)
         */
        COMBAT(1);

        private final int level;

        Context() {
            this.level = 4;
        }
        Context(int level) {
            this.level = level;
        }

        public byte deserialize() {
            return (byte) this.ordinal();
        }

        public int getLevel() {
            return this.level;
        }
    }
}
