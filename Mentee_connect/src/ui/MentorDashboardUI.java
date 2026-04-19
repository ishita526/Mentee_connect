package ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.*;
import db.DBConnection;

public class MentorDashboardUI {

    JFrame frame;
    int    mentorId;
    String username;
    JPanel contentPanel;

    public MentorDashboardUI(int mentorId, String username) {

        this.mentorId = mentorId;
        this.username = username;

        frame = UITheme.createFrame("Mentor Connect – Dashboard", 700, 560);
        frame.setLayout(new BorderLayout());

        // ─── TOP BAR ─────────────────────────────────────────────────────────
        JPanel topBar = UITheme.createTopBar();
        JLabel brand  = UITheme.makeBrandLabel();

        // Welcome info
        JLabel welcome = new JLabel("Welcome, " + username);
        welcome.setFont(UITheme.FONT_LABEL);
        welcome.setForeground(UITheme.TEXT_SECONDARY);

        JLabel roleTag = new JLabel("  ·  Mentor Dashboard");
        roleTag.setFont(UITheme.FONT_LABEL);
        roleTag.setForeground(UITheme.ACCENT);

        JPanel leftInfo = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        leftInfo.setOpaque(false);
        leftInfo.add(brand);
        leftInfo.add(Box.createHorizontalStrut(24));
        leftInfo.add(welcome);
        leftInfo.add(roleTag);

        JButton refreshBtn = UITheme.makeTopBarBtn("Refresh");
        
        JButton profileIcon = ProfileIconUtil.createProfileIcon(username, () -> {
            new MentorProfileModal(frame, mentorId, () -> {
                String[] updated = new dao.MentorDAO().getMentorDetails(mentorId);
                if (updated != null) {
                    frame.dispose();
                    new MentorDashboardUI(mentorId, updated[0]);
                }
            });
        });

        JPanel rightPanel  = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        rightPanel.setOpaque(false);
        rightPanel.add(refreshBtn);
        rightPanel.add(Box.createHorizontalStrut(8));
        rightPanel.add(profileIcon);

        topBar.add(leftInfo, BorderLayout.WEST);
        topBar.add(rightPanel, BorderLayout.EAST);
        frame.add(topBar, BorderLayout.NORTH);

        // ─── CONTENT SCROLL ───────────────────────────────────────────────────
        contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(UITheme.BG_PRIMARY);
        contentPanel.setBorder(new EmptyBorder(20, 30, 20, 30));

        JScrollPane scroll = new JScrollPane(contentPanel);
        scroll.setBorder(null);
        scroll.getViewport().setBackground(UITheme.BG_PRIMARY);
        scroll.getVerticalScrollBar().setUnitIncrement(16);

        frame.add(scroll, BorderLayout.CENTER);

        loadRequests();
        refreshBtn.addActionListener(e -> loadRequests());

        frame.setVisible(true);
    }

    // ─── LOAD REQUESTS ────────────────────────────────────────────────────────
    private void loadRequests() {
        contentPanel.removeAll();

        JLabel heading = UITheme.makeHeading("Incoming Mentee Requests");
        heading.setAlignmentX(Component.LEFT_ALIGNMENT);
        contentPanel.add(heading);
        contentPanel.add(UITheme.vspace(20));

        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM requests WHERE mentor_id=?");
            ps.setInt(1, mentorId);
            ResultSet rs = ps.executeQuery();

            boolean hasData = false;
            while (rs.next()) {
                hasData = true;
                int    requestId = rs.getInt("request_id");
                int    menteeId  = rs.getInt("mentee_id");
                String status    = rs.getString("status");

                contentPanel.add(buildRequestRow(requestId, menteeId, status));
                contentPanel.add(UITheme.vspace(12));
            }

            if (!hasData) {
                JLabel empty = new JLabel("No incoming requests at the moment.");
                empty.setFont(new Font("Segoe UI", Font.ITALIC, 13));
                empty.setForeground(UITheme.TEXT_SECONDARY);
                empty.setAlignmentX(Component.LEFT_ALIGNMENT);
                contentPanel.add(empty);
            }

        } catch (Exception e) { e.printStackTrace(); }

        contentPanel.revalidate();
        contentPanel.repaint();
    }

    // ─── REQUEST ROW ──────────────────────────────────────────────────────────
    private JPanel buildRequestRow(int requestId, int menteeId, String status) {

        JPanel row = new JPanel(new BorderLayout(0, 0));
        row.setBackground(UITheme.BG_SECONDARY);
        row.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(UITheme.BORDER_COLOR, 1),
            new EmptyBorder(12, 18, 12, 18)
        ));
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
        row.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Left: info
        JLabel info = new JLabel(
            "<html><b style='color:#111827'>Mentee ID: " + menteeId + "</b>"
            + "&nbsp;&nbsp;<span style='color:#6b7280'>Status: </span>"
            + "<span style='color:" + statusColor(status) + "'>" + status + "</span></html>"
        );
        info.setFont(UITheme.FONT_LABEL);

        // Right: Review button
        JButton review = UITheme.makeTopBarBtn("REVIEW");
        review.addActionListener(e -> {
            new ReviewRequestUI(requestId, menteeId, mentorId);
            loadRequests();
        });

        JPanel rightSide = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        rightSide.setOpaque(false);
        rightSide.add(review);

        row.add(info,      BorderLayout.WEST);
        row.add(rightSide, BorderLayout.EAST);

        return row;
    }

    private String statusColor(String status) {
        if (status == null) return "#a0a0c8";
        String s = status.toLowerCase();
        if (s.equals("accepted")) return "#22c55e";
        if (s.equals("rejected")) return "#ef4444";
        return "#a0a0c8";
    }
}