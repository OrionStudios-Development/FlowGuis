package it.orionstudios.flowgui.Animation;

import it.orionstudios.flowgui.Animation.Animation;
import it.orionstudios.flowgui.Animation.GlowAnimation;

public final class Animations {

    private Animations() {}

    public static Animation glow() {
        return new GlowAnimation ();
    }
}
