//import controller.ControllerInterface;
//import controller.Controller;
//import model.expression.ArithExp;
//import model.expression.ValueExp;
//import model.expression.VarExp;
//import model.expression.RelationalExp;
//import model.expression.HeapReadExp;
//import model.statement.*;
//import model.type.SimpleType;
//import model.type.ReferenceType;
//import model.value.BooleanValue;
//import model.value.IntegerValue;
//import model.value.StringValue;
//import repository.RepositoryInterface;
//import repository.Repository;
//import state.*;
//import view.TextMenu;
////import view.ViewImp1;
//import view.command.ExitCommand;
//import view.command.RunExampleCommand;
//import model.exception.TypeCheckException;
//import model.type.Type;
//import model.adt.Dictionary;
//
//
//class Main {
//    public static void main(String[] args) {
//        Statement ex1 = new CompoundStatement(
//                new VariableDeclarationStatement(SimpleType.INTEGER, "v"),
//                new CompoundStatement(
//                        new AssignmentStatement("v", new ValueExp(new IntegerValue(2))),
//                        new PrintStatement(new VarExp("v"))
//                )
//        );
//        ControllerInterface ctr1;
//        Dictionary<String, Type> typeEnv1 = new Dictionary<>();
//        try{
//            ex1.typecheck(typeEnv1);
//            ExecutionStack exeStack1 = new ListExecutionStack();
//            exeStack1.push(ex1);
//            SymbolTable symTable1 = new MapSymbolTable();
//            Out out1 = new ListOut();
//            FileTable fileTable1 = new MapFileTable();
//            Heap heap1 = new HeapMemory();
//            ProgramState prg1 = new ProgramState(exeStack1, symTable1, out1, fileTable1, heap1);
//            RepositoryInterface repo1 = new Repository(prg1, "log1.txt");
//            ctr1 = new Controller(repo1);
//        } catch (TypeCheckException e){
//            String err = e.getMessage();
//            System.out.println("Typecheck error in ex1: "+err);
//            // create a dummy controller that reports the typecheck error
//            ctr1 = new ControllerInterface() {
//                @Override public void allStep() { System.out.println("Cannot run ex1: "+err); }
//                @Override public void displayCurrentState() {}
//                @Override public void setDisplayFlag(boolean flag) {}
//                @Override public boolean getDisplayFlag() { return false; }
//            };
//        }
//
//        Statement ex2 = new CompoundStatement(
//                new VariableDeclarationStatement(SimpleType.INTEGER, "a"),
//                new CompoundStatement(
//                        new VariableDeclarationStatement(SimpleType.INTEGER, "b"),
//                        new CompoundStatement(
//                                new AssignmentStatement("a", new ArithExp(
//                                        new ValueExp(new IntegerValue(2)),
//                                        new ArithExp(
//                                                new ValueExp(new IntegerValue(3)),
//                                                new ValueExp(new IntegerValue(5)),
//                                                "*"
//                                        ),
//                                        "+"
//                                )),
//                                new CompoundStatement(
//                                        new AssignmentStatement("b", new ArithExp(
//                                                new VarExp("a"),
//                                                new ValueExp(new IntegerValue(1)),
//                                                "+"
//                                        )),
//                                        new PrintStatement(new VarExp("b"))
//                                )
//                        )
//                )
//        );
//        ControllerInterface ctr2;
//        Dictionary<String, Type> typeEnv2 = new Dictionary<>();
//        try{
//            ex2.typecheck(typeEnv2);
//            ExecutionStack exeStack2 = new ListExecutionStack();
//            exeStack2.push(ex2);
//            SymbolTable symTable2 = new MapSymbolTable();
//            Out out2 = new ListOut();
//            FileTable fileTable2 = new MapFileTable();
//            Heap heap2 = new HeapMemory();
//            ProgramState prg2 = new ProgramState(exeStack2, symTable2, out2, fileTable2, heap2);
//            RepositoryInterface repo2 = new Repository(prg2, "log2.txt");
//            ctr2 = new Controller(repo2);
//        } catch (TypeCheckException e){
//            String err = e.getMessage();
//            System.out.println("Typecheck error in ex2: "+err);
//            ctr2 = new ControllerInterface() {
//                @Override public void allStep() { System.out.println("Cannot run ex2: "+err); }
//                @Override public void displayCurrentState() {}
//                @Override public void setDisplayFlag(boolean flag) {}
//                @Override public boolean getDisplayFlag() { return false; }
//            };
//        }
//
//        Statement ex3 = new CompoundStatement(
//                new VariableDeclarationStatement(SimpleType.BOOLEAN, "a"),
//                new CompoundStatement(
//                        new VariableDeclarationStatement(SimpleType.INTEGER, "v"),
//                        new CompoundStatement(
//                                new AssignmentStatement("a", new ValueExp(new BooleanValue(true))),
//                                new CompoundStatement(
//                                        new IfStatement(
//                                                new VarExp("a"),
//                                                new AssignmentStatement("v", new ValueExp(new IntegerValue(2))),
//                                                new AssignmentStatement("v", new ValueExp(new IntegerValue(3)))
//                                        ),
//                                        new PrintStatement(new VarExp("v"))
//                                )
//                        )
//                )
//        );
//        ControllerInterface ctr3;
//        Dictionary<String, Type> typeEnv3 = new Dictionary<>();
//        try{
//            ex3.typecheck(typeEnv3);
//            ExecutionStack exeStack3 = new ListExecutionStack();
//            exeStack3.push(ex3);
//            SymbolTable symTable3 = new MapSymbolTable();
//            Out out3 = new ListOut();
//            FileTable fileTable3 = new MapFileTable();
//            Heap heap3 = new HeapMemory();
//            ProgramState prg3 = new ProgramState(exeStack3, symTable3, out3, fileTable3, heap3);
//            RepositoryInterface repo3 = new Repository(prg3, "log3.txt");
//            ctr3 = new Controller(repo3);
//        } catch (TypeCheckException e){
//            String err = e.getMessage();
//            System.out.println("Typecheck error in ex3: "+err);
//            ctr3 = new ControllerInterface() {
//                @Override public void allStep() { System.out.println("Cannot run ex3: "+err); }
//                @Override public void displayCurrentState() {}
//                @Override public void setDisplayFlag(boolean flag) {}
//                @Override public boolean getDisplayFlag() { return false; }
//            };
//        }
//
//        Statement fileExample = new CompoundStatement(
//                new VariableDeclarationStatement(SimpleType.STRING, "varf"),
//                new CompoundStatement(
//                        new AssignmentStatement("varf", new ValueExp(new StringValue("test.in"))),
//                        new CompoundStatement(
//                                new OpenRFileStatement(new VarExp("varf")),
//                                new CompoundStatement(
//                                        new VariableDeclarationStatement(SimpleType.INTEGER, "varc"),
//                                        new CompoundStatement(
//                                                new ReadFileStatement(new VarExp("varf"), "varc"),
//                                                new CompoundStatement(
//                                                        new PrintStatement(new VarExp("varc")),
//                                                        new CompoundStatement(
//                                                                new ReadFileStatement(new VarExp("varf"), "varc"),
//                                                                new CompoundStatement(
//                                                                        new PrintStatement(new VarExp("varc")),
//                                                                        new CloseRFileStatement(new VarExp("varf"))
//                                                                )
//                                                        )
//                                                )
//                                        )
//                                )
//                        )
//                )
//        );
//        ControllerInterface ctr4;
//        Dictionary<String, Type> typeEnv4 = new Dictionary<>();
//        try{
//            fileExample.typecheck(typeEnv4);
//            ExecutionStack exeStack4 = new ListExecutionStack();
//            exeStack4.push(fileExample);
//            SymbolTable symTable4 = new MapSymbolTable();
//            Out out4 = new ListOut();
//            FileTable fileTable4 = new MapFileTable();
//            Heap heap4 = new HeapMemory();
//            ProgramState prg4 = new ProgramState(exeStack4, symTable4, out4, fileTable4, heap4);
//            RepositoryInterface repo4 = new Repository(prg4, "log4.txt");
//            ctr4 = new Controller(repo4);
//        } catch (TypeCheckException e){
//            String err = e.getMessage();
//            System.out.println("Typecheck error in fileExample: "+err);
//            ctr4 = new ControllerInterface() {
//                @Override public void allStep() { System.out.println("Cannot run fileExample: "+err); }
//                @Override public void displayCurrentState() {}
//                @Override public void setDisplayFlag(boolean flag) {}
//                @Override public boolean getDisplayFlag() { return false; }
//            };
//        }
//
//        // Ref int v; new(v,20); print(rH(v));
//        Statement heapEx1 = new CompoundStatement(
//                new VariableDeclarationStatement(new ReferenceType(SimpleType.INTEGER), "v"),
//                new CompoundStatement(
//                        new HeapDeclarationStatement("v", new ValueExp(new IntegerValue(20))),
//                        new PrintStatement(new HeapReadExp(new VarExp("v")))
//                )
//        );
//        ControllerInterface ctr5;
//        Dictionary<String, Type> typeEnv5 = new Dictionary<>();
//        try{
//            heapEx1.typecheck(typeEnv5);
//            ExecutionStack exeStack5 = new ListExecutionStack();
//            exeStack5.push(heapEx1);
//            SymbolTable symTable5 = new MapSymbolTable();
//            Out out5 = new ListOut();
//            FileTable fileTable5 = new MapFileTable();
//            Heap heap5 = new HeapMemory();
//            ProgramState prg5 = new ProgramState(exeStack5, symTable5, out5, fileTable5, heap5);
//            RepositoryInterface repo5 = new Repository(prg5, "log5.txt");
//            ctr5 = new Controller(repo5);
//        } catch (TypeCheckException e){
//            String err = e.getMessage();
//            System.out.println("Typecheck error in heapEx1: "+err);
//            ctr5 = new ControllerInterface() {
//                @Override public void allStep() { System.out.println("Cannot run heapEx1: "+err); }
//                @Override public void displayCurrentState() {}
//                @Override public void setDisplayFlag(boolean flag) {}
//                @Override public boolean getDisplayFlag() { return false; }
//            };
//        }
//
//        // Ref int v; new(v,20); Ref Ref int a; new(a,v); print(rH(v)); print(rH(rH(a)));
//        Statement heapEx2 = new CompoundStatement(
//                new VariableDeclarationStatement(new ReferenceType(SimpleType.INTEGER), "v"),
//                new CompoundStatement(
//                        new HeapDeclarationStatement("v", new ValueExp(new IntegerValue(20))),
//                        new CompoundStatement(
//                                new VariableDeclarationStatement(new ReferenceType(new ReferenceType(SimpleType.INTEGER)), "a"),
//                                new CompoundStatement(
//                                        new HeapDeclarationStatement("a", new VarExp("v")),
//                                        new CompoundStatement(
//                                                new PrintStatement(new HeapReadExp(new VarExp("v"))),
//                                                new PrintStatement(new HeapReadExp(new HeapReadExp(new VarExp("a"))))
//                                        )
//                                )
//                        )
//                )
//        );
//        ControllerInterface ctr6;
//        Dictionary<String, Type> typeEnv6 = new Dictionary<>();
//        try{
//            heapEx2.typecheck(typeEnv6);
//            ExecutionStack exeStack6 = new ListExecutionStack();
//            exeStack6.push(heapEx2);
//            SymbolTable symTable6 = new MapSymbolTable();
//            Out out6 = new ListOut();
//            FileTable fileTable6 = new MapFileTable();
//            Heap heap6 = new HeapMemory();
//            ProgramState prg6 = new ProgramState(exeStack6, symTable6, out6, fileTable6, heap6);
//            RepositoryInterface repo6 = new Repository(prg6, "log6.txt");
//            ctr6 = new Controller(repo6);
//        } catch (TypeCheckException e){
//            String err = e.getMessage();
//            System.out.println("Typecheck error in heapEx2: "+err);
//            ctr6 = new ControllerInterface() {
//                @Override public void allStep() { System.out.println("Cannot run heapEx2: "+err); }
//                @Override public void displayCurrentState() {}
//                @Override public void setDisplayFlag(boolean flag) {}
//                @Override public boolean getDisplayFlag() { return false; }
//            };
//        }
//
//        // Ref int v; new(v,20); Ref Ref int a; new(a,v); print(rH(rH(a))+5);
//        Statement heapEx3 = new CompoundStatement(
//                new VariableDeclarationStatement(new ReferenceType(SimpleType.INTEGER), "v"),
//                new CompoundStatement(
//                        new HeapDeclarationStatement("v", new ValueExp(new IntegerValue(20))),
//                        new CompoundStatement(
//                                new VariableDeclarationStatement(new ReferenceType(new ReferenceType(SimpleType.INTEGER)), "a"),
//                                new CompoundStatement(
//                                        new HeapDeclarationStatement("a", new VarExp("v")),
//                                        new PrintStatement(new ArithExp(
//                                                new HeapReadExp(new HeapReadExp(new VarExp("a"))),
//                                                new ValueExp(new IntegerValue(5)),
//                                                "+"
//                                        ))
//                                )
//                        )
//                )
//        );
//        ControllerInterface ctr7;
//        Dictionary<String, Type> typeEnv7 = new Dictionary<>();
//        try{
//            heapEx3.typecheck(typeEnv7);
//            ExecutionStack exeStack7 = new ListExecutionStack();
//            exeStack7.push(heapEx3);
//            SymbolTable symTable7 = new MapSymbolTable();
//            Out out7 = new ListOut();
//            FileTable fileTable7 = new MapFileTable();
//            Heap heap7 = new HeapMemory();
//            ProgramState prg7 = new ProgramState(exeStack7, symTable7, out7, fileTable7, heap7);
//            RepositoryInterface repo7 = new Repository(prg7, "log7.txt");
//            ctr7 = new Controller(repo7);
//        } catch (TypeCheckException e){
//            String err = e.getMessage();
//            System.out.println("Typecheck error in heapEx3: "+err);
//            ctr7 = new ControllerInterface() {
//                @Override public void allStep() { System.out.println("Cannot run heapEx3: "+err); }
//                @Override public void displayCurrentState() {}
//                @Override public void setDisplayFlag(boolean flag) {}
//                @Override public boolean getDisplayFlag() { return false; }
//            };
//        }
//
//        // Ref int v; new(v,20); print(rH(v)); wH(v,30); print(rH(v)+5);
//        Statement heapEx4 = new CompoundStatement(
//                new VariableDeclarationStatement(new ReferenceType(SimpleType.INTEGER), "v"),
//                new CompoundStatement(
//                        new HeapDeclarationStatement("v", new ValueExp(new IntegerValue(20))),
//                        new CompoundStatement(
//                                new PrintStatement(new HeapReadExp(new VarExp("v"))),
//                                new CompoundStatement(
//                                        new HeapWriteStatement("v", new ValueExp(new IntegerValue(30))),
//                                        new PrintStatement(new ArithExp(
//                                                new HeapReadExp(new VarExp("v")),
//                                                new ValueExp(new IntegerValue(5)),
//                                                "+"
//                                        ))
//                                )
//                        )
//                )
//        );
//        ControllerInterface ctr8;
//        Dictionary<String, Type> typeEnv8 = new Dictionary<>();
//        try{
//            heapEx4.typecheck(typeEnv8);
//            ExecutionStack exeStack8 = new ListExecutionStack();
//            exeStack8.push(heapEx4);
//            SymbolTable symTable8 = new MapSymbolTable();
//            Out out8 = new ListOut();
//            FileTable fileTable8 = new MapFileTable();
//            Heap heap8 = new HeapMemory();
//            ProgramState prg8 = new ProgramState(exeStack8, symTable8, out8, fileTable8, heap8);
//            RepositoryInterface repo8 = new Repository(prg8, "log8.txt");
//            ctr8 = new Controller(repo8);
//        } catch (TypeCheckException e){
//            String err = e.getMessage();
//            System.out.println("Typecheck error in heapEx4: "+err);
//            ctr8 = new ControllerInterface() {
//                @Override public void allStep() { System.out.println("Cannot run heapEx4: "+err); }
//                @Override public void displayCurrentState() {}
//                @Override public void setDisplayFlag(boolean flag) {}
//                @Override public boolean getDisplayFlag() { return false; }
//            };
//        }
//
//        // Ref int v; new(v,20);
//        // Ref int u; new(u,30);
//        // print(rH(v));
//        // v = u;
//        // print(rH(v));
//        Statement heapGcEx = new CompoundStatement(
//                new VariableDeclarationStatement(new ReferenceType(SimpleType.INTEGER), "v"),
//                new CompoundStatement(
//                        new HeapDeclarationStatement("v", new ValueExp(new IntegerValue(20))),
//                        new CompoundStatement(
//                                new VariableDeclarationStatement(new ReferenceType(SimpleType.INTEGER), "u"),
//                                new CompoundStatement(
//                                        new HeapDeclarationStatement("u", new ValueExp(new IntegerValue(30))),
//                                        new CompoundStatement(
//                                                new PrintStatement(new HeapReadExp(new VarExp("v"))),
//                                                new CompoundStatement(
//                                                        new AssignmentStatement("v", new VarExp("u")),
//                                                        new PrintStatement(new HeapReadExp(new VarExp("v")))
//                                                )
//                                        )
//                                )
//                        )
//                )
//        );
//        ControllerInterface ctr9;
//        Dictionary<String, Type> typeEnv9 = new Dictionary<>();
//        try{
//            heapGcEx.typecheck(typeEnv9);
//            ExecutionStack exeStack9 = new ListExecutionStack();
//            exeStack9.push(heapGcEx);
//            SymbolTable symTable9 = new MapSymbolTable();
//            Out out9 = new ListOut();
//            FileTable fileTable9 = new MapFileTable();
//            Heap heap9 = new HeapMemory();
//            ProgramState prg9 = new ProgramState(exeStack9, symTable9, out9, fileTable9, heap9);
//            RepositoryInterface repo9 = new Repository(prg9, "log9.txt");
//            ctr9 = new Controller(repo9);
//        } catch (TypeCheckException e){
//            String err = e.getMessage();
//            System.out.println("Typecheck error in heapGcEx: "+err);
//            ctr9 = new ControllerInterface() {
//                @Override public void allStep() { System.out.println("Cannot run heapGcEx: "+err); }
//                @Override public void displayCurrentState() {}
//                @Override public void setDisplayFlag(boolean flag) {}
//                @Override public boolean getDisplayFlag() { return false; }
//            };
//        }
//
//        // Ref int v; new(v,20);
//        // Ref Ref int a; new(a,v);
//        // new(v,30);
//        // print(rH(rH(a)));
//        Statement heapGcEx2 = new CompoundStatement(
//                new VariableDeclarationStatement(new ReferenceType(SimpleType.INTEGER), "v"),
//                new CompoundStatement(
//                        new HeapDeclarationStatement("v", new ValueExp(new IntegerValue(20))),
//                        new CompoundStatement(
//                                new VariableDeclarationStatement(new ReferenceType(new ReferenceType(SimpleType.INTEGER)), "a"),
//                                new CompoundStatement(
//                                        new HeapDeclarationStatement("a", new VarExp("v")),
//                                        new CompoundStatement(
//                                                new HeapDeclarationStatement("v", new ValueExp(new IntegerValue(30))),
//                                                new PrintStatement(new HeapReadExp(new HeapReadExp(new VarExp("a"))))
//                                        )
//                                )
//                        )
//                )
//        );
//        ControllerInterface ctr10;
//        Dictionary<String, Type> typeEnv10 = new Dictionary<>();
//        try{
//            heapGcEx2.typecheck(typeEnv10);
//            ExecutionStack exeStack10 = new ListExecutionStack();
//            exeStack10.push(heapGcEx2);
//            SymbolTable symTable10 = new MapSymbolTable();
//            Out out10 = new ListOut();
//            FileTable fileTable10 = new MapFileTable();
//            Heap heap10 = new HeapMemory();
//            ProgramState prg10 = new ProgramState(exeStack10, symTable10, out10, fileTable10, heap10);
//            RepositoryInterface repo10 = new Repository(prg10, "log10.txt");
//            ctr10 = new Controller(repo10);
//        } catch (TypeCheckException e){
//            String err = e.getMessage();
//            System.out.println("Typecheck error in heapGcEx2: "+err);
//            ctr10 = new ControllerInterface() {
//                @Override public void allStep() { System.out.println("Cannot run heapGcEx2: "+err); }
//                @Override public void displayCurrentState() {}
//                @Override public void setDisplayFlag(boolean flag) {}
//                @Override public boolean getDisplayFlag() { return false; }
//            };
//        }
//
//        // int v; v=4; while(v>0) { print(v); v=v-1; } print(v)
//        Statement whileEx = new CompoundStatement(
//                new VariableDeclarationStatement(SimpleType.INTEGER, "v"),
//                new CompoundStatement(
//                        new AssignmentStatement("v", new ValueExp(new IntegerValue(4))),
//                        new CompoundStatement(
//                                new WhileStatement(
//                                        new RelationalExp(new VarExp("v"), new ValueExp(new IntegerValue(0)), ">"),
//                                        new CompoundStatement(
//                                                new PrintStatement(new VarExp("v")),
//                                                new AssignmentStatement("v", new ArithExp(new VarExp("v"), new ValueExp(new IntegerValue(1)), "-"))
//                                        )
//                                ),
//                                new PrintStatement(new VarExp("v"))
//                        )
//                )
//        );
//        ControllerInterface ctr11;
//        Dictionary<String, Type> typeEnv11 = new Dictionary<>();
//        try{
//            whileEx.typecheck(typeEnv11);
//            ExecutionStack exeStack11 = new ListExecutionStack();
//            exeStack11.push(whileEx);
//            SymbolTable symTable11 = new MapSymbolTable();
//            Out out11 = new ListOut();
//            FileTable fileTable11 = new MapFileTable();
//            Heap heap11 = new HeapMemory();
//            ProgramState prg11 = new ProgramState(exeStack11, symTable11, out11, fileTable11, heap11);
//            RepositoryInterface repo11 = new Repository(prg11, "log11.txt");
//            ctr11 = new Controller(repo11);
//        } catch (TypeCheckException e){
//            String err = e.getMessage();
//            System.out.println("Typecheck error in whileEx: "+err);
//            ctr11 = new ControllerInterface() {
//                @Override public void allStep() { System.out.println("Cannot run whileEx: "+err); }
//                @Override public void displayCurrentState() {}
//                @Override public void setDisplayFlag(boolean flag) {}
//                @Override public boolean getDisplayFlag() { return false; }
//            };
//        }
//
//        // Fork example
//        // int v; Ref int a; v=10; new(a,22);
//        // fork(wH(a,30); v=32; print(v); print(rH(a)));
//        // print(v); print(rH(a))
//        Statement forkExample = new CompoundStatement(
//                new VariableDeclarationStatement(SimpleType.INTEGER, "v"),
//                new CompoundStatement(
//                        new VariableDeclarationStatement(new ReferenceType(SimpleType.INTEGER), "a"),
//                        new CompoundStatement(
//                                new AssignmentStatement("v", new ValueExp(new IntegerValue(10))),
//                                new CompoundStatement(
//                                        new HeapDeclarationStatement("a", new ValueExp(new IntegerValue(22))),
//                                        new CompoundStatement(
//                                                new ForkStatement(
//                                                        new CompoundStatement(
//                                                                new HeapWriteStatement("a", new ValueExp(new IntegerValue(30))),
//                                                                new CompoundStatement(
//                                                                        new AssignmentStatement("v", new ValueExp(new IntegerValue(32))),
//                                                                        new CompoundStatement(
//                                                                                new PrintStatement(new VarExp("v")),
//                                                                                new PrintStatement(new HeapReadExp(new VarExp("a")))
//                                                                        )
//                                                                )
//                                                        )
//                                                ),
//                                                new CompoundStatement(
//                                                        new PrintStatement(new VarExp("v")),
//                                                        new PrintStatement(new HeapReadExp(new VarExp("a")))
//                                                )
//                                        )
//                                )
//                        )
//                )
//        );
//        ControllerInterface ctr12;
//        Dictionary<String, Type> typeEnv12 = new Dictionary<>();
//        try{
//            forkExample.typecheck(typeEnv12);
//            ExecutionStack exeStack12 = new ListExecutionStack();
//            exeStack12.push(forkExample);
//            SymbolTable symTable12 = new MapSymbolTable();
//            Out out12 = new ListOut();
//            FileTable fileTable12 = new MapFileTable();
//            Heap heap12 = new HeapMemory();
//            ProgramState prg12 = new ProgramState(exeStack12, symTable12, out12, fileTable12, heap12);
//            RepositoryInterface repo12 = new Repository(prg12, "log12.txt");
//            ctr12 = new Controller(repo12);
//        } catch (TypeCheckException e){
//            String err = e.getMessage();
//            System.out.println("Typecheck error in forkExample: "+err);
//            ctr12 = new ControllerInterface() {
//                @Override public void allStep() { System.out.println("Cannot run forkExample: "+err); }
//                @Override public void displayCurrentState() {}
//                @Override public void setDisplayFlag(boolean flag) {}
//                @Override public boolean getDisplayFlag() { return false; }
//            };
//        }
//
//        // Small example that intentionally fails the typechecker:
//        // int x; x = true;
//        Statement typecheckFailEx = new CompoundStatement(
//                new VariableDeclarationStatement(SimpleType.INTEGER, "x"),
//                new AssignmentStatement("x", new ValueExp(new BooleanValue(true)))
//        );
//        ControllerInterface ctr13;
//        Dictionary<String, Type> typeEnv13 = new Dictionary<>();
//        try {
//            typecheckFailEx.typecheck(typeEnv13);
//            // If typecheck somehow succeeds, create a runnable controller as usual
//            ExecutionStack exeStack13 = new ListExecutionStack();
//            exeStack13.push(typecheckFailEx);
//            SymbolTable symTable13 = new MapSymbolTable();
//            Out out13 = new ListOut();
//            FileTable fileTable13 = new MapFileTable();
//            Heap heap13 = new HeapMemory();
//            ProgramState prg13 = new ProgramState(exeStack13, symTable13, out13, fileTable13, heap13);
//            RepositoryInterface repo13 = new Repository(prg13, "log13.txt");
//            ctr13 = new Controller(repo13);
//        } catch (TypeCheckException e) {
//            String err = e.getMessage();
//            System.out.println("Typecheck error in typecheckFailEx (expected): "+err);
//            ctr13 = new ControllerInterface() {
//                @Override public void allStep() { System.out.println("Cannot run typecheckFailEx: "+err); }
//                @Override public void displayCurrentState() {}
//                @Override public void setDisplayFlag(boolean flag) {}
//                @Override public boolean getDisplayFlag() { return false; }
//            };
//        }
//
//        TextMenu menu = new TextMenu();
//        menu.addCommand(new ExitCommand("0", "exit"));
//        menu.addCommand(new RunExampleCommand("1", "ex1", ctr1));
//        menu.addCommand(new RunExampleCommand("2", "ex2", ctr2));
//        menu.addCommand(new RunExampleCommand("3", "ex3", ctr3));
//        menu.addCommand(new RunExampleCommand("4", "file example", ctr4));
//        menu.addCommand(new RunExampleCommand("5", "heap ex1: basic heap allocation", ctr5));
//        menu.addCommand(new RunExampleCommand("6", "heap ex2: nested references", ctr6));
//        menu.addCommand(new RunExampleCommand("7", "heap ex3: heap read with arithmetic", ctr7));
//        menu.addCommand(new RunExampleCommand("8", "heap ex4: heap write", ctr8));
//        menu.addCommand(new RunExampleCommand("9", "heap GC example", ctr9));
//        menu.addCommand(new RunExampleCommand("10", "heap GC example 2", ctr10));
//        menu.addCommand(new RunExampleCommand("11", "while example", ctr11));
//        menu.addCommand(new RunExampleCommand("12", "fork example", ctr12));
//        menu.addCommand(new RunExampleCommand("13", "typecheck fail example", ctr13));
//        menu.show();
//    }
//}