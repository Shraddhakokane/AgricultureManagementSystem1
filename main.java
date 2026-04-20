import javax.swing.UIManager;

public class main {
    public static void main(String[] args) {
        new LoginRegisterUI();
        try {
    UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
} catch (Exception e) {}
    }
    
}
