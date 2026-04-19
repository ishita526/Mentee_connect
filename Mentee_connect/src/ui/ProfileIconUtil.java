package ui;

import javax.swing.*;
import java.awt.*;

public class ProfileIconUtil {

    public static JButton createProfileIcon(String name, Runnable onClick) {
        String initials = getInitials(name);
        
        JButton iconBtn = new JButton(initials) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillOval(0, 0, getWidth(), getHeight());
                super.paintComponent(g2);
                g2.dispose();
            }
            @Override
            protected void paintBorder(Graphics g) {
                // No border
            }
        };
        
        iconBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        iconBtn.setForeground(Color.WHITE);
        iconBtn.setBackground(UITheme.ACCENT);
        iconBtn.setFocusPainted(false);
        iconBtn.setContentAreaFilled(false);
        iconBtn.setOpaque(false);
        iconBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        iconBtn.setPreferredSize(new Dimension(38, 38));
        
        iconBtn.addActionListener(e -> {
            if (onClick != null) {
                onClick.run();
            }
        });
        
        return iconBtn;
    }

    private static String getInitials(String name) {
        if (name == null || name.trim().isEmpty()) {
            return "?";
        }
        String[] parts = name.trim().split("\\s+");
        if (parts.length == 1) {
            return parts[0].substring(0, Math.min(2, parts[0].length())).toUpperCase();
        } else {
            return (parts[0].substring(0, 1) + parts[parts.length - 1].substring(0, 1)).toUpperCase();
        }
    }
}
