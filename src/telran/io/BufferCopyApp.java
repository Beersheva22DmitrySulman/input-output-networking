package telran.io;

public class BufferCopyApp {
	public static void main(String[] args) {
		FilesCopyBuilder builder = new FilesCopyBuilder();
		Copy copy = builder.build("buffer", args);
		copy.copyRun();
	}
}
