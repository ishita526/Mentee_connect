package ui;

import dao.MentorDAO;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class MentorProfileModal extends JDialog {

    public MentorProfileModal(JFrame parent, int mentorId, Runnable onProfileUpdated) {
        super(parent, "Update Profile", true);
        
        setLayout(new BorderLayout());
        getContentPane().setBackground(UITheme.BG_PRIMARY);
        
        MentorDAO dao = new MentorDAO();
        String[] details = dao.getMentorDetails(mentorId);
        
        if (details == null) {
            JOptionPane.showMessageDialog(parent, "Could not load profile details.");
            dispose();
            return;
        }
        
        String currentName = details[0];
        String currentEmail = details[1];
        String currentSkills = details[2];
        int currentExp = Integer.parseInt(details[3]);
        
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(UITheme.BG_SECONDARY);
        panel.setBorder(new EmptyBorder(25, 30, 25, 30));
        
        JLabel title = UITheme.makeTitle("Your Profile");
        panel.add(title);
        panel.add(UITheme.vspace(20));
        
        JTextField nameField = UITheme.makeField();
        nameField.setText(currentName);
        panel.add(UITheme.makeLabel("Name"));
        panel.add(nameField);
        panel.add(UITheme.vspace(15));
        
        JTextField emailField = UITheme.makeField();
        emailField.setText(currentEmail);
        panel.add(UITheme.makeLabel("Email"));
        panel.add(emailField);
        panel.add(UITheme.vspace(15));
        
        JTextField skillsField = UITheme.makeField();
        skillsField.setText(currentSkills);
        panel.add(UITheme.makeLabel("Skills (comma separated)"));
        panel.add(skillsField);
        panel.add(UITheme.vspace(15));
        
        JTextField expField = UITheme.makeField();
        expField.setText(String.valueOf(currentExp));
        panel.add(UITheme.makeLabel("Experience (Years)"));
        panel.add(expField);
        panel.add(UITheme.vspace(25));
        
        JButton updateBtn = UITheme.makePrimaryBtn("UPDATE");
        updateBtn.addActionListener(e -> {
            try {
                int exp = Integer.parseInt(expField.getText().trim());
                boolean success = dao.updateMentor(mentorId, nameField.getText(), emailField.getText(), skillsField.getText(), exp);
                if (success) {
                    JOptionPane.showMessageDialog(this, "Profile updated successfully!");
                    dispose();
                    if (onProfileUpdated != null) {
                        onProfileUpdated.run();
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to update profile.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Experience must be a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        panel.add(updateBtn);
        
        add(panel, BorderLayout.CENTER);
        
        pack();
        setLocationRelativeTo(parent);
        setResizable(false);
        setVisible(true);
    }
}
