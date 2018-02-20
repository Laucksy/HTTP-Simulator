compile:
	javac *.java
clean:
	rm -r *.class
cc:
	make clean; make compile
