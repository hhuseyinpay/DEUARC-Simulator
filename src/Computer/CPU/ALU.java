package Computer.CPU;

import Computer.Computer;

/**
 * Created by asuss on 19.05.2017.
 */
public class ALU {

    public void add(Register dest,Register src1,Register src2) {
        int tmp=toDecimal(src1.data) + toDecimal(src2.data);

        dest.data=toBinary(tmp,4);

    }

    public void inc(Register dest,Register src1) {
        dest.data=toBinary(toDecimal(src1.data)+1,4);
    }
    public void dbl(Register dest,Register src1) {
        int tmp=toDecimal(src1.data) + toDecimal(src1.data);

        dest.data=toBinary(tmp,4);
    }
    public void dbt(Register dest,Register src1) {
        dest.data="0"+src1.data.substring(0,src1.data.length()-1);
    }
    public void not(Register dest,Register src1) {

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

    }
    public void and(Register dest,Register src1,Register src2) {
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

    }


    public int toDecimal(String str){
        return Integer.parseInt(str,2);
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
