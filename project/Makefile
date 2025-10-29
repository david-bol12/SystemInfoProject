.PHONY: libsysinfo/libsysinfo.so

MODULES=sysInfo cpuInfo pciInfo usbInfo diskInfo memInfo
HEADERS=$(MODULES:=.h)

MYPROJECT=Main

all: $(HEADERS) $(MYPROJECT).class libsysinfo/libsysinfo.so

$(MYPROJECT).class: $(MYPROJECT).java libsysinfo/libsysinfo.so
	javac $<

$(HEADERS): %.h: %.java
	javac -h . $<libsysinfo/libsysinfo.so:
	$(MAKE) -C libsysinfo

JAR_DIR := lib
JFC_JAR := $(JAR_DIR)/gson-2.13.1.jar
JAVA_SOURCES := $(wildcard *.java)

compile_java:
	javac -cp $(JFC_JAR):. $(JAVA_SOURCES)

run: all
	java -cp $(JFC_JAR):. -Djava.library.path=libsysinfo $(MYPROJECT)