package ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import db.DBConnection;

public class IntroUI {

    JFrame frame;
    JPanel centerPanel;
    JPanel tableWrapper;

    public IntroUI() {

        frame = UITheme.createFrame("Mentee Connect", 960, 680);
        frame.setLayout(new BorderLayout());

        // ─── TOP BAR ─────────────────────────────────────────────────────────
        JPanel topBar = UITheme.createTopBar();

        JLabel brand = UITheme.makeBrandLabel();

        JPanel rightNav = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        rightNav.setOpaque(false);

        JButton signIn = UITheme.makeTopBarBtn("Sign In");
        JButton signUp = UITheme.makeTopBarBtn("Sign Up");
        signUp.setBackground(new Color(55, 55, 90));
        signUp.setBorder(BorderFactory.createLineBorder(UITheme.BORDER_COLOR, 1));

        rightNav.add(signIn);
        rightNav.add(signUp);

        topBar.add(brand, BorderLayout.WEST);
        topBar.add(rightNav, BorderLayout.EAST);
        frame.add(topBar, BorderLayout.NORTH);

        // ─── HERO SECTION ────────────────────────────────────────────────────
        JPanel heroPanel = new JPanel();
        heroPanel.setLayout(new BoxLayout(heroPanel, BoxLayout.Y_AXIS));
        heroPanel.setBackground(UITheme.BG_PRIMARY);
        heroPanel.setBorder(new EmptyBorder(30, 0, 20, 0));

        // Logo image
        String logoPath = "C:\\Users\\LENOVO\\Desktop\\Projects\\DBMS MiniProject - MenteeConnect\\Mentee_connect\\logo.png";
        ImageIcon raw = new ImageIcon(logoPath);
        if (raw.getIconWidth() > 0) {
            Image img = raw.getImage().getScaledInstance(880, 220, Image.SCALE_SMOOTH);
            JLabel imageLabel = new JLabel(new ImageIcon(img));
            imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            heroPanel.add(imageLabel);
        }

        heroPanel.add(UITheme.vspace(20));

        // Tagline
        JLabel tagline = new JLabel("Discover. Connect. Grow.", SwingConstants.CENTER);
        tagline.setFont(new Font("Segoe UI", Font.ITALIC, 15));
        tagline.setForeground(UITheme.TEXT_SECONDARY);
        tagline.setAlignmentX(Component.CENTER_ALIGNMENT);
        heroPanel.add(tagline);

        heroPanel.add(UITheme.vspace(20));

        // ─── BROWSE BUTTONS ──────────────────────────────────────────────────
        JPanel btnRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        btnRow.setOpaque(false);

        JButton mentorsBtn = UITheme.makeChoiceBtn("View Connected Mentors");
        JButton menteesBtn = UITheme.makeChoiceBtn("View Connected Mentees");
        mentorsBtn.setPreferredSize(new Dimension(220, 38));
        menteesBtn.setPreferredSize(new Dimension(220, 38));

        btnRow.add(mentorsBtn);
        btnRow.add(menteesBtn);
        heroPanel.add(btnRow);

        heroPanel.add(UITheme.vspace(20));

        // ─── TABLE AREA ──────────────────────────────────────────────────────
        centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(UITheme.BG_PRIMARY);
        centerPanel.setPreferredSize(new Dimension(880, 210));
        centerPanel.setBorder(new EmptyBorder(0, 30, 10, 30));

        heroPanel.add(centerPanel);

        frame.add(heroPanel, BorderLayout.CENTER);

        // ─── ACTIONS ─────────────────────────────────────────────────────────
        signIn.addActionListener(e -> { frame.dispose(); new LoginChoiceUI(); });
        signUp.addActionListener(e -> { frame.dispose(); new SignupChoiceUI(); });
        mentorsBtn.addActionListener(e -> showMentors());
        menteesBtn.addActionListener(e -> showMentees());

        frame.setVisible(true);
    }

    // ─── SHOW MENTORS ────────────────────────────────────────────────────────
    private void showMentors() {
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT name, email, skills, experience FROM mentors");
            ResultSet rs = ps.executeQuery();

            String[] cols = {"Name", "Email", "Skills", "Experience (yrs)"};
            DefaultTableModel model = new DefaultTableModel(cols, 0) {
                public boolean isCellEditable(int r, int c) { return false; }
            };
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("name"), rs.getString("email"),
                    rs.getString("skills"), rs.getInt("experience")
                });
            }
            renderTable(model);
        } catch (Exception e) { e.printStackTrace(); }
    }

    // ─── SHOW MENTEES ────────────────────────────────────────────────────────
    private void showMentees() {
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT name, email, interests, goal FROM mentees");
            ResultSet rs = ps.executeQuery();

            String[] cols = {"Name", "Email", "Interests", "Goal"};
            DefaultTableModel model = new DefaultTableModel(cols, 0) {
                public boolean isCellEditable(int r, int c) { return false; }
            };
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("name"), rs.getString("email"),
                    rs.getString("interests"), rs.getString("goal")
                });
            }
            renderTable(model);
        } catch (Exception e) { e.printStackTrace(); }
    }

    private void renderTable(DefaultTableModel model) {
        JTable table = new JTable(model);
        UITheme.styleTable(table);

        JScrollPane scroll = new JScrollPane(table);
        UITheme.styleScrollPane(scroll);

        centerPanel.removeAll();
        centerPanel.add(scroll, BorderLayout.CENTER);
        centerPanel.revalidate();
        centerPanel.repaint();
    }
}