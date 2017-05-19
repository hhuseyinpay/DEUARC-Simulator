package Computer.CPU;

import Computer.Memory.Data;
import Computer.Memory.Instruction;
import Computer.Memory.Stack;

/**
 * Created by asuss on 19.05.2017.
 */
public class CPU {
    ALU alu;
    ControlUnit controlUnit;
    StackPointer SP;
    ProgramCounter PC;
    Register R0;
    Register R1;
    Register R2;
    Register AR;
    Register inpr;
    Register outr;
    Register IR;

    public CPU() {
        alu=new ALU();
        controlUnit=new ControlUnit();
        SP =new StackPointer(4);
        PC=new ProgramCounter(5);
        R0 = new Register(4);
        R1 = new Register(4);
        R2 = new Register(4);
        AR = new Register(4);
        inpr = new Register(4);
        outr = new Register(4);
        IR = new Register(11);

    }

    public void run(Data DM, Instruction IM, Stack SM){
        controlUnit.step(DM,AR,SM,SP,PC,IR,IM,R0,R1,R2,inpr,outr);


    }



}
