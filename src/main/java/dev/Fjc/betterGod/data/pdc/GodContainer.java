package dev.Fjc.betterGod.data.pdc;

import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

@Deprecated
public class GodContainer implements PersistentDataType<byte[], Boolean> {
    @Override
    public @NotNull Class<byte[]> getPrimitiveType() {
        return byte[].class;
    }

    @Override
    public @NotNull Class<Boolean> getComplexType() {
        return Boolean.class;
    }

    @Override
    public byte @NonNull [] toPrimitive(@NonNull Boolean complex, @NotNull PersistentDataAdapterContext context) {
        if (complex) return new byte[]{1};
        else return new byte[]{0};
    }

    @Override
    public @NonNull Boolean fromPrimitive(byte @NonNull [] primitive, @NotNull PersistentDataAdapterContext context) {
        byte b = primitive[0];
        return b == 1;
    }
}
