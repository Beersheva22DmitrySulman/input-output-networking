package telran.io;

public class DisplayResult {
	protected final long fileSize;
	protected final long copyTime;
	
	public DisplayResult(long fileSize, long copyTime) {
		this.fileSize = fileSize;
		this.copyTime = copyTime;
	}

	public long getFileSize() {
		return fileSize;
	}

	public long getCopyTime() {
		return copyTime;
	}

	@Override
	public String toString() {
		return "DisplayResult [fileSize=" + fileSize + ", copyTime=" + copyTime + "]";
	}
}
