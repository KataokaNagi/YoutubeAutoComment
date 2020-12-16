# @file     Makefile
# @author   Kataoka Nagi (al18036[at]shibaura-it.ac.jp)
# @brief    Make Diagrams or clean files
# @version  1.0
# @date     2020-12-16 10:37:16

COMPILER := java
CLASS_DIAGRAM_TARGET := YoutubeAutoComment_ClassDiagram
SEQUENCE_DIAGRAM_TARGET := YoutubeAutoComment_ScequenceDiagram
JAR_FLAGS := -jar plantuml.jar
GRAPHVIZ_FLAGS := -graphvizdot C:\Graphviz\bin\dot.exe
CHARSET_FLAGS := -charset UTF-8

.PHONY: all
all: class seq
# java -jar plantuml.jar -graphvizdot C:\Graphviz\bin\dot.exe -charset UTF-8 .\YoutubeAutoComment_ClassDiagram.pu
# java -jar plantuml.jar -graphvizdot C:\Graphviz\bin\dot.exe -charset UTF-8 .\YoutubeAutoComment_ScequenceDiagram.pu

.PHONY: class
class: $(CLASS_DIAGRAM_TARGET).png

.PHONY: seq
seq: $(SEQUENCE_DIAGRAM_TARGET).png

$(CLASS_DIAGRAM_TARGET).png: $(CLASS_DIAGRAM_TARGET).pu
	$(COMPILER) $(JAR_FLAGS) $(GRAPHVIZ_FLAGS) $(CHARSET_FLAGS) $^

$(SEQUENCE_DIAGRAM_TARGET).png: $(SEQUENCE_DIAGRAM_TARGET).pu
	$(COMPILER) $(JAR_FLAGS) $(GRAPHVIZ_FLAGS) $(CHARSET_FLAGS) $^

.PHONY: clean
clean:
	rm -rf .\out\*.class
