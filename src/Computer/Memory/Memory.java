package Computer.Memory;

import Computer.Computer;

/**
 * Created by asuss on 19.05.2017.
 */
public class Memory {

    String[] data;
    boolean readEnable;//???

    public Memory(int size) {
        data = new String[size];

    }

    public String[] getData() {
        return data;
    }

    public void setData(String[] data) {
        this.data = data;
    }
}

