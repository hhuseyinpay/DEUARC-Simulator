import javax.swing.*;
public class Main {


    public static void main(String[] args) {
        JFrame frame = new JFrame("DEUARC Similator");
        frame.setContentPane(new Tester().getContentPane());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setResizable(false);
        frame.setSize(1525,580);
        frame.setVisible(true);
    }
}
