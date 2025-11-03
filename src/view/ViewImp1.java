package view;

import controller.Controller;
import controller.ControllerImp1;
import model.expression.*;
import model.statement.*;
import model.type.Type;
import model.value.*;
import repository.*;
import state.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ViewImp1 implements View {
    private final Scanner scanner;

    public ViewImp1() {
        this.scanner = new Scanner(System.in);
    }

    private List<Statement> getHardcodedPrograms() {
        List<Statement> programs = new ArrayList<>();

        // Example 1: int v; v=2; Print(v)
        Statement ex1 = new CompoundStatement(
                new VariableDeclarationStatement(Type.INTEGER, "v"),
                new CompoundStatement(
                        new AssignmentStatement("v", new ValueExp(new IntegerValue(2))),
                        new PrintStatement(new VarExp("v"))
                )
        );

        // Example 2: int a; int b; a=2+3*5; b=a+1; Print(b)
        Statement ex2 = new CompoundStatement(
                new VariableDeclarationStatement(Type.INTEGER, "a"),
                new CompoundStatement(
                        new VariableDeclarationStatement(Type.INTEGER,"b"),
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

        // Example 3: bool a; int v; a=true; (If a Then v=2 Else v=3); Print(v)
        Statement ex3 = new CompoundStatement(
                new VariableDeclarationStatement(Type.BOOLEAN, "a"),
                new CompoundStatement(
                        new VariableDeclarationStatement(Type.INTEGER, "v"),
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

        programs.add(ex1);
        programs.add(ex2);
        programs.add(ex3);
        return programs;
    }

    public void run() {
        List<Statement> programs = getHardcodedPrograms();
        while (true) {
            displayMenu();
            System.out.print("Option: ");
            String option = scanner.nextLine();

            try {
                switch (option) {
                    case "1":
                        displayProgramList();
                        System.out.print("Program number: ");
                        int progNumber = Integer.parseInt(scanner.nextLine().trim());
                        executeProgram(programs, progNumber);
                        break;
                    case "2":
                        return;
                    default:
                        System.out.println("Invalid option!");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private void displayMenu() {
        System.out.println("\n=== Menu ===");
        System.out.println("1. Select program");
        System.out.println("2. Exit");
    }

    private void displayProgramList() {
        System.out.println("Available programs:");
        System.out.println("1. int v; v=2; Print(v)");
        System.out.println("2. int a; int b; a=2+3*5; b=a+1; Print(b)");
        System.out.println("3. bool a; int v; a=true; (If a Then v=2 Else v=3); Print(v)");
    }

    private void executeProgram(List<Statement> programs, int progNumber) {
        if (progNumber < 1 || progNumber > programs.size()) {
            System.out.println("Invalid program number!");
            return;
        }

        ExecutionStack stack = new ListExecutionStack();
        SymbolTable symbolTable = new MapSymbolTable();
        Out out = new ListOut();
        stack.push(programs.get(progNumber - 1));

        ProgramState programState = new ProgramState(
                stack,
                symbolTable,
                out
        );
        Repository repository = new RepositoryImp1(programState);
        Controller controller = new ControllerImp1(repository);

        try {
            System.out.println("Program execution:");
            controller.allStep();
        } catch (Exception e) {
            System.out.println("Execution error: " + e.getMessage());
        }
    }
}
