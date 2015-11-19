package tp8;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class CppFileOutput implements CppOutput {

	private final String _fileName;

	public CppFileOutput(String fileName) {
		_fileName = fileName;
	}

	@Override
	public OutputStream startHeaderStream() {
		try {
			return new FileOutputStream(_fileName + ".h");
		} catch (FileNotFoundException ex) {
			ex.printStackTrace(System.err);
			System.err.printf("Here is what should have been written to %s.h :\n\n", _fileName);
			return System.err;
		}
	}

	@Override
	public OutputStream startImplementationStream() {
		try {
			return new FileOutputStream(_fileName + ".cpp");
		} catch (FileNotFoundException ex) {
			ex.printStackTrace(System.err);
			System.err.printf("Here is what should have been written to %s.cpp :\n\n", _fileName);
			return System.err;
		}
	}
}
