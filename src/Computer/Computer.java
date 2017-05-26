package Computer;

import Computer.CPU.CPU;
import Computer.Memory.Data;
import Computer.Memory.Instruction;
import Computer.Memory.Stack;

/**
 * Created by asuss on 19.05.2017.
 */
public class Computer {
    public Instruction instMem;
    public Stack stackMem;
    public Data dataMem;
    public CPU cpu;

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

    public String stepRun(){//runs clock by clock
        return cpu.run(dataMem,instMem,stackMem);
    }

    public void instructionRun(){//runs instruction by instruction

    }

}
