package it.orionstudios.flowgui.pagination;

import it.orionstudios.flowgui.FlowGuiPlugin;
import it.orionstudios.flowgui.item.GuiItem;
import org.bukkit.Bukkit;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Consumer;

public class PaginatedRenderer<T> {

    private final List<T> data;
    private int page = 0;

    private PaginatedRenderer(List<T> data) {
        this.data = data;
    }

    public static <T> PaginatedRenderer<T> of(List<T> data) {
        return new PaginatedRenderer<>(data);
    }

    public void renderAsync(
            int[] slots,
            BiFunction<T, Integer, GuiItem> mapper,
            Consumer<Map<Integer, GuiItem>> callback
    ) {
        Bukkit.getScheduler().runTaskAsynchronously(
                FlowGuiPlugin.instance(), () -> {

                    Map<Integer, GuiItem> map = new HashMap<>();
                    int start = page * slots.length;

                    for (int i = 0; i < slots.length; i++) {
                        int index = start + i;
                        if (index >= data.size()) break;
                        map.put(slots[i], mapper.apply(data.get(index), index));
                    }

                    Bukkit.getScheduler().runTask(
                            FlowGuiPlugin.instance(),
                            () -> callback.accept(map)
                    );
                });
    }

    public void next() { page++; }
    public void previous() { if (page > 0) page--; }
}
