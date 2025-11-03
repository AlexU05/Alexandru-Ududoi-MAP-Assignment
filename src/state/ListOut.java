package state;


import model.value.Value;

import java.util.ArrayList;
import java.util.List;

public class ListOut implements Out{

    private final List<Value> values =  new ArrayList<>();

    @Override
    public void append(Value value) {
        values.add(value);
    }
    @Override
    public String toString() {
        return "Out: " + values.toString();
    }
}
