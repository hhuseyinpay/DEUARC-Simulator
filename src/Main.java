import javax.swing.*;
public class Main {


    public static void main(String[] args) {
        JFrame frame = new JFrame("MyForm");
        frame.setContentPane(new GUI().basePanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();

        frame.setVisible(true);
    }
}
