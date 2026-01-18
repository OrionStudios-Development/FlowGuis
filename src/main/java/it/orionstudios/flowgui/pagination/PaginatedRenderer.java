package it.orionstudios.flowgui.pagination;

import it.orionstudios.flowgui.FlowGui;
import it.orionstudios.flowgui.item.GuiItem;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Consumer;

public class PaginatedRenderer<T> {

    private final FlowGui gui;
    private final List<T> data;
    private final int pageSize;

    private PaginatedRenderer(FlowGui gui, List<T> data, int pageSize) {
        this.gui = gui;
        this.data = data;
        this.pageSize = pageSize;
    }

    public static <T> PaginatedRenderer<T> of(
            FlowGui gui,
            List<T> data,
            int pageSize
    ) {
        return new PaginatedRenderer<> ( gui, data, pageSize );
    }

    public void render(
            int[] slots,
            BiFunction<T, Integer, GuiItem> mapper,
            Consumer<Map<Integer, GuiItem>> callback
    ) {
        Map<Integer, GuiItem> map = new HashMap<> ( );

        int page = gui.page ( ); // 🔥 prende la pagina dalla GUI
        int start = page * slots.length;

        for (int i = 0; i < slots.length; i++) {
            int index = start + i;
            if (index >= data.size ( )) break;

            map.put ( slots[i], mapper.apply ( data.get ( index ), index ) );
        }

        callback.accept ( map );
    }
}