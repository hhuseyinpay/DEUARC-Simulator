import Computer.*;

import java.awt.*;
import java.awt.event.*;
import java.io.*;

import java.util.LinkedList;
import javax.swing.*;
import javax.swing.text.*;
/*
 * Created by JFormDesigner on Sun Apr 30 12:07:32 EEST 2017
 */


/**
 * @author hasan pay
 */
class Tester extends JFrame {
    private String fileContent = null;
    private String filePath;
    private FileOperation fo = new FileOperation();
    LinkedList<LexNode> ll;
    private Parser p = new Parser();
    private Compiler c = new Compiler();
    private boolean parseOK = false;
    private StyledDocument doc;
    private Style style;
    private Computer computer;
    private boolean isHLT;



    public Tester() {
        initComponents();
        loginPanel.setVisible(true);
        compilePanel.setVisible(false);
        computerPanel.setVisible(false);
        isHLT=false;
    }

    private void exitProcedure() {
        int answer = JOptionPane.showConfirmDialog(this, "You want to quit?", "Quit", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (answer == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    private void mainScreenProcedure() {
        int answer = JOptionPane.showConfirmDialog(this, "Would you like to go back to Main Screen?", "Main Screen", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (answer == JOptionPane.YES_OPTION) {
            parseOK = false;
            codePane.setBackground(Color.WHITE);

            instructionRegisterTxtfl.setText("0 0 0 0 0 0 0 0 0 0 0");
            addressRegisterTxtfl.setText("0 0 0 0 0");
            programCounterTxtfl.setText("0 0 0 0");
            stackPointerTxtfl.setText("0 0 0 0");
            inputRegisterTxtfl.setText("0 0 0 0");
            outputRegisterTxtfl.setText("0 0 0 0");

            register0Txtfl.setText("0 0 0 0");
            register1Txtfl.setText("0 0 0 0");
            register2Txtfl.setText("0 0 0 0");

            overflowTxtfl.setText("false");

            clockCycle.setText("0");

            instructionMemoryTxtAr.setText("");
            dataMemoryTxtAr.setText("");
            stackMemoryTxtAr.setText("");
            labelTableTxtPn.setText("");
            assemblyTxtpn.setText("");
            microInstructionsTxtAr.setText("");

            compilePanel.setVisible(false);
            computerPanel.setVisible(false);
            loginPanel.setVisible(true);
        }
    }

    private void codePrint(LinkedList<LexNode> code) {
        if (code == null) {
            parseOK = false;
            return;
        }
        doc = codePane.getStyledDocument();
        style = codePane.addStyle("first style", null);
        StyleConstants.setForeground(style, Color.black);


        for (LexNode ln : code) {
            //System.out.println(ln.isWrong());
            if (ln.isWrong()) {
                StyleConstants.setForeground(style, Color.red);
                try {
                    doc.insertString(doc.getLength(), ln.getLine(), style);
                } catch (BadLocationException e) {
                    System.out.println(e.getMessage());
                }
                parseOK = false;
                StyleConstants.setForeground(style, Color.black);
                continue;

            }


            try {
                doc.insertString(doc.getLength(), ln.getLine(), style);
            } catch (BadLocationException e) {
                System.out.println(e.getMessage());
            }
        }
        StyleConstants.setForeground(style, Color.black);
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
                fileContent = fo.readFile(filePath);
                codePane.setText(fileContent);

                loginPanel.setVisible(false);
                compilePanel.setVisible(true);
            }
        }
    }

    private void btnExitMouseClicked(MouseEvent e) {
        exitProcedure();
    }
// END OF LOGIN PANEL

// COMPILE PANEL

    private void btnParseMouseClicked(MouseEvent e) {
        codePane.setText("");
        if (!parseOK) {          // parse bir kere çalışacak !
            parseOK = true;
            ll = p.parse(fileContent);

            if (ll == null) // fileConent boşsa ekrana bişey yazmayacak.
                return;

            codePrint(ll);
        }
    }

    private void btnCompileMouseClicked(MouseEvent e) {
        String[] inst;
        if (parseOK) {
            inst = c.compile(ll);
            if (inst == null) {
                codePane.setBackground(Color.red);
                JOptionPane.showMessageDialog(null, "Compile error!");
                parseOK = false;
            } else {

                assemblyTxtpn.setText(codePane.getText());
                codePane.setText("");
                compileInit(inst);
                compilePanel.setVisible(false);
                computerPanel.setVisible(true);
                parseOK = false;

                String[] tmpInst = new String[32];
                String[] tmpData = new String[16];

                for (int i = 0; i < 32; i++) {
                    tmpInst[i]=inst[i];
                }

                for (int i = 32; i <48 ; i++) {
                    tmpData[i-32]=inst[i];
                }



                computer = new Computer(tmpInst,tmpData);

            }
        }
    }

    private void btnExit2MouseClicked(MouseEvent e) {
        exitProcedure();
    }

    private void bntBackMouseClicked(MouseEvent e) {
        mainScreenProcedure();
    }

    private void compileInit(String[] inst) {

        instMemAddressTxtAr.setText(null);
        instructionMemoryTxtAr.setText(null);
        for (int i = 0; i < 32; i++) {
            instMemAddressTxtAr.append(i + "\n");
            instructionMemoryTxtAr.append(inst[i] + "\n");
        }

        micInstAddressTxtAr.setText(null);
        stackMemAddressTxtAr.setText(null);
        dataMemAddressTxtAr.setText(null);
        dataMemoryTxtAr.setText(null);
        for (int i = 32; i < 48; i++) {
            // stack memory dinamik olacak, computer dolduracak !
            stackMemAddressTxtAr.append(i - 32 + "\n");

            micInstAddressTxtAr.append(i - 32 + "\n");

            dataMemAddressTxtAr.append(i - 32 + "\n");
            dataMemoryTxtAr.append(inst[i] + "\n");
        }


        doc = labelTableTxtPn.getStyledDocument();
        labelTableTxtPn.setText("");
        for (int i = 48; i < 64; i++) {
            try {
                doc.insertString(doc.getLength(), inst[i], style);
            } catch (BadLocationException e) {
                System.out.println(e.getMessage());
            }
        }
    }
// END OF COMPILE PANEL

// COMPUTER PANEL

    private void btnExit3MouseClicked(MouseEvent e) {
        exitProcedure();
    }

    private void btnMainScreenMouseClicked(MouseEvent e) {
        mainScreenProcedure();
    }

    public void runstep(){
        if(isHLT){
            JOptionPane.showMessageDialog(this,"Program is finished!","Finished",JOptionPane.ERROR_MESSAGE);
            return;
        }

        clockCycle.setText(Integer.toString(computer.cpu.controlUnit.T));

        String temp = computer.stepRun();

        if(temp==null){
            return;
        }

        if(temp.equals("ENDofPROGRAM")){
            computer.cpu.controlUnit.T=0;
            computer.cpu.PC.setData(0);
            isHLT=true;
            return;
        }

        if(computer.cpu.controlUnit.T-1==0){
            microInstructionsTxtAr.setText("");
        }
        microInstructionsTxtAr.append(temp+"\n");


        instructionRegisterTxtfl.setText(computer.cpu.IR.data);
        addressRegisterTxtfl.setText(computer.cpu.AR.data);
        programCounterTxtfl.setText(Integer.toString(computer.cpu.PC.getData()));
        stackPointerTxtfl.setText(computer.cpu.SP.data);
        inputRegisterTxtfl.setText(computer.cpu.inpr.data);
        outputRegisterTxtfl.setText(computer.cpu.outr.data);
        register0Txtfl.setText(computer.cpu.R0.data);
        register1Txtfl.setText(computer.cpu.R1.data);
        register2Txtfl.setText(computer.cpu.R2.data);

        if(computer.cpu.overflow.data.equals("true")){
            overflowTxtfl.setText("true");
        }

        stackMemoryTxtAr.setText("");
        dataMemoryTxtAr.setText("");

        for (int i = 0; i < 16; i++) {
            stackMemoryTxtAr.append(computer.stackMem.getData()[i]+"\n");
            dataMemoryTxtAr.append(computer.dataMem.getData()[i]+"\n");
        }


    }

    private void stepRunMouseClicked(MouseEvent e) {
        runstep();

    }

    private void exportBtnMouseClicked(MouseEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        fileChooser.showOpenDialog(fileChooser);
        File selectedFile = fileChooser.getSelectedFile();

        if (selectedFile == null || !selectedFile.isFile() || !fo.extentionChecker(filePath)) {
            return;
        }

        try {

            FileOutputStream fos = new FileOutputStream(selectedFile);

            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));


            bw.write("DEPTH = 32;            -- The size of memory in words");
            bw.newLine();
            bw.write("WIDTH = 11;            -- The size of data in bits");
            bw.newLine();
            bw.write("ADDRESS_RADIX = HEX;   -- The radix for address values");
            bw.newLine();
            bw.write("DATA_RADIX = BIN;      -- The radix for data values");
            bw.newLine();
            bw.write("CONTENT                -- start of (address : data pairs)");
            bw.newLine();
            bw.write("BEGIN");
            bw.newLine();

            for (int i = 0; i < 32; i++) {
                if(computer.instMem.getData()[i]==null){
                    bw.write(((i % 16 == i)?"0":"")+Integer.toHexString(i)+ " : " + "00000000000" + ";");
                }
                else{
                    bw.write(((i % 16 == i)?"0":"") + Integer.toHexString(i)+ " : " + computer.instMem.getData()[i] + ";");

                }

                bw.newLine();
            }

            bw.write("END;");

            bw.close();



        } catch (IOException es) {
            es.printStackTrace();
        }

    }

