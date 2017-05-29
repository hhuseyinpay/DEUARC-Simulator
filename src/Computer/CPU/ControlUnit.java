package Computer.CPU;

import Computer.Computer;
import Computer.Memory.Data;
import Computer.Memory.Instruction;
import Computer.Memory.Stack;
import com.sun.org.apache.regexp.internal.RE;

/**
 * Created by asuss on 19.05.2017.
 */
public class ControlUnit {
    private int D;// Determine what next instruction is.(Is it ALU operation or other ones?)
    private char Q; // Demonstrate where data come from(Q=1,Q=0)
    private int S1;//Source address(register)
    private int S2;//Source address(register)
    private int Dest;//Destination address(register)

    public int T;//Sequence Counter

    public String  fetch(Register IR, ProgramCounter PC, Instruction IM) {

        if (T == 0) {
            IR.setData(IM.getData()[PC.getData()]);
            T++;
            return "T0 : IR <- IM[PC]";
        }
        else if (T == 1) {
            PC.setData(PC.getData() + 1);
            T++;
            return "T1 : PC <- PC + 1";
        }

        return null;

    }

    public String decode(Register IR) {

        Q = IR.data.charAt(0);
        D = Integer.parseInt(IR.data.substring(1, 5), 2);//OPCODE
        Dest = Integer.parseInt(IR.data.substring(5, 7), 2);
        S1 = Integer.parseInt(IR.data.substring(7, 9), 2);
        S2 = Integer.parseInt(IR.data.substring(9, 11), 2);
        T++;
        return "T2 : D0..D15 <- IR[9..6], Q <- IR[10], S2 <- IR[1..0], S1 <- IR[3..2], D <- IR[5..4]";
    }

    private String toBinarys(int number, int len) {
        String binary = Integer.toBinaryString(number);
        StringBuilder tmp = new StringBuilder("0");
        for (int i = 1; i < len; i++) {
            tmp.append("0");
        }
        if(binary.length()>4){
            tmp = new StringBuilder(binary.substring(1,binary.length()));
        }
        else {
            tmp = new StringBuilder(tmp.substring(0, len - binary.length()) + binary);
        }


        return tmp.toString();
    }

    public String execute(Data DM, Register AR, Stack SM, StackPointer SP, ProgramCounter PC, Register IR, Register R0, Register R1, Register R2,Register inpr,Register outr,ALU alu,Register OVERFLOW,int input) {
        //Stackpointer arttırılacak!!!!!!!!!!!!!!!,,,Sequence Counter sıfırlanacak....!!!!!!!!!!!!!!!!!!!
        Register src1,src2,dest;

        inpr.setData(toBinarys(input,4));

        if (D == 0) {//ADD
            src1=chooseRegister(S1,R0,R1,R2,inpr);
            src2=chooseRegister(S2,R0,R1,R2,inpr);
            dest=chooseRegister(Dest,R0,R1,R2,inpr);
            T=0;
            return alu.add(dest,src1,src2,OVERFLOW);


        }
        else if (D == 1) {//INC
            src1=chooseRegister(S1,R0,R1,R2,inpr);
            dest=chooseRegister(Dest,R0,R1,R2,inpr);


            T=0;
            return alu.inc(dest,src1,OVERFLOW);
        }
        else if (D == 2) {//DBL
            src1=chooseRegister(S1,R0,R1,R2,inpr);
            dest=chooseRegister(Dest,R0,R1,R2,inpr);
            T=0;
            return alu.dbl(dest,src1,OVERFLOW);


        }
        else if (D == 3) {//DBT
            src1=chooseRegister(S1,R0,R1,R2,inpr);
            dest=chooseRegister(Dest,R0,R1,R2,inpr);
            T=0;
            return alu.dbt(dest,src1);

        }
        else if (D == 4) {//NOT
            src1=chooseRegister(S1,R0,R1,R2,inpr);
            dest=chooseRegister(Dest,R0,R1,R2,inpr);
            T=0;
            return alu.not(dest,src1);


        }
        else if (D == 5) {//AND
            src1=chooseRegister(S1,R0,R1,R2,inpr);
            src2=chooseRegister(S2,R0,R1,R2,inpr);
            dest=chooseRegister(Dest,R0,R1,R2,inpr);
            T=0;
            return alu.and(dest,src1,src2);


        }
        else if (D == 6) {//LD
            return load(AR, DM, R0, R1, R2,inpr,outr);
        }
        else if (D == 7) {//ST
            return store(AR, DM, R0, R1, R2,inpr,outr);
        }
        else if (D == 8) {//HLT
            return hlt();
        }
        else if (D == 9) {//TSF
            return transfer(R0, R1, R2,inpr,outr);
        }
        else if (D == 10) {//CAL
            return call(SM, SP, PC);
        }
        else if (D == 11) {//RET
            return RET(SM,SP,PC);
        }
        else if (D == 12) {//JMP
            return jump(PC);
        }
        else if (D == 13) {//JMR
            return jumpRelative(PC);
        }
        else if (D == 14) {//PSH
            return push(AR,SM,SP,DM);
        }
        else if (D == 15) {//POP
            return pop(AR,SM,SP,DM);
        }

        return null;
    }

