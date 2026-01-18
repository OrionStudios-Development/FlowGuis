package it.orionstudios.flowgui.internal;

import it.orionstudios.flowgui.FlowGui;
import it.orionstudios.flowgui.item.GuiItem;

public final class SlotBatch {

    private final int[] slots;
    private int page;
    private GuiItem item;

    public SlotBatch(int... slots) {
        this.slots = slots;
    }

    public SlotBatch page(int page) {
        this.page = page;
        return this;
    }

    public SlotBatch item(GuiItem item) {
        this.item = item;
        return this;
    }

    void apply(FlowGui gui) {
        for (int slot : slots) {
            gui.applySlot(slot, page, item);
        }
    }
}
