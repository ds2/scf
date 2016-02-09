[![Build Status](https://travis-ci.org/ds2/scf.svg?branch=master)](https://travis-ci.org/ds2/scf)

scf
===

The other source code formatter.

Line Handlers
=============

They get a line to print, analyze it if it must be broken into more than one line.

The source writers
==================

The source writers actually perform writing data onto the filesystem. Or into a stream. They can make use
of the LineHandlers to handle writing a set of lines.

Sample config
=============

//code
<?xml version="1.0" encoding="utf-8"?>
<formatter-rules
	xmlns="http://www.ds2/ns/socofo"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://www.ds2/ns/socofo http://www.ds2/ns/socofo "
>
	<xml-rules>
		<commonAttributes>
			<maxLineWidth>120</maxLineWidth>
			<indentSequence><![CDATA[  ]]></indentSequence>
			<tabSize>4</tabSize>
			<stopOnLongline>false</stopOnLongline>
		</commonAttributes>
		<separateAttributesPerLine>true</separateAttributesPerLine>
		<alignFinalBracketOnNewline>OnAttributes</alignFinalBracketOnNewline>
		<commentsRules>
			<breakType>BeautyBreak</breakType>
		</commentsRules>
		<newlineRules>
			<beforeComment>true</beforeComment>
			<afterXmlEndTag>true</afterXmlEndTag>
			<afterEachXmlAttribute>true</afterEachXmlAttribute>
			<afterProcessingInstruction>true</afterProcessingInstruction>
		</newlineRules>
	</xml-rules>
</formatter-rules>
//code
