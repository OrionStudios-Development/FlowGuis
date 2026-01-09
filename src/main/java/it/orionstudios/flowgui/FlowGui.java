package it.orionstudios.flowgui;

import it.orionstudios.flowgui.animation.Animation;
import it.orionstudios.flowgui.context.GuiContext;
import it.orionstudios.flowgui.internal.GuiActions;
import it.orionstudios.flowgui.internal.GuiManager;
import it.orionstudios.flowgui.internal.InventoryListener;
import it.orionstudios.flowgui.item.GuiItem;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;
import it.orionstudios.flowgui.animation.animations.*;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public abstract class FlowGui {

    private String title = "GUI";
    private int size = 9;

    private Player player;
    private Inventory inventory;

    private final Map<Integer, GuiItem> items = new HashMap<>();
    private Consumer<Player> onOpen;
    private Consumer<Player> onClose;

    private int page = 0;
    private int pages = 1;

    private final Map<Integer, Map<Integer, GuiItem>> pageItems = new HashMap<>();

    /* ===== API FLUENTE ===== */

    protected void title(String title) {
        this.title = title;
    }

    protected void size(int size) {
        this.size = size;
    }

    protected void slot(int slot, int page, GuiItem item) {
        if (page < 0 || page >= pages) return;
        if (!item.shouldShow(player)) return;

        inventory.setItem(slot,
                item.isDisabled(player)
                        ? item.renderDisabled()
                        : item.render(player)
        );

        pageItems
                .computeIfAbsent(page, k -> new HashMap<>())
                .put(slot, item);

        if (this.page == page && inventory != null) {
            inventory.setItem(slot, item.render(player));
        }
    }

    public void redraw() {
        inventory.clear();

        Map<Integer, GuiItem> items = pageItems.get(page);
        if (items != null) {
            items.forEach((slot, item) ->
                    inventory.setItem(slot, item.render(player))
            );
        }
    }



    /* ===== LIFECYCLE ===== */

    public void open(Player player, JavaPlugin plugin) {
        this.player = player;
        this.inventory = Bukkit.createInventory(null, size, title);

        registerListener(plugin);
        draw();
        player.openInventory(inventory);
        GuiManager.register(player, this);

        if (onOpen != null) onOpen.accept(player);
    }

    private static boolean listenerRegistered = false;

    private static void registerListener(JavaPlugin plugin) {
        if (!listenerRegistered) {
            Bukkit.getPluginManager().registerEvents(new InventoryListener (), plugin);
            listenerRegistered = true;
        }
    }

    public void close() {
        player.closeInventory();
        GuiManager.unregister(player);
        if (onClose != null) onClose.accept(player);
    }

    public void handleClick(InventoryClickEvent e) {
        e.setCancelled(true);

        Map<Integer, GuiItem> items = pageItems.get(page);
        if (items == null) return;

        GuiItem item = items.get(e.getSlot());
        if (item != null) {
            item.click(new GuiContext(player, e.getSlot(), this));
        }
    }


    protected abstract void drawPage(int page);

    /** Imposta numero totale di pagine */
    protected void pages(int total) {
        if (total < 1) total = 1;
        this.pages = total;
    }

    /** Vai alla pagina successiva */
    public void internalnextPage() {
        if (hasNextPage()) page++;
        redraw();
    }

    /** Vai alla pagina precedente */
    public void internalpreviousPage() {
        if (hasPreviousPage()) page--;
        redraw();
    }

    /** Restituisce pagina attuale (0-indexed) */
    protected int page() {
        return page;
    }

    /** Numero totale di pagine */
    protected int pages() {
        return pages;
    }

    /** Controlla se esiste pagina successiva */
    public boolean hasNextPage() {
        return page < pages - 1;
    }

    /** Controlla se esiste pagina precedente */
    public boolean hasPreviousPage() {
        return page > 0;
    }

    protected final void draw() {
        drawPage(page);
    }

    public void applySlot(int slot, int page, GuiItem item) {
        slot(slot, page, item);
    }

    protected void onOpen(Consumer<Player> action) {
        this.onOpen = action;
    }

    protected void onClose(Consumer<Player> action) {
        this.onClose = action;
    }

    public Inventory inventory() {
        return inventory;
    }

    /* ================= ACTIONS FACADE ================= */

    public static Consumer<GuiContext> message(String msg) {
        return GuiActions.message(msg);
    }

    public static Consumer<GuiContext> nextPage() {
        return GuiActions.nextPage();
    }

    public static Consumer<GuiContext> previousPage() {
        return GuiActions.previousPage();
    }

    public static Consumer<GuiContext> sound(
            org.bukkit.Sound sound,
            float volume,
            float pitch
    ) {
        return GuiActions.sound(sound, volume, pitch);
    }

    public static Consumer<GuiContext> requirePermission(
            String permission,
            Consumer<GuiContext> action
    ) {
        return GuiActions.requirePermission(permission, action);
    }

    public static Consumer<GuiContext> withLevel(
            java.util.function.IntConsumer action
    ) {
        return GuiActions.withLevel(action);
    }

    public static Animation glow() {
        return new GlowAnimation ();
    }

    public static Animation pulse() {
        return new PulseAnimation ();
    }

    public static Animation bounce() {
        return new BounceAnimation ();
    }
}


