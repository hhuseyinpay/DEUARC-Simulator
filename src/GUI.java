import javax.swing.*;
import java.io.File;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.nio.file.Path;

/**
 * Created by hhp on 29/04/2017.
 */
public class GUI {
    public JPanel basePanel;
    private JPanel loginPanel;
    private JButton compileBtn;
    private JButton exitButton;
    private JPanel compilePanel;
    private String filePath;
    private FileOperation fo = new FileOperation();

    public GUI() {

        loginPanel.setVisible(true);
        compilePanel.setVisible(false);

        // login panel file chooser
        compileBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
                fileChooser.showOpenDialog(fileChooser);
                File selectedFile = fileChooser.getSelectedFile();
                if (selectedFile.isFile()) {
                    filePath = selectedFile.getAbsolutePath();

                    if (fo.extentionChecker(filePath)) {
                        loginPanel.setVisible(false);
                        compilePanel.setVisible(true);
                    }
                }
            }
        });



    }
}
