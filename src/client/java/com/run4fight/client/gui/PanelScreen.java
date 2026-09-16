package com.run4fight.client.gui;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

/**
 * A centered panel with a border, a title and a vertical list of rows,
 * plus navigation back to the screen that opened it. Shared chrome for
 * every BeeMod screen.
 */
public abstract class PanelScreen extends Screen {

    protected static final int BACKGROUND_COLOR = 0xCC000000;
    protected static final int PANEL_COLOR = 0xFF111111;
    protected static final int BORDER_COLOR = 0xFF555555;
    protected static final int BORDER_SIZE = 2;

    protected static final int TEXT_COLOR = 0xFFFFFFFF;
    protected static final int ACCENT_COLOR = 0xFFFFAA00;
    protected static final int MUTED_COLOR = 0xFFAAAAAA;

    protected static final int PANEL_WIDTH = 400;

    /** Vertical distance between two rows. */
    protected static final int ROW_HEIGHT = 30;

    /** Panel top to the first row. */
    protected static final int CONTENT_TOP = 65;

    /** Space below the last row, for the scroll indicator and Done button. */
    protected static final int FOOTER_HEIGHT = 75;

    protected static final int ROW_INSET = 30;
    protected static final int ROW_BUTTON_WIDTH = 130;
    protected static final int ROW_BUTTON_HEIGHT = 20;

    /** Smallest gap kept between the panel and the screen edges. */
    private static final int SCREEN_MARGIN = 20;

    private final Screen parent;

    private int scrollOffset = 0;

    protected PanelScreen(Text title, Screen parent) {
        super(title);
        this.parent = parent;
    }

    /** Total rows this screen wants to show, scrolled or not. */
    protected abstract int rowCount();

    protected int panelWidth() {
        return PANEL_WIDTH;
    }

    protected final int panelHeight() {
        return CONTENT_TOP + visibleRowCount() * ROW_HEIGHT + FOOTER_HEIGHT;
    }

    protected final int panelLeft() {
        return (this.width - panelWidth()) / 2;
    }

    protected final int panelTop() {
        return Math.max(0, (this.height - panelHeight()) / 2);
    }

    protected final int panelRight() {
        return panelLeft() + panelWidth();
    }

    protected final int panelBottom() {
        return panelTop() + panelHeight();
    }

    /** How many rows fit on screen, given the chrome above and below them. */
    private int maxVisibleRows() {
        int available = this.height
                - SCREEN_MARGIN
                - CONTENT_TOP
                - FOOTER_HEIGHT;

        return Math.max(1, available / ROW_HEIGHT);
    }

    protected final int visibleRowCount() {
        return Math.min(rowCount(), maxVisibleRows());
    }

    /** Index of the first row currently on screen. */
    protected final int scrollOffset() {
        return scrollOffset;
    }

    private int maxScroll() {
        return Math.max(0, rowCount() - visibleRowCount());
    }

    /**
     * Keeps the scroll position valid after a resize or a change in row
     * count. Subclasses must call this from {@code init()}.
     */
    protected final void clampScroll() {
        scrollOffset = Math.clamp(scrollOffset, 0, maxScroll());
    }

    /** X of a row's right-aligned button of the given width. */
    protected final int rowButtonX(int buttonWidth) {
        return panelRight() - ROW_INSET - buttonWidth;
    }

    /** Y of visible slot {@code slot}, counting from 0. */
    protected final int rowY(int slot) {
        return panelTop() + CONTENT_TOP + slot * ROW_HEIGHT;
    }

    /** Dim background, panel, border, title and scroll indicator. */
    protected void renderPanel(DrawContext context) {
        int left = panelLeft();
        int top = panelTop();
        int right = panelRight();
        int bottom = panelBottom();

        context.fill(0, 0, this.width, this.height, BACKGROUND_COLOR);

        context.fill(left, top, right, bottom, PANEL_COLOR);

        context.fill(left, top, right, top + BORDER_SIZE, BORDER_COLOR);
        context.fill(left, bottom - BORDER_SIZE, right, bottom, BORDER_COLOR);
        context.fill(left, top, left + BORDER_SIZE, bottom, BORDER_COLOR);
        context.fill(right - BORDER_SIZE, top, right, bottom, BORDER_COLOR);

        context.drawCenteredTextWithShadow(
                this.textRenderer,
                this.title,
                this.width / 2,
                top + 20,
                TEXT_COLOR
        );

        if (maxScroll() > 0) {
            context.drawCenteredTextWithShadow(
                    this.textRenderer,
                    Text.literal(String.format(
                            "scroll - %d-%d of %d",
                            scrollOffset + 1,
                            scrollOffset + visibleRowCount(),
                            rowCount()
                    )),
                    this.width / 2,
                    bottom - FOOTER_HEIGHT + 4,
                    MUTED_COLOR
            );
        }
    }

    @Override
    public boolean mouseScrolled(
            double mouseX,
            double mouseY,
            double horizontalAmount,
            double verticalAmount
    ) {
        if (maxScroll() > 0 && verticalAmount != 0) {

            scrollOffset = Math.clamp(
                    scrollOffset - (int) Math.signum(verticalAmount),
                    0,
                    maxScroll()
            );

            // Rebuilds the row widgets at their new positions.
            this.clearAndInit();

            return true;
        }

        return super.mouseScrolled(
                mouseX,
                mouseY,
                horizontalAmount,
                verticalAmount
        );
    }

    /** Label for a row's button, e.g. {@code "Show overlay: ON"}. */
    protected static Text toggleText(String label, boolean value) {
        return Text.literal(label + ": " + (value ? "ON" : "OFF"));
    }

    @Override
    public void close() {
        this.client.setScreen(parent);
    }
}
