package ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class SignupChoiceUI {

    public SignupChoiceUI() {

        JFrame frame = UITheme.createFrame("Sign Up - Mentee Connect", 440, 380);
        frame.setLayout(new BorderLayout());

        // ─── CENTERING WRAPPER ────────────────────────────────────────────────
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

        JLabel title = UITheme.makeTitle("Create Account");
        JLabel sub = new JLabel("Choose your role to get started", SwingConstants.CENTER);
        sub.setFont(UITheme.FONT_LABEL);
        sub.setForeground(UITheme.TEXT_SECONDARY);
        sub.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton mentorBtn = UITheme.makeChoiceBtn("Sign Up as Mentor");
        JButton menteeBtn = UITheme.makeChoiceBtn("Sign Up as Mentee");
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

        mentorBtn.addActionListener(e -> { frame.dispose(); new MentorSignupUI(); });
        menteeBtn.addActionListener(e -> { frame.dispose(); new MenteeSignupUI(); });

        frame.setVisible(true);
    }
}