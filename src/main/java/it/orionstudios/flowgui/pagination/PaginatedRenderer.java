package it.orionstudios.flowgui.pagination;

import it.orionstudios.flowgui.item.GuiItem;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Consumer;

public class PaginatedRenderer<T> {

    private final List<T> data;
    private final int pageSize;
    private int page = 0;

    private PaginatedRenderer(List<T> data, int pageSize) {
        this.data = data;
        this.pageSize = pageSize;
    }

    public static <T> PaginatedRenderer<T> of(List<T> data, int pageSize) {
        return new PaginatedRenderer<>(data, pageSize);
    }

    public void render(
            int[] slots,
            java.util.function.BiFunction<T, Integer, GuiItem> mapper,
            java.util.function.Consumer<Map<Integer, GuiItem>> callback
    ) {
        Map<Integer, GuiItem> map = new HashMap<>();
        int start = page * pageSize;

        for (int i = 0; i < slots.length; i++) {
            int index = start + i;
            if (index >= data.size()) break;
            map.put(slots[i], mapper.apply(data.get(index), index));
        }

        callback.accept(map);
    }

    /* ===== PAGE API ===== */

    public int page() {
        return page;
    }

    public int pages() {
        return (int) Math.ceil((double) data.size() / pageSize);
    }

    public boolean hasNext() {
        return page + 1 < pages();
    }

    public boolean hasPrevious() {
        return page > 0;
    }

    public void next() {
        if (hasNext()) page++;
    }

    public void previous() {
        if (hasPrevious()) page--;
    }
}


