package telran.io;

import java.io.IOException;

public class FilesCopyApp {
	public static void main(String[] args) {
		FilesCopyBuilder builder = new FilesCopyBuilder();
		Copy copy = builder.build("files", args);

		try {
			long timeStart = System.currentTimeMillis();
			long size = copy.copy();
			long timeEnd = System.currentTimeMillis();
			DisplayResult displayResult = copy.getDisplayResult(timeEnd - timeStart, size);
			System.out.println(displayResult);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
