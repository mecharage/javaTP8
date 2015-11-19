package tp8;

import java.io.PrintStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class CppGenerator {

	private final CppGenClass _clazz;
	private final CppOutput _output;
	private PrintStream _outStream;

	private static class TypeInfo {

		final String cppName;
		final String cppInclude;

		public TypeInfo(String cppName_, String cppInclude_) {
			cppName = cppName_;
			cppInclude = cppInclude_;
		}
	}

	private static final Map<Class<?>, TypeInfo> typeInfo = new HashMap<Class<?>, TypeInfo>() {
		{
			put(ArrayList.class, new TypeInfo("std::string", "string"));
			put(ArrayList.class, new TypeInfo("std::vector<?>", "vector"));
		}
	};

	public static void generate(CppGenClass clazz, CppOutput outStream) {
		CppGenerator gen = new CppGenerator(clazz, outStream);

		Set<String> includes = gen.includes();

		gen.startHeader();

		gen.outputIncludeGuard();

		for (String incName : includes)
			gen.outputSystemInclude(incName);

		gen.outputClassHeader();

		if (!clazz.publicMethods.isEmpty())
			for (Method m : clazz.publicMethods)
				gen.outputDecl(m);

		if (!clazz.protectedMethods.isEmpty()) {
			gen.outputProtectedSection();
			for (Method m : clazz.protectedMethods)
				gen.outputDecl(m);
		}

		if (!clazz.privateMethods.isEmpty()) {
			gen.outputPrivateSection();
			for (Method m : clazz.privateMethods)
				gen.outputDecl(m);
		}

		if (!clazz.publicFields.isEmpty()) {
			gen.outputPublicSection();
			for (Field f : clazz.publicFields)
				gen.outputDecl(f);
		}

		if (!clazz.protectedFields.isEmpty()) {
			gen.outputProtectedSection();
			for (Field f : clazz.protectedFields)
				gen.outputDecl(f);
		}

		if (!clazz.privateFields.isEmpty()) {
			gen.outputPrivateSection();
			for (Field f : clazz.privateFields)
				gen.outputDecl(f);
		}

		gen.outputClassFooter();

		gen.startImplementation();

		gen.outputUserInclude(clazz.name + ".h");
		
		gen.output("\n");
		
		for (String incName : includes)
			gen.outputSystemInclude(incName);

		gen.output("\n");
		
		for (Method m : clazz.publicMethods)
			gen.outputDef(m);

		for (Method m : clazz.protectedMethods)
			gen.outputDef(m);

		for (Method m : clazz.privateMethods)
			gen.outputDef(m);
	}

	private CppGenerator(CppGenClass clazz, CppOutput output) {
		_clazz = clazz;
		_output = output;
	}

	private void startHeader() {
		_outStream = new PrintStream(_output.startHeaderStream());
	}

	private void startImplementation() {
		_outStream = new PrintStream(_output.startImplementationStream());
	}

	private Set<String> includes(Class<?> clazz) {
		final Set<String> set = new TreeSet<>();

		TypeInfo info = typeInfo.get(clazz);
		if (info != null)
			set.add(info.cppInclude);

		return set;
	}

	private Set<String> includes(Field f) {
		return includes(f.getType());
	}

	private Set<String> includes(Method m) {
		Set<String> set = new TreeSet<>();

		for (Class<?> p : m.getParameterTypes())
			set.addAll(includes(p));

		return set;
	}

	private Set<String> includes() {
		Set<String> set = new TreeSet<>();

		for (Method m : _clazz.publicMethods)
			set.addAll(includes(m));
		for (Method m : _clazz.protectedMethods)
			set.addAll(includes(m));
		for (Method m : _clazz.privateMethods)
			set.addAll(includes(m));

		for (Field f : _clazz.publicFields)
			set.addAll(includes(f));
		for (Field f : _clazz.protectedFields)
			set.addAll(includes(f));
		for (Field f : _clazz.privateFields)
			set.addAll(includes(f));

		return set;
	}

	private String transcode(Class<?> clazz) {
		TypeInfo info = typeInfo.get(clazz);
		return info != null ? info.cppName : clazz.getName();
	}
	
	private void output(String fmt, Object... args) {
		_outStream.printf(fmt, args);
	}

	private void outputIncludeGuard() {
		_outStream.printf("#pragma once\n\n");
	}

	private void outputSystemInclude(String name) {
		_outStream.printf("#include <%s>\n", name);
	}

	private void outputUserInclude(String name) {
		_outStream.printf("#include \"%s\"\n", name);
	}

	private void outputClassHeader() {
		_outStream.printf("\nstruct %s {\n", _clazz.name);
	}

	private void outputPublicSection() {
		_outStream.printf("public:\n");
	}

	private void outputProtectedSection() {
		_outStream.printf("protected:\n");
	}

	private void outputPrivateSection() {
		_outStream.printf("private:\n");
	}

	private void outputCommaSep(Class<?>[] types) {
		boolean first = true;
		for (Class<?> t : types)
			_outStream.printf("%s%s", first ? "" : ", ", transcode(t));
	}

	private void outputDecl(Method m) {
		_outStream.printf("\t%s %s(", transcode(m.getReturnType()), m.getName());
		outputCommaSep(m.getParameterTypes());
		_outStream.printf(");\n");
	}

	private void outputDecl(Field f) {
		_outStream.printf("\t%s %s;\n", transcode(f.getType()), f.getName());
	}

	private void outputClassFooter() {
		_outStream.printf("};\n");
	}

	private void outputDef(Method m) {
		_outStream.printf("%s %s::%s(", transcode(m.getReturnType()), _clazz.name, m.getName());
		outputCommaSep(m.getParameterTypes());
		_outStream.printf(") {\n\t\n}\n\n");
	}
}
