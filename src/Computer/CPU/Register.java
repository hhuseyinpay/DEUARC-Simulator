package Computer.CPU;

import Computer.Computer;

/**
 * Created by asuss on 19.05.2017.
 */
public class Register {

    int size;
    public String data;
    boolean load;//???

    public Register(int size) {
        this.size=size;
    }

    public void setData(String data) {
        this.data = data;
    }
}
