package it.orionstudios.flowgui.animation.animations;

import it.orionstudios.flowgui.animation.Animation;
import org.bukkit.inventory.ItemStack;

public final class PulseAnimation implements Animation {

    @Override
    public ItemStack apply(ItemStack base, long tick) {
        ItemStack copy = base.clone();
        var meta = copy.getItemMeta();

        if ((tick / 10) % 2 == 0) {
            meta.setDisplayName("§e➤ " + meta.getDisplayName());
        }

        copy.setItemMeta(meta);
        return copy;
    }
}
