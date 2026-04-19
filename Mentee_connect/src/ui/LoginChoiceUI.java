package ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class LoginChoiceUI {

    public LoginChoiceUI() {

        JFrame frame = UITheme.createFrame("Login - Mentee Connect", 440, 380);
        frame.setLayout(new BorderLayout());

        // ─── OUTER WRAPPER to vertically center card ──────────────────────────
        JPanel outer = new JPanel(new GridBagLayout());
        outer.setBackground(UITheme.BG_PRIMARY);

        // ─── CARD ─────────────────────────────────────────────────────────────
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(UITheme.BG_SECONDARY);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(UITheme.BORDER_COLOR, 1),
            new EmptyBorder(40, 50, 40, 50)
        ));

        JLabel title = UITheme.makeTitle("Welcome Back");
        JLabel sub = new JLabel("Choose your role to continue", SwingConstants.CENTER);
        sub.setFont(UITheme.FONT_LABEL);
        sub.setForeground(UITheme.TEXT_SECONDARY);
        sub.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton mentorBtn = UITheme.makeChoiceBtn("Login as Mentor");
        JButton menteeBtn = UITheme.makeChoiceBtn("Login as Mentee");
        mentorBtn.setMaximumSize(new Dimension(240, 40));
        menteeBtn.setMaximumSize(new Dimension(240, 40));

        card.add(title);
        card.add(UITheme.vspace(8));
        card.add(sub);
        card.add(UITheme.vspace(36));
        card.add(mentorBtn);
        card.add(UITheme.vspace(14));
        card.add(menteeBtn);

        outer.add(card);
        frame.add(outer, BorderLayout.CENTER);

        mentorBtn.addActionListener(e -> { frame.dispose(); new MentorLoginUI(); });
        menteeBtn.addActionListener(e -> { frame.dispose(); new MenteeLoginUI(); });

        frame.setVisible(true);
    }
}