package it.orionstudios.flowgui;

import it.orionstudios.flowgui.context.GuiContext;
import it.orionstudios.flowgui.internal.GuiManager;
import it.orionstudios.flowgui.item.GuiItem;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;
import java.util.Map;

public abstract class FlowGui {

    private String title = "GUI";
    private int size = 9;

    private Player player;
    private Inventory inventory;

    private final Map<Integer, GuiItem> items = new HashMap<>();

    protected abstract void draw();

    /* ===== API FLUENTE ===== */

    protected void title(String title) {
        this.title = title;
    }

    protected void size(int size) {
        this.size = size;
    }

    protected void slot(int slot, GuiItem item) {
        items.put(slot, item);
        inventory.setItem(slot, item.render());
    }

    protected void apply(Map<Integer, GuiItem> map) {
        map.forEach(this::slot);
    }

    protected void redraw() {
        items.clear();
        draw();
    }

    /* ===== LIFECYCLE ===== */

    public void open(Player player) {
        this.player = player;
        this.inventory = Bukkit.createInventory(null, size, title);
        draw();
        player.openInventory(inventory);
        GuiManager.register(player, this);
    }

    public void close() {
        player.closeInventory();
        GuiManager.unregister(player);
    }

    public void handleClick(InventoryClickEvent e) {
        e.setCancelled(true);
        GuiItem item = items.get(e.getSlot());
        if (item != null) {
            item.click(new GuiContext(player, e.getSlot(), this));
        }
    }

    public Inventory inventory() {
        return inventory;
    }
}

