package ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import service.SignupService;

public class MenteeSignupUI {

    public MenteeSignupUI() {

        JFrame frame = UITheme.createFrame("Mentee Sign Up - Mentee Connect", 500, 640);
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
            new EmptyBorder(35, 50, 40, 50)
        ));

        JLabel title = UITheme.makeTitle("Create Mentee Account");
        JLabel sub   = new JLabel("Fill in your details to register", SwingConstants.CENTER);
        sub.setFont(UITheme.FONT_LABEL);
        sub.setForeground(UITheme.TEXT_SECONDARY);
        sub.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextField name      = UITheme.makeField();
        JTextField email     = UITheme.makeField();
        JTextField password  = UITheme.makeField();
        JTextField interests = UITheme.makeField();
        JTextField goal      = UITheme.makeField();
        JButton signupBtn    = UITheme.makePrimaryBtn("Create Account");

        card.add(title);
        card.add(UITheme.vspace(6));
        card.add(sub);
        card.add(UITheme.vspace(24));

        addField(card, "Full Name", name);
        addField(card, "Email Address", email);
        addField(card, "Password", password);
        addField(card, "Interests (e.g. AI, Web Dev)", interests);
        addField(card, "Learning Goal", goal);

        card.add(UITheme.vspace(24));
        card.add(signupBtn);

        outer.add(card);
        frame.add(outer, BorderLayout.CENTER);

        // ─── ACTION ───────────────────────────────────────────────────────────
        signupBtn.addActionListener(e -> {
            try {
                new SignupService().registerMentee(
                    name.getText(), email.getText(), password.getText(),
                    interests.getText(), goal.getText()
                );
                JOptionPane.showMessageDialog(frame, "Mentee account created successfully!");
                frame.dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        frame.setVisible(true);
    }

    private void addField(JPanel card, String labelText, JTextField field) {
        card.add(UITheme.makeLabel(labelText));
        card.add(UITheme.vspace(5));
        card.add(field);
        card.add(UITheme.vspace(14));
    }
}