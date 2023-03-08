package telran.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class FilesCopy extends Copy {

	public FilesCopy(String srcFilePath, String destFilePath, boolean overwrite) {
		super(srcFilePath, destFilePath, overwrite);
	}

	@Override
	public long copy() throws IOException {
		Path src = Path.of(srcFilePath);
		Path dest = Path.of(destFilePath);
		if (overwrite) {
			Files.copy(src, dest, StandardCopyOption.REPLACE_EXISTING);
		} else {
			Files.copy(src, dest);
		}
		
		return Files.size(src);
	}

	@Override
	public DisplayResult getDisplayResult(long copyTime, long fileSize) {
		return new DisplayResult(fileSize, copyTime);
	}

}
