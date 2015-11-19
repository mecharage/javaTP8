package tp8;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;

public class CppGenClass {

	public String name;
	public ArrayList<Method> publicMethods = new ArrayList<>(), protectedMethods = new ArrayList<>(), privateMethods = new ArrayList<>();
	public ArrayList<Field> publicFields = new ArrayList<>(), protectedFields = new ArrayList<>(), privateFields = new ArrayList<>();

	public CppGenClass(Class<?> clazz) {
		final Method[] methods = clazz.getDeclaredMethods();

		for (Method m : methods) {
			int mod = m.getModifiers();
			if (Modifier.isPublic(mod))
				publicMethods.add(m);
			else if (Modifier.isProtected(mod))
				protectedMethods.add(m);
			else if (Modifier.isPrivate(mod))
				privateMethods.add(m);
			else
				publicMethods.add(m);
		}

		final Field[] fields = clazz.getDeclaredFields();

		for (Field f : fields) {
			int mod = f.getModifiers();
			if (Modifier.isPublic(mod))
				publicFields.add(f);
			else if (Modifier.isProtected(mod))
				protectedFields.add(f);
			else if (Modifier.isPrivate(mod))
				privateFields.add(f);
			else
				publicFields.add(f);
		}

	}
}
