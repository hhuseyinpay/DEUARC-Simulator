import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * Created by quiet on 02/05/17.
 */
public class Compiler {
    private String[] instsTable;
    private String[] dataTable;

    private Map<String, String> instructions;
    private Map<String, String> registers;
    private Map<String, String> labelTable;
    private String[] labelTableKeys;
    private int[] labelAdress;
    private int labelPointer;

    public Compiler() {
        instsTable = new String[32];
        dataTable = new String[16];
        instructions = new HashMap<String, String>();
        registers = new HashMap<String, String>();
        labelTable = new HashMap<String, String>();
        labelTableKeys = new String[16];
        labelPointer = 0;
        labelAdress = new int[16];

        instructions.put("HLT", "1000");
        instructions.put("ADD", "0000");
        instructions.put("INC", "0001");
        instructions.put("DBL", "0010");
        instructions.put("DBT", "0011");
        instructions.put("NOT", "0100");
        instructions.put("AND", "0101");
        instructions.put("LD", "0110");
        instructions.put("ST", "0111");
        instructions.put("TSF", "1001");
        instructions.put("CAL", "1010");
        instructions.put("RET", "1011");
        instructions.put("JMP", "1100");
        instructions.put("JMR", "1101");
        instructions.put("PSH", "1110");
        instructions.put("POP", "1111");

        registers.put("R0", "00");
        registers.put("R1", "01");
        registers.put("R2", "10");
    }

