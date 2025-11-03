package model.type;

import model.value.BooleanValue;
import model.value.IntegerValue;
import model.value.Value;

public enum Type {
    INTEGER,
    BOOLEAN;

    public Value getDefaultValue() {
        return switch (this) {
            case INTEGER -> new IntegerValue(0);
            case BOOLEAN -> new BooleanValue(false);
        };
    }
}
