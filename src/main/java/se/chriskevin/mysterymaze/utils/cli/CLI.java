package se.chriskevin.mysterymaze.utils.cli;

import io.vavr.collection.Map;
import io.vavr.control.Option;
import io.vavr.control.Try;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

public class CLI {

  private boolean enabled;
  private String currentCommand;
  private String status;
  private final List<Command> history;
  private int historyIndex;
  private final Map<Command, Supplier<String>> commands;

  public CLI(final Map<Command, Supplier<String>> commands) {
    Objects.requireNonNull(commands);

    this.enabled = false;
    this.commands = commands;
    this.currentCommand = "";
    this.history = new ArrayList<>();
    this.historyIndex = -1;
    this.status = "";
  }

  public boolean isEnabled() {
    return this.enabled;
  }

  public void isEnabled(final boolean enabled) {
    this.enabled = enabled;
  }

  public String getCurrentCommand() {
    return this.currentCommand;
  }

  public String getStatus() {
    return this.status;
  }

  public String historyPrevious() {
    if (!history.isEmpty()) {
      historyIndex -= 1;
      return history.get(historyIndex).toString();
    } else {
      return currentCommand;
    }
  }

  public String historyNext() {
    if (history.size() - 1 > historyIndex + 1) {
      historyIndex += 1;
      return history.get(historyIndex).toString();
    } else {
      return currentCommand;
    }
  }

  private void updateHistory(final Command command) {
    history.add(command);
    historyIndex = history.size() - 1;
  }

  public String input(final Character character) {
    currentCommand = currentCommand + character;
    return currentCommand;
  }

  public String backspace() {
    currentCommand = currentCommand.length() >= 1 ? currentCommand.substring(0, currentCommand.length() - 1) : "";
    return currentCommand;
  }

  /**
   * Executes the current command and returns a message.
   *
   * @return A message with feedback from the executed
   */
  public String run() {
    final var response =
        Try.of(() -> Command.valueOf(currentCommand))
            .andThen(this::updateHistory)
            .map(
                command ->
                    commands
                        .get(command)
                        .map(Supplier::get)
                        .getOrElse("No given action for command " + command))
            .andThen(() -> currentCommand = "")
            .getOrElse("Unknown command");

    status = response;
    return response;
  }
}
