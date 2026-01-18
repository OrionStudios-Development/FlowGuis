package it.orionstudios.flowgui.context;

import it.orionstudios.flowgui.FlowGui;
import org.bukkit.entity.Player;

public record GuiContext(Player player, int slot, FlowGui gui) {}
