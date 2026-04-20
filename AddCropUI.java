
import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class AddCropUI extends JFrame {

    int userId;
    JTextField name, season, qty, status;

    public AddCropUI(int userId) {
        this.userId = userId;

        setTitle("Add Crop");
        setSize(350, 300);
        setLayout(new GridLayout(5,2,10,10));

        add(new JLabel("Crop Name"));
        name = new JTextField();
        add(name);

        add(new JLabel("Season"));
        season = new JTextField();
        add(season);

        add(new JLabel("Quantity"));
        qty = new JTextField();
        add(qty);

        add(new JLabel("Status"));
        status = new JTextField();
        add(status);

        JButton addBtn = new JButton("Add Crop");
        add(new JLabel());
        add(addBtn);

        addBtn.addActionListener(e -> addCrop());

        setVisible(true);
    }

    void addCrop() {
        try {
            Connection con = DBConnection.connect();

            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO crops(user_id,crop_name,season,quantity,status) VALUES(?,?,?,?,?)");

            ps.setInt(1, userId);
            ps.setString(2, name.getText());
            ps.setString(3, season.getText());
            ps.setInt(4, Integer.parseInt(qty.getText()));
            ps.setString(5, status.getText());

            ps.executeUpdate();

            JOptionPane.showMessageDialog(this, "Crop Added!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}








