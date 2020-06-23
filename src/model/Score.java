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
		return name + "\t" + time.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) + "\t" + score;
	}
	
	/**
	 * Parses a String to a Score object. The String has to have the following form: 
	 * name\tISO_LOCAL_DATE_TIME\tScore where Score is an int
	 * @return
	 */
	public static Score parse(String s) {
		//TODO
		
		return null;
	}
}
