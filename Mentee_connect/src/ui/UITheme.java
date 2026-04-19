package ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Centralized theme constants and factory methods for MenteeConnect UI.
 * All UI classes should use this for consistent professional styling.
 */
public class UITheme {

    // ─── Color Palette ───────────────────────────────────────────────────────
    public static final Color BG_PRIMARY = new Color(248, 249, 252); // Off-white page
    public static final Color BG_SECONDARY = Color.WHITE; // Card white
    public static final Color BG_TOPBAR = Color.WHITE; // Top bar white
    public static final Color ACCENT = new Color(79, 70, 229); // Indigo accent
    public static final Color ACCENT_HOVER = new Color(55, 48, 200); // Darker indigo
    public static final Color ACCENT_SUCCESS = new Color(22, 163, 74); // Green
    public static final Color ACCENT_DANGER = new Color(220, 38, 38); // Red
    public static final Color TEXT_PRIMARY = new Color(17, 24, 39); // Near-black
    public static final Color TEXT_SECONDARY = new Color(107, 114, 128); // Gray-500
    public static final Color BORDER_COLOR = new Color(209, 213, 219); // Gray-300

    // ─── Fonts ───────────────────────────────────────────────────────────────
    public static final Font FONT_TITLE = new Font("Segoe UI", Font.BOLD, 22);
    public static final Font FONT_HEADING = new Font("Segoe UI", Font.BOLD, 16);
    public static final Font FONT_LABEL = new Font("Segoe UI", Font.PLAIN, 13);
    public static final Font FONT_INPUT = new Font("Segoe UI", Font.PLAIN, 13);
    public static final Font FONT_BTN = new Font("Segoe UI", Font.BOLD, 13);
    public static final Font FONT_SMALL = new Font("Segoe UI", Font.PLAIN, 11);

    // ─── Dimensions ──────────────────────────────────────────────────────────
    public static final Dimension FIELD_SIZE = new Dimension(300, 36);
    public static final Dimension BTN_WIDE = new Dimension(300, 40);
    public static final Dimension BTN_MEDIUM = new Dimension(200, 36);
    public static final Dimension BTN_SMALL = new Dimension(120, 32);

    // ─── Panel Builders ──────────────────────────────────────────────────────

    /** Creates a themed JFrame centered on screen */
    public static JFrame createFrame(String title, int w, int h) {
        JFrame frame = new JFrame(title);
        frame.setSize(w, h);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(BG_PRIMARY);
        return frame;
    }

