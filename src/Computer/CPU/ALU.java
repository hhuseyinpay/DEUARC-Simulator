package Computer.CPU;

import Computer.Computer;
import com.sun.org.apache.regexp.internal.RE;

/**
 * Created by asuss on 19.05.2017.
 */
public class ALU {

    public String  add(Register dest,Register src1,Register src2,Register OVERFLOW) {
        int tmp=toDecimal(src1.data) + toDecimal(src2.data);

        dest.data=toBinary(tmp,4,OVERFLOW);

        return "T3 : D <- S1+S2, SC <- 0";

    }

    public String  inc(Register dest,Register src1,Register OVERFLOW) {
        dest.data=toBinary(toDecimal(src1.data)+1,4,OVERFLOW);
        return "T3 : D <- S1 + 1, SC <- 0";
    }

    public String dbl(Register dest,Register src1,Register OVERFLOW) {
        int tmp=toDecimal(src1.data) + toDecimal(src1.data);

        dest.data=toBinary(tmp,4,OVERFLOW);
        return "T3 : D <- S1+S1, SC <- 0";
    }

    public String dbt(Register dest,Register src1) {
        dest.data="0"+src1.data.substring(0,src1.data.length()-1);
        return "T3 : D <- S1>>, SC <- 0";
    }

    public String not(Register dest,Register src1) {

        String tmp="";

        for (int i = 0; i < 4; i++) {
            if(src1.data.charAt(i)=='0'){
                tmp+="1";
            }
            else
            {
                tmp+="0";
            }
        }

        dest.data=tmp;
        return "D <- S1', SC <- 0";

    }
    public String and(Register dest,Register src1,Register src2) {
        String tmp="";

        for (int i = 0; i < 4 ; i++) {
            if(src1.data.charAt(i)!=src2.data.charAt(i)){
                tmp+="0";
            }
            else
            {
                tmp+="1";
            }
        }

        dest.data=tmp;

        return "T3 : D <- S1^S2, SC <- 0";

    }


    public int toDecimal(String str){
        return Integer.parseInt(str,2);
    }

    private String toBinary(int number, int len,Register  OVERFLOW) {
        String binary = Integer.toBinaryString(number);
        StringBuilder tmp = new StringBuilder("0");
        for (int i = 1; i < len; i++) {
            tmp.append("0");
        }
        if(binary.length()>4){
            tmp = new StringBuilder(binary.substring(1,binary.length()));
            OVERFLOW.setData("true");
        }
        else {
            tmp = new StringBuilder(tmp.substring(0, len - binary.length()) + binary);
        }


        return tmp.toString();
    }
}
