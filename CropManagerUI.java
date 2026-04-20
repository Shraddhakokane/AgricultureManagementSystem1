import java.awt.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class CropManagerUI extends JFrame {

    JTable table;
    DefaultTableModel model;
    int userId;

    public CropManagerUI(int userId) {
        this.userId = userId;

        setTitle("Crop Management (CRUD)");
        setSize(700, 400);
        setLayout(new BorderLayout());

        model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{"ID", "Name", "Season", "Qty", "Status"});

        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel buttons = new JPanel();

        JButton add = new JButton("Add");
        JButton refresh = new JButton("Refresh");
        JButton delete = new JButton("Delete");
        JButton update = new JButton("Update");

        buttons.add(add);
        buttons.add(refresh);
        buttons.add(delete);
        buttons.add(update);

        add(buttons, BorderLayout.SOUTH);

        // 🔥 BUTTON ACTIONS
        add.addActionListener(e -> addCrop());      // INSERT
        refresh.addActionListener(e -> loadData()); // READ
        delete.addActionListener(e -> deleteCrop());// DELETE
        update.addActionListener(e -> updateCrop());// UPDATE

        loadData();
        setVisible(true);
    }

    // 🔥 INSERT
    void addCrop() {
        String name = JOptionPane.showInputDialog(this, "Enter Crop Name:");
        String season = JOptionPane.showInputDialog(this, "Enter Season:");
        String qty = JOptionPane.showInputDialog(this, "Enter Quantity:");
        String status = JOptionPane.showInputDialog(this, "Enter Status:");

        try {
            Connection con = DBConnection.connect();

            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO crops(user_id,crop_name,season,quantity,status) VALUES(?,?,?,?,?)");

            ps.setInt(1, userId);
            ps.setString(2, name);
            ps.setString(3, season);
            ps.setInt(4, Integer.parseInt(qty));
            ps.setString(5, status);

            ps.executeUpdate();

            JOptionPane.showMessageDialog(this, "Crop Added Successfully!");
            loadData(); // auto refresh

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 🔥 READ
    void loadData() {
        try {
            model.setRowCount(0);

            Connection con = DBConnection.connect();
            PreparedStatement ps = con.prepareStatement(
                "SELECT * FROM crops WHERE user_id=?");
            ps.setInt(1, userId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("crop_id"),
                    rs.getString("crop_name"),
                    rs.getString("season"),
                    rs.getInt("quantity"),
                    rs.getString("status")
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 🔥 DELETE
    void deleteCrop() {
        int row = table.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a row first!");
            return;
        }

        int id = (int) model.getValueAt(row, 0);

        try {
            Connection con = DBConnection.connect();
            PreparedStatement ps = con.prepareStatement(
                "DELETE FROM crops WHERE crop_id=?");
            ps.setInt(1, id);

            ps.executeUpdate();

            JOptionPane.showMessageDialog(this, "Deleted!");
            loadData();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 🔥 UPDATE
    void updateCrop() {
        int row = table.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a row first!");
            return;
        }

        int id = (int) model.getValueAt(row, 0);

        String name = JOptionPane.showInputDialog("New Name:");
        String season = JOptionPane.showInputDialog("New Season:");
        String qty = JOptionPane.showInputDialog("New Quantity:");
        String status = JOptionPane.showInputDialog("New Status:");

        try {
            Connection con = DBConnection.connect();

            PreparedStatement ps = con.prepareStatement(
                "UPDATE crops SET crop_name=?, season=?, quantity=?, status=? WHERE crop_id=?");

            ps.setString(1, name);
            ps.setString(2, season);
            ps.setInt(3, Integer.parseInt(qty));
            ps.setString(4, status);
            ps.setInt(5, id);

            ps.executeUpdate();

            JOptionPane.showMessageDialog(this, "Updated!");
            loadData();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}