package Computer.CPU;

import Computer.Computer;
import Computer.Memory.Data;
import Computer.Memory.Instruction;
import Computer.Memory.Stack;

/**
 * Created by asuss on 19.05.2017.
 */
public class ControlUnit {
    int D ;// Determine what next instruction is.(Is it ALU operation or other ones?)
    char Q; // Demonstrate where data come from(Q=1,Q=0)
    int S1;//Source address(register)
    int S2;//Source address(register)
    int Dest;//Destination address(register)
    int T;//Sequence Counter
    public void fetch(Register IR, ProgramCounter PC, Instruction IM){

        if(T==0)
        {
            IR.setData(IM.getData()[PC.getData()]);
        }
        else if (T==1)
        {
            PC.setData(PC.getData()+1);
        }
        T++;

    }
    public void decode(Register IR){

        Q=IR.data.charAt(0);
        D=Integer.parseInt(IR.data.substring(1,5),2);//OPCODE
        Dest=Integer.parseInt(IR.data.substring(5,7),2);
        S1=Integer.parseInt(IR.data.substring(7,9),2);
        S2=Integer.parseInt(IR.data.substring(9,11),2);
        T++;
    }

    public void execute(Data DM, Register AR, Stack SM,StackPointer SP,ProgramCounter PC, Register IR){
        //Stackpointer arttırılacak!!!!!!!!!!!!!!!,,,Sequence Counter sıfırlanacak....!!!!!!!!!!!!!!!!!!!
        if(D==0){
            //call alu.add function
        }
    }
    public void step(Data DM, Register AR, Stack SM,StackPointer SP,ProgramCounter PC, Register IR,Instruction IM,Register R0,Register R1,Register R2,Register inpr,Register outr){
        if(T==0 || T==1){
            fetch(IR,PC,IM);
        }
        else if(T==2){
            decode(IR);
        }
        else if(T>2 && T<16){
            execute(DM,AR,SM,SP,PC,IR);
            T=0;

        }
    }
    public void load(Register AR,Data DM){

    }
    public void store(){

    }public void transfer(){

    }
    public void call(){

    }
    public void  RET(){

    }
    public void jump(){

    }
    public void jumpRelative(){

    }
    public void push(){

    }
    public void pop(){

    }

}
