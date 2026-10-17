package dev.acid.mytheria;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public final class MytheriaMenuScreen extends Screen {
    private static final int PANEL_W = 430;
    private static final int PANEL_H = 282;
    private static final int BG = 0xF20B0F18;
    private static final int PANEL = 0xFF101722;
    private static final int CARD = 0xFF171F2D;
    private static final int CARD_HOVER = 0xFF202C3D;
    private static final int ACCENT = 0xFF63D9FF;
    private static final int TEXT = 0xFFF2F6FF;
    private static final int MUTED = 0xFF8998AD;
    private static final int PURPLE = 0xFF9D83FF;

    private int selectedTab = 0;
    private boolean[] toggles = {true, false, true, false};
    private double openProgress = 0.0;

    public MytheriaMenuScreen() {
        super(Text.literal("Mytheria Visuals"));
    }

    @Override
    public boolean shouldPause() {
        return false;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        openProgress = Math.min(1.0, openProgress + delta * 0.16);
        context.fill(0, 0, width, height, 0x99050A12);

        int panelX = (width - PANEL_W) / 2;
        int panelY = (height - PANEL_H) / 2;
        int lift = (int) ((1.0 - openProgress) * 10);
        panelY += lift;

        rounded(context, panelX + 2, panelY + 5, PANEL_W, PANEL_H, 16, 0x55000000);
        rounded(context, panelX, panelY, PANEL_W, PANEL_H, 16, BG);
        rounded(context, panelX + 1, panelY + 1, PANEL_W - 2, PANEL_H - 2, 15, PANEL);
        rounded(context, panelX + 18, panelY + 17, 3, PANEL_H - 34, 2, ACCENT);

        context.drawTextWithShadow(textRenderer, "MYTHERIA", panelX + 34, panelY + 22, TEXT);
        context.drawText(textRenderer, "VISUALS  /  CLIENT MENU", panelX + 34, panelY + 36, MUTED, false);
        rounded(context, panelX + PANEL_W - 94, panelY + 19, 76, 20, 8, 0xFF1A2D3A);
        context.drawCenteredTextWithShadow(textRenderer, "BETA", panelX + PANEL_W - 56, panelY + 25, ACCENT);
        context.fill(panelX + 24, panelY + 52, panelX + PANEL_W - 20, panelY + 53, 0xFF283342);

        String[] tabs = {"Visuals", "Interface", "Profiles"};
        for (int i = 0; i < tabs.length; i++) {
            int tx = panelX + 26;
            int ty = panelY + 67 + i * 37;
            boolean active = selectedTab == i;
            boolean hovered = mouseX >= tx && mouseX <= tx + 112 && mouseY >= ty && mouseY <= ty + 29;
            if (active) rounded(context, tx, ty, 112, 29, 9, 0xFF203648);
            else if (hovered) rounded(context, tx, ty, 112, 29, 9, CARD_HOVER);
            if (active) rounded(context, tx + 5, ty + 7, 3, 15, 2, ACCENT);
            context.drawText(textRenderer, tabs[i], tx + 15, ty + 10, active ? TEXT : MUTED, false);
        }

        int contentX = panelX + 157;
        int contentY = panelY + 69;
        if (selectedTab == 0) {
            context.drawTextWithShadow(textRenderer, "Visual modules", contentX, contentY, TEXT);
            context.drawText(textRenderer, "Toggle client-side visual options", contentX, contentY + 13, MUTED, false);
            String[] names = {"HUD accents", "Soft glow", "Rounded panels", "Motion effects"};
            String[] descriptions = {"Accent colors for interface elements", "Subtle color emphasis", "Smoother corner rendering", "Animated interface transitions"};
            for (int i = 0; i < names.length; i++) {
                int cy = contentY + 34 + i * 39;
                boolean hovered = mouseX >= contentX - 2 && mouseX <= panelX + PANEL_W - 18 && mouseY >= cy && mouseY <= cy + 32;
                rounded(context, contentX - 2, cy, 245, 32, 8, hovered ? CARD_HOVER : CARD);
                context.drawText(textRenderer, names[i], contentX + 8, cy + 5, TEXT, false);
                context.drawText(textRenderer, descriptions[i], contentX + 8, cy + 17, MUTED, false);
                int sx = panelX + PANEL_W - 53;
                int sy = cy + 9;
                rounded(context, sx, sy, 32, 14, 7, toggles[i] ? 0xFF2D9FC3 : 0xFF394454);
                rounded(context, toggles[i] ? sx + 18 : sx + 3, sy + 3, 8, 8, 4, 0xFFFFFFFF);
            }
        } else if (selectedTab == 1) {
            context.drawTextWithShadow(textRenderer, "Interface", contentX, contentY, TEXT);
            context.drawText(textRenderer, "Appearance preview", contentX, contentY + 15, MUTED, false);
            rounded(context, contentX - 2, contentY + 39, 245, 54, 10, CARD);
            rounded(context, contentX + 9, contentY + 50, 34, 32, 8, ACCENT);
            context.drawTextWithShadow(textRenderer, "Aa", contentX + 17, contentY + 61, 0xFF0B1721);
            context.drawText(textRenderer, "Smooth corners", contentX + 53, contentY + 51, TEXT, false);
            context.drawText(textRenderer, "UI scaling and color accents", contentX + 53, contentY + 65, MUTED, false);
            rounded(context, contentX - 2, contentY + 104, 245, 38, 9, CARD);
            context.drawText(textRenderer, "Theme", contentX + 9, contentY + 117, TEXT, false);
            rounded(context, contentX + 171, contentY + 112, 61, 22, 7, 0xFF243347);
            context.drawCenteredTextWithShadow(textRenderer, "NIGHT", contentX + 201, contentY + 119, ACCENT);
        } else {
            context.drawTextWithShadow(textRenderer, "Profiles", contentX, contentY, TEXT);
            context.drawText(textRenderer, "Choose a visual preset", contentX, contentY + 15, MUTED, false);
            String[] profiles = {"AURORA", "MIDNIGHT", "VIOLET"};
            int[] colors = {ACCENT, 0xFF8AA7FF, PURPLE};
            for (int i = 0; i < profiles.length; i++) {
                int cy = contentY + 39 + i * 43;
                rounded(context, contentX - 2, cy, 245, 35, 9, CARD);
                rounded(context, contentX + 8, cy + 9, 17, 17, 6, colors[i]);
                context.drawText(textRenderer, profiles[i], contentX + 35, cy + 13, TEXT, false);
                context.drawText(textRenderer, i == 0 ? "Active preset" : "Preview preset", panelX + PANEL_W - 103, cy + 13, MUTED, false);
            }
        }

        context.fill(panelX + 24, panelY + PANEL_H - 35, panelX + PANEL_W - 20, panelY + PANEL_H - 34, 0xFF283342);
        context.drawText(textRenderer, "MYTHERIA VISUALS", panelX + 27, panelY + PANEL_H - 23, MUTED, false);
        context.drawText(textRenderer, "RIGHT SHIFT  •  CLOSE", panelX + PANEL_W - 143, panelY + PANEL_H - 23, ACCENT, false);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button != 0) return super.mouseClicked(mouseX, mouseY, button);
        int panelX = (width - PANEL_W) / 2;
        int panelY = (height - PANEL_H) / 2;
        if (mouseX >= panelX + 26 && mouseX <= panelX + 138) {
            for (int i = 0; i < 3; i++) {
                int ty = panelY + 67 + i * 37;
                if (mouseY >= ty && mouseY <= ty + 29) {
                    selectedTab = i;
                    return true;
                }
            }
        }
        if (selectedTab == 0) {
            int contentX = panelX + 157;
            int contentY = panelY + 69;
            for (int i = 0; i < toggles.length; i++) {
                int cy = contentY + 34 + i * 39;
                if (mouseX >= contentX - 2 && mouseX <= panelX + PANEL_W - 18 && mouseY >= cy && mouseY <= cy + 32) {
                    toggles[i] = !toggles[i];
                    return true;
                }
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == 256) {
            close();
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    private static void rounded(DrawContext context, int x, int y, int w, int h, int radius, int color) {
        int r = Math.max(1, Math.min(radius, Math.min(w, h) / 2));
        for (int row = 0; row < h; row++) {
            double dy = row < r ? r - row - 0.5 : row >= h - r ? row - (h - r) + 0.5 : 0;
            int inset = 0;
            if (dy > 0) inset = Math.max(0, r - (int) Math.floor(Math.sqrt(Math.max(0, r * r - dy * dy))));
            context.fill(x + inset, y + row, x + w - inset, y + row + 1, color);
        }
    }
}
