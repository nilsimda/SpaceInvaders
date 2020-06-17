package src.model;

import java.time.LocalDateTime;
import java.util.stream.Stream;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.*;

public class DataManager {
	
	private static final Path scorefile = Paths.get("..", "resources", "scorefile.txt");
	
	/**
	 * Save a new tuple of score and time to scorefile.
	 * @param score the achieved score
	 * @param time the time at which the game was played
	 */
	public void saveData(int score, LocalDateTime time) {
		try(BufferedWriter bw = Files.newBufferedWriter(scorefile, Charset.forName("UTF-8"),
				StandardOpenOption.APPEND, StandardOpenOption.CREATE)) {
			bw.append(new Score(score, time).toString() + "\n");
			bw.flush();
		}
		catch(IOException exc) {
			System.out.println("An error occured and the score data could not be saved:");
			System.out.println(exc.getMessage());
		}
	}
	
	/**
	 * Read all the score information from scorefile.txt and return it as a Stream of Score objects.
	 */
	public Stream<Score> getData() {
		Stream<String> lines = Stream.of("0000-00-00T00:00:00\t0");
		
		try(BufferedReader br = Files.newBufferedReader(scorefile, Charset.forName("UTF-8"))) {
			lines = br.lines();
		}
		catch(IOException exc) {
			System.out.println("An error occured while reading the score data from the file system:");
			System.out.println(exc.getMessage());
		}
		
		return lines.map(s -> Score.parse(s));
	}
}
