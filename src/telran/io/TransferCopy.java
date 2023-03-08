package telran.io;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;

public class TransferCopy extends Copy {

	public TransferCopy(String srcFilePath, String destFilePath, boolean overwrite) {
		super(srcFilePath, destFilePath, overwrite);
	}

	@Override
	public long copy() throws IOException {
		checkDestFile();
		long res;
		try (InputStream input = new FileInputStream(srcFilePath);
				OutputStream output = new FileOutputStream(destFilePath)) {
			res = input.transferTo(output);
		}
		
		return res;
	}
	
	private void checkDestFile() throws FileAlreadyExistsException {
		if (!overwrite) {
			if (Files.exists(Path.of(destFilePath))) {
				throw new FileAlreadyExistsException("File already exist");
			}
		}
	}

	@Override
	public DisplayResult getDisplayResult(long copyTime, long fileSize) {
		return new DisplayResult(fileSize, copyTime);
	}

}
