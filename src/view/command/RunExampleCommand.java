package view.command;

import controller.ControllerInterface;

public class RunExampleCommand extends Command {
    private final ControllerInterface ctr;
    public RunExampleCommand(String key, String desc, ControllerInterface ctr){
        super(key, desc);
        this.ctr=ctr;
    }
    @Override
    public void execute() {
        try{
            ctr.allStep(); }

        catch (Exception e){
            System.out.println("Error: "+e.getMessage());}
        }
}
