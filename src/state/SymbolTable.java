package state;

import model.type.Type;
import model.value.Value;
import java.util.Map;

public interface SymbolTable {
    boolean isDefined(String variableName);

    void declareVariable(Type type, String variableName);

    Type getVariableType(String variableName);

    void updateValue(String variableName, Value value);

    Value getValue(String variableName);

    Map<String, Value> getContent();

    SymbolTable deepCopy();
}