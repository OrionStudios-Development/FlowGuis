package it.orionstudios.flowguis.example;

import it.orionstudios.flowguis.FlowGui;
import it.orionstudios.flowguis.animation.Animations;
import it.orionstudios.flowguis.item.GuiItem;
import it.orionstudios.flowguis.pagination.PaginatedRenderer;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class ShopGui extends FlowGui {

    private final PaginatedRenderer<ItemStack> pager;

    public ShopGui(List<ItemStack> items) {
        title("§aShop");
        size(27);

        pager = PaginatedRenderer.of(items);
    }

    @Override
    protected void draw() {

        int[] itemSlots = {10,11,12,13,14};

        pager.renderAsync(
                itemSlots,
                (item, index) -> GuiItem.of(item)
                        .name("Item #" + index)
                        .animation(Animations.glow())
                        .onClick(ctx ->
                                ctx.player().sendMessage("You clicked item #" + index)
                        ),
                this::apply
        );

        slot(18, GuiItem.of(Material.ARROW)
                .name("← Previous Page")
                .onClick(e -> {
                    pager.previous();
                    redraw();
                }));

        slot(26, GuiItem.of(Material.ARROW)
                .name("Next Page →")
                .enchanted()
                .onClick(e -> {
                    pager.next();
                    redraw();
                }));
    }
}