    private void button1MouseClicked(MouseEvent e) {
        do{
            runstep();
        }while (computer.cpu.controlUnit.T>0);
    }

    private void button2MouseClicked(MouseEvent e) {
        while (!isHLT){
            runstep();
        }
    }


    // END OF COMPUTER PANEL
    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - Mazlum asdasf
        computerPanel = new JPanel();
        label1 = new JLabel();
        label2 = new JLabel();
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
        stackMemoryTxtAr = new JTextArea();
        stepRun = new JButton();
        button1 = new JButton();
        button2 = new JButton();
        btnExit3 = new JButton();
        btnMainScreen = new JButton();
        label14 = new JLabel();
        clockCycle = new JTextField();
        labelTableAddrTxtPn = new JTextPane();
        labelTableTxtPn = new JTextPane();
        label15 = new JLabel();
        microInstructionsTxtAr = new JTextArea();
        micInstAddressTxtAr = new JTextArea();
        label16 = new JLabel();
        LabelOverflow = new JLabel();
        overflowTxtfl = new JTextField();
        exportBtn = new JButton();
        scrollPane2 = new JScrollPane();
        assemblyTxtpn = new JTextPane();
        label17 = new JLabel();
        label18 = new JLabel();
        textField1 = new JTextField();
        compilePanel = new JPanel();
        scrollPane1 = new JScrollPane();
        codePane = new JTextPane();
        btnCompile = new JButton();
        btnExit2 = new JButton();
        btnParse = new JButton();
        bntBack = new JButton();
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

