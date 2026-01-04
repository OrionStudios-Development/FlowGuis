package it.orionstudios.flowgui.Animation;

import it.orionstudios.flowgui.Animation.Animation;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

public class GlowAnimation implements Animation {

    private boolean glow = false;

    @Override
    public ItemStack apply(ItemStack base) {
        ItemStack copy = base.clone();
        glow = !glow;

        if (glow)
            copy.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
        else
            copy.removeEnchantment(Enchantment.DURABILITY);

        return copy;
    }
}
