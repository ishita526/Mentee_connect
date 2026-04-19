package ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import dao.LoginDAO;
import util.Session;

public class MenteeLoginUI {

    public MenteeLoginUI() {

        JFrame frame = UITheme.createFrame("Mentee Login - Mentee Connect", 460, 500);
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
                new EmptyBorder(40, 50, 40, 50)));

        JLabel title = UITheme.makeTitle("Mentee Login");
        JLabel sub = new JLabel("Sign in to your mentee account", SwingConstants.CENTER);
        sub.setFont(UITheme.FONT_LABEL);
        sub.setForeground(UITheme.TEXT_SECONDARY);
        sub.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextField email = UITheme.makeField();
        JPasswordField pass = UITheme.makePasswordField();
        JButton loginBtn = UITheme.makePrimaryBtn("Login");

        card.add(title);
        card.add(UITheme.vspace(6));
        card.add(sub);
        card.add(UITheme.vspace(30));

        card.add(UITheme.makeLabel("Email"));
        card.add(UITheme.vspace(6));
        card.add(email);
        card.add(UITheme.vspace(16));

        card.add(UITheme.makeLabel("Password"));
        card.add(UITheme.vspace(6));
        card.add(pass);
        card.add(UITheme.vspace(28));

        card.add(loginBtn);

        outer.add(card);
        frame.add(outer, BorderLayout.CENTER);

        // ─── ACTION ───────────────────────────────────────────────────────────
        loginBtn.addActionListener(e -> {
            try {
                LoginDAO dao = new LoginDAO();
                String[] userData = dao.getMenteeLogin(email.getText(), new String(pass.getPassword()));
                if (userData != null) {
                    int userId = Integer.parseInt(userData[0]);
                    String uname = userData[1];
                    Session.userId = userId;
                    Session.role = "mentee";
                    JOptionPane.showMessageDialog(frame, "Login successful!");
                    frame.dispose();
                    new MenteeDashboardUI(userId, uname);
                } else {
                    JOptionPane.showMessageDialog(frame, "Invalid credentials. Please try again.", "Login Failed",
                            JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        frame.setVisible(true);
    }
}