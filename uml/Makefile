# @file     Makefile
# @author   Kataoka Nagi (al18036[at]shibaura-it.ac.jp)
# @brief    Make Diagrams or clean files
# @version  1.0
# @date     2020-12-16 10:37:16

COMPILER := java
CLASS_DIAGRAM_TARGET := YoutubeAutoComment_ClassDiagram
GET_COMMENT_TARGET := YoutubeAutoComment_SequenceDiagram_GetComment
COMMENT_ANALYSER_TARGET := YoutubeAutoComment_SequenceDiagram_CommentAnalyser
JAR_FLAGS := -jar plantuml.jar
GRAPHVIZ_FLAGS := -graphvizdot C:\Graphviz\bin\dot.exe
CHARSET_FLAGS := -charset UTF-8

.PHONY: all
all: class getter analyser
# java -jar plantuml.jar -graphvizdot C:\Graphviz\bin\dot.exe -charset UTF-8 .\YoutubeAutoComment_ClassDiagram.pu
# java -jar plantuml.jar -graphvizdot C:\Graphviz\bin\dot.exe -charset UTF-8 .\YoutubeAutoComment_SequenceDiagram.pu

.PHONY: class
class: $(CLASS_DIAGRAM_TARGET).png

.PHONY: getter
getter: $(GET_COMMENT_TARGET).png

.PHONY: analyser
analyser: $(COMMENT_ANALYSER_TARGET).png

$(CLASS_DIAGRAM_TARGET).png: $(CLASS_DIAGRAM_TARGET).pu
	$(COMPILER) $(JAR_FLAGS) $(GRAPHVIZ_FLAGS) $(CHARSET_FLAGS) $^

$(GET_COMMENT_TARGET).png: $(GET_COMMENT_TARGET).pu
	$(COMPILER) $(JAR_FLAGS) $(GRAPHVIZ_FLAGS) $(CHARSET_FLAGS) $^

$(COMMENT_ANALYSER_TARGET).png: $(COMMENT_ANALYSER_TARGET).pu
	$(COMPILER) $(JAR_FLAGS) $(GRAPHVIZ_FLAGS) $(CHARSET_FLAGS) $^

.PHONY: clean
clean:
	rm -rf .\out\*.class
