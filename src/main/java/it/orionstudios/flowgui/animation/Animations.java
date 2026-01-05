package it.orionstudios.flowgui.animation;

import it.orionstudios.flowgui.animation.animations.*;

public final class Animations {

    private Animations() {}

    public static Animation glow() {
        return new GlowAnimation ();
    }

    public static Animation pulse() {
        return new PulseAnimation ();
    }

    public static Animation bounce() {
        return new BounceAnimation ();
    }
}

