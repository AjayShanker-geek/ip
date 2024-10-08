package org.ajay.commands;

import org.ajay.data.TaskList;
import org.ajay.data.exceptions.EmptyArgumentException;
import org.ajay.data.exceptions.InvalidCommandFormatException;
import org.ajay.data.task.Event;
import org.ajay.parser.Parser;
import org.ajay.storage.Storage;
import org.ajay.ui.TextUi;

public class EventCommand extends Command{

    public static final String COMMAND_WORD = Event.COMMAND_STRING;
    public static final String MESSAGE_USAGE = """
                                               Creates an event task.
                                               Example: """ + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Event has been created!";

    @Override
    public void execute(TaskList tasks, TextUi ui, Storage storage) {
        try {
            tasks.addTask(new Event(Parser.task));
        } catch (EmptyArgumentException e) {
            ui.printExceptions(e.getMessage());
        } catch (InvalidCommandFormatException e) {
            ui.printExceptions(e.getMessage());
        }

        System.out.println("Got it. I've added this task:");
        System.out.println("  " + tasks.getLatestTask().toString());
        TaskList.printNumberOfTasks();

        storage.saveTaskList(tasks.getTaskList());
    }

}
