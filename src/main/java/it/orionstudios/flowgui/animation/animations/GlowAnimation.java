package it.orionstudios.flowgui.animation.animations;

import it.orionstudios.flowgui.animation.Animation;
import org.bukkit.inventory.ItemStack;

public final class GlowAnimation implements Animation {

    @Override
    public ItemStack apply(ItemStack base, long tick) {
        ItemStack copy = base.clone();
        copy.addUnsafeEnchantment(
                org.bukkit.enchantments.Enchantment.UNBREAKING, 1
        );
        copy.getItemMeta().addItemFlags(
                org.bukkit.inventory.ItemFlag.HIDE_ENCHANTS
        );
        return copy;
    }
}
