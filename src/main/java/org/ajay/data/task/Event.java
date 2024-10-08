package org.ajay.data.task;

import org.ajay.data.exceptions.EmptyArgumentException;
import org.ajay.data.exceptions.Error;
import org.ajay.data.exceptions.InvalidCommandFormatException;

public class Event extends Task {
    public final static String COMMAND_STRING = "event"; // Command string for the Event class
    final static String FROM_KEYWORD_STRING = "/from"; // Keyword string for the from date
    final static String TO_KEYWORD_STRING = "/to"; // Keyword string for the to date
    public final static String TASK_STRING = "E";

    protected String from;
    protected String to;

    /**
     * Constructor for the Event class.
     *
     * @param description description of the event
     *
     * @throws EmptyArgumentException
     * @throws InvalidCommandFormatException
     */
    public Event(String description) throws EmptyArgumentException, InvalidCommandFormatException {
        super(getDescriptionFromString(description));
        setFrom(getFromFromString(description));
        setTo(getToFromString(description));

    }

    /**
     * Constructor for the Event class.
     *
     * @param description description of the event
     * @param from        from date
     * @param to          to date
     */
    public Event(String description, String from, String to) throws EmptyArgumentException {
        super(description);
        setFrom(from);
        setTo(to);

    }

    /**
     * Constructor for the Event class.
     *
     * @param description description of the event
     * @param from        from date
     * @param to          to date
     */
    public Event(boolean isDone, String description, String from, String to) throws EmptyArgumentException {
        super(description);
        super.setDoneState(isDone);
        setFrom(from);
        setTo(to);
    }

    /**
     * Returns the from date.
     *
     * @return from date
     */
    public String getFrom() {
        return from;
    }

    /**
     * Changes the from date.
     *
     * @param from
     */
    public void setFrom(String from) {
        this.from = from;
    }

    /**
     * Returns the to date.
     *
     * @return
     */
    public String getTo() {
        return to;
    }

    /**
     * Changes the to date.
     *
     * @param to
     */
    public void setTo(String to) {
        this.to = to;
    }

    /**
     * Returns the description from the input string.
     *
     * @param input input string
     *
     * @return description
     */
    public static String getDescriptionFromString(String input) throws InvalidCommandFormatException {

        if (input == null) {
            return null;
        }

        int indexOfFrom = input.indexOf(FROM_KEYWORD_STRING);
        input = input.substring(0, indexOfFrom);

        return input.trim();
    }

    /**
     * Returns the from date from the input string.
     *
     * @param input input string
     *
     * @return
     */
    public static String getFromFromString(String input) throws InvalidCommandFormatException {

        if (input == null || !input.contains(FROM_KEYWORD_STRING)) {
            throw new InvalidCommandFormatException(
                    "Invalid command format. Please include the from date. " + Error.INVAILD_COMMAND_FORMAT.toString());
        }

        int indexAfterFrom = input.indexOf(FROM_KEYWORD_STRING) + FROM_KEYWORD_STRING.length();
        int indexOfTo = input.indexOf(TO_KEYWORD_STRING);

        return input.substring(indexAfterFrom, indexOfTo).trim();
    }

    /**
     * Returns the to date from the input string.
     *
     * @param input input string
     *
     * @return
     */
    public static String getToFromString(String input) throws InvalidCommandFormatException {

        if (input == null || !input.contains(TO_KEYWORD_STRING)) {
            throw new InvalidCommandFormatException(
                    "Invalid command format. Please include the to date. " + Error.INVAILD_COMMAND_FORMAT.toString());
        }

        int indexAfterTo = input.indexOf(TO_KEYWORD_STRING) + TO_KEYWORD_STRING.length();

        return input.substring(indexAfterTo).trim();
    }

    @Override
    public String saveTaskString() {
        return TASK_STRING + " | " + (super.getDoneState() ? "1" : "0") + " | " + description + " | " + from + " | "
                + to;
    }

    public static Event loadTaskString(boolean isDone, String description, String from, String to)
            throws EmptyArgumentException {
        Event event = new Event(isDone, description, from, to);
        return event;
    }

    @Override
    public String toString() {
        return "[" + TASK_STRING + "]" + super.toString() + " (from: " + from + " to: " + to + ")";
    }
}