    public String step(Data DM, Register AR, Stack SM, StackPointer SP, ProgramCounter PC, Register IR, Instruction IM, Register R0, Register R1, Register R2, Register inpr, Register outr,ALU alu,Register OVERFLOW,int input) {
        if (T == 0 || T == 1) {
            return fetch(IR, PC, IM);
        }
        else if (T == 2) {
            return decode(IR);//GEÇİCİ ATTTTTTTTTTTTTTTT

        }
        else if (T > 2 && T < 16) {
            return execute(DM, AR, SM, SP, PC, IR, R0, R1, R2,inpr,outr,alu,OVERFLOW,input);
            //T=0;

        }

        return null;
    }

    public String hlt(){
        return "ENDofPROGRAM";
    }

    public String load(Register AR, Data DM, Register R0, Register R1, Register R2,Register inpr,Register outr) {
        Register temp;
        if (Q == '0') {
            if (T == 3) {
                AR.data = toBinary(4);
                T++;
                return "T3 : AR <- S1S2";
            }
            else if (T == 4) {
                //chooseRegister(R0,R1,R2,DM,DM.getData()[Integer.parseInt(AR.data,2)],0);
                temp = chooseRegister(Dest, R0, R1, R2,inpr);
                temp.data = DM.getData()[Integer.parseInt(AR.data, 2)];
                T = 0;
                return "T4 : D <- DM[AR], SC <- 0";
            }

        }
        else if (Q == '1') {
            //chooseRegister(R0,R1,R2,DM,toBinary(S1,S2),0);
            temp = chooseRegister(Dest, R0, R1, R2,inpr);
            temp.data = toBinary(4);
            T = 0;
            return "T3 : D <- S1S2, SC <- 0";
        }

        return null;

    }

    public String store(Register AR, Data DM, Register R0, Register R1, Register R2,Register inpr,Register outr) {
        Register dest_reg, src_reg;
        if (Q == '0') {
            if (T == 3) {
                AR.data = toBinary(4);
                T++;
                return "T3 : AR <- S1S2";
            }
            else if (T == 4) {
                //chooseRegister(R0,R1,R2,DM,AR.data,1);
                dest_reg = chooseRegister(Dest, R0, R1, R2,inpr);
                DM.getData()[Integer.parseInt(AR.data, 2)] = dest_reg.data;
                T = 0;
                return "T4 : DM[AR] <- D, SC <- 0";
            }
        }
        else if (Q == '1') {
            dest_reg = chooseRegister(S2, R0, R1, R2,inpr);
            src_reg = chooseRegister(Dest, R0, R1, R2,inpr);

            dest_reg.data = src_reg.data;
            T = 0;
            return "T3 : S2 <- D, SC <- 0";
        }

        return null;
    }

