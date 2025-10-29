.PHONY: all run libsysinfo/libsysinfo.so

MODULES = sysInfo cpuInfo pciInfo usbInfo diskInfo memInfo
HEADERS = $(MODULES:=.h)

MYPROJECT = Main

JAR_DIR := lib
GSON_JAR := $(JAR_DIR)/gson-2.13.1.jar
JAVA_SOURCES := $(wildcard *.java)

all: $(HEADERS) libsysinfo/libsysinfo.so compile_java

$(HEADERS): %.h: %.java
	javac -cp $(GSON_JAR):. -h . $<

libsysinfo/libsysinfo.so:
	$(MAKE) -C libsysinfo

compile_java:
	javac -cp $(GSON_JAR):. $(JAVA_SOURCES)

run: all
	java -cp $(GSON_JAR):. -Djava.library.path=libsysinfo $(MYPROJECT)