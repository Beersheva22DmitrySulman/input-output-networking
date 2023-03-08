package telran.io;

public class FilesCopyBuilder {
	public Copy build(String type, String[] args) {
		if (args.length < 2) {
			throw new IllegalArgumentException("Not enough arguments");
		}
		String srcFilePath = args[0];
		String destFilePath = args[1];
		boolean overwrite = getOverwrite(args);
		
		return switch (type) {
			case "files" -> new FilesCopy(srcFilePath, destFilePath, overwrite);
			case "transfer" -> new TransferCopy(srcFilePath, destFilePath, overwrite);
			case "buffer" -> new BufferCopy(srcFilePath, destFilePath, overwrite, getBuffer(args));
			default -> throw new IllegalArgumentException("Illegal copy type");
		};
	}

	private int getBuffer(String[] args) {
		int res = 1_000_000;
		if (args.length > 3) {
			res = Integer.parseInt(args[3]);
		}
		
		return res;
	}

	private boolean getOverwrite(String[] args) {
		boolean res = false;
		if (args.length > 2) {
			String override = args[2].toLowerCase();
			if ("true".equals(override)) {
				res = true;
			} else if (!"false".equals(override)) {
				throw new IllegalArgumentException();
			}
		}

		return res;
	}
}
