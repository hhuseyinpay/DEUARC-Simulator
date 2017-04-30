import java.awt.*;
import java.awt.event.*;
import java.io.File;
import javax.swing.*;
/*
 * Created by JFormDesigner on Sun Apr 30 12:07:32 EEST 2017
 */



/**
 * @author hasan pay
 */
public class Tester extends JFrame {
    private String filePath;
    private FileOperation fo = new FileOperation();
    public Tester() {
        initComponents();
        loginPanel.setVisible(false);
        compilePanel.setVisible(true);
        computerPanel.setVisible(false);
    }

// LOGIN PANEL
    private void btnFileChooserMouseClicked(MouseEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        fileChooser.showOpenDialog(fileChooser);
        File selectedFile = fileChooser.getSelectedFile();

        if (selectedFile != null && selectedFile.isFile()) {
            filePath = selectedFile.getAbsolutePath();

            if (fo.extentionChecker(filePath)) {
                loginPanel.setVisible(false);
                compilePanel.setVisible(true);
            }
        }
    }

    private void btnExitMouseClicked(MouseEvent e) {
        int answer = JOptionPane.showConfirmDialog(this, "You want to quit?", "Quit", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (answer == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }
// END OF LOGIN PANEL

// COMPILE PANEL
    private void btnExit2MouseClicked(MouseEvent e) {
        int answer = JOptionPane.showConfirmDialog(this, "You want to quit?", "Quit", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (answer == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    private void btnCompileMouseClicked(MouseEvent e) {
        for (int i = 0; i < 32; i++) {
            instMemAddressTxtAr.append(i + "\n");
        }
        instructionMemoryTxtAr.setText("0 0 0 0 0 0 0 0 0 0 0");



        compilePanel.setVisible(false);
        computerPanel.setVisible(true);
    }
// END OF COMPILE PANEL



    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - hasan pay
        computerPanel = new JPanel();
        label1 = new JLabel();
        label2 = new JLabel();
        textPane1 = new JTextPane();
        addressRegisterTxtfl = new JTextField();
        programCounterTxtfl = new JTextField();
        stackPointerTxtfl = new JTextField();
        inputRegisterTxtfl = new JTextField();
        outputRegisterTxtfl = new JTextField();
        instructionRegisterTxtfl = new JTextField();
        register0Txtfl = new JTextField();
        register1Txtfl = new JTextField();
        register2Txtfl = new JTextField();
        label3 = new JLabel();
        label4 = new JLabel();
        label5 = new JLabel();
        label6 = new JLabel();
        label7 = new JLabel();
        label8 = new JLabel();
        label9 = new JLabel();
        label10 = new JLabel();
        label11 = new JLabel();
        instructionMemoryTxtAr = new JTextArea();
        instMemAddressTxtAr = new JTextArea();
        label12 = new JLabel();
        dataMemoryTxtAr = new JTextArea();
        dataMemAddressTxtAr = new JTextArea();
        label13 = new JLabel();
        stackMemAddressTxtAr = new JTextArea();
        StackMemoryTxtAr = new JTextArea();
        stepRun = new JButton();
        button1 = new JButton();
        button2 = new JButton();
        button3 = new JButton();
        label14 = new JLabel();
        clockCycle = new JTextField();
        compilePanel = new JPanel();
        scrollPane1 = new JScrollPane();
        btnCompile = new JButton();
        btnExit2 = new JButton();
        loginPanel = new JPanel();
        btnExit = new JButton();
        btnFileChooser = new JButton();

        //======== this ========
        Container contentPane = getContentPane();
        contentPane.setLayout(null);

        //======== computerPanel ========
        {

            // JFormDesigner evaluation mark
            computerPanel.setBorder(new javax.swing.border.CompoundBorder(
                new javax.swing.border.TitledBorder(new javax.swing.border.EmptyBorder(0, 0, 0, 0),
                    "JFormDesigner Evaluation", javax.swing.border.TitledBorder.CENTER,
                    javax.swing.border.TitledBorder.BOTTOM, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12),
                    java.awt.Color.red), computerPanel.getBorder())); computerPanel.addPropertyChangeListener(new java.beans.PropertyChangeListener(){public void propertyChange(java.beans.PropertyChangeEvent e){if("border".equals(e.getPropertyName()))throw new RuntimeException();}});

            computerPanel.setLayout(null);

            //---- label1 ----
            label1.setText("Registers");
            computerPanel.add(label1);
            label1.setBounds(new Rectangle(new Point(135, 25), label1.getPreferredSize()));

            //---- label2 ----
            label2.setText("ADDRESS REGISTER");
            computerPanel.add(label2);
            label2.setBounds(new Rectangle(new Point(20, 100), label2.getPreferredSize()));
            computerPanel.add(textPane1);
            textPane1.setBounds(new Rectangle(new Point(155, 110), textPane1.getPreferredSize()));

            //---- addressRegisterTxtfl ----
            addressRegisterTxtfl.setText("0 0 0 0 0");
            addressRegisterTxtfl.setEditable(false);
            computerPanel.add(addressRegisterTxtfl);
            addressRegisterTxtfl.setBounds(190, 95, 70, addressRegisterTxtfl.getPreferredSize().height);

            //---- programCounterTxtfl ----
            programCounterTxtfl.setText("0 0 0 0");
            programCounterTxtfl.setEditable(false);
            computerPanel.add(programCounterTxtfl);
            programCounterTxtfl.setBounds(190, 130, 60, programCounterTxtfl.getPreferredSize().height);

            //---- stackPointerTxtfl ----
            stackPointerTxtfl.setEditable(false);
            stackPointerTxtfl.setText("0 0 0 0");
            computerPanel.add(stackPointerTxtfl);
            stackPointerTxtfl.setBounds(190, 165, 60, stackPointerTxtfl.getPreferredSize().height);

            //---- inputRegisterTxtfl ----
            inputRegisterTxtfl.setEditable(false);
            inputRegisterTxtfl.setText("0 0 0 0");
            computerPanel.add(inputRegisterTxtfl);
            inputRegisterTxtfl.setBounds(190, 200, 60, inputRegisterTxtfl.getPreferredSize().height);

            //---- outputRegisterTxtfl ----
            outputRegisterTxtfl.setEditable(false);
            outputRegisterTxtfl.setText("0 0 0 0");
            computerPanel.add(outputRegisterTxtfl);
            outputRegisterTxtfl.setBounds(190, 235, 60, outputRegisterTxtfl.getPreferredSize().height);

            //---- instructionRegisterTxtfl ----
            instructionRegisterTxtfl.setText("0 0 0 0 0 0 0 0 0 0 0");
            instructionRegisterTxtfl.setEditable(false);
            computerPanel.add(instructionRegisterTxtfl);
            instructionRegisterTxtfl.setBounds(190, 60, 145, instructionRegisterTxtfl.getPreferredSize().height);

            //---- register0Txtfl ----
            register0Txtfl.setEditable(false);
            register0Txtfl.setText("0 0 0 0");
            computerPanel.add(register0Txtfl);
            register0Txtfl.setBounds(190, 295, 60, register0Txtfl.getPreferredSize().height);

            //---- register1Txtfl ----
            register1Txtfl.setEditable(false);
            register1Txtfl.setText("0 0 0 0");
            computerPanel.add(register1Txtfl);
            register1Txtfl.setBounds(190, 330, 60, 24);

            //---- register2Txtfl ----
            register2Txtfl.setEditable(false);
            register2Txtfl.setText("0 0 0 0");
            computerPanel.add(register2Txtfl);
            register2Txtfl.setBounds(190, 365, 60, 24);

            //---- label3 ----
            label3.setText("PROGRAM COUNTER");
            computerPanel.add(label3);
            label3.setBounds(20, 135, 145, label3.getPreferredSize().height);

            //---- label4 ----
            label4.setText("STACK POINTER");
            computerPanel.add(label4);
            label4.setBounds(20, 170, 120, 16);

            //---- label5 ----
            label5.setText("INPUT REGISTER");
            computerPanel.add(label5);
            label5.setBounds(20, 205, 110, 16);

            //---- label6 ----
            label6.setText("OUTPUT REGISTER");
            computerPanel.add(label6);
            label6.setBounds(20, 235, 125, 16);

            //---- label7 ----
            label7.setText("INSTRUCTION REGISTER");
            label7.setBackground(new Color(153, 102, 255));
            computerPanel.add(label7);
            label7.setBounds(20, 65, 155, 16);

            //---- label8 ----
            label8.setText("REGISTER 0");
            computerPanel.add(label8);
            label8.setBounds(20, 300, 90, 16);

            //---- label9 ----
            label9.setText("REGISTER 1");
            computerPanel.add(label9);
            label9.setBounds(20, 335, 75, 16);

            //---- label10 ----
            label10.setText("REGISTER 2");
            computerPanel.add(label10);
            label10.setBounds(20, 370, 85, 16);

            //---- label11 ----
            label11.setText("Instruction Memory");
            computerPanel.add(label11);
            label11.setBounds(450, 25, 140, label11.getPreferredSize().height);

            //---- instructionMemoryTxtAr ----
            instructionMemoryTxtAr.setRows(32);
            instructionMemoryTxtAr.setTabSize(11);
            instructionMemoryTxtAr.setEditable(false);
            computerPanel.add(instructionMemoryTxtAr);
            instructionMemoryTxtAr.setBounds(455, 50, 151, 520);

            //---- instMemAddressTxtAr ----
            instMemAddressTxtAr.setRows(32);
            instMemAddressTxtAr.setEditable(false);
            computerPanel.add(instMemAddressTxtAr);
            instMemAddressTxtAr.setBounds(420, 50, 30, 520);

            //---- label12 ----
            label12.setText("Data Memory");
            computerPanel.add(label12);
            label12.setBounds(new Rectangle(new Point(685, 25), label12.getPreferredSize()));

            //---- dataMemoryTxtAr ----
            dataMemoryTxtAr.setEditable(false);
            computerPanel.add(dataMemoryTxtAr);
            dataMemoryTxtAr.setBounds(710, 50, 65, 265);

            //---- dataMemAddressTxtAr ----
            dataMemAddressTxtAr.setEditable(false);
            computerPanel.add(dataMemAddressTxtAr);
            dataMemAddressTxtAr.setBounds(675, 50, 30, 265);

            //---- label13 ----
            label13.setText("Stack Memory");
            computerPanel.add(label13);
            label13.setBounds(new Rectangle(new Point(860, 25), label13.getPreferredSize()));
            computerPanel.add(stackMemAddressTxtAr);
            stackMemAddressTxtAr.setBounds(850, 50, 30, 265);
            computerPanel.add(StackMemoryTxtAr);
            StackMemoryTxtAr.setBounds(885, 50, 80, 265);

            //---- stepRun ----
            stepRun.setText("Step Run");
            computerPanel.add(stepRun);
            stepRun.setBounds(625, 430, stepRun.getPreferredSize().width, 47);

            //---- button1 ----
            button1.setText("Instruction Run");
            computerPanel.add(button1);
            button1.setBounds(715, 430, button1.getPreferredSize().width, 47);

            //---- button2 ----
            button2.setText("Run Over");
            computerPanel.add(button2);
            button2.setBounds(845, 430, button2.getPreferredSize().width, 47);

            //---- button3 ----
            button3.setText("Exit");
            computerPanel.add(button3);
            button3.setBounds(960, 430, button3.getPreferredSize().width, 47);

            //---- label14 ----
            label14.setText("T");
            label14.setFont(label14.getFont().deriveFont(label14.getFont().getSize() + 20f));
            computerPanel.add(label14);
            label14.setBounds(55, 485, 40, label14.getPreferredSize().height);

            //---- clockCycle ----
            clockCycle.setText("0");
            clockCycle.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
            clockCycle.setEditable(false);
            clockCycle.setFont(new Font(".SF NS Text", Font.PLAIN, 45));
            computerPanel.add(clockCycle);
            clockCycle.setBounds(90, 475, 45, clockCycle.getPreferredSize().height);

            { // compute preferred size
                Dimension preferredSize = new Dimension();
                for(int i = 0; i < computerPanel.getComponentCount(); i++) {
                    Rectangle bounds = computerPanel.getComponent(i).getBounds();
                    preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                    preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                }
                Insets insets = computerPanel.getInsets();
                preferredSize.width += insets.right;
                preferredSize.height += insets.bottom;
                computerPanel.setMinimumSize(preferredSize);
                computerPanel.setPreferredSize(preferredSize);
            }
        }
        contentPane.add(computerPanel);
        computerPanel.setBounds(0, -15, 1020, 570);

        //======== compilePanel ========
        {
            compilePanel.setLayout(null);
            compilePanel.add(scrollPane1);
            scrollPane1.setBounds(0, 0, 420, 535);

            //---- btnCompile ----
            btnCompile.setText("Compile");
            btnCompile.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    btnCompileMouseClicked(e);
                }
            });
            compilePanel.add(btnCompile);
            btnCompile.setBounds(new Rectangle(new Point(530, 260), btnCompile.getPreferredSize()));

            //---- btnExit2 ----
            btnExit2.setText("Exit");
            btnExit2.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    btnExitMouseClicked(e);
                    btnExit2MouseClicked(e);
                }
            });
            compilePanel.add(btnExit2);
            btnExit2.setBounds(640, 260, 53, 32);

            { // compute preferred size
                Dimension preferredSize = new Dimension();
                for(int i = 0; i < compilePanel.getComponentCount(); i++) {
                    Rectangle bounds = compilePanel.getComponent(i).getBounds();
                    preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                    preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                }
                Insets insets = compilePanel.getInsets();
                preferredSize.width += insets.right;
                preferredSize.height += insets.bottom;
                compilePanel.setMinimumSize(preferredSize);
                compilePanel.setPreferredSize(preferredSize);
            }
        }
        contentPane.add(compilePanel);
        compilePanel.setBounds(85, 0, 770, 535);

        //======== loginPanel ========
        {
            loginPanel.setLayout(null);

            //---- btnExit ----
            btnExit.setText("Exit");
            btnExit.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    btnExitMouseClicked(e);
                }
            });
            loginPanel.add(btnExit);
            btnExit.setBounds(235, 10, btnExit.getPreferredSize().width, 32);

            //---- btnFileChooser ----
            btnFileChooser.setText("Choose a File(.asm or .basm)");
            btnFileChooser.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    btnFileChooserMouseClicked(e);
                }
            });
            loginPanel.add(btnFileChooser);
            btnFileChooser.setBounds(new Rectangle(new Point(0, 10), btnFileChooser.getPreferredSize()));

            { // compute preferred size
                Dimension preferredSize = new Dimension();
                for(int i = 0; i < loginPanel.getComponentCount(); i++) {
                    Rectangle bounds = loginPanel.getComponent(i).getBounds();
                    preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                    preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                }
                Insets insets = loginPanel.getInsets();
                preferredSize.width += insets.right;
                preferredSize.height += insets.bottom;
                loginPanel.setMinimumSize(preferredSize);
                loginPanel.setPreferredSize(preferredSize);
            }
        }
        contentPane.add(loginPanel);
        loginPanel.setBounds(380, 240, 305, 45);

        { // compute preferred size
            Dimension preferredSize = new Dimension();
            for(int i = 0; i < contentPane.getComponentCount(); i++) {
                Rectangle bounds = contentPane.getComponent(i).getBounds();
                preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
            }
            Insets insets = contentPane.getInsets();
            preferredSize.width += insets.right;
            preferredSize.height += insets.bottom;
            contentPane.setMinimumSize(preferredSize);
            contentPane.setPreferredSize(preferredSize);
        }
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - hasan pay
    protected JPanel computerPanel;
    private JLabel label1;
    private JLabel label2;
    private JTextPane textPane1;
    private JTextField addressRegisterTxtfl;
    private JTextField programCounterTxtfl;
    private JTextField stackPointerTxtfl;
    private JTextField inputRegisterTxtfl;
    private JTextField outputRegisterTxtfl;
    private JTextField instructionRegisterTxtfl;
    private JTextField register0Txtfl;
    private JTextField register1Txtfl;
    private JTextField register2Txtfl;
    private JLabel label3;
    private JLabel label4;
    private JLabel label5;
    private JLabel label6;
    private JLabel label7;
    private JLabel label8;
    private JLabel label9;
    private JLabel label10;
    private JLabel label11;
    protected JTextArea instructionMemoryTxtAr;
    private JTextArea instMemAddressTxtAr;
    private JLabel label12;
    protected JTextArea dataMemoryTxtAr;
    protected JTextArea dataMemAddressTxtAr;
    private JLabel label13;
    protected JTextArea stackMemAddressTxtAr;
    protected JTextArea StackMemoryTxtAr;
    private JButton stepRun;
    private JButton button1;
    private JButton button2;
    private JButton button3;
    private JLabel label14;
    protected JTextField clockCycle;
    private JPanel compilePanel;
    private JScrollPane scrollPane1;
    private JButton btnCompile;
    private JButton btnExit2;
    private JPanel loginPanel;
    private JButton btnExit;
    private JButton btnFileChooser;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}