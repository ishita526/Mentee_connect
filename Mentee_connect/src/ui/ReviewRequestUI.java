package ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.*;
import db.DBConnection;
import dao.RequestDAO;

public class ReviewRequestUI {

    JFrame frame;
    int requestId, menteeId, mentorId;

    public ReviewRequestUI(int requestId, int menteeId, int mentorId) {

        this.requestId = requestId;
        this.menteeId  = menteeId;
        this.mentorId  = mentorId;

        frame = UITheme.createFrame("Review Request – Mentee Connect", 480, 460);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // ─── TOP BAR ─────────────────────────────────────────────────────────
        JPanel topBar = UITheme.createTopBar();
        JLabel brand  = UITheme.makeBrandLabel();
        JLabel sub    = new JLabel("  ·  Review Request");
        sub.setFont(UITheme.FONT_LABEL);
        sub.setForeground(UITheme.TEXT_SECONDARY);
        JPanel left = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        left.setOpaque(false);
        left.add(brand);
        left.add(sub);
        topBar.add(left, BorderLayout.WEST);
        frame.add(topBar, BorderLayout.NORTH);

        // ─── CENTERING WRAPPER ────────────────────────────────────────────────
        JPanel outer = new JPanel(new GridBagLayout());
        outer.setBackground(UITheme.BG_PRIMARY);
        frame.add(outer, BorderLayout.CENTER);

        // ─── CARD ─────────────────────────────────────────────────────────────
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(UITheme.BG_SECONDARY);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(UITheme.BORDER_COLOR, 1),
            new EmptyBorder(30, 40, 30, 40)
        ));

        loadMenteeDetails(card);

        outer.add(card);
        frame.setVisible(true);
    }

    private void loadMenteeDetails(JPanel card) {
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM mentees WHERE mentee_id=?");
            ps.setInt(1, menteeId);
            ResultSet rs = ps.executeQuery();

            JLabel title = UITheme.makeTitle("Mentee Profile");
            title.setAlignmentX(Component.CENTER_ALIGNMENT);
            card.add(title);
            card.add(UITheme.vspace(20));

            if (rs.next()) {
                String name      = rs.getString("name");
                String email     = rs.getString("email");
                String interests = rs.getString("interests");
                String goal      = rs.getString("goal");
                if (interests == null) interests = "Not specified";
                if (goal      == null) goal      = "Not specified";

                // Profile info rows
                addInfoRow(card, "Name",      name);
                addInfoRow(card, "Email",     email);
                addInfoRow(card, "Interests", interests);
                addInfoRow(card, "Goal",      goal);

                card.add(UITheme.vspace(28));

                // ─── ACCEPT / REJECT buttons ─────────────────────────────────
                JPanel btnRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
                btnRow.setOpaque(false);

                JButton accept = UITheme.makeSuccessBtn("ACCEPT");
                JButton reject = UITheme.makeDangerBtn("REJECT");
                accept.setMaximumSize(new Dimension(140, 38));
                reject.setMaximumSize(new Dimension(140, 38));
                accept.setPreferredSize(new Dimension(140, 38));
                reject.setPreferredSize(new Dimension(140, 38));

                RequestDAO dao = new RequestDAO();

                accept.addActionListener(e -> {
                    dao.updateRequestStatus(requestId, "Accepted");
                    JOptionPane.showMessageDialog(frame, "Request Accepted!");
                    frame.dispose();
                });
                reject.addActionListener(e -> {
                    dao.deleteRequest(requestId);
                    JOptionPane.showMessageDialog(frame, "Request Rejected & Removed.");
                    frame.dispose();
                });

                btnRow.add(accept);
                btnRow.add(reject);

                JPanel btnWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
                btnWrapper.setOpaque(false);
                btnWrapper.add(btnRow);
                card.add(btnWrapper);

            } else {
                JLabel error = new JLabel("Mentee profile not found.");
                error.setFont(UITheme.FONT_LABEL);
                error.setForeground(UITheme.TEXT_SECONDARY);
                error.setAlignmentX(Component.CENTER_ALIGNMENT);
                card.add(error);
            }

        } catch (Exception e) {
            e.printStackTrace();
            JLabel error = new JLabel("Error loading profile.");
            error.setFont(UITheme.FONT_LABEL);
            error.setForeground(UITheme.ACCENT_DANGER);
            error.setAlignmentX(Component.CENTER_ALIGNMENT);
            card.add(error);
        }
    }

    /** Renders a label-value row in the profile card */
    private void addInfoRow(JPanel card, String label, String value) {
        JPanel row = new JPanel(new BorderLayout(10, 0));
        row.setOpaque(false);
        row.setMaximumSize(new Dimension(340, 28));
        row.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lbl = new JLabel(label + ":");
        lbl.setFont(UITheme.FONT_LABEL);
        lbl.setForeground(UITheme.TEXT_SECONDARY);
        lbl.setPreferredSize(new Dimension(80, 24));

        JLabel val = new JLabel(value);
        val.setFont(UITheme.FONT_LABEL);
        val.setForeground(UITheme.TEXT_PRIMARY);

        row.add(lbl, BorderLayout.WEST);
        row.add(val, BorderLayout.CENTER);

        card.add(row);
        card.add(UITheme.vspace(10));
    }
}