package tp8;

import java.io.OutputStream;

public interface CppOutput {
	public OutputStream startHeaderStream();
	public OutputStream startImplementationStream();
}
