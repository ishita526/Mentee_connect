package ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import dao.MentorDAO;
import model.Mentor;

public class MentorUI {

    public static void main(String[] args) {

        JFrame frame = UITheme.createFrame("Mentee Connect – Add Mentor", 500, 580);
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

        JLabel title = UITheme.makeTitle("Add New Mentor");
        JLabel sub   = new JLabel("Enter mentor details below", SwingConstants.CENTER);
        sub.setFont(UITheme.FONT_LABEL);
        sub.setForeground(UITheme.TEXT_SECONDARY);
        sub.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextField nameField   = UITheme.makeField();
        JTextField emailField  = UITheme.makeField();
        JTextField skillsField = UITheme.makeField();
        JTextField expField    = UITheme.makeField();
        JButton addButton      = UITheme.makePrimaryBtn("Add Mentor");

        card.add(title);
        card.add(UITheme.vspace(6));
        card.add(sub);
        card.add(UITheme.vspace(24));

        addFieldRow(card, "Full Name",           nameField);
        addFieldRow(card, "Email Address",       emailField);
        addFieldRow(card, "Skills",              skillsField);
        addFieldRow(card, "Years of Experience", expField);

        card.add(UITheme.vspace(24));
        card.add(addButton);

        outer.add(card);
        frame.add(outer, BorderLayout.CENTER);

        // ─── ACTION ───────────────────────────────────────────────────────────
        addButton.addActionListener(e -> {
            try {
                int exp = Integer.parseInt(expField.getText().trim());
                Mentor mentor = new Mentor(
                    nameField.getText(), emailField.getText(), "1234", skillsField.getText(), exp
                );
                new MentorDAO().addMentor(mentor);
                JOptionPane.showMessageDialog(frame, "Mentor added successfully!");
                frame.dispose();
            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(frame, "Experience must be a valid number.", "Validation Error", JOptionPane.WARNING_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        frame.setVisible(true);
    }

    private static void addFieldRow(JPanel card, String label, JTextField field) {
        card.add(UITheme.makeLabel(label));
        card.add(UITheme.vspace(5));
        card.add(field);
        card.add(UITheme.vspace(14));
    }
}