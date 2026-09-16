package com.run4fight.client.core.overlay;

/**
 * Size of an overlay's drawn content, used for drag hit-testing
 * and screen-edge clamping in the config screen.
 */
public record OverlayBounds(int width, int height) {

    public static final OverlayBounds EMPTY = new OverlayBounds(0, 0);

    public boolean contains(
            int originX,
            int originY,
            double pointX,
            double pointY
    ) {
        return pointX >= originX
                && pointX <= originX + width
                && pointY >= originY
                && pointY <= originY + height;
    }
}
