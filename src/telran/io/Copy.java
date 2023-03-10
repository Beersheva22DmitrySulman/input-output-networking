package telran.io;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;

abstract public class Copy {
	protected final String srcFilePath;
	protected final String destFilePath;
	protected final boolean overwrite;
	
	public Copy(String srcFilePath, String destFilePath, boolean overwrite) {
		this.srcFilePath = srcFilePath;
		this.destFilePath = destFilePath;
		this.overwrite = overwrite;
	}
	
	abstract public long copy() throws IOException;
	
	abstract public DisplayResult getDisplayResult(long copyTime, long fileSize);
	
	public void copyRun() {
		try {
			checkDestFile();
			long timeStart = System.currentTimeMillis();
			long size = copy();
			long timeEnd = System.currentTimeMillis();
			DisplayResult displayResult = getDisplayResult(timeEnd - timeStart, size);
			System.out.println(displayResult);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	private void checkDestFile() throws FileAlreadyExistsException {
		if (!overwrite && Files.exists(Path.of(destFilePath))) {
			throw new FileAlreadyExistsException("File already exist");
		}
	}
	
	public String getSrcFilePath() {
		return srcFilePath;
	}
		
	public String getDestFilePath() {
		return destFilePath;
	}
	
	public boolean isOverwrite() {
		return overwrite;
	}
	
}
