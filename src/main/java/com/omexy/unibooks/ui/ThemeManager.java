package com.omexy.unibooks.ui;

import javax.swing.*;
import java.awt.*;

public class ThemeManager {

    public enum Theme {
        LIGHT, DARK
    }

    private static Theme currentTheme = Theme.LIGHT;

    public static void applyTheme(Theme theme) {
        currentTheme = theme;
        switch (theme) {
            case LIGHT:
                UIManager.put("control", new Color(240, 240, 240));
                UIManager.put("info", new Color(240, 240, 240));
                UIManager.put("nimbusBase", new Color(50, 50, 50));
                UIManager.put("nimbusAlertYellow", new Color(255, 220, 35));
                UIManager.put("nimbusDisabledText", new Color(142, 143, 145));
                UIManager.put("nimbusFocus", new Color(115, 164, 209));
                UIManager.put("nimbusGreen", new Color(176, 179, 50));
                UIManager.put("nimbusInfoBlue", new Color(66, 139, 221));
                UIManager.put("nimbusLightBackground", new Color(240, 240, 240));
                UIManager.put("nimbusOrange", new Color(191, 98, 4));
                UIManager.put("nimbusRed", new Color(169, 46, 34));
                UIManager.put("nimbusSelectedText", new Color(255, 255, 255));
                UIManager.put("nimbusSelectionBackground", new Color(57, 105, 138));
                UIManager.put("text", new Color(0, 0, 0));
                break;
            case DARK:
                UIManager.put("control", new Color(60, 63, 65));
                UIManager.put("info", new Color(60, 63, 65));
                UIManager.put("nimbusBase", new Color(18, 30, 49));
                UIManager.put("nimbusAlertYellow", new Color(248, 187, 0));
                UIManager.put("nimbusDisabledText", new Color(128, 128, 128));
                UIManager.put("nimbusFocus", new Color(115, 164, 209));
                UIManager.put("nimbusGreen", new Color(176, 179, 50));
                UIManager.put("nimbusInfoBlue", new Color(66, 139, 221));
                UIManager.put("nimbusLightBackground", new Color(43, 43, 43));
                UIManager.put("nimbusOrange", new Color(191, 98, 4));
                UIManager.put("nimbusRed", new Color(169, 46, 34));
                UIManager.put("nimbusSelectedText", new Color(255, 255, 255));
                UIManager.put("nimbusSelectionBackground", new Color(104, 93, 156));
                UIManager.put("text", new Color(230, 230, 230));
                break;
        }
        for (Frame frame : Frame.getFrames()) {
            SwingUtilities.updateComponentTreeUI(frame);
        }
    }

    public static Theme getCurrentTheme() {
        return currentTheme;
    }
} 