    public String[] compile(LinkedList<LexNode> lexList) {
        String[] lex;
        int instPointer = 0;
        String binaryParameter;
        if (dataController(lexList)) // fill the data and label tables, if error occurs return null
            return null;

        lex = lexList.getFirst().getLexes();
        if (lex.length == 3 && lex[0].equals("ORG") && lex[1].equals("I")) {
            int number = stringToInt(lex[2]);

            if (number < 0 || number > 31) {
                return null;
            }

            instPointer = number;
            binaryParameter = Integer.toBinaryString(instPointer);

            String tmp = "0" + instructions.get("JMP") + "000000";

            instsTable[0] = tmp.substring(0, 11 - binaryParameter.length()) + binaryParameter; // PC 0 dan başlayacak ve ilk olarak ORG I olan yere atlayacak
        } else
            return null;

        int size = lexList.size();
        LexNode lexem;
        for (int i = 1; i < size; i++) {
            lexem = lexList.get(i);
            lex = lexem.getLexes();
            if (lex.length == 1 && lex[0].equals("HLT")) {

                instsTable[instPointer] = "0" + instructions.get("HLT") + "000000";
                break;

            } else if (lex.length == 4 && lex[0].equals("ADD")) { // BURASI ÇOK SAÇMA, SOR !!!
                if (registers.containsKey(lex[1]) && registers.containsKey(lex[2]) && registers.containsKey(lex[3]))
                    instsTable[instPointer] = "0" + instructions.get("ADD") + registers.get(lex[1])
                            + registers.get(lex[2]) + registers.get(lex[3]);
                else
                    return null;

            } else if (lex.length == 3 && lex[0].equals("INC")) {
                if (registers.containsKey(lex[1]) && registers.containsKey(lex[2]))
                    instsTable[instPointer] = "0" + instructions.get("INC") + registers.get(lex[1])
                            + registers.get(lex[2]) + "00";
                else
                    return null;
            } else if (lex.length == 3 && lex[0].equals("DBL")) {
                if (registers.containsKey(lex[1]) && registers.containsKey(lex[2]))
                    instsTable[instPointer] = "0" + instructions.get("DBL") + registers.get(lex[1])
                            + registers.get(lex[2]) + "00";
                else
                    return null;
            } else if (lex.length == 3 && lex[0].equals("DBT")) {
                if (registers.containsKey(lex[1]) && registers.containsKey(lex[2]))
                    instsTable[instPointer] = "0" + instructions.get("DBT") + registers.get(lex[1])
                            + registers.get(lex[2]) + "00";
                else
                    return null;
            } else if (lex.length == 3 && lex[0].equals("NOT")) {
                if (registers.containsKey(lex[1]) && registers.containsKey(lex[2]))
                    instsTable[instPointer] = "0" + instructions.get("NOT") + registers.get(lex[1])
                            + registers.get(lex[2]) + "00";
                else
                    return null;
            } else if (lex.length == 4 && lex[0].equals("AND")) {
                if (registers.containsKey(lex[1]) && registers.containsKey(lex[2]) && registers.containsKey(lex[3]))
                    instsTable[instPointer] = "0" + instructions.get("AND") + registers.get(lex[1])
                            + registers.get(lex[2]) + registers.get(lex[3]);
                else
                    return null;
            } else if (lex.length == 3 && lex[0].equals("LD")) {
                if (registers.containsKey(lex[1])) { // register varmı yokmu diye kontrol

                    if (lex[2].charAt(0) == '@') {
                        if (labelTable.containsKey(lex[2].substring(1))) {
                            instsTable[instPointer] = "0" + instructions.get("LD") + registers.get(lex[1]) +
                                    labelTable.get(lex[2].substring(1));
                        } else
                            return null;
                    } else if (lex[2].charAt(0) == '#') {
                        int number = stringToInt(lex[2].substring(1));
                        if (number < 0 || number > 32)                   // BURASI ÇOK SAÇMA, SAYI NEGATİF OLABİLİR, ANKARA AYAĞINI DENK ALSIN !!!
                            return null;

                        String tmp = toBinary(number, 4);
                        instsTable[instPointer] = "1" + instructions.get("LD") + registers.get(lex[1]) + tmp;
                    }
                }

            } else if (lex.length == 3 && lex[0].equals("ST")) {
                if (registers.containsKey(lex[1])) { // register varmı yokmu diye kontrol

                    if (lex[2].charAt(0) == '@') {
                        if (labelTable.containsKey(lex[2].substring(1))) {
                            instsTable[instPointer] = "0" + instructions.get("ST") + registers.get(lex[1]) +
                                    labelTable.get(lex[2].substring(1));
                        } else
                            return null;
                    } else if (lex[2].charAt(0) == '#') {
                        int number = stringToInt(lex[2].substring(1));
                        if (number < 0 || number > 32)                   // BURASI ÇOK SAÇMA, SAYI NEGATİF OLABİLİR, ANKARA AYAĞINI DENK ALSIN !!!
                            return null;

                        String tmp = toBinary(number, 4);
                        instsTable[instPointer] = "1" + instructions.get("ST") + registers.get(lex[1]) + tmp;  // UYDURMUŞLAR, DOKUZ EYLÜL KENDİNE DİKKAT ETSİN
                    }
                } else
                    return null;
            } else if (lex.length == 3 && lex[0].equals("TSF")) {
                if (registers.containsKey(lex[1]) && registers.containsKey(lex[2]))
                    instsTable[instPointer] = "0" + instructions.get("TSF") + registers.get(lex[1]) + registers.get(lex[2]) + "00";

                else if (lex[1].equals("OUTPR") && registers.containsKey(lex[2])) {
                    instsTable[instPointer] = "0" + instructions.get("TSF") + "11" + registers.get(lex[2]) + "00";

                } else if (registers.containsKey(lex[1]) && lex[2].equals("INPR")) {
                    instsTable[instPointer] = "0" + instructions.get("TSF") + registers.get(lex[1]) + "11" + "00";

                } else return null;

            } else if (lex.length == 2 && lex[0].equals("CAL")) {
                int number = stringToInt(lex[1]);
                if (number < 0 || number > 63)
                    return null;

                String tmp = toBinary(number, 5);
                instsTable[instPointer] = "0" + instructions.get("CALL") + "000" + tmp; // başdaki 1 tane ve ortadaki 3 tane dont care

            } else if (lex.length == 2 && lex[0].equals("RET")) {
                int number = stringToInt(lex[1]);
                if (number < 0 || number > 63)
                    return null;

                String tmp = toBinary(number, 5);
                instsTable[instPointer] = "0" + instructions.get("RET") + "0" + tmp; // başdaki 1 tane ve ortadaki 3 tane dont care

            } else if (lex.length == 2 && lex[0].equals("JMP")) {
                int number = stringToInt(lex[1]);
                if (number < 0 || number > 63)
                    return null;

                String tmp = toBinary(number, 5);
                instsTable[instPointer] = "0" + instructions.get("JMP") + "0" + tmp; // başdaki 1 tane ve ortadaki 3 tane dont care


            } else if (lex.length == 2 && lex[0].equals("JMR")) {
                int number = stringToInt(lex[1]);
                if (number < 0 || number > 63)
                    return null;

                String tmp = toBinary(number, 4);
                instsTable[instPointer] = "0" + instructions.get("JMR") + "00" + tmp; // başdaki 1 tane ve ortadaki 3 tane dont care


            } else if (lex.length == 2 && lex[0].equals("PSH")) {
                int number = stringToInt(lex[1]);
                if (number < 0 || number > 63)
                    return null;

                String tmp = toBinary(number, 5);
                instsTable[instPointer] = "0" + instructions.get("PSH") + "0" + tmp; // başdaki 1 tane ve ortadaki 3 tane dont care


            } else if (lex.length == 2 && lex[0].equals("POP")) {
                int number = stringToInt(lex[1]);
                if (number < 0 || number > 63)
                    return null;

                String tmp = toBinary(number, 5);
                instsTable[instPointer] = "0" + instructions.get("POP") + "0" + tmp; // başdaki 1 tane ve ortadaki 3 tane dont care

            } else {
                return null;
            }

            instPointer += 1;
        }
        return tableMerge();
    }

