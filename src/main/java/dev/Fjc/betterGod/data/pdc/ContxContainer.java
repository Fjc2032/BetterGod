package dev.Fjc.betterGod.data.pdc;

import dev.Fjc.betterGod.data.Invincibility;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

/**
 * A {@link PersistentDataType} implementation that saves the context of invincibility on a player's container.
 */
public class ContxContainer implements PersistentDataType<byte[], Invincibility.Context> {
    @Override
    public @NotNull Class<byte[]> getPrimitiveType() {
        return byte[].class;
    }

    @Override
    public @NotNull Class<Invincibility.Context> getComplexType() {
        return Invincibility.Context.class;
    }

    @Override
    public byte @NonNull [] toPrimitive(Invincibility.@NonNull Context complex, @NotNull PersistentDataAdapterContext context) {
        return new byte[]{complex.deserialize()};
    }

    @Override
    public Invincibility.@NonNull Context fromPrimitive(byte @NonNull [] primitive, @NotNull PersistentDataAdapterContext context) {
        for (Invincibility.Context context1 : Invincibility.Context.values()) {
            if (context1.deserialize() == primitive[0]) return context1;
        }
        return Invincibility.Context.FULL;
    }
}
