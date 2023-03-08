package telran.io;

public class TransferCopyApp {
	public static void main(String[] args) {
		FilesCopyBuilder builder = new FilesCopyBuilder();
		Copy copy = builder.build("transfer", args);
		copy.copyRun();
	}
}
