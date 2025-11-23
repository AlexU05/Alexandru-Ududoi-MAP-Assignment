import controller.ControllerInterface;
import controller.Controller;
import model.expression.ArithExp;
import model.expression.ValueExp;
import model.expression.VarExp;
import model.expression.RelationalExp;
import model.expression.HeapReadExp;
import model.statement.*;
import model.type.SimpleType;
import model.type.ReferenceType;
import model.value.BooleanValue;
import model.value.IntegerValue;
import model.value.StringValue;
import repository.RepositoryInterface;
import repository.Repository;
import state.*;
import view.TextMenu;
//import view.ViewImp1;
import view.command.ExitCommand;
import view.command.RunExampleCommand;


class Main {
    public static void main(String[] args) {
            Statement ex1 = new CompoundStatement(
                    new VariableDeclarationStatement(SimpleType.INTEGER, "v"),
                    new CompoundStatement(
                            new AssignmentStatement("v", new ValueExp(new IntegerValue(2))),
                            new PrintStatement(new VarExp("v"))
                    )
            );
            ExecutionStack exeStack1 = new ListExecutionStack();
            exeStack1.push(ex1);
            SymbolTable symTable1 = new MapSymbolTable();
            Out out1 = new ListOut();
            FileTable fileTable1 = new MapFileTable();
            Heap heap1 = new HeapMemory();
            ProgramState prg1 = new ProgramState(exeStack1, symTable1, out1, fileTable1, heap1);
            RepositoryInterface repo1 = new Repository(prg1, "log1.txt");
            ControllerInterface ctr1 = new Controller(repo1);

            Statement ex2 = new CompoundStatement(
                    new VariableDeclarationStatement(SimpleType.INTEGER, "a"),
                    new CompoundStatement(
                            new VariableDeclarationStatement(SimpleType.INTEGER, "b"),
                            new CompoundStatement(
                                    new AssignmentStatement("a", new ArithExp(
                                            new ValueExp(new IntegerValue(2)),
                                            new ArithExp(
                                                    new ValueExp(new IntegerValue(3)),
                                                    new ValueExp(new IntegerValue(5)),
                                                    "*"
                                            ),
                                            "+"
                                    )),
                                    new CompoundStatement(
                                            new AssignmentStatement("b", new ArithExp(
                                                    new VarExp("a"),
                                                    new ValueExp(new IntegerValue(1)),
                                                    "+"
                                            )),
                                            new PrintStatement(new VarExp("b"))
                                    )
                            )
                    )
            );
            ExecutionStack exeStack2 = new ListExecutionStack();
            exeStack2.push(ex2);
            SymbolTable symTable2 = new MapSymbolTable();
            Out out2 = new ListOut();
            FileTable fileTable2 = new MapFileTable();
            Heap heap2 = new HeapMemory();
            ProgramState prg2 = new ProgramState(exeStack2, symTable2, out2, fileTable2, heap2);
            RepositoryInterface repo2 = new Repository(prg2, "log2.txt");
            ControllerInterface ctr2 = new Controller(repo2);

            Statement ex3 = new CompoundStatement(
                    new VariableDeclarationStatement(SimpleType.BOOLEAN, "a"),
                    new CompoundStatement(
                            new VariableDeclarationStatement(SimpleType.INTEGER, "v"),
                            new CompoundStatement(
                                    new AssignmentStatement("a", new ValueExp(new BooleanValue(true))),
                                    new CompoundStatement(
                                            new IfStatement(
                                                    new VarExp("a"),
                                                    new AssignmentStatement("v", new ValueExp(new IntegerValue(2))),
                                                    new AssignmentStatement("v", new ValueExp(new IntegerValue(3)))
                                            ),
                                            new PrintStatement(new VarExp("v"))
                                    )
                            )
                    )
            );
            ExecutionStack exeStack3 = new ListExecutionStack();
            exeStack3.push(ex3);
            SymbolTable symTable3 = new MapSymbolTable();
            Out out3 = new ListOut();
            FileTable fileTable3 = new MapFileTable();
            Heap heap3 = new HeapMemory();
            ProgramState prg3 = new ProgramState(exeStack3, symTable3, out3, fileTable3, heap3);
            RepositoryInterface repo3 = new Repository(prg3, "log3.txt");
            ControllerInterface ctr3 = new Controller(repo3);


            Statement fileExample = new CompoundStatement(
                    new VariableDeclarationStatement(SimpleType.STRING, "varf"),
                    new CompoundStatement(
                            new AssignmentStatement("varf", new ValueExp(new StringValue("test.in"))),
                            new CompoundStatement(
                                    new OpenRFileStatement(new VarExp("varf")),
                                    new CompoundStatement(
                                            new VariableDeclarationStatement(SimpleType.INTEGER, "varc"),
                                            new CompoundStatement(
                                                    new ReadFileStatement(new VarExp("varf"), "varc"),
                                                    new CompoundStatement(
                                                            new PrintStatement(new VarExp("varc")),
                                                            new CompoundStatement(
                                                                    new ReadFileStatement(new VarExp("varf"), "varc"),
                                                                    new CompoundStatement(
                                                                            new PrintStatement(new VarExp("varc")),
                                                                            new CloseRFileStatement(new VarExp("varf"))
                                                                    )
                                                            )
                                                    )
                                            )
                                    )
                            )
                    )
            );

            ExecutionStack exeStack4 = new ListExecutionStack();
            exeStack4.push(fileExample);
            SymbolTable symTable4 = new MapSymbolTable();
            Out out4 = new ListOut();
            FileTable fileTable4 = new MapFileTable();
            Heap heap4 = new HeapMemory();
            ProgramState prg4 = new ProgramState(exeStack4, symTable4, out4, fileTable4, heap4);
            RepositoryInterface repo4 = new Repository(prg4, "log4.txt");
            ControllerInterface ctr4 = new Controller(repo4);

            // Ref int v; new(v,20); print(rH(v));
            Statement heapEx1 = new CompoundStatement(
                    new VariableDeclarationStatement(new ReferenceType(SimpleType.INTEGER), "v"),
                    new CompoundStatement(
                            new HeapDeclarationStatement("v", new ValueExp(new IntegerValue(20))),
                            new PrintStatement(new HeapReadExp(new VarExp("v")))
                    )
            );
            ExecutionStack exeStack5 = new ListExecutionStack();
            exeStack5.push(heapEx1);
            SymbolTable symTable5 = new MapSymbolTable();
            Out out5 = new ListOut();
            FileTable fileTable5 = new MapFileTable();
            Heap heap5 = new HeapMemory();
            ProgramState prg5 = new ProgramState(exeStack5, symTable5, out5, fileTable5, heap5);
            RepositoryInterface repo5 = new Repository(prg5, "log5.txt");
            ControllerInterface ctr5 = new Controller(repo5);

            // Ref int v; new(v,20); Ref Ref int a; new(a,v); print(rH(v)); print(rH(rH(a)));
            Statement heapEx2 = new CompoundStatement(
                    new VariableDeclarationStatement(new ReferenceType(SimpleType.INTEGER), "v"),
                    new CompoundStatement(
                            new HeapDeclarationStatement("v", new ValueExp(new IntegerValue(20))),
                            new CompoundStatement(
                                    new VariableDeclarationStatement(new ReferenceType(new ReferenceType(SimpleType.INTEGER)), "a"),
                                    new CompoundStatement(
                                            new HeapDeclarationStatement("a", new VarExp("v")),
                                            new CompoundStatement(
                                                    new PrintStatement(new HeapReadExp(new VarExp("v"))),
                                                    new PrintStatement(new HeapReadExp(new HeapReadExp(new VarExp("a"))))
                                            )
                                    )
                            )
                    )
            );
            ExecutionStack exeStack6 = new ListExecutionStack();
            exeStack6.push(heapEx2);
            SymbolTable symTable6 = new MapSymbolTable();
            Out out6 = new ListOut();
            FileTable fileTable6 = new MapFileTable();
            Heap heap6 = new HeapMemory();
            ProgramState prg6 = new ProgramState(exeStack6, symTable6, out6, fileTable6, heap6);
            RepositoryInterface repo6 = new Repository(prg6, "log6.txt");
            ControllerInterface ctr6 = new Controller(repo6);

            // Ref int v; new(v,20); Ref Ref int a; new(a,v); print(rH(rH(a))+5);
            Statement heapEx3 = new CompoundStatement(
                    new VariableDeclarationStatement(new ReferenceType(SimpleType.INTEGER), "v"),
                    new CompoundStatement(
                            new HeapDeclarationStatement("v", new ValueExp(new IntegerValue(20))),
                            new CompoundStatement(
                                    new VariableDeclarationStatement(new ReferenceType(new ReferenceType(SimpleType.INTEGER)), "a"),
                                    new CompoundStatement(
                                            new HeapDeclarationStatement("a", new VarExp("v")),
                                            new PrintStatement(new ArithExp(
                                                    new HeapReadExp(new HeapReadExp(new VarExp("a"))),
                                                    new ValueExp(new IntegerValue(5)),
                                                    "+"
                                            ))
                                    )
                            )
                    )
            );
            ExecutionStack exeStack7 = new ListExecutionStack();
            exeStack7.push(heapEx3);
            SymbolTable symTable7 = new MapSymbolTable();
            Out out7 = new ListOut();
            FileTable fileTable7 = new MapFileTable();
            Heap heap7 = new HeapMemory();
            ProgramState prg7 = new ProgramState(exeStack7, symTable7, out7, fileTable7, heap7);
            RepositoryInterface repo7 = new Repository(prg7, "log7.txt");
            ControllerInterface ctr7 = new Controller(repo7);

            // Ref int v; new(v,20); print(rH(v)); wH(v,30); print(rH(v)+5);
            Statement heapEx4 = new CompoundStatement(
                    new VariableDeclarationStatement(new ReferenceType(SimpleType.INTEGER), "v"),
                    new CompoundStatement(
                            new HeapDeclarationStatement("v", new ValueExp(new IntegerValue(20))),
                            new CompoundStatement(
                                    new PrintStatement(new HeapReadExp(new VarExp("v"))),
                                    new CompoundStatement(
                                            new HeapWriteStatement("v", new ValueExp(new IntegerValue(30))),
                                            new PrintStatement(new ArithExp(
                                                    new HeapReadExp(new VarExp("v")),
                                                    new ValueExp(new IntegerValue(5)),
                                                    "+"
                                            ))
                                    )
                            )
                    )
            );
            ExecutionStack exeStack8 = new ListExecutionStack();
            exeStack8.push(heapEx4);
            SymbolTable symTable8 = new MapSymbolTable();
            Out out8 = new ListOut();
            FileTable fileTable8 = new MapFileTable();
            Heap heap8 = new HeapMemory();
            ProgramState prg8 = new ProgramState(exeStack8, symTable8, out8, fileTable8, heap8);
            RepositoryInterface repo8 = new Repository(prg8, "log8.txt");
            ControllerInterface ctr8 = new Controller(repo8);

            // Ref int v; new(v,20);
            // Ref int u; new(u,30);
            // print(rH(v));
            // v = u;
            // print(rH(v));
            Statement heapGcEx = new CompoundStatement(
                    new VariableDeclarationStatement(new ReferenceType(SimpleType.INTEGER), "v"),
                    new CompoundStatement(
                            new HeapDeclarationStatement("v", new ValueExp(new IntegerValue(20))),
                            new CompoundStatement(
                                    new VariableDeclarationStatement(new ReferenceType(SimpleType.INTEGER), "u"),
                                    new CompoundStatement(
                                            new HeapDeclarationStatement("u", new ValueExp(new IntegerValue(30))),
                                            new CompoundStatement(
                                                    new PrintStatement(new HeapReadExp(new VarExp("v"))),
                                                    new CompoundStatement(
                                                            new AssignmentStatement("v", new VarExp("u")),
                                                            new PrintStatement(new HeapReadExp(new VarExp("v")))
                                                    )
                                            )
                                    )
                            )
                    )
            );
            ExecutionStack exeStack9 = new ListExecutionStack();
            exeStack9.push(heapGcEx);
            SymbolTable symTable9 = new MapSymbolTable();
            Out out9 = new ListOut();
            FileTable fileTable9 = new MapFileTable();
            Heap heap9 = new HeapMemory();
            ProgramState prg9 = new ProgramState(exeStack9, symTable9, out9, fileTable9, heap9);
            RepositoryInterface repo9 = new Repository(prg9, "log9.txt");
            ControllerInterface ctr9 = new Controller(repo9);

            // Ref int v; new(v,20);
            // Ref Ref int a; new(a,v);
            // new(v,30);
            // print(rH(rH(a)));
            Statement heapGcEx2 = new CompoundStatement(
                    new VariableDeclarationStatement(new ReferenceType(SimpleType.INTEGER), "v"),
                    new CompoundStatement(
                            new HeapDeclarationStatement("v", new ValueExp(new IntegerValue(20))),
                            new CompoundStatement(
                                    new VariableDeclarationStatement(new ReferenceType(new ReferenceType(SimpleType.INTEGER)), "a"),
                                    new CompoundStatement(
                                            new HeapDeclarationStatement("a", new VarExp("v")),
                                            new CompoundStatement(
                                                    new HeapDeclarationStatement("v", new ValueExp(new IntegerValue(30))),
                                                    new PrintStatement(new HeapReadExp(new HeapReadExp(new VarExp("a"))))
                                            )
                                    )
                            )
                    )
            );
            ExecutionStack exeStack10 = new ListExecutionStack();
            exeStack10.push(heapGcEx2);
            SymbolTable symTable10 = new MapSymbolTable();
            Out out10 = new ListOut();
            FileTable fileTable10 = new MapFileTable();
            Heap heap10 = new HeapMemory();
            ProgramState prg10 = new ProgramState(exeStack10, symTable10, out10, fileTable10, heap10);
            RepositoryInterface repo10 = new Repository(prg10, "log10.txt");
            ControllerInterface ctr10 = new Controller(repo10);

            // int v; v=4; while(v>0) { print(v); v=v-1; } print(v)
            Statement whileEx = new CompoundStatement(
                    new VariableDeclarationStatement(SimpleType.INTEGER, "v"),
                    new CompoundStatement(
                            new AssignmentStatement("v", new ValueExp(new IntegerValue(4))),
                            new CompoundStatement(
                                    new WhileStatement(
                                            new RelationalExp(new VarExp("v"), new ValueExp(new IntegerValue(0)), ">"),
                                            new CompoundStatement(
                                                    new PrintStatement(new VarExp("v")),
                                                    new AssignmentStatement("v", new ArithExp(new VarExp("v"), new ValueExp(new IntegerValue(1)), "-"))
                                            )
                                    ),
                                    new PrintStatement(new VarExp("v"))
                            )
                    )
            );
            ExecutionStack exeStack11 = new ListExecutionStack();
            exeStack11.push(whileEx);
            SymbolTable symTable11 = new MapSymbolTable();
            Out out11 = new ListOut();
            FileTable fileTable11 = new MapFileTable();
            Heap heap11 = new HeapMemory();
            ProgramState prg11 = new ProgramState(exeStack11, symTable11, out11, fileTable11, heap11);
            RepositoryInterface repo11 = new Repository(prg11, "log11.txt");
            ControllerInterface ctr11 = new Controller(repo11);

            TextMenu menu = new TextMenu();
            menu.addCommand(new ExitCommand("0", "exit"));
            menu.addCommand(new RunExampleCommand("1", "ex1", ctr1));
            menu.addCommand(new RunExampleCommand("2", "ex2", ctr2));
            menu.addCommand(new RunExampleCommand("3", "ex3", ctr3));
            menu.addCommand(new RunExampleCommand("4", "file example", ctr4));
            menu.addCommand(new RunExampleCommand("5", "heap ex1: basic heap allocation", ctr5));
            menu.addCommand(new RunExampleCommand("6", "heap ex2: nested references", ctr6));
            menu.addCommand(new RunExampleCommand("7", "heap ex3: heap read with arithmetic", ctr7));
            menu.addCommand(new RunExampleCommand("8", "heap ex4: heap write", ctr8));
            menu.addCommand(new RunExampleCommand("9", "heap GC example", ctr9));
            menu.addCommand(new RunExampleCommand("10", "heap GC transitive example", ctr10));
            menu.addCommand(new RunExampleCommand("11", "while example", ctr11));
            menu.show();
        }
    }
