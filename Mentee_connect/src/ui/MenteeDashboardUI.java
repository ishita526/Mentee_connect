package ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.*;
import db.DBConnection;
import dao.RequestDAO;

public class MenteeDashboardUI {

    JFrame  frame;
    JPanel  listPanel;
    JScrollPane scrollPane;
    int    menteeId;
    String username;

    public MenteeDashboardUI(int menteeId, String username) {

        this.menteeId = menteeId;
        this.username = username;

        frame = UITheme.createFrame("Mentee Connect – Dashboard", 1060, 700);
        frame.setLayout(new BorderLayout());

        // ─── TOP BAR ─────────────────────────────────────────────────────────
        JPanel topBar = UITheme.createTopBar();

        JLabel brand = UITheme.makeBrandLabel();

        // Welcome info
        JLabel welcome = new JLabel("Welcome, " + username);
        welcome.setFont(UITheme.FONT_LABEL);
        welcome.setForeground(UITheme.TEXT_SECONDARY);

        JLabel roleTag = new JLabel("  ·  Mentee");
        roleTag.setFont(UITheme.FONT_LABEL);
        roleTag.setForeground(UITheme.ACCENT);

        JPanel leftInfo = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        leftInfo.setOpaque(false);
        leftInfo.add(brand);
        leftInfo.add(Box.createHorizontalStrut(24));
        leftInfo.add(welcome);
        leftInfo.add(roleTag);

        // Search bar
        JTextField searchField = UITheme.makeSearchField(18);
        searchField.setToolTipText("Search mentors by name or skill...");

        JButton searchBtn = UITheme.makeTopBarBtn("Search");

        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        rightPanel.setOpaque(false);

        JButton logout = new JButton("Logout");
        logout.setFont(UITheme.FONT_BTN);
        logout.setBackground(Color.WHITE);
        logout.setForeground(UITheme.TEXT_PRIMARY);
        logout.setFocusPainted(false);
        logout.setBorder(BorderFactory.createLineBorder(UITheme.BORDER_COLOR, 1));
        logout.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        logout.setPreferredSize(new Dimension(90, 32));

        JButton profileIcon = ProfileIconUtil.createProfileIcon(username, () -> {
            new MenteeProfileModal(frame, menteeId, () -> {
                String[] updated = new dao.MenteeDAO().getMenteeDetails(menteeId);
                if (updated != null) {
                    frame.dispose();
                    new MenteeDashboardUI(menteeId, updated[0]);
                }
            });
        });

        rightPanel.add(searchField);
        rightPanel.add(searchBtn);
        rightPanel.add(Box.createHorizontalStrut(8));
        rightPanel.add(profileIcon);
        rightPanel.add(Box.createHorizontalStrut(8));
        rightPanel.add(logout);

        topBar.add(leftInfo, BorderLayout.WEST);
        topBar.add(rightPanel, BorderLayout.EAST);
        frame.add(topBar, BorderLayout.NORTH);

        // ─── SECTION LABEL ────────────────────────────────────────────────────
        JPanel subBar = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
        subBar.setBackground(UITheme.BG_PRIMARY);
        JLabel sectionLbl = new JLabel("Available Mentors");
        sectionLbl.setFont(UITheme.FONT_HEADING);
        sectionLbl.setForeground(UITheme.TEXT_PRIMARY);
        subBar.add(sectionLbl);

        // ─── MENTOR CARD GRID ────────────────────────────────────────────────
        listPanel = new JPanel();
        listPanel.setLayout(new WrapLayout(FlowLayout.LEFT, 20, 20));
        listPanel.setBackground(UITheme.BG_PRIMARY);
        listPanel.setBorder(new EmptyBorder(0, 20, 20, 20));

        scrollPane = new JScrollPane(listPanel);
        scrollPane.setBorder(null);
        scrollPane.getViewport().setBackground(UITheme.BG_PRIMARY);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        JPanel centerWrapper = new JPanel(new BorderLayout());
        centerWrapper.setBackground(UITheme.BG_PRIMARY);
        centerWrapper.add(subBar, BorderLayout.NORTH);
        centerWrapper.add(scrollPane, BorderLayout.CENTER);

        frame.add(centerWrapper, BorderLayout.CENTER);

        loadMentors("");
        searchBtn.addActionListener(e -> loadMentors(searchField.getText()));
        searchField.addActionListener(e -> loadMentors(searchField.getText())); // Enter key
        logout.addActionListener(e -> { frame.dispose(); new IntroUI(); });

        frame.setVisible(true);
    }

