package it.orionstudios.flowgui.item;

import it.orionstudios.flowgui.animation.Animation;
import it.orionstudios.flowgui.context.GuiContext;
import it.orionstudios.flowgui.utils.EnchantUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class GuiItem {

    private final ItemStack item;
    private Consumer<GuiContext> click;
    private Animation animation;
    private long tick = 0;
    private Predicate<Player> showCondition = p -> true;
    private Predicate<Player> disableCondition = p -> false;
    private final Map<String, Consumer<GuiItem>> states = new HashMap<> ();
    private String activeState;
    private GuiItem(ItemStack item) {
        this.item = item;
    }


    /* ===== FACTORY ===== */

    public static GuiItem of(Material material) {
        return new GuiItem(new ItemStack(material));
    }

    public static GuiItem of(ItemStack stack) {
        return new GuiItem(stack.clone());
    }

    /* ===== META ===== */

    public GuiItem name(String name) {
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        item.setItemMeta(meta);
        return this;
    }

    public GuiItem lore(String... lore) {
        ItemMeta meta = item.getItemMeta();
        meta.setLore(List.of(lore));
        item.setItemMeta(meta);
        return this;
    }

    /* ===== GLINT / ENCHANT API ===== */

    /** Simple visual glint */
    public GuiItem enchanted() {
        return enchanted(true);
    }

    /** Enable / disable glint */
    public GuiItem enchanted(boolean glint) {
        ItemMeta meta = item.getItemMeta();

        if (glint) {
            item.addUnsafeEnchantment(Enchantment.UNBREAKING, 1);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        } else {
            item.removeEnchantment(Enchantment.UNBREAKING);
            meta.removeItemFlags(ItemFlag.HIDE_ENCHANTS);
        }

        item.setItemMeta(meta);
        return this;
    }

    /** Single enchant by name */
    public GuiItem enchanted(String enchant, int level) {
        Enchantment ench = EnchantUtils.fromString(enchant);
        if (ench != null) {
            item.addUnsafeEnchantment(ench, level);
        }
        return this;
    }

    /** Multiple enchants (String, int, String, int...) */
    public GuiItem enchanted(Object... enchants) {
        if (enchants.length % 2 != 0) {
            throw new IllegalArgumentException(
                    "Enchant list must be (String, Integer) pairs"
            );
        }

        for (int i = 0; i < enchants.length; i += 2) {
            String name = (String) enchants[i];
            int level = (int) enchants[i + 1];

            Enchantment ench = EnchantUtils.fromString(name);
            if (ench != null) {
                item.addUnsafeEnchantment(ench, level);
            }
        }
        return this;
    }

    /** Enchants via map */
    public GuiItem enchanted(Map<String, Integer> enchants) {
        enchants.forEach((name, level) -> {
            Enchantment ench = EnchantUtils.fromString(name);
            if (ench != null) {
                item.addUnsafeEnchantment(ench, level);
            }
        });
        return this;
    }

    public GuiItem showIf(Predicate<Player> condition) {
        this.showCondition = condition;
        return this;
    }

    public GuiItem disableIf(Predicate<Player> condition) {
        this.disableCondition = condition;
        return this;
    }

    /* ===== EXTRA ITEM META ===== */

    public GuiItem flags(ItemFlag... flags) {
        ItemMeta meta = item.getItemMeta();
        meta.addItemFlags(flags);
        item.setItemMeta(meta);
        return this;
    }

    public GuiItem unbreakable(boolean value) {
        ItemMeta meta = item.getItemMeta();
        meta.setUnbreakable(value);
        item.setItemMeta(meta);
        return this;
    }

    /* ===== ANIMATION ===== */

    public GuiItem animation(Animation animation) {
        this.animation = animation;
        return this;
    }

    /* ===== CLICK ===== */

    public GuiItem onClick(Consumer<GuiContext> click) {
        this.click = click;
        return this;
    }

    /* ===== INTERNAL ===== */

    public ItemStack render(Player player) {
        if (animation == null) return item;
        ItemStack base = item.clone();

        if (activeState != null && states.containsKey(activeState)) {
            states.get(activeState).accept(this);
        }

        tick++;
        return animation == null ? base : animation.apply(base, tick);
    }
    public boolean shouldShow(Player p) {
        return showCondition.test(p);
    }

    public boolean isDisabled(Player p) {
        return disableCondition.test(p);
    }

    public ItemStack renderDisabled() {
        ItemStack copy = item.clone();
        var meta = copy.getItemMeta();
        meta.setLore(List.of("§cLocked"));
        copy.setItemMeta(meta);
        return copy;
    }

    public GuiItem state(String name, Consumer<GuiItem> builder) {
        states.put(name, builder);
        return this;
    }

    public GuiItem stateIf(String name, Predicate<Player> condition) {
        if (condition.test( Bukkit.getPlayerExact("dummy"))) {
            this.activeState = name;
        }
        return this;
    }

    public void click(GuiContext ctx) {
        if (click != null) click.accept(ctx);
    }
}
