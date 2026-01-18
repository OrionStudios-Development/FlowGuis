package it.orionstudios.flowgui.pagination;

public sealed interface PageTarget
        permits PageTarget.Single, PageTarget.All {

    record Single(int page) implements PageTarget {}
    final class All implements PageTarget {
        private All() {}
        public static final All INSTANCE = new All();
    }
}
