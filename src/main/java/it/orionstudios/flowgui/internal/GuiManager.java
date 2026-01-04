package it.orionstudios.flowgui.internal;

import it.orionstudios.flowgui.FlowGui;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class GuiManager {

    private static final Map<UUID, FlowGui> OPEN = new HashMap<>();

    public static void register(Player player, FlowGui gui) {
        OPEN.put(player.getUniqueId(), gui);
    }

    public static void unregister(Player player) {
        OPEN.remove(player.getUniqueId());
    }

    public static FlowGui get(Player player) {
        return OPEN.get(player.getUniqueId());
    }
}
