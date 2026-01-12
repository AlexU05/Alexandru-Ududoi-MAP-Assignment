package controller;

import model.exception.InterpreterException;
import model.statement.Statement;
import repository.RepositoryInterface;
import state.ExecutionStack;
import state.ProgramState;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class Controller implements ControllerInterface {
    private final RepositoryInterface repositoryInterface;
    private boolean displayFlag;
    private ExecutorService executor;

    public Controller(RepositoryInterface repositoryInterface) {
        this.repositoryInterface = repositoryInterface;
        this.displayFlag = true;
    }

    private List<ProgramState> removeCompletedPrg(List<ProgramState> inPrgList) {
        return inPrgList.stream()
                .filter(ProgramState::isNotCompleted)
                .collect(Collectors.toList());
    }

    private void oneStepForAllPrg(List<ProgramState> prgList) {
        prgList.forEach(prg -> {
            try {
                repositoryInterface.logPrgStateExec(prg);
            } catch (IOException e) {
                throw new InterpreterException(e.toString());
            }
        });
        List<Callable<ProgramState>> callList = prgList.stream()
                .map((ProgramState p) -> (Callable<ProgramState>)(() -> p.oneStep()))
                .collect(Collectors.toList());

        try {
            List<Future<ProgramState>> futures = executor.invokeAll(callList);
            List<ProgramState> newPrgList = new ArrayList<>();
            for (Future<ProgramState> f : futures) {
                try {
                    ProgramState r = f.get();
                    if (r != null) newPrgList.add(r);
                } catch (ExecutionException e) {
                    throw new InterpreterException(e.getCause().toString());
                }
            }

            prgList.addAll(newPrgList);

            prgList.forEach(prg -> {
                try {
                    repositoryInterface.logPrgStateExec(prg);
                } catch (IOException e) {
                    throw new InterpreterException(e.toString());
                }
            });

            repositoryInterface.setPrgList(prgList);
        } catch (InterruptedException e) {
            throw new InterpreterException(e.toString());
        }
    }

    @Override
    public void allStep() {
        List<ProgramState> prgList = removeCompletedPrg(repositoryInterface.getPrgList());
        executor = Executors.newFixedThreadPool(2);

        while (!prgList.isEmpty()) {
            oneStepForAllPrg(prgList);

            try {
                var allSymValues = repositoryInterface.getPrgList().stream()
                        .flatMap(p -> p.symbolTable().getContent().values().stream())
                        .collect(Collectors.toList());

                var heapContent = repositoryInterface.getPrgList().get(0).heap().getContent();
                var newHeap = GarbageCollector.safeGarbageCollector(allSymValues, heapContent);

                repositoryInterface.getPrgList().forEach(p -> p.heap().setContent(newHeap));
            } catch (Exception e) {
                throw new InterpreterException(e.toString());
            }

            prgList = removeCompletedPrg(repositoryInterface.getPrgList());
        }

        executor.shutdownNow();
        repositoryInterface.setPrgList(prgList);
    }

    @Override
    public void displayCurrentState() {
        List<ProgramState> prgList = repositoryInterface.getPrgList();
        prgList.forEach(p -> System.out.println(p + "\n"));
    }

    @Override
    public void setDisplayFlag(boolean flag) {
        displayFlag = flag;
    }

    @Override
    public boolean getDisplayFlag() {
        return displayFlag;
    }

    @Override
    public void oneStep() {
        repositoryInterface.setPrgList(removeCompletedPrg(repositoryInterface.getPrgList()));
        List<ProgramState> prgList = repositoryInterface.getPrgList();
        if (prgList.isEmpty()) {
            // throw new InterpreterException("All programs have completed execution.");
            // ^ if you want to not erase everything accidentally with one extra click uncomment this
            return;
        }
        executor = Executors.newFixedThreadPool(2);
        oneStepForAllPrg(prgList);
        try {
            var allSymValues = repositoryInterface.getPrgList().stream()
                    .flatMap(p -> p.symbolTable().getContent().values().stream())
                    .collect(Collectors.toList());

            var heapContent = repositoryInterface.getPrgList().get(0).heap().getContent();
            var newHeap = GarbageCollector.safeGarbageCollector(allSymValues, heapContent);

            repositoryInterface.getPrgList().forEach(p -> p.heap().setContent(newHeap));
        } catch (Exception e) {
            throw new InterpreterException(e.toString());
        }


        executor.shutdownNow();
    }

    @Override
    public List<ProgramState> getProgramStates() {
        return repositoryInterface.getPrgList();
    }
}