            //---- addressRegisterTxtfl ----
            addressRegisterTxtfl.setText("0 0 0 0 0");
            addressRegisterTxtfl.setEditable(false);
            computerPanel.add(addressRegisterTxtfl);
            addressRegisterTxtfl.setBounds(205, 95, 70, addressRegisterTxtfl.getPreferredSize().height);

            //---- programCounterTxtfl ----
            programCounterTxtfl.setText("0 0 0 0");
            programCounterTxtfl.setEditable(false);
            computerPanel.add(programCounterTxtfl);
            programCounterTxtfl.setBounds(205, 130, 60, programCounterTxtfl.getPreferredSize().height);

            //---- stackPointerTxtfl ----
            stackPointerTxtfl.setEditable(false);
            stackPointerTxtfl.setText("0 0 0 0");
            computerPanel.add(stackPointerTxtfl);
            stackPointerTxtfl.setBounds(205, 165, 60, stackPointerTxtfl.getPreferredSize().height);

            //---- inputRegisterTxtfl ----
            inputRegisterTxtfl.setEditable(false);
            inputRegisterTxtfl.setText("0 0 0 0");
            computerPanel.add(inputRegisterTxtfl);
            inputRegisterTxtfl.setBounds(205, 200, 60, inputRegisterTxtfl.getPreferredSize().height);

            //---- outputRegisterTxtfl ----
            outputRegisterTxtfl.setEditable(false);
            outputRegisterTxtfl.setText("0 0 0 0");
            computerPanel.add(outputRegisterTxtfl);
            outputRegisterTxtfl.setBounds(205, 235, 60, outputRegisterTxtfl.getPreferredSize().height);

            //---- instructionRegisterTxtfl ----
            instructionRegisterTxtfl.setText("0 0 0 0 0 0 0 0 0 0 0");
            instructionRegisterTxtfl.setEditable(false);
            computerPanel.add(instructionRegisterTxtfl);
            instructionRegisterTxtfl.setBounds(190, 60, 145, instructionRegisterTxtfl.getPreferredSize().height);

            //---- register0Txtfl ----
            register0Txtfl.setEditable(false);
            register0Txtfl.setText("0 0 0 0");
            computerPanel.add(register0Txtfl);
            register0Txtfl.setBounds(205, 280, 60, register0Txtfl.getPreferredSize().height);

