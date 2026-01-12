import controller.ControllerInterface;
import controller.Controller;
import model.expression.*;
import model.statement.*;
import model.type.SimpleType;
import model.type.ReferenceType;
import model.value.*;
import repository.RepositoryInterface;
import repository.Repository;
import state.*;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.type.Type;
import model.exception.TypeCheckException;
import model.adt.Dictionary;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class JFXMain extends Application {

    private final List<ControllerInterface> controllers = new ArrayList<>();
    private final List<String> programStrings = new ArrayList<>();

    @Override
    public void start(Stage primaryStage) {
        buildExamples();

        ListView<String> programListView = new ListView<>();
        ObservableList<String> items = FXCollections.observableArrayList(programStrings);
        programListView.setItems(items);

        Button selectBtn = new Button("Select Program");

        VBox leftPane = new VBox(10, new Label("Examples:"), programListView, selectBtn);
        leftPane.setPadding(new Insets(10));
        leftPane.setPrefWidth(350);

        BorderPane root = new BorderPane();
        root.setLeft(leftPane);

        Scene scene = new Scene(root, 1000, 600);
        primaryStage.setTitle("Toy Language Interpreter - JavaFX");
        primaryStage.setScene(scene);
        primaryStage.show();

        selectBtn.setOnAction(e -> {
            int idx = programListView.getSelectionModel().getSelectedIndex();
            if (idx < 0) return;
            ControllerInterface controller = controllers.get(idx);
            try {
                openMainWindow(controller);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }

    private void openMainWindow(ControllerInterface controller) {
        Stage stage = new Stage();

        // Top: number of programs
        TextField nrPrgField = new TextField();
        nrPrgField.setEditable(false);
        HBox top = new HBox(10, new Label("Number of Program States:"), nrPrgField);
        top.setPadding(new Insets(10));

        // Heap table
        TableView<MapEntry> heapTable = new TableView<>();
        TableColumn<MapEntry, Integer> addrCol = new TableColumn<>("Address");
        addrCol.setCellValueFactory(new PropertyValueFactory<>("key"));
        addrCol.setPrefWidth(100);
        TableColumn<MapEntry, String> valCol = new TableColumn<>("Value");
        valCol.setCellValueFactory(new PropertyValueFactory<>("value"));
        valCol.setPrefWidth(200);
        heapTable.getColumns().addAll(addrCol, valCol);

        // Out list
        ListView<String> outList = new ListView<>();

        // File table list
        ListView<String> fileList = new ListView<>();

        // PrgState IDs list
        ListView<Integer> prgIdsList = new ListView<>();

        // SymTable for selected prg
        TableView<VarEntry> symTableView = new TableView<>();
        TableColumn<VarEntry, String> varNameCol = new TableColumn<>("Variable");
        varNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        varNameCol.setPrefWidth(150);
        TableColumn<VarEntry, String> varValCol = new TableColumn<>("Value");
        varValCol.setCellValueFactory(new PropertyValueFactory<>("value"));
        varValCol.setPrefWidth(200);
        symTableView.getColumns().addAll(varNameCol, varValCol);

        // ExeStack list
        ListView<String> exeStackList = new ListView<>();

        Button runOneStepBtn = new Button("Run one step");

        GridPane center = new GridPane();
        center.setPadding(new Insets(10));
        center.setHgap(10);
        center.setVgap(10);

        center.add(new Label("Heap:"), 0, 0);
        center.add(heapTable, 0, 1);
        center.add(new Label("Out:"), 1, 0);
        center.add(outList, 1, 1);
        center.add(new Label("FileTable:"), 2, 0);
        center.add(fileList, 2, 1);

        center.add(new Label("Program IDs:"), 0, 2);
        center.add(prgIdsList, 0, 3);
        center.add(new Label("SymTable:"), 1, 2);
        center.add(symTableView, 1, 3);
        center.add(new Label("ExeStack:"), 2, 2);
        center.add(exeStackList, 2, 3);

        VBox right = new VBox(10, top, center, runOneStepBtn);
        right.setPadding(new Insets(10));

        Scene scene = new Scene(right, 900, 600);
        stage.setScene(scene);
        stage.setTitle("Program Execution");
        stage.show();

        // initialize view
        updateAllViews(controller, nrPrgField, heapTable, outList, fileList, prgIdsList, symTableView, exeStackList);

        prgIdsList.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            updatePerPrgSelection(controller, newVal, symTableView, exeStackList);
        });

        runOneStepBtn.setOnAction(ev -> {
            try {
                controller.oneStep();
                updateAllViews(controller, nrPrgField, heapTable, outList, fileList, prgIdsList, symTableView, exeStackList);
            } catch (Exception ex) {
                if (ex.getMessage().contains("completed")) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Program Completed");
                    alert.setHeaderText("No more steps to execute.");
                    alert.setContentText(ex.getMessage());
                    alert.showAndWait();
                }
                else  {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("An error occurred");
                    alert.setHeaderText("Execution Error");
                    alert.setContentText(ex.getMessage());
                    alert.showAndWait();
                }

            }
        });
    }

    private void updateAllViews(ControllerInterface controller,
                                TextField nrPrgField,
                                TableView<MapEntry> heapTable,
                                ListView<String> outList,
                                ListView<String> fileList,
                                ListView<Integer> prgIdsList,
                                TableView<VarEntry> symTableView,
                                ListView<String> exeStackList) {
        try {
            List<ProgramState> prgList = controller.getProgramStates();

            nrPrgField.setText(String.valueOf(prgList.size()));

            // heap (use first prg's heap if exists)
            heapTable.getItems().clear();
            if (!prgList.isEmpty()) {
                Map<Integer, ?> heapContent = prgList.get(0).heap().getContent();
                List<MapEntry> heapEntries = heapContent.entrySet().stream()
                        .map(e -> new MapEntry(e.getKey(), e.getValue().toString()))
                        .collect(Collectors.toList());
                heapTable.getItems().addAll(heapEntries);
            }

            // out: collect outputs from the first program
            outList.getItems().clear();
            if (!prgList.isEmpty()) {
                var out = prgList.get(0).out();
                // show each value separately
                out.getAll().stream().map(Object::toString).forEach(outList.getItems()::add);
            }

            // fileTable
            fileList.getItems().clear();
            if (!prgList.isEmpty()) {
                fileList.getItems().add(prgList.get(0).fileTable().toString());
            }

            // program ids
            prgIdsList.getItems().clear();
            prgList.stream().map(ProgramState::id).forEach(prgIdsList.getItems()::add);

            // if any selected, update those
            Integer selectedId = prgIdsList.getSelectionModel().getSelectedItem();
            if (selectedId == null && !prgIdsList.getItems().isEmpty()) {
                prgIdsList.getSelectionModel().select(0);
                selectedId = prgIdsList.getItems().get(0);
            }
            updatePerPrgSelection(controller, selectedId, symTableView, exeStackList);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updatePerPrgSelection(ControllerInterface controller,
                                       Integer prgId,
                                       TableView<VarEntry> symTableView,
                                       ListView<String> exeStackList) {
        try {
            List<ProgramState> prgList = controller.getProgramStates();

            if (prgId == null) return;
            ProgramState selected = prgList.stream().filter(p -> p.id() == prgId).findFirst().orElse(null);
            if (selected == null) return;

            symTableView.getItems().clear();
            Map<String, ?> sym = selected.symbolTable().getContent();
            sym.entrySet().stream().map(e -> new VarEntry(e.getKey(), e.getValue().toString()))
                    .forEach(symTableView.getItems()::add);

            exeStackList.getItems().clear();
            selected.executionStack().getAll().stream().map(Object::toString).forEach(exeStackList.getItems()::add);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void buildExamples() {
        // Build a few examples the same way as in Main.java

        // Example 1
        Statement ex1 = new CompoundStatement(
                new VariableDeclarationStatement(SimpleType.INTEGER, "v"),
                new CompoundStatement(
                        new AssignmentStatement("v", new ValueExp(new IntegerValue(2))),
                        new PrintStatement(new VarExp("v"))
                )

        );

        controllers.add(makeControllerForStmt(ex1, "log1.txt"));
        programStrings.add(ex1.toString());

        // Example 2
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
        controllers.add(makeControllerForStmt(ex2, "log2.txt"));
        programStrings.add(ex2.toString());

        // Example 3
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
        controllers.add(makeControllerForStmt(ex3, "log3.txt"));
        programStrings.add(ex3.toString());

        // File example
        // string varf; varf = "test.in"; openRFile(varf); int varc; readFile(varf,varc); print(varc); readFile(varf,varc); print(varc); closeRFile(varf)
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
        controllers.add(makeControllerForStmt(fileExample, "log4.txt"));
        programStrings.add(fileExample.toString());

        // Ref int v; new(v,20); print(rH(v));
        Statement heapEx1 = new CompoundStatement(
                new VariableDeclarationStatement(new ReferenceType(SimpleType.INTEGER), "v"),
                new CompoundStatement(
                        new HeapDeclarationStatement("v", new ValueExp(new IntegerValue(20))),
                        new PrintStatement(new HeapReadExp(new VarExp("v")))
                )
        );
        controllers.add(makeControllerForStmt(heapEx1, "log5.txt"));
        programStrings.add(heapEx1.toString());

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
        controllers.add(makeControllerForStmt(heapEx2, "log6.txt"));
        programStrings.add(heapEx2.toString());

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
        controllers.add(makeControllerForStmt(heapEx3, "log7.txt"));
        programStrings.add(heapEx3.toString());

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
        controllers.add(makeControllerForStmt(heapEx4, "log8.txt"));
        programStrings.add(heapEx4.toString());

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
        controllers.add(makeControllerForStmt(heapGcEx, "log9.txt"));
        programStrings.add(heapGcEx.toString());

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
        controllers.add(makeControllerForStmt(heapGcEx2, "log10.txt"));
        programStrings.add(heapGcEx2.toString());

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
        controllers.add(makeControllerForStmt(whileEx, "log11.txt"));
        programStrings.add(whileEx.toString());

        // Fork example
        // int v; Ref int a; v=10; new(a,22);
        // fork(wH(a,30); v=32; print(v); print(rH(a)));
        // print(v); print(rH(a))
        Statement forkExample = new CompoundStatement(
                new VariableDeclarationStatement(SimpleType.INTEGER, "v"),
                new CompoundStatement(
                        new VariableDeclarationStatement(new ReferenceType(SimpleType.INTEGER), "a"),
                        new CompoundStatement(
                                new AssignmentStatement("v", new ValueExp(new IntegerValue(10))),
                                new CompoundStatement(
                                        new HeapDeclarationStatement("a", new ValueExp(new IntegerValue(22))),
                                        new CompoundStatement(
                                                new ForkStatement(
                                                        new CompoundStatement(
                                                                new HeapWriteStatement("a", new ValueExp(new IntegerValue(30))),
                                                                new CompoundStatement(
                                                                        new AssignmentStatement("v", new ValueExp(new IntegerValue(32))),
                                                                        new CompoundStatement(
                                                                                new PrintStatement(new VarExp("v")),
                                                                                new PrintStatement(new HeapReadExp(new VarExp("a")))
                                                                        )
                                                                )
                                                        )
                                                ),
                                                new CompoundStatement(
                                                        new PrintStatement(new VarExp("v")),
                                                        new PrintStatement(new HeapReadExp(new VarExp("a")))
                                                )
                                        )
                                )
                        )
                )
        );
        controllers.add(makeControllerForStmt(forkExample, "log12.txt"));
        programStrings.add(forkExample.toString());

        // Small example that intentionally fails the typechecker:
        // int x; x = true;
        Statement typecheckFailEx = new CompoundStatement(
                new VariableDeclarationStatement(SimpleType.INTEGER, "x"),
                new AssignmentStatement("x", new ValueExp(new BooleanValue(true)))
        );
        controllers.add(makeControllerForStmt(typecheckFailEx, "log13.txt"));
        programStrings.add(typecheckFailEx.toString());
    }

    private ControllerInterface makeControllerForStmt(Statement stmt, String logFile) {
        Dictionary<String, Type> typeEnv = new Dictionary<>();
        try {
            stmt.typecheck(typeEnv);
            ExecutionStack stack = new ListExecutionStack();
            stack.push(stmt);
            SymbolTable symTable = new MapSymbolTable();
            Out out = new ListOut();
            FileTable fileTable = new MapFileTable();
            Heap heap = new HeapMemory();
            ProgramState prg = new ProgramState(stack, symTable, out, fileTable, heap);
            RepositoryInterface repo = new Repository(prg, logFile);
            return new Controller(repo);
        } catch (TypeCheckException e) {
            // return a dummy controller that prints the error when run
            return new Controller(new Repository(prgStub(stmt), logFile));
        }
    }

    private ProgramState prgStub(Statement stmt) {
        ExecutionStack stack = new ListExecutionStack();
        stack.push(stmt);
        SymbolTable symTable = new MapSymbolTable();
        Out out = new ListOut();
        FileTable fileTable = new MapFileTable();
        Heap heap = new HeapMemory();
        return new ProgramState(stack, symTable, out, fileTable, heap);
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static class MapEntry {
        private final Integer key;
        private final String value;

        public MapEntry(Integer key, String value) {
            this.key = key;
            this.value = value;
        }

        public Integer getKey() { return key; }
        public String getValue() { return value; }
    }

    public static class VarEntry {
        private final String name;
        private final String value;

        public VarEntry(String name, String value) {
            this.name = name;
            this.value = value;
        }

        public String getName() { return name; }
        public String getValue() { return value; }
    }
}
