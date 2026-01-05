package it.orionstudios.flowgui.animation;

import org.bukkit.inventory.ItemStack;

public interface Animation {
    ItemStack apply(ItemStack base, long tick);
}
