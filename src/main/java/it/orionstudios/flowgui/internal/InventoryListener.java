package it.orionstudios.flowgui.internal;

import it.orionstudios.flowgui.internal.GuiManager;
import it.orionstudios.flowgui.FlowGui;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.entity.Player;

public class InventoryListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if (!(e.getWhoClicked() instanceof Player player)) return;
        FlowGui gui = GuiManager.get(player);
        if (gui != null && e.getInventory().equals(player.getOpenInventory().getTopInventory())) {
            gui.handleClick(e);
        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent e) {
        if (e.getPlayer() instanceof Player player) {
            GuiManager.unregister(player);
        }
    }
}
