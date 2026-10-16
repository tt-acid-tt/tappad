package com.acid.clicker;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.Locale;

public class MainActivity extends Activity {
    private static final int BG = Color.rgb(8, 11, 18);
    private static final int PANEL = Color.rgb(17, 23, 35);
    private static final int MUTED = Color.rgb(151, 165, 185);
    private static final int MINT = Color.rgb(97, 246, 210);
    private static final int WHITE = Color.rgb(242, 247, 255);

    private SharedPreferences prefs;
    private long coins;
    private int power;
    private long upgradeCost;
    private TextView coinsText;
    private TextView powerText;
    private TextView upgradeDescription;
    private Button upgradeButton;
    private Button tapButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(BG);
        getWindow().setNavigationBarColor(BG);

        prefs = getSharedPreferences("neon_clicker_save", MODE_PRIVATE);
        coins = prefs.getLong("coins", 0L);
        power = Math.max(1, prefs.getInt("power", 1));
        upgradeCost = Math.max(25L, prefs.getLong("upgrade_cost", 25L));

        ScrollView scroll = new ScrollView(this);
        scroll.setFillViewport(true);
        scroll.setBackgroundColor(BG);

        LinearLayout page = new LinearLayout(this);
        page.setOrientation(LinearLayout.VERTICAL);
        page.setGravity(Gravity.CENTER_HORIZONTAL);
        page.setPadding(dp(22), dp(24), dp(22), dp(28));
        scroll.addView(page);

        TextView brand = label("NEON", 14, MINT, true);
        brand.setLetterSpacing(0.28f);
        page.addView(brand, fullWidth());

        TextView title = label("CLICKER", 34, WHITE, true);
        title.setLetterSpacing(0.06f);
        LinearLayout.LayoutParams titleParams = fullWidth();
        titleParams.topMargin = dp(2);
        page.addView(title, titleParams);

        TextView subtitle = label("Кликай. Копи. Улучшай.", 14, MUTED, false);
        LinearLayout.LayoutParams subParams = fullWidth();
        subParams.topMargin = dp(5);
        page.addView(subtitle, subParams);

        LinearLayout balanceCard = card();
        LinearLayout.LayoutParams cardParams = fullWidth();
        cardParams.topMargin = dp(28);
        page.addView(balanceCard, cardParams);

        TextView balanceCaption = label("ТВОЙ БАЛАНС", 12, MUTED, true);
        balanceCaption.setLetterSpacing(0.12f);
        balanceCard.addView(balanceCaption, fullWidth());

        coinsText = label("", 38, WHITE, true);
        LinearLayout.LayoutParams coinsParams = fullWidth();
        coinsParams.topMargin = dp(8);
        balanceCard.addView(coinsText, coinsParams);

        TextView coinHint = label("✦  монет на счету", 13, MINT, false);
        LinearLayout.LayoutParams hintParams = fullWidth();
        hintParams.topMargin = dp(2);
        balanceCard.addView(coinHint, hintParams);

