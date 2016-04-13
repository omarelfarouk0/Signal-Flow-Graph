
import java.awt.Graphics;
import java.awt.geom.QuadCurve2D;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Test extends JFrame {

    public static void main(String[] args) {
//        Test test = new Test();

        JTextField firstName = new JTextField();
        JTextField lastName = new JTextField();
        JPasswordField password = new JPasswordField();
        final JComponent[] inputs = new JComponent[]{
            new JLabel("First"),
            firstName,
            new JLabel("Last"),
            lastName,
            new JLabel("Password"),
            password
        };
        JOptionPane.showMessageDialog(null, inputs, "My custom dialog", JOptionPane.PLAIN_MESSAGE);
        System.out.println("You entered "
                + firstName.getText() + ", "
                + lastName.getText() + ", "
                + password.getText());

    }

    public Test() {
        this.setSize(400, 400);
        this.setVisible(true);
    }

    public void paint(Graphics g) {
        g.fillArc(100, 100, 100, 100, 70, 30);
    }
}
