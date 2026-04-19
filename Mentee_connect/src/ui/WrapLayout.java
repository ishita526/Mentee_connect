package ui;

import java.awt.*;

public class WrapLayout extends FlowLayout {

    public WrapLayout() {
        super();
    }

    public WrapLayout(int align) {
        super(align);
    }

    public WrapLayout(int align, int hgap, int vgap) {
        super(align, hgap, vgap);
    }

    @Override
    public Dimension preferredLayoutSize(Container target) {
        synchronized (target.getTreeLock()) {
            return layoutSize(target, true);
        }
    }

    @Override
    public Dimension minimumLayoutSize(Container target) {
        synchronized (target.getTreeLock()) {
            return layoutSize(target, false);
        }
    }

    private Dimension layoutSize(Container target, boolean preferred) {

        int maxWidth = target.getWidth();

        // fallback for initial rendering
        if (maxWidth <= 0) {
            maxWidth = Integer.MAX_VALUE;
        }

        Insets insets = target.getInsets();
        int width = maxWidth - (insets.left + insets.right);

        int x = 0;
        int y = 0;
        int rowHeight = 0;

        for (Component comp : target.getComponents()) {

            Dimension d = preferred ? comp.getPreferredSize() : comp.getMinimumSize();

            if (x + d.width > width) {
                x = 0;
                y += rowHeight + getVgap();
                rowHeight = 0;
            }

            if (x > 0) {
                x += getHgap();
            }

            x += d.width;
            rowHeight = Math.max(rowHeight, d.height);
        }

        y += rowHeight;

        return new Dimension(maxWidth, y + insets.top + insets.bottom);
    }
}