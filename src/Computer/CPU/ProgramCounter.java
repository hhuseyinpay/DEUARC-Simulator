package Computer.CPU;

import Computer.Computer;

/**
 * Created by asuss on 19.05.2017.
 */
public class ProgramCounter extends Register {

    public ProgramCounter(int size) {
        super(size);
        data="0";
    }

    public int getData() {
        return Integer.parseInt(data, 2);
    }

    public void setData(int pcData) {
        this.data = Integer.toBinaryString(pcData);
    }

}
