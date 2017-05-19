package Computer;

import Computer.CPU.CPU;
import Computer.Memory.Data;
import Computer.Memory.Instruction;
import Computer.Memory.Stack;

/**
 * Created by asuss on 19.05.2017.
 */
public class Computer {
    Instruction instMem;
    Stack stackMem;
    Data dataMem;
    CPU cpu;

    public Computer(String[] instMem,String[] datamem) {
        this.instMem = new Instruction(32);
        this.instMem.setData(instMem);

        this.dataMem = new Data(16);
        this.dataMem.setData(datamem);

        stackMem= new Stack(16);

        cpu=new CPU();

    }

    public void run(){//runs to the end

    }

    public void stepRun(){//runs clock by clock
        cpu.run(dataMem,instMem,stackMem);
    }

    public void instructionRun(){//runs instruction by instruction

    }

}
