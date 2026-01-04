# FlowGuis 🌀
### Simple, fluent and powerful GUI library for Minecraft plugins

FlowGuis is a **developer-friendly GUI library** for Minecraft (Paper / Spigot 1.19+)  
designed to make inventories **easy, readable and fun to use**, even for beginners.

> Stop fighting Bukkit inventories.  
> Start writing GUIs that make sense.

---

## ✨ Features

- ✅ Fluent & readable API
- ✅ No Bukkit inventory spaghetti
- ✅ Built-in pagination
- ✅ Async rendering support
- ✅ Item animations
- ✅ Zero boilerplate
- ✅ One main class to extend: `FlowGui`

---

# How to use?

We've created a test GUI so you can understand how to best use this library!

The file is ShopGui.java

Here is a copy of it:

``` java

public class ShopGui extends FlowGui {

    private final PaginatedRenderer<ItemStack> pager;

    public ShopGui(List<ItemStack> items) {
        title("§aShop");
        size(27);
        pager = PaginatedRenderer.of(items);
    }

    @Override
    protected void draw() {

        int[] slots = {10,11,12,13,14};

        pager.renderAsync(
            slots,
            (item, i) -> GuiItem.of(item)
                .name("Item #" + i)
                .animation(Animations.glow())
                .onClick(ctx -> ctx.player().sendMessage("Purchased!")),
            this::apply
        );

        slot(18, GuiItem.of(Material.ARROW)
            .name("← Previous")
            .onClick(e -> { pager.previous(); redraw(); }));

        slot(26, GuiItem.of(Material.ARROW)
            .name("Next →")
            .enchanted()
            .onClick(e -> { pager.next(); redraw(); }));
        }
    }
```

## How i open the GUI?
In the main file you import the GUI File and call like this in the onEnable Method
``` java
import org.example.example.guis.ShopGui;

@Override
    public void onEnable() {
        instance = this;
        new ShopGui(items).open(player);
    }
```

## 📦 Installation

### Gradle
```

repositories {
    maven { url 'https://jitpack.io' }
}

dependencies {
    implementation 'it.orionstudios:flowguis:1.0.0'
}

```

### Maven
``` xml
<repository>
    <id>jitpack.io</id>
    <url>https://jitpack.io</url>
</repository>

<dependency>
    <groupId>it.orionstudios</groupId>
    <artifactId>flowguis</artifactId>
    <version>1.0.0</version>
</dependency>
``` 
## Need Help?
### Join our Discord Server!

#### https://discord.gg/JC4RNbQnfx

