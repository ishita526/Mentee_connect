package ui;

import dao.MenteeDAO;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class MenteeProfileModal extends JDialog {

    public MenteeProfileModal(JFrame parent, int menteeId, Runnable onProfileUpdated) {
        super(parent, "Update Profile", true);
        
        setLayout(new BorderLayout());
        getContentPane().setBackground(UITheme.BG_PRIMARY);
        
        MenteeDAO dao = new MenteeDAO();
        String[] details = dao.getMenteeDetails(menteeId);
        
        if (details == null) {
            JOptionPane.showMessageDialog(parent, "Could not load profile details.");
            dispose();
            return;
        }
        
        String currentName = details[0];
        String currentEmail = details[1];
        String currentInterests = details[2];
        String currentGoal = details[3];
        
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
        
        JTextField interestsField = UITheme.makeField();
        interestsField.setText(currentInterests);
        panel.add(UITheme.makeLabel("Interests (comma separated)"));
        panel.add(interestsField);
        panel.add(UITheme.vspace(15));
        
        JTextField goalField = UITheme.makeField();
        goalField.setText(currentGoal);
        panel.add(UITheme.makeLabel("Primary Goal"));
        panel.add(goalField);
        panel.add(UITheme.vspace(25));
        
        JButton updateBtn = UITheme.makePrimaryBtn("UPDATE");
        updateBtn.addActionListener(e -> {
            boolean success = dao.updateMentee(menteeId, nameField.getText(), emailField.getText(), interestsField.getText(), goalField.getText());
            if (success) {
                JOptionPane.showMessageDialog(this, "Profile updated successfully!");
                dispose();
                if (onProfileUpdated != null) {
                    onProfileUpdated.run();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Failed to update profile.", "Error", JOptionPane.ERROR_MESSAGE);
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
