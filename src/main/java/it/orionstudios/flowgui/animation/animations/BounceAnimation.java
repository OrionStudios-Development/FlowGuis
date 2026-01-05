package it.orionstudios.flowgui.animation.animations;

import it.orionstudios.flowgui.animation.Animation;
import org.bukkit.inventory.ItemStack;

public final class BounceAnimation implements Animation {

    @Override
    public ItemStack apply(ItemStack base, long tick) {
        ItemStack copy = base.clone();
        copy.setAmount(1 + (int) (tick % 3));
        return copy;
    }
}
