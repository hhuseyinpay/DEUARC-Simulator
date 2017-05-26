package Computer.CPU;

import Computer.Computer;

/**
 * Created by asuss on 19.05.2017.
 */
public class StackPointer extends Register {

    public StackPointer(int size) {
        super(size);
        data="0000";
    }

    public void increment(){//???
        int data_int=Integer.parseInt(data,2);
        data_int++;
        data=Integer.toBinaryString(data_int);
    }
    public void decrement(){//???

    }

}
