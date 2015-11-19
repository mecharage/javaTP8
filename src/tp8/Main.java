package tp8;

import com.lexicalscope.jewel.cli.ArgumentValidationException;
import com.lexicalscope.jewel.cli.CliFactory;
import com.lexicalscope.jewel.cli.ValidationFailure;
import java.io.FileNotFoundException;
import java.util.List;

public class Main {

	public static void main(String[] args) throws FileNotFoundException, ClassNotFoundException {
		CppGenOptions pargs = null;
		try {
			pargs = CliFactory.parseArguments(CppGenOptions.class, args);
		} catch (ArgumentValidationException ex) {
			List<ValidationFailure> vfs = ex.getValidationFailures();
			for (ValidationFailure vf : vfs)
				System.err.println(vf.getMessage());
			System.exit(1);
		} catch (Throwable ex) {
			ex.printStackTrace(System.err);
			System.exit(1);
		}

		final String classToConvert = pargs.getClassPath();
		
		final String cppClassName = pargs.isCppClassName()
				? pargs.getCppClassName()
				: pargs.getClassPath().substring(pargs.getClassPath().lastIndexOf('.') + 1);
		
		final CppOutput outputStream = pargs.isOutputFile()
				? new CppFileOutput(pargs.getOutputFile())
				: new CppConsoleOutput(System.out);

		final CppGenClass cgc = new CppGenClass(Class.forName(classToConvert));
		cgc.name = cppClassName;
		CppGenerator.generate(cgc, outputStream);
	}
}
