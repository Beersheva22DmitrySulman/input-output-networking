package telran.io;

import java.io.IOException;

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
	
	public void copyRun() throws IOException {
		copy();
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
