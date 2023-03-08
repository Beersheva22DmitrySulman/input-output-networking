package telran.io;

public class FilesCopyApp {
	public static void main(String[] args) {
		FilesCopyBuilder builder = new FilesCopyBuilder();
		Copy copy = builder.build("files", args);
		copy.copyRun();
	}
}
