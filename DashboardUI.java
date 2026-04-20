import javax.swing.*;
import java.awt.*;

public class DashboardUI extends JFrame {

    int userId;

    public DashboardUI(int userId) {
        this.userId = userId;

        setTitle("Agriculture Dashboard");
        setSize(400, 300);
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Dashboard", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        add(title, BorderLayout.NORTH);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1, 10, 10));

        JButton addCrop = new JButton("Add Crop");
        JButton manageCrop = new JButton("Manage Crops");
        JButton progress = new JButton("View Progress");

        panel.add(addCrop);
        panel.add(manageCrop);
        panel.add(progress);

        add(panel, BorderLayout.CENTER);

        addCrop.addActionListener(e -> new AddCropUI(userId));
        manageCrop.addActionListener(e -> new CropManagerUI(userId));
        progress.addActionListener(e -> new ProgressUI());

        setVisible(true);
    }
}


