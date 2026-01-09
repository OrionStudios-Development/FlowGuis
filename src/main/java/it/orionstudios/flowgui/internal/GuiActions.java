package it.orionstudios.flowgui.internal;

import it.orionstudios.flowgui.FlowGui;
import it.orionstudios.flowgui.context.GuiContext;
import it.orionstudios.flowgui.item.GuiItem;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.function.Consumer;

public final class GuiActions {

    private GuiActions() {} // non istanziabile

    public static Consumer<GuiContext> message(String msg) {
        return ctx -> ctx.player().sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
    }

    public static Consumer<GuiContext> sound(Sound sound, float volume, float pitch) {
        return ctx -> ctx.player().playSound(ctx.player().getLocation(), sound, volume, pitch);
    }

    public static Consumer<GuiContext> nextPage() {
        return ctx -> {
            FlowGui gui = ctx.gui();
            if (gui.hasNextPage()) {
                gui.internalnextPage ();
                gui.redraw();
            }
        };
    }

    public static Consumer<GuiContext> previousPage() {
        return ctx -> {
            FlowGui gui = ctx.gui();
            if (gui.hasPreviousPage()) {
                gui.internalpreviousPage ();
                gui.redraw();
            }
        };
    }

    public static Consumer<GuiContext> requirePermission(String permission, Consumer<GuiContext> action) {
        return ctx -> {
            Player p = ctx.player();
            if (p.hasPermission(permission)) {
                action.accept(ctx);
            } else {
                p.sendMessage(ChatColor.RED + "You don't have permission!");
            }
        };
    }

    public static Consumer<GuiContext> withLevel(java.util.function.IntConsumer action) {
        return ctx -> action.accept(ctx.player().getLevel());
    }
}
