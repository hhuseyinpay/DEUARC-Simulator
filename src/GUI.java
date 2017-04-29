import javax.swing.*;
import java.io.File;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by hhp on 29/04/2017.
 */
public class GUI {
    public JPanel basePanel;
    private JPanel loginPanel;
    private JButton compileBtn;
    private JButton exitButton;
    private String filePath;
    public GUI() {


        // login panel file chooser
        compileBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
                fileChooser.showOpenDialog(fileChooser);
                File selectedFile = fileChooser.getSelectedFile();
                filePath = selectedFile.getAbsolutePath();
                System.out.println(filePath);
                super.mouseClicked(e);
            }
        });
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