    /** Creates a themed top navigation bar */
    public static JPanel createTopBar() {
        JPanel bar = new JPanel(new BorderLayout());
        bar.setBackground(BG_TOPBAR);
        bar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, BORDER_COLOR),
                new EmptyBorder(10, 20, 10, 20)));
        bar.setPreferredSize(new Dimension(0, 55));
        return bar;
    }

    /** Creates a centered form panel with padding */
    public static JPanel createFormPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(BG_PRIMARY);
        panel.setBorder(new EmptyBorder(30, 60, 30, 60));
        return panel;
    }

    /** Creates a card panel with rounded border */
    public static JPanel createCard(int w, int h) {
        JPanel card = new JPanel();
        card.setLayout(new BorderLayout());
        card.setPreferredSize(new Dimension(w, h));
        card.setBackground(BG_SECONDARY);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COLOR, 1, true),
                new EmptyBorder(14, 14, 14, 14)));
        return card;
    }

    // ─── Component Builders ──────────────────────────────────────────────────

    /** Styled title label */
    public static JLabel makeTitle(String text) {
        JLabel lbl = new JLabel(text, SwingConstants.CENTER);
        lbl.setFont(FONT_TITLE);
        lbl.setForeground(TEXT_PRIMARY);
        lbl.setAlignmentX(Component.CENTER_ALIGNMENT);
        return lbl;
    }

    /** Styled heading label */
    public static JLabel makeHeading(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(FONT_HEADING);
        lbl.setForeground(TEXT_PRIMARY);
        lbl.setAlignmentX(Component.CENTER_ALIGNMENT);
        return lbl;
    }

    /** Styled field label */
    public static JLabel makeLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(FONT_LABEL);
        lbl.setForeground(TEXT_SECONDARY);
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);
        return lbl;
    }

    /** Styled text field */
    public static JTextField makeField() {
        JTextField field = new JTextField();
        styleField(field);
        return field;
    }

    /** Styled password field */
    public static JPasswordField makePasswordField() {
        JPasswordField field = new JPasswordField();
        styleField(field);
        return field;
    }

    private static void styleField(JTextField field) {
        field.setMaximumSize(FIELD_SIZE);
        field.setPreferredSize(new Dimension(300, 36));
        field.setFont(FONT_INPUT);
        field.setBackground(Color.WHITE);
        field.setForeground(TEXT_PRIMARY);
        field.setCaretColor(TEXT_PRIMARY);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COLOR, 1),
                new EmptyBorder(4, 10, 4, 10)));
        field.setAlignmentX(Component.LEFT_ALIGNMENT);
    }

    /** Primary accent button */
    public static JButton makePrimaryBtn(String text) {
        JButton btn = new JButton(text);
        btn.setFont(FONT_BTN);
        btn.setBackground(ACCENT);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setMaximumSize(BTN_WIDE);
        btn.setPreferredSize(new Dimension(300, 40));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) {
                btn.setBackground(ACCENT_HOVER);
            }

            public void mouseExited(java.awt.event.MouseEvent e) {
                btn.setBackground(ACCENT);
            }
        });
        return btn;
    }

    /** Outlined choice button (medium width) */
    public static JButton makeChoiceBtn(String text) {
        JButton btn = new JButton(text);
        btn.setFont(FONT_BTN);
        btn.setBackground(Color.WHITE);
        btn.setForeground(TEXT_PRIMARY);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createLineBorder(BORDER_COLOR, 1));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setMaximumSize(BTN_MEDIUM);
        btn.setPreferredSize(new Dimension(200, 36));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) {
                btn.setBackground(ACCENT);
                btn.setForeground(Color.WHITE);
                btn.setBorder(BorderFactory.createLineBorder(ACCENT, 1));
            }

            public void mouseExited(java.awt.event.MouseEvent e) {
                btn.setBackground(Color.WHITE);
                btn.setForeground(TEXT_PRIMARY);
                btn.setBorder(BorderFactory.createLineBorder(BORDER_COLOR, 1));
            }
        });
        return btn;
    }

    /** Danger/Reject button */
    public static JButton makeDangerBtn(String text) {
        JButton btn = makePrimaryBtn(text);
        btn.setBackground(ACCENT_DANGER);
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) {
                btn.setBackground(new Color(200, 40, 40));
            }

            public void mouseExited(java.awt.event.MouseEvent e) {
                btn.setBackground(ACCENT_DANGER);
            }
        });
        return btn;
    }

    /** Success/Accept button */
    public static JButton makeSuccessBtn(String text) {
        JButton btn = makePrimaryBtn(text);
        btn.setBackground(ACCENT_SUCCESS);
        btn.setForeground(Color.WHITE);
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) {
                btn.setBackground(new Color(15, 130, 55));
            }

            public void mouseExited(java.awt.event.MouseEvent e) {
                btn.setBackground(ACCENT_SUCCESS);
            }
        });
        return btn;
    }

    /** Small top-bar action button */
    public static JButton makeTopBarBtn(String text) {
        JButton btn = new JButton(text);
        btn.setFont(FONT_BTN);
        btn.setBackground(ACCENT);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(BTN_SMALL);
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) {
                btn.setBackground(ACCENT_HOVER);
            }

            public void mouseExited(java.awt.event.MouseEvent e) {
                btn.setBackground(ACCENT);
            }
        });
        return btn;
    }

    /** Styled search field for top bars */
    public static JTextField makeSearchField(int cols) {
        JTextField field = new JTextField(cols);
        field.setFont(FONT_INPUT);
        field.setBackground(Color.WHITE);
        field.setForeground(TEXT_PRIMARY);
        field.setCaretColor(TEXT_PRIMARY);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COLOR, 1),
                new EmptyBorder(4, 10, 4, 10)));
        field.setPreferredSize(new Dimension(220, 32));
        return field;
    }

    /** Branded app logo label for top bars */
    public static JLabel makeBrandLabel() {
        JLabel lbl = new JLabel("MENTEE CONNECT");
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lbl.setForeground(ACCENT);
        return lbl;
    }

    /** Sets FlatLaf-like look on a JTable */
    public static void styleTable(JTable table) {
        table.setFont(FONT_LABEL);
        table.setForeground(TEXT_PRIMARY);
        table.setBackground(Color.WHITE);
        table.setGridColor(BORDER_COLOR);
        table.setRowHeight(28);
        table.setShowVerticalLines(false);
        table.getTableHeader().setFont(FONT_BTN);
        table.getTableHeader().setBackground(new Color(243, 244, 246));
        table.getTableHeader().setForeground(TEXT_SECONDARY);
        table.setSelectionBackground(new Color(224, 231, 255));
        table.setSelectionForeground(TEXT_PRIMARY);
    }

    /** Styled scroll pane */
    public static JScrollPane styleScrollPane(JScrollPane sp) {
        sp.getViewport().setBackground(Color.WHITE);
        sp.setBorder(BorderFactory.createLineBorder(BORDER_COLOR, 1));
        sp.getVerticalScrollBar().setUnitIncrement(16);
        return sp;
    }

    /** Vertical spacer */
    public static Component vspace(int h) {
        return Box.createVerticalStrut(h);
    }

    /** Apply light background to frame content pane */
    public static void applyLightBg(JFrame frame) {
        ((JPanel) frame.getContentPane()).setBackground(BG_PRIMARY);
    }
}
