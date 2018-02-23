compile:
	javac $$(find . -name '*.java')
clean:
	rm -r engines/*.class extra/*.class helpers/*.class layers/*.class runners/*.class endsystems/*.class
cc:
	make clean; make compile
server:
	java runners.ServerApp
proxy:
	java runners.ProxyApp
client:
	java runners.ClientApp