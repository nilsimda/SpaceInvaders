package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/*
 * a helper class to conviently deal with the score data as a tuple of score (int) and time (LocalDateTime)
 */
public class Score {

    private int score;
    private LocalDateTime time;
    private String name;

    public Score(int score, LocalDateTime time, String name) {
        this.score = score;
        this.time = time;
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public LocalDateTime getTime() {
        return time;
    }

    @Override
    public String toString() {
        return name + "," + time.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) + "," + score;
    }

    /**
     * Parses a String to a Score object. The String has to have the following form:
     * name,ISO_LOCAL_DATE_TIME,Score where Score is an int
     *
     * @return the resulting Score-Object. Score with 0 value when there was a Parsing-Error.
     */
    public static Score parse(String s) {
        String[] input = s.split(",");
        if (input.length != 3) return new Score(0, LocalDateTime.MIN, "PARSING_ERROR");
        try {
            int score = Integer.parseInt(input[0].trim());
            LocalDateTime time = LocalDateTime.parse(input[1]);
            String name = input[2].trim();
            return new Score(score, time, name);
        } catch (ClassCastException e) {
            return new Score(0, LocalDateTime.MIN, "PARSING_ERROR");
        }
    }
}