            //---- register1Txtfl ----
            register1Txtfl.setEditable(false);
            register1Txtfl.setText("0 0 0 0");
            computerPanel.add(register1Txtfl);
            register1Txtfl.setBounds(205, 315, 60, 24);

            //---- register2Txtfl ----
            register2Txtfl.setEditable(false);
            register2Txtfl.setText("0 0 0 0");
            computerPanel.add(register2Txtfl);
            register2Txtfl.setBounds(205, 350, 60, 24);

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
            label8.setBounds(20, 285, 90, 16);

            //---- label9 ----
            label9.setText("REGISTER 1");
            computerPanel.add(label9);
            label9.setBounds(20, 320, 75, 16);

            //---- label10 ----
            label10.setText("REGISTER 2");
            computerPanel.add(label10);
            label10.setBounds(20, 355, 85, 16);

            //---- label11 ----
            label11.setText("Instruction Memory");
            computerPanel.add(label11);
            label11.setBounds(380, 25, 140, label11.getPreferredSize().height);

            //---- instructionMemoryTxtAr ----
            instructionMemoryTxtAr.setRows(32);
            instructionMemoryTxtAr.setTabSize(11);
            instructionMemoryTxtAr.setEditable(false);
            computerPanel.add(instructionMemoryTxtAr);
            instructionMemoryTxtAr.setBounds(385, 50, 151, 520);

            //---- instMemAddressTxtAr ----
            instMemAddressTxtAr.setRows(32);
            instMemAddressTxtAr.setEditable(false);
            computerPanel.add(instMemAddressTxtAr);
            instMemAddressTxtAr.setBounds(350, 50, 30, 520);

            //---- label12 ----
            label12.setText("Data Memory");
            computerPanel.add(label12);
            label12.setBounds(new Rectangle(new Point(575, 25), label12.getPreferredSize()));

            //---- dataMemoryTxtAr ----
            dataMemoryTxtAr.setEditable(false);
            computerPanel.add(dataMemoryTxtAr);
            dataMemoryTxtAr.setBounds(595, 50, 65, 265);

            //---- dataMemAddressTxtAr ----
            dataMemAddressTxtAr.setEditable(false);
            computerPanel.add(dataMemAddressTxtAr);
            dataMemAddressTxtAr.setBounds(560, 50, 30, 265);

            //---- label13 ----
            label13.setText("Stack Memory");
            computerPanel.add(label13);
            label13.setBounds(new Rectangle(new Point(705, 25), label13.getPreferredSize()));
            computerPanel.add(stackMemAddressTxtAr);
            stackMemAddressTxtAr.setBounds(690, 50, 30, 265);
            computerPanel.add(stackMemoryTxtAr);
            stackMemoryTxtAr.setBounds(725, 50, 80, 265);

