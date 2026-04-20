import java.awt.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ProgressUI extends JFrame {

    JTable table;
    DefaultTableModel model;

    public ProgressUI() {

        setTitle("Crop Progress");
        setSize(600, 400);
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Crop Progress Report", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 18));
        add(title, BorderLayout.NORTH);

        model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{
            "Crop ID", "Crop Name", "Stage", "Notes", "Date"
        });

        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        loadData();

        setVisible(true);
    }

    void loadData() {
        try {
            model.setRowCount(0);

            Connection con = DBConnection.connect();

            // 🔥 JOIN query (IMPORTANT)
            PreparedStatement ps = con.prepareStatement(
                "SELECT p.crop_id, c.crop_name, p.growth_stage, p.notes, p.date " +
                "FROM progress p JOIN crops c ON p.crop_id = c.crop_id");

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("crop_id"),
                    rs.getString("crop_name"),
                    rs.getString("growth_stage"),
                    rs.getString("notes"),
                    rs.getDate("date")
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}