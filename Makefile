
make-java:
	mkdir -p bin
	javac -d bin -sourcepath src -cp lib/jewelcli-0.8.9.jar src/tp8/Main.java src/tp8/TestClass.java

run-java:
	java -cp lib/jewelcli-0.8.9.jar:bin tp8/Main -o bin/TestClass tp8.TestClass

make-cpp:
	c++ -std=c++14 -Wall -Wextra -pedantic -c bin/TestClass.cpp -o TestClass.o
