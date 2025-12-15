package model.expression;

import model.exception.InvalidTypeException;
import model.exception.RuntimeInterpreterException;
import model.exception.TypeCheckException;
import model.type.ReferenceType;
import model.type.Type;
import model.value.ReferenceValue;
import model.value.Value;
import state.SymbolTable;
import state.Heap;
import model.adt.Dictionary;

public record HeapReadExp(Expression expression) implements Expression {

    @Override
    public Value evaluate(SymbolTable symbolTable) {
        throw new RuntimeInterpreterException("HeapReadExp requires heap context. Use evaluate(SymbolTable, Heap) instead.");
    }

    @Override
    public Value evaluate(SymbolTable symbolTable, Heap heap) throws InvalidTypeException, RuntimeInterpreterException {
        Value evaluatedValue = expression.evaluate(symbolTable, heap);

        if (!(evaluatedValue instanceof ReferenceValue refValue)) {
            throw new InvalidTypeException("HeapReadExp: expression must evaluate to a reference value, got " + evaluatedValue.getType());
        }

        int address = refValue.getAddr();

        if (!heap.contains(address)) {
            throw new RuntimeInterpreterException("HeapReadExp: address " + address + " is not in the heap");
        }

        return heap.get(address);
    }

    @Override
    public Expression deepCopy() {
        return new HeapReadExp(expression.deepCopy());
    }

    @Override
    public Type typecheck(Dictionary<String, Type> typeEnv) throws TypeCheckException {
        Type t = expression.typecheck(typeEnv);
        if (!(t instanceof ReferenceType ref))
            throw new TypeCheckException("the rH argument is not a Ref Type");
        return ref.getInner();
    }
}
