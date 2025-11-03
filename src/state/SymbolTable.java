package state;

import model.type.Type;
import model.value.Value;

public interface SymbolTable {
    boolean isDefined(String variableName);

    void declareVariable(Type type, String variableName);

    Type getVariableType(String variableName);

    void updateValue(String variableName, Value value);

    Value getValue(String variableName);
}