        tapButton = new Button(this);
        tapButton.setText("T A P  !");
        tapButton.setTextSize(25);
        tapButton.setTextColor(Color.rgb(6, 20, 24));
        tapButton.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        tapButton.setAllCaps(false);
        tapButton.setElevation(dp(8));
        tapButton.setBackground(round(MINT, 100));
        tapButton.setMinHeight(dp(0));
        tapButton.setPadding(dp(20), dp(24), dp(20), dp(24));
        LinearLayout.LayoutParams tapParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, dp(150));
        tapParams.topMargin = dp(24);
        page.addView(tapButton, tapParams);

        TextView tapHint = label("Нажми, чтобы получить +" + power + " монету", 13, MUTED, false);
        tapHint.setTag("tap_hint");
        LinearLayout.LayoutParams tapHintParams = fullWidth();
        tapHintParams.topMargin = dp(12);
        page.addView(tapHint, tapHintParams);

        LinearLayout upgradeCard = card();
        LinearLayout.LayoutParams upgradeCardParams = fullWidth();
        upgradeCardParams.topMargin = dp(26);
        page.addView(upgradeCard, upgradeCardParams);

        TextView upgradeTitle = label("УЛУЧШЕНИЕ", 12, MINT, true);
        upgradeTitle.setLetterSpacing(0.13f);
        upgradeCard.addView(upgradeTitle, fullWidth());

        upgradeDescription = label("", 17, WHITE, true);
        LinearLayout.LayoutParams descParams = fullWidth();
        descParams.topMargin = dp(10);
        upgradeCard.addView(upgradeDescription, descParams);

        TextView upgradeInfo = label("Увеличивает награду за каждое нажатие.", 13, MUTED, false);
        LinearLayout.LayoutParams infoParams = fullWidth();
        infoParams.topMargin = dp(5);
        upgradeCard.addView(upgradeInfo, infoParams);

        upgradeButton = new Button(this);
        upgradeButton.setTextColor(WHITE);
        upgradeButton.setTextSize(15);
        upgradeButton.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        upgradeButton.setAllCaps(false);
        upgradeButton.setPadding(dp(16), dp(12), dp(16), dp(12));
        upgradeButton.setBackground(round(Color.rgb(37, 49, 68), 16));
        LinearLayout.LayoutParams upgradeBtnParams = fullWidth();
        upgradeBtnParams.topMargin = dp(16);
        upgradeCard.addView(upgradeButton, upgradeBtnParams);

        TextView saveNote = label("Прогресс сохраняется на этом устройстве", 12, MUTED, false);
        LinearLayout.LayoutParams saveParams = fullWidth();
        saveParams.topMargin = dp(22);
        page.addView(saveNote, saveParams);

        tapButton.setOnClickListener(v -> {
            coins += power;
            save();
            updateUi();
            v.animate().cancel();
            v.setScaleX(0.94f);
            v.setScaleY(0.94f);
            v.animate().scaleX(1f).scaleY(1f).setDuration(170)
                    .setInterpolator(new OvershootInterpolator()).start();
        });

        upgradeButton.setOnClickListener(v -> {
            if (coins >= upgradeCost) {
                coins -= upgradeCost;
                power++;
                upgradeCost = Math.max(upgradeCost + 1L, (long) Math.ceil(upgradeCost * 1.65d));
                save();
                updateUi();
            }
        });

        setContentView(scroll);
        updateUi();
    }

    private void updateUi() {
        coinsText.setText(NumberFormat.getNumberInstance(Locale.getDefault()).format(coins));
        powerText = null;
        upgradeDescription.setText("Сила нажатия: +" + power);
        upgradeButton.setText("УЛУЧШИТЬ  •  " + NumberFormat.getNumberInstance(Locale.getDefault()).format(upgradeCost) + " монет");
        upgradeButton.setEnabled(coins >= upgradeCost);
        upgradeButton.setAlpha(coins >= upgradeCost ? 1f : 0.55f);
        View hint = findHintView((View) coinsText.getParent().getParent());
        // The tap hint is updated by walking the root view tree below.
        updateTapHint(getWindow().getDecorView(), "Нажми, чтобы получить +" + power + (power == 1 ? " монету" : " монеты"));
    }

    private View findHintView(View root) {
        if (root instanceof TextView && "tap_hint".equals(root.getTag())) return root;
        if (root instanceof LinearLayout) {
            LinearLayout group = (LinearLayout) root;
            for (int i = 0; i < group.getChildCount(); i++) {
                View found = findHintView(group.getChildAt(i));
                if (found != null) return found;
            }
        }
        if (root instanceof ScrollView && ((ScrollView) root).getChildCount() > 0) {
            return findHintView(((ScrollView) root).getChildAt(0));
        }
        return null;
    }

    private void updateTapHint(View root, String text) {
        View found = findHintView(root);
        if (found instanceof TextView) ((TextView) found).setText(text);
    }

    private void save() {
        prefs.edit().putLong("coins", coins).putInt("power", power)
                .putLong("upgrade_cost", upgradeCost).apply();
    }

    private LinearLayout card() {
        LinearLayout box = new LinearLayout(this);
        box.setOrientation(LinearLayout.VERTICAL);
        box.setPadding(dp(20), dp(20), dp(20), dp(20));
        box.setBackground(round(PANEL, 24));
        return box;
    }

    private TextView label(String value, int size, int color, boolean bold) {
        TextView view = new TextView(this);
        view.setText(value);
        view.setTextSize(size);
        view.setTextColor(color);
        view.setGravity(Gravity.CENTER);
        if (bold) view.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        return view;
    }

    private GradientDrawable round(int color, int radius) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setColor(color);
        drawable.setCornerRadius(dp(radius));
        return drawable;
    }

    private LinearLayout.LayoutParams fullWidth() {
        return new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    private int dp(int value) {
        return Math.round(value * getResources().getDisplayMetrics().density);
    }
}