    // ─── LOAD MENTORS ─────────────────────────────────────────────────────────
    private void loadMentors(String keyword) {
        listPanel.removeAll();

        try {
            Connection con = DBConnection.getConnection();
            String sql;
            if (keyword == null || keyword.trim().isEmpty()) {
                sql = "SELECT * FROM mentors";
            } else {
                sql = "SELECT * FROM mentors WHERE name LIKE ? OR skills LIKE ?";
            }
            PreparedStatement ps = con.prepareStatement(sql);
            if (keyword != null && !keyword.trim().isEmpty()) {
                ps.setString(1, "%" + keyword + "%");
                ps.setString(2, "%" + keyword + "%");
            }
            ResultSet rs = ps.executeQuery();

            boolean found = false;
            while (rs.next()) {
                found = true;
                int    mentorId = rs.getInt("mentor_id");
                String name     = rs.getString("name");
                String email    = rs.getString("email");
                String skills   = rs.getString("skills");
                int    exp      = rs.getInt("experience");

                listPanel.add(buildMentorCard(mentorId, name, email, skills, exp));
            }

            if (!found) {
                JLabel empty = new JLabel("No mentors found.");
                empty.setFont(UITheme.FONT_LABEL);
                empty.setForeground(UITheme.TEXT_SECONDARY);
                listPanel.add(empty);
            }

        } catch (Exception e) { e.printStackTrace(); }

        listPanel.revalidate();
        listPanel.repaint();
    }

    // ─── MENTOR CARD ─────────────────────────────────────────────────────────
    private JPanel buildMentorCard(int mentorId, String name, String email, String skills, int exp) {

        JPanel card = new JPanel(new BorderLayout(0, 10));
        card.setPreferredSize(new Dimension(240, 170));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(UITheme.BORDER_COLOR, 1),
            new EmptyBorder(14, 14, 14, 14)
        ));

        // ─ Info ─
        JLabel info = new JLabel(
            "<html>"
            + "<b style='font-size:13px;color:#111827'>" + name + "</b><br>"
            + "<span style='color:#6b7280'>Skills: </span>" + skills + "<br>"
            + "<span style='color:#6b7280'>Exp: </span>" + exp + " yr" + (exp != 1 ? "s" : "") + "<br>"
            + "<span style='color:#6b7280'>Email: </span><small>" + email + "</small>"
            + "</html>"
        );
        info.setFont(UITheme.FONT_LABEL);

        // ─ Connect Button ─
        JButton connect = new JButton("CONNECT");
        connect.setFont(UITheme.FONT_BTN);
        connect.setBackground(UITheme.ACCENT);
        connect.setForeground(Color.WHITE);
        connect.setFocusPainted(false);
        connect.setBorderPainted(false);
        connect.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        connect.setPreferredSize(new Dimension(210, 34));
        connect.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) { connect.setBackground(UITheme.ACCENT_HOVER); }
            public void mouseExited (java.awt.event.MouseEvent e) { connect.setBackground(UITheme.ACCENT); }
        });
        connect.addActionListener(e -> {
            new RequestDAO().sendRequest(mentorId, menteeId);
            JOptionPane.showMessageDialog(frame, "Connection request sent to " + name + "!");
        });

        card.add(info,    BorderLayout.CENTER);
        card.add(connect, BorderLayout.SOUTH);

        return card;
    }
}