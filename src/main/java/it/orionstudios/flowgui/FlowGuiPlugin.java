package it.orionstudios.flowgui;

import it.orionstudios.flowgui.internal.InventoryListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class FlowGuiPlugin extends JavaPlugin {

    private static FlowGuiPlugin instance;

    @Override
    public void onEnable() {
        instance = this;
        Bukkit.getPluginManager().registerEvents(new InventoryListener(), this);

        Bukkit.getScheduler().runTaskTimer(this, () ->
                Bukkit.getOnlinePlayers().forEach(player -> {
                    var gui = it.orionstudios.flowgui.internal.GuiManager.get(player);
                    if (gui != null) gui.redraw();
                }), 1L, 1L
        );
    }

    public static FlowGuiPlugin instance() {
        return instance;
    }
}
