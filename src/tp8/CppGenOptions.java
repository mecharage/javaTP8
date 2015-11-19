package tp8;

import com.lexicalscope.jewel.cli.Option;
import com.lexicalscope.jewel.cli.Unparsed;

public interface CppGenOptions {
	@Option(shortName = "o", description = "The files to which the generated C++ code will be output. If not specified, output will be printed to stdout.")
	String getOutputFile();
	boolean isOutputFile();
	
	@Option(longName = "classname", shortName = "c", description = "The name of the generated C++ class. If not specified, it will be the same as the Java class's name.")
	String getCppClassName();
	boolean isCppClassName();
	
	@Option(helpRequest = true, description = "Print this usage help.")
	boolean getHelp();
	
	@Unparsed(description = "The classpath to the Java class to convert.")
	String getClassPath();
}
