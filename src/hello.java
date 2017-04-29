import javax.swing.*;
public class hello {


    public static void main(String[] args) {
        JFrame frame = new JFrame("MyForm");
        frame.setContentPane(new GUI().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();

        frame.setVisible(true);
    }
}
