package telran.io;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class BufferCopy extends Copy {
	private final int bufferSize;

	public BufferCopy(String srcFilePath, String destFilePath, boolean overwrite, int bufferSize) {
		super(srcFilePath, destFilePath, overwrite);
		this.bufferSize = bufferSize;
	}

	@Override
	public long copy() throws IOException {
		byte[] buffer = new byte[bufferSize];
		long res = 0L;
		try (InputStream input = new FileInputStream(srcFilePath);
				OutputStream output = new FileOutputStream(destFilePath)) {
			int len = input.read(buffer);
			while (len > 0) {
				output.write(buffer, 0, len);
				res += len;
				len = input.read(buffer);
			}
		}
		
		return res;
	}

	@Override
	public DisplayResult getDisplayResult(long copyTime, long fileSize) {
		return new DisplayResultBuffer(fileSize, copyTime, bufferSize);
	}

	public int getBufferSize() {
		return bufferSize;
	}

}
