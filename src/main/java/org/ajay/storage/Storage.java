package org.ajay.storage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import org.ajay.common.Messages;
import org.ajay.data.exceptions.EmptyArgumentException;
import org.ajay.data.exceptions.IllegalArgumentException;
import org.ajay.data.task.Deadline;
import org.ajay.data.task.Event;
import org.ajay.data.task.Task;
import org.ajay.data.task.Todo;
import org.ajay.ui.TextUi;

public class Storage {

    private static String filePath;

    public static String getFilePath() {
        return filePath;
    }

    public static void setFilePath(String filePath) {
        Storage.filePath = filePath;
    }

    public Storage() {
    }

    public Storage(String filePath) {
        setFilePath(filePath);
    }

    public ArrayList<Task> load() throws IllegalArgumentException {
        ArrayList<Task> taskList = new ArrayList<>();

        // Check if the file exists
        File f = new File(filePath);
        if (!f.exists()) {
            try {
                f.getParentFile().mkdirs();
                f.createNewFile();
            } catch (IOException e) {
                TextUi.printExceptions(Messages.MESSAGE_FILE_NOT_FOUND + " " + e.getMessage());
                TextUi.exit(0);
            }
        }

        // Try block to check for exceptions
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {

                // Splitting the data
                String[] task = line.split(" \\| ");

                boolean isDone = false;
                if (task[1].equals("1")) {
                    isDone = true;
                }

                switch (task[0]) {
                    case Todo.TASK_STRING:
                        if (task.length < 3) {
                            throw new IllegalArgumentException(Messages.MESSAGE_IMPORT_TODO_FAILED);
                        }

                        taskList.add(Todo.loadTaskString(isDone, task[2]));

                        break;
                    case Event.TASK_STRING:
                        if (task.length < 5) {
                            throw new IllegalArgumentException(Messages.MESSAGE_IMPORT_EVENT_FAILED);
                        }

                        taskList.add(Event.loadTaskString(isDone, task[2], task[3], task[4]));
                        break;
                    case Deadline.TASK_STRING:
                        if (task.length < 4) {
                            throw new IllegalArgumentException(Messages.MESSAGE_IMPORT_DEADLINE_FAILED);
                        }

                        taskList.add(Deadline.loadTaskString(isDone, task[2], task[3]));
                        break;
                    default:
                        throw new AssertionError();
                }
            }
        } catch (IOException e) {
            TextUi.printExceptions(Messages.MESSAGE_FILE_NOT_READ + " " + e.getMessage());
        } catch (EmptyArgumentException e) {
            TextUi.printExceptions(e.getMessage());
        }

        return taskList;

    }

    public void saveTaskList(ArrayList<Task> taskList) {

        try (FileWriter fw = new FileWriter(filePath)) {
            for (Task task : taskList) {
                fw.write(task.saveTaskString() + "\n");
            }
        } catch (IOException e) {
            TextUi.printExceptions("Unable to write to file: " + filePath);
        }
    }
}
