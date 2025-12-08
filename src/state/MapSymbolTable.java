package state;

import model.type.Type;
import model.statement.Statement;
import model.value.Value;

import java.util.HashMap;
import java.util.Map;

public class MapSymbolTable implements SymbolTable {
    private final Map<String, Value> symbolTable;

    public MapSymbolTable() {
        this.symbolTable = new HashMap<>();
    }

    public MapSymbolTable(Map<String, Value> initial) {
        this.symbolTable = new HashMap<>(initial);
    }

    @Override
    public boolean isDefined(String variableName) {
        return symbolTable.containsKey(variableName);
    }

    @Override
    public void declareVariable(Type type, String variableName) {
        symbolTable.put(variableName, type.getDefaultValue());
    }

    @Override
    public Type getVariableType(String variableName) {
        return symbolTable.get(variableName).getType();
    }

    @Override
    public void updateValue(String variableName, Value value) {
        symbolTable.put(variableName, value);
    }

    @Override
    public Value getValue(String variableName) {
        return symbolTable.get(variableName);
    }

    @Override
    public Map<String, Value> getContent() {
        return new HashMap<>(symbolTable);
    }

    @Override
    public SymbolTable deepCopy() {
        return new MapSymbolTable(this.getContent());
    }

    @Override
    public String toString() {
        return "SymbolTable: " + symbolTable.toString();
    }
}