            //---- stepRun ----
            stepRun.setText("Step Run");
            stepRun.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    stepRunMouseClicked(e);
                    stepRunMouseClicked(e);
                }
            });
            computerPanel.add(stepRun);
            stepRun.setBounds(1020, 335, 111, 47);

            //---- button1 ----
            button1.setText("Instruction Run");
            button1.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    button1MouseClicked(e);
                }
            });
            computerPanel.add(button1);
            button1.setBounds(965, 385, button1.getPreferredSize().width, 47);

            //---- button2 ----
            button2.setText("Run Over");
            button2.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    button2MouseClicked(e);
                }
            });
            computerPanel.add(button2);
            button2.setBounds(1075, 385, 111, 47);

            //---- btnExit3 ----
            btnExit3.setText("Exit");
            btnExit3.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    btnExit3MouseClicked(e);
                }
            });
            computerPanel.add(btnExit3);
            btnExit3.setBounds(1080, 505, 111, 47);

            //---- btnMainScreen ----
            btnMainScreen.setText("Main Screen");
            btnMainScreen.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    btnMainScreenMouseClicked(e);
                }
            });
            computerPanel.add(btnMainScreen);
            btnMainScreen.setBounds(965, 505, 111, 47);

            //---- label14 ----
            label14.setText("T");
            label14.setFont(label14.getFont().deriveFont(label14.getFont().getSize() + 20f));
            computerPanel.add(label14);
            label14.setBounds(95, 475, 40, label14.getPreferredSize().height);

            //---- clockCycle ----
            clockCycle.setText("0");
            clockCycle.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
            clockCycle.setEditable(false);
            clockCycle.setFont(new Font(".SF NS Text", Font.PLAIN, 45));
            computerPanel.add(clockCycle);
            clockCycle.setBounds(130, 465, 45, clockCycle.getPreferredSize().height);
            computerPanel.add(labelTableAddrTxtPn);
            labelTableAddrTxtPn.setBounds(830, 50, 30, 265);
            computerPanel.add(labelTableTxtPn);
            labelTableTxtPn.setBounds(865, 50, 130, 265);

            //---- label15 ----
            label15.setText("Label Table");
            computerPanel.add(label15);
            label15.setBounds(new Rectangle(new Point(875, 25), label15.getPreferredSize()));
            computerPanel.add(microInstructionsTxtAr);
            microInstructionsTxtAr.setBounds(595, 350, 360, 210);
            computerPanel.add(micInstAddressTxtAr);
            micInstAddressTxtAr.setBounds(560, 350, 30, 210);

            //---- label16 ----
            label16.setText("Micro Instructions");
            computerPanel.add(label16);
            label16.setBounds(new Rectangle(new Point(710, 330), label16.getPreferredSize()));

            //---- LabelOverflow ----
            LabelOverflow.setText("OVERFLOW");
            computerPanel.add(LabelOverflow);
            LabelOverflow.setBounds(20, 395, 70, LabelOverflow.getPreferredSize().height);

            //---- overflowTxtfl ----
            overflowTxtfl.setEditable(false);
            overflowTxtfl.setText("false");
            computerPanel.add(overflowTxtfl);
            overflowTxtfl.setBounds(205, 390, 60, overflowTxtfl.getPreferredSize().height);

            //---- exportBtn ----
            exportBtn.setText("Export");
            exportBtn.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    exportBtnMouseClicked(e);
                }
            });
            computerPanel.add(exportBtn);
            exportBtn.setBounds(1030, 455, 111, 47);

            //======== scrollPane2 ========
            {
                scrollPane2.setViewportView(assemblyTxtpn);
            }
            computerPanel.add(scrollPane2);
            scrollPane2.setBounds(1005, 50, 185, 265);

            //---- label17 ----
            label17.setText("Assembly Code");
            computerPanel.add(label17);
            label17.setBounds(new Rectangle(new Point(1065, 25), label17.getPreferredSize()));

            //---- label18 ----
            label18.setText("ENTER INPUT :");
            computerPanel.add(label18);
            label18.setBounds(20, 429, 90, label18.getPreferredSize().height);
            computerPanel.add(textField1);
            textField1.setBounds(205, 425, 60, textField1.getPreferredSize().height);

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
        computerPanel.setBounds(0, -15, 1210, 570);

        //======== compilePanel ========
        {
            compilePanel.setLayout(null);

            //======== scrollPane1 ========
            {
                scrollPane1.setViewportView(codePane);
            }
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
            btnCompile.setBounds(560, 260, 100, 34);

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
            btnExit2.setBounds(820, 260, 100, 34);

            //---- btnParse ----
            btnParse.setText("Parse");
            btnParse.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    btnParseMouseClicked(e);
                }
            });
            compilePanel.add(btnParse);
            btnParse.setBounds(450, 260, 100, 34);

            //---- bntBack ----
            bntBack.setText("Back");
            bntBack.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    bntBackMouseClicked(e);
                }
            });
            compilePanel.add(bntBack);
            bntBack.setBounds(705, 260, 100, 34);

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
        compilePanel.setBounds(30, 0, 965, 535);

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
            btnFileChooser.setBounds(0, 10, btnFileChooser.getPreferredSize().width, 32);

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
    // Generated using JFormDesigner Evaluation license - Mazlum asdasf
    protected JPanel computerPanel;
    private JLabel label1;
    private JLabel label2;
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
    protected JTextArea stackMemoryTxtAr;
    private JButton stepRun;
    private JButton button1;
    private JButton button2;
    private JButton btnExit3;
    private JButton btnMainScreen;
    private JLabel label14;
    protected JTextField clockCycle;
    protected JTextPane labelTableAddrTxtPn;
    protected JTextPane labelTableTxtPn;
    private JLabel label15;
    protected JTextArea microInstructionsTxtAr;
    protected JTextArea micInstAddressTxtAr;
    private JLabel label16;
    private JLabel LabelOverflow;
    private JTextField overflowTxtfl;
    private JButton exportBtn;
    private JScrollPane scrollPane2;
    private JTextPane assemblyTxtpn;
    private JLabel label17;
    private JLabel label18;
    private JTextField textField1;
    private JPanel compilePanel;
    private JScrollPane scrollPane1;
    public JTextPane codePane;
    private JButton btnCompile;
    private JButton btnExit2;
    protected JButton btnParse;
    protected JButton bntBack;
    private JPanel loginPanel;
    private JButton btnExit;
    private JButton btnFileChooser;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
