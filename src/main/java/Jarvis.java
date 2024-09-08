import java.util.Scanner;

public class Jarvis {
    // Constants
    static final int MAX_TASK_LENGTH = 100; // Maximum length of a string

    private static final String chatBotName = "Jarvis"; // Name of the chatbot

    private static Task[] taskList = new Task[MAX_TASK_LENGTH]; // Array to store tasks

    static String command; // Variable to store the command
    static String task; // Variable to store the task

    /**
     * Prints a break line to the console.
     */
    private static void printBreakLine() {
        System.out.println("────────────────────────────────────────────────────────────");
    }

    /**
     * Gracefully exits the program.
     *
     * @param status
     */
    private static void exit(int status) {
        System.exit(status);
    }

    /**
     * Prints the prompt to the console for visual marker for user to type.
     */
    private static void printPrompt() {
        System.out.print("  ");
    }

    /**
     * Prints the greeting messages to the console.
     */
    private static void printGreetingMsgs() {
        String[] greetings = {"Hello! I'm " + chatBotName + "\nWhat can I do for you?"}; // List of greetings

        // Print the greetings
        for (String greeting : greetings) {
            printBreakLine(); // Print a break line before each greeting
            System.out.println(greeting); // Print the greeting
        }
        printBreakLine();
    }

    /**
     * Prints the goodbye messages to the console.
     */
    private static void printGoodbyeMsgs() {
        String[] goodbyes = {"Bye. Hope to see you again soon!"}; // List of goodbye messages

        // Print the goodbye messages
        for (String goodbye : goodbyes) {
            printBreakLine();
            System.out.println(goodbye); // Print the goodbye message
        }
        printBreakLine();
        exit(0);
    }

    /**
     * Prints the tasks to the console.
     */
    public static void printTasks() {
        printBreakLine();
        Task.printAllTasks(taskList);
        printBreakLine();
    }


    /**
     * Splits the command and task from the input.
     *
     * @param lineBufferString
     */
    public static void splitCommandAndTask(String lineBufferString) {
        if (lineBufferString.contains(" ")) {
            command = lineBufferString.split(" ")[0];
            task = lineBufferString.substring(command.length() + 1);
        } else {
            command = lineBufferString;
            task = null;
        }
    }

    /**
     * Reads the input from the user and processes it.
     *
     * @param in
     * @param lineBufferString
     */
    public static void readInput(Scanner in, String lineBufferString) {
        try (in) {
            printPrompt(); // Print the prompt to the console
            lineBufferString = in.nextLine();
            splitCommandAndTask(lineBufferString);

            switch (command) {
            case "bye":
                printGoodbyeMsgs();
                break;
            case "exit": // Habit of typing exit to exit the program
                printGoodbyeMsgs();
                break;
            case Task.LIST_COMMAND_STRING: // List all the tasks
                printTasks();
                break;
            case Todo.COMMAND_STRING: // Add a todo task
                taskList[Task.getNumberOfTasks()] = new Todo(task);
                printBreakLine();
                break;
            case Deadline.COMMAND_STRING: // Add a deadline task
                taskList[Task.getNumberOfTasks()] = new Deadline(task);
                printBreakLine();
                break;
            case Event.COMMAND_STRING: // Add an event task
                taskList[Task.getNumberOfTasks()] = new Event(task);
                printBreakLine();
                break;
            case Task.MARK_COMMAND_STRING: // Mark the task as done
                int taskNumberMark = Integer.parseInt(task); // Get the task number
                Task.markAsDone(taskList, taskNumberMark); // Mark the task as done
                break;
            case Task.UNMARK_COMMAND_STRING: // Mark the task as undone
                int taskNumberUnmark = Integer.parseInt(task); // Get the task number
                Task.markAsUndone(taskList, taskNumberUnmark); // Mark the task as undone
                break;
            case "":
                System.out.println("You did not enter anything. Please try again.");
                break;
            default:
                break;
            }

            readInput(in, lineBufferString); // Recursively call the function to read the next input
        } catch (Exception e) {
            System.err.println("An error occurred. Please try again.");
        }
    }

    public static void main(String[] args) {
        String logo = """
                                  @@@@@@@@@@@@@@@@@@@@@@@
                              @@%     @@          @@      @@@
                           @@         @@          @@          @@
                        @@            @@          @@             @@
                      @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
                     @                                              *@
                    @@@                                             @@@
                   @  @@@                                         @@@  @
                  @&    @@         @@@@@@@@@@@@@@@@@@@@@         @@.   @@
                  @      @@@        @@              @@         @@@      @
                  @       *@@         @@           @(         @@(       @
                  @       ,@@@@        @@        @@         @@@@        @
                  @   @@@@@  %@@         @@     @          @@& @@@@@    @
                  @@@@         @@@        /@  @@         @@@       *@@@@/
                   @@           @@@         @@          @@@           @@
                    @@            @@&                 (@@            @@
                      @       @@@@@@@@               @@@@@@@        @
                       @@ @@@@*      @@,            @@      @@@@  @&
                          @@          @@@         @@@          @@
                            @@@         @@       @@         @@#
                                @@@@     @@@   @@@     @@@@
                                       @@@@@@@@@@@@@

                     ██╗    █████╗    ██████╗   ██╗   ██╗  ██╗   ███████╗
                     ██║   ██╔══██╗   ██╔══██╗  ██║   ██║  ██║   ██╔════╝
                     ██║   ███████║   ██████╔╝  ██║   ██║  ██║   ███████╗
                ██   ██║   ██╔══██║   ██╔══██╗  ╚██╗ ██╔╝  ██║   ╚════██║
                ╚█████╔╝██╗██║  ██║██╗██║  ██║██╗╚████╔╝██╗██║██╗███████║██╗
                 ╚════╝ ╚═╝╚═╝  ╚═╝╚═╝╚═╝  ╚═╝╚═╝ ╚═══╝ ╚═╝╚═╝╚═╝╚══════╝╚═╝
                """;

        String lineBufferString = ""; // Buffer to store the input from the user
        Scanner in = new Scanner(System.in); // Scanner object to read input from the user

        System.out.println("Hello from\n" + logo);
        printGreetingMsgs();
        readInput(in, lineBufferString); // Read the input from the user
    }
}