    private String[] tableMerge() {
        String[] tmp = new String[64];
        for (int i = 0; i < 32; i++) {
            tmp[i] = instsTable[i];
        }
        for (int i = 32; i < 48; i++) {
            tmp[i] = dataTable[i - 32];
        }
        for (int i = 48; i < 64; i++) {
            tmp[i] = labelTableKeys[i - 48] + "    " + labelAdress[i - 48] + "    " + (labelAdress[i - 48] != 0? dataTable[labelAdress[i - 48]]:null) + "\n"; // GEÇİCİ AT KAFASI ÇÖZÜMÜ
        }

        return tmp;
    }

    private boolean dataController(LinkedList<LexNode> lexList) {
        LexNode lexem;
        String[] lex;
        int size = lexList.size();
        int i;
        int dataPointer = 0;
        // ORG D yi bulasıya kadar devam edilecek
        for (i = 0; i < size; i++) {
            lexem = lexList.get(i);
            lex = lexem.getLexes();
            if (lex.length == 3 && lex[0].equals("ORG") && lex[1].equals("D")) {
                dataPointer = stringToInt(lex[2]);
                if (dataPointer < 0 || dataPointer > 15) {
                    return true; // dataPointer yanlis yeri gosteriyorsa, hata vardir
                }
                break;
            }
        }

        if (i == size - 1) // son elemana geldiyse
            return true;

        i += 1;// bir sonraki label'ı almak için

        String binary;
        for (int j = i; j < size; j++) {
            lexem = lexList.get(j);
            lex = lexem.getLexes();
            if (lex.length == 3 && lex[0].charAt(lex[0].length() - 1) == ':') {

                if (lex[1].equals("DEC")) {
                    int number = stringToInt(lex[2]);
                    if (number < 0 || number > 31) {                        /* buraya İYİCE BAK, HATA OLABİLİR!!! NEGATİF OLACAKMI Bİ ÖĞREN*/
                        return true;
                    }

                    /*
                     * number'ı 4 bitlik formata çevirdik.
                     */
                    String tmp;
                    tmp = toBinary(number, 4);

                    /*
                     * number'ı dataTable'a yerleştirdik
                     */
                    dataTable[dataPointer] = tmp;
                    /*
                     * label'ın :'ya kadar olan kısmı key, dataPointer ise adresimiz.
                     */
                    tmp = toBinary(dataPointer, 4);
                    labelTable.put(lex[0].substring(0, lex[0].length() - 1), tmp); // labelTable doldu

                    labelTableKeys[labelPointer] = lex[0].substring(0, lex[0].length() - 1);
                    labelAdress[labelPointer] = dataPointer;
                    labelPointer++;
                    dataPointer++;

                } else if (lex[1].equals("HEX")) {
                    int number = hexToInt(lex[2]);
                    if (number < 0 || number > 31) {                        /* buraya İYİCE BAK, HATA OLABİLİR!!! NEGATİF OLACAKMI Bİ ÖĞREN*/
                        return true;
                    }
                    String tmp = toBinary(number, 4);
                    dataTable[dataPointer] = tmp;
                    tmp = toBinary(dataPointer, 4);
                    labelTable.put(lex[0].substring(0, lex[0].length() - 1), tmp); // labelTable doldu
                    labelTableKeys[labelPointer] = lex[0].substring(0, lex[0].length() - 1);
                    labelAdress[labelPointer] = dataPointer;
                    labelPointer++;
                    dataPointer++;

                } else {
                    return true;
                }
            } else if (lex.length == 1 && lex[0].equals("END")) {
                break;
            } else {
                return true;
            }
        }

        return false;
    }

    private int stringToInt(String str) {
        int n = -999;
        try {
            n = Integer.parseInt(str);
        } catch (Exception e) {
        }
        return n;
    }

    private int hexToInt(String str) {
        int n = -999;
        try {
            n = Integer.parseInt(str, 16);
        } catch (Exception e) {
        }

        return n;
    }

    private String toBinary(int number, int len) {
        String binary = Integer.toBinaryString(number);
        StringBuilder tmp = new StringBuilder("0");
        for (int i = 1; i < len; i++) {
            tmp.append("0");
        }
        tmp = new StringBuilder(tmp.substring(0, len - binary.length()) + binary);

        return tmp.toString();
    }
}