    public String transfer(Register R0, Register R1, Register R2,Register inpr,Register outr) {
        Register dest_reg, src_reg;

        dest_reg = chooseRegister(Dest, R0, R1, R2,inpr);
        src_reg = chooseRegister(S1, R0, R1, R2,inpr);

        dest_reg.data = src_reg.data;

        T = 0;

        return "T3 : D <- S1, SC <- 0";
    }

    public String  call(Stack SM, StackPointer SP, ProgramCounter PC) {
        if (T == 3) {
            SM.getData()[Integer.parseInt(SP.data, 2)] = Integer.toBinaryString(PC.getData());
            T++;
            return "T3 : SM[SP] <- PC";
        }
        else if (T == 4) {
            PC.setData(Integer.parseInt(toBinary(5), 2));
            SP.increment();
            T = 0;
            return "T4 : PC <- IR[4..0], SP <- SP + 1, SC <- 0";
        }

        return null;

    }

    public String  RET(Stack SM, StackPointer SP, ProgramCounter PC) {
        if (T == 3) {
            SP.decrement();
            T++;
            return "T3 : SP <- SP -1";
        }
        else if (T == 4) {
            PC.setData(SM.getData()[Integer.parseInt(SP.data)]);
            T = 0;
            return "T4 : PC <- SM[SP], SC <- 0";
        }

        return null;

    }


    public String jump(ProgramCounter PC) {
        PC.setData(Integer.parseInt(toBinary(5), 2));
        T = 0;
        return "T3 : PC <- IR[4..0], SC <- 0";
    }

    public String jumpRelative(ProgramCounter PC) {
        PC.setData(PC.getData() + Integer.parseInt(toBinary(4), 2));
        T = 0;
        return "T4 : PC <- PC + IR[3..0], SC <- 0";
    }

    public String  push(Register AR,Stack SM,StackPointer SP,Data DM) {
        if (T == 3) {
            AR.setData(toBinary(4));
            T++;
            return "T3 : AR <- IR[3..0]";
        }
        else if (T == 4) {
            SM.getData()[Integer.parseInt(SP.data)]=DM.getData()[Integer.parseInt(AR.data)];
            T++;
            return "T4 : SM[SP] <- DM[AR]";
        }
        else if (T == 5) {
            SP.increment();
            T=0;
            return "T5 : SP <- SP + 1, SC <- 0";
        }

        return null;

    }

    public String  pop(Register AR,Stack SM,StackPointer SP,Data DM) {
        if (T == 3) {
            AR.setData(toBinary(4));
            T++;
            return "T3 : AR <- IR[3..0]";
        }
        else if (T == 4) {
            SP.decrement();
            T++;
            return "T4 : SP <- SP -1";
        }
        else if (T == 5) {
            DM.getData()[Integer.parseInt(AR.data)]=SM.getData()[Integer.parseInt(SP.data)];
            T=0;
            return "T5 : DM[AR] <- SM[SP], SC <- 0";
        }

        return null;
    }

    public String toBinary(int length) {
        String tmp1, tmp2, tmp3 = "";

        tmp1 = Integer.toBinaryString(S1);
        tmp2 = Integer.toBinaryString(S2);

        if (tmp1.equals("0") || tmp1.equals("1")) {
            tmp1 = "0" + tmp1;
        }

        if (tmp2.equals("0") || tmp2.equals("1")) {
            tmp2 = "0" + tmp2;
        }

        if (length == 5) {
            tmp3 = Integer.toBinaryString(Dest);
        }

        if (tmp3.length() == 2) {
            tmp3 = tmp3.substring(1, 2);
        }

        return tmp3 + tmp1 + tmp2;
    }


    public Register chooseRegister(int parameter, Register R0, Register R1, Register R2,Register inpr) {
        if (parameter == 0) {
            return R0;
        }
        else if (parameter == 1) {
            return R1;
        }
        else if (parameter == 2) {
            return R2;
        }
        else if(parameter == 3){
            return inpr;
        }

        return null;

    }

}