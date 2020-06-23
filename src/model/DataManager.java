package model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.util.stream.Stream;

public class DataManager {
	
	private static final Path scorefile = Paths.get("..", "resources", "scorefile.csv");
	
	/**
	 * Save a new tuple of score and time to scorefile.
	 * @param score the achieved score
	 * @param time the time at which the game was played
	 */
	public void saveData(int score, LocalDateTime time, String name) {
		try(BufferedWriter bw = Files.newBufferedWriter(scorefile, StandardCharsets.UTF_8,
				StandardOpenOption.APPEND, StandardOpenOption.CREATE)) {
			bw.append(new Score(score, time, name).toString() + "\n");
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
		Stream<String> lines = Stream.of("unknown\t0000-00-00T00:00:00\t0");
		
		try(BufferedReader br = Files.newBufferedReader(scorefile, StandardCharsets.UTF_8)) {
			lines = br.lines();
		}
		catch(IOException exc) {
			System.out.println("An error occured while reading the score data from the file system:");
			System.out.println(exc.getMessage());
		}
		
		return lines.map(Score::parse);
	}
}
