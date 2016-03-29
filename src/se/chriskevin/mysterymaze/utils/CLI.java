package se.chriskevin.mysterymaze.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by CHSU7648 on 2016-03-14.
 */
public class CLI {

    private Map<String, CliAction> actions;
    private boolean enabled;
    private String currentCommand;
    private List<String> history;

    public CLI() {
        this.enabled = false;
        this.currentCommand = "";
        history = new ArrayList<>();
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public void isEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getCurrentCommand() {
        return this.currentCommand;
    }

    public void setCurrentCommand(String currentCommand) {
        this.currentCommand = currentCommand;
    }

    public List<String> getHistory() {
        return this.history;
    }

    public void setActions(Map<String, CliAction> actions) {
        this.actions = actions;
    }

    /**
     * Executes the current command and returns a message.
     * @return A message with feedback from the executed
     */
    public String run() {
        if (actions != null && actions.size() > 0) {
            history.add(currentCommand);
            String msg = actions.get(currentCommand).execute();
            currentCommand = "";
            return msg;
        } else {
            currentCommand = "";
            return "Unknown command";
        }
    }
}

