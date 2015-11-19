package tp8;

import java.util.ArrayList;

public class TestClass {
	public int iFoo;
	private float fBar;
	protected String sBaz;
	private ArrayList<Double> dvQux;
	
	TestClass() {}
	
	public void foo(int i) {}
	
	private float bar(ArrayList<TestClass> a) { return 0.0f; }
	
	protected int baz() { return 0; }
}
