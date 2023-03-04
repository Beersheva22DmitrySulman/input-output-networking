package telran.io.test;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

public class PrintFileVisitor extends SimpleFileVisitor<Path> {
	private final int rootDirNameCount;
	private static final int MARGIN_LENGTH = 3;

	public PrintFileVisitor(int rootDirNameCount) {
		this.rootDirNameCount = rootDirNameCount;
	}

	@Override
	public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
		printNode(dir);
		return FileVisitResult.CONTINUE;
	}

	@Override
	public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
		printNode(file);
		return FileVisitResult.CONTINUE;
	}

	private void printNode(Path node) {
		int margin = (node.getNameCount() - rootDirNameCount) * MARGIN_LENGTH;
		String type = Files.isDirectory(node) ? "dir" : "file";
		System.out.printf("%s%s - %s\n", " ".repeat(margin), node.getFileName(), type);
	}
}
