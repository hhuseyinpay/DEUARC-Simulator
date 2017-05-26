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

    public void fetch(Register IR, ProgramCounter PC, Instruction IM) {

        if (T == 0) {
            IR.setData(IM.getData()[PC.getData()]);
        }
        else if (T == 1) {
            PC.setData(PC.getData() + 1);
        }
        T++;

    }

    public String decode(Register IR) {

        Q = IR.data.charAt(0);
        D = Integer.parseInt(IR.data.substring(1, 5), 2);//OPCODE
        Dest = Integer.parseInt(IR.data.substring(5, 7), 2);
        S1 = Integer.parseInt(IR.data.substring(7, 9), 2);
        S2 = Integer.parseInt(IR.data.substring(9, 11), 2);
        T++;
        String temp = "Opcode : " + D + '\n' + "Destination : " + Dest + '\n' + "S2 : " + S2 + '\n' + "S1 : " + S1 + '\n';//GEÇİCİ AT KAFASI
        return temp;
    }

    public void execute(Data DM, Register AR, Stack SM, StackPointer SP, ProgramCounter PC, Register IR, Register R0, Register R1, Register R2,ALU alu) {
        //Stackpointer arttırılacak!!!!!!!!!!!!!!!,,,Sequence Counter sıfırlanacak....!!!!!!!!!!!!!!!!!!!
        Register src1,src2,dest;



        if (D == 0) {//ADD
            src1=chooseRegister(S1,R0,R1,R2);
            src2=chooseRegister(S2,R0,R1,R2);
            dest=chooseRegister(Dest,R0,R1,R2);

            alu.add(dest,src1,src2);
            T=0;

        }
        else if (D == 1) {//INC
            src1=chooseRegister(S1,R0,R1,R2);
            dest=chooseRegister(Dest,R0,R1,R2);

            alu.inc(dest,src1);
            T=0;
        }
        else if (D == 2) {//DBL
            src1=chooseRegister(S1,R0,R1,R2);
            dest=chooseRegister(Dest,R0,R1,R2);

            alu.dbl(dest,src1);
            T=0;
        }
        else if (D == 3) {//DBT
            src1=chooseRegister(S1,R0,R1,R2);
            dest=chooseRegister(Dest,R0,R1,R2);

            alu.dbt(dest,src1);
            T=0;
        }
        else if (D == 4) {//NOT
            src1=chooseRegister(S1,R0,R1,R2);
            dest=chooseRegister(Dest,R0,R1,R2);

            alu.not(dest,src1);
            T=0;

        }
        else if (D == 5) {//AND
            src1=chooseRegister(S1,R0,R1,R2);
            src2=chooseRegister(S2,R0,R1,R2);
            dest=chooseRegister(Dest,R0,R1,R2);

            alu.and(dest,src1,src2);
            T=0;

        }
        else if (D == 6) {//LD
            load(AR, DM, R0, R1, R2);
        }
        else if (D == 7) {//ST
            store(AR, DM, R0, R1, R2);
        }
        else if (D == 8) {//HLT

        }
        else if (D == 9) {//TSF
            transfer(R0, R1, R2);
        }
        else if (D == 10) {//CAL
            call(SM, SP, PC);
        }
        else if (D == 11) {//RET
            RET(SM,SP,PC);
        }
        else if (D == 12) {//JMP
            jump(PC);
        }
        else if (D == 13) {//JMR
            jumpRelative(PC);
        }
        else if (D == 14) {//PSH
            push(AR,SM,SP,DM);
        }
        else if (D == 15) {//POP
            pop(AR,SM,SP,DM);
        }


    }

    public String step(Data DM, Register AR, Stack SM, StackPointer SP, ProgramCounter PC, Register IR, Instruction IM, Register R0, Register R1, Register R2, Register inpr, Register outr,ALU alu) {
        if (T == 0 || T == 1) {
            fetch(IR, PC, IM);
        }
        else if (T == 2) {
            return decode(IR);//GEÇİCİ ATTTTTTTTTTTTTTTT

        }
        else if (T > 2 && T < 16) {
            execute(DM, AR, SM, SP, PC, IR, R0, R1, R2,alu);
            //T=0;

        }
        return null;
    }

    public void load(Register AR, Data DM, Register R0, Register R1, Register R2) {
        Register temp;
        if (Q == '0') {
            if (T == 3) {
                AR.data = toBinary(4);
                T++;
            }
            else if (T == 4) {
                //chooseRegister(R0,R1,R2,DM,DM.getData()[Integer.parseInt(AR.data,2)],0);
                temp = chooseRegister(Dest, R0, R1, R2);
                temp.data = DM.getData()[Integer.parseInt(AR.data, 2)];
                T = 0;
            }
        }
        else if (Q == '1') {
            //chooseRegister(R0,R1,R2,DM,toBinary(S1,S2),0);
            temp = chooseRegister(Dest, R0, R1, R2);
            temp.data = toBinary(4);
            T = 0;
        }

    }

    public void store(Register AR, Data DM, Register R0, Register R1, Register R2) {
        Register dest_reg, src_reg;
        if (Q == '0') {
            if (T == 3) {
                AR.data = toBinary(4);
                T++;
            }
            else if (T == 4) {
                //chooseRegister(R0,R1,R2,DM,AR.data,1);
                dest_reg = chooseRegister(Dest, R0, R1, R2);
                DM.getData()[Integer.parseInt(AR.data, 2)] = dest_reg.data;
                T = 0;
            }
        }
        else if (Q == '1') {
            dest_reg = chooseRegister(S2, R0, R1, R2);
            src_reg = chooseRegister(Dest, R0, R1, R2);

            dest_reg.data = src_reg.data;
            T = 0;
        }
    }

    public void transfer(Register R0, Register R1, Register R2) {
        Register dest_reg, src_reg;

        dest_reg = chooseRegister(Dest, R0, R1, R2);
        src_reg = chooseRegister(S1, R0, R1, R2);

        dest_reg.data = src_reg.data;

        T = 0;
    }

    public void call(Stack SM, StackPointer SP, ProgramCounter PC) {
        if (T == 3) {
            SM.getData()[Integer.parseInt(SP.data, 2)] = Integer.toBinaryString(PC.getData());
            T++;
        }
        else if (T == 4) {
            PC.setData(Integer.parseInt(toBinary(5), 2));
            SP.increment();
            T = 0;
        }

    }

    public void RET(Stack SM, StackPointer SP, ProgramCounter PC) {
        if (T == 3) {
            SP.decrement();
            T++;
        }
        else if (T == 4) {
            PC.setData(SM.getData()[Integer.parseInt(SP.data)]);
            T = 0;
        }

    }


    public void jump(ProgramCounter PC) {
        PC.setData(Integer.parseInt(toBinary(5), 2));
        T = 0;
    }

    public void jumpRelative(ProgramCounter PC) {
        PC.setData(PC.getData() + Integer.parseInt(toBinary(4), 2));
        T = 0;
    }

    public void push(Register AR,Stack SM,StackPointer SP,Data DM) {
        if (T == 3) {
            AR.setData(toBinary(4));
            T++;
        }
        else if (T == 4) {
            SM.getData()[Integer.parseInt(SP.data)]=DM.getData()[Integer.parseInt(AR.data)];
            T++;
        }
        else if (T == 5) {
            SP.increment();
            T=0;
        }

    }

    public void pop(Register AR,Stack SM,StackPointer SP,Data DM) {
        if (T == 3) {
            AR.setData(toBinary(4));
            T++;
        }
        else if (T == 4) {
            SP.decrement();
            T++;
        }
        else if (T == 5) {
            DM.getData()[Integer.parseInt(AR.data)]=SM.getData()[Integer.parseInt(SP.data)];
            T=0;
        }
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


    public Register chooseRegister(int parameter, Register R0, Register R1, Register R2) {
        if (parameter == 0) {
            return R0;
        }
        else if (parameter == 1) {
            return R1;
        }
        else if (parameter == 2) {
            return R2;
        }

        return null;

    }

}