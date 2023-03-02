package telran.io.test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class InputOutputTest {

	private static final int MARGIN_LENGTH = 3;

	private String fileName = "myFile";
	private String directoryName = "myDirectory1/myDirectory2";

	@AfterEach
	void tearDown() throws Exception {
		new File(fileName).delete();
		new File(directoryName).delete();
	}

	@Test
	@Disabled
	void test() throws IOException {
		File f1 = new File(fileName);
		assertTrue(f1.createNewFile());

		File dir1 = new File(directoryName);
		assertTrue(dir1.mkdirs());
		System.out.println(dir1.getAbsolutePath());
	}

	@Test
	void printDirectoryFilesTest() throws IOException {
		printDirectoryFiles(".", 0);
	}

	void printDirectoryFiles(String path, int maxLevel) throws IOException {
		if (maxLevel < 1) {
			maxLevel = Integer.MAX_VALUE;
		}
		Path dir = Path.of(path).toAbsolutePath().normalize();
		Files.walkFileTree(dir, Collections.emptySet(), maxLevel, new PrintFileVisitor(dir));
	}

	@Test
	void printDirectoryFileTest() throws IOException {
		printDirectoryFile(".", 0);
	}

	void printDirectoryFile(String path, int maxLevel) throws IOException {
		if (maxLevel < 1) {
			maxLevel = Integer.MAX_VALUE;
		}
		File dir = new File(path).getCanonicalFile();
		printDirectoryFile(dir, maxLevel, 0);
	}

	void printDirectoryFile(File file, int maxLevel, int level) {
		if (level <= maxLevel) {
			String type = file.isDirectory() ? "dir" : "file";
			System.out.printf("%s%s - %s\n", " ".repeat(level * MARGIN_LENGTH), file.getName(), type);
			if (file.isDirectory()) {
				File[] children = file.listFiles();
				Arrays.stream(children).forEach(child -> printDirectoryFile(child, maxLevel, level + 1));
			}
		}
	}

}
