package it.orionstudios.flowgui.utils;

import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public final class EnchantUtils {

    private static final Map<String, Enchantment> CACHE = new HashMap<>();

    static {
        for (Enchantment enchantment : Enchantment.values()) {
            CACHE.put(
                    enchantment.getKey().getKey().toLowerCase(Locale.ROOT),
                    enchantment
            );
        }
    }

    private EnchantUtils() {}

    public static Enchantment fromString(String name) {
        if (name == null) return null;
        return CACHE.get(name.toLowerCase(Locale.ROOT));
    }
}
