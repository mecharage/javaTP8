package tp8;

import java.io.OutputStream;
import java.io.PrintStream;

public class CppConsoleOutput implements CppOutput {

	private final OutputStream _stream;

	CppConsoleOutput(OutputStream stream) {
		_stream = stream;
	}

	@Override
	public OutputStream startHeaderStream() {
		new PrintStream(_stream).printf("\n----------------\nHeader file\n----------------\n\n");
		return _stream;
	}

	@Override
	public OutputStream startImplementationStream() {
		new PrintStream(_stream).printf("\n----------------\nCpp file\n----------------\n\n");
		return _stream;
	}
}
