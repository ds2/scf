execution can be done via shell:

$ mvn com.googlecode.socofo:socofo-maven-plugin:0.0.2-SNAPSHOT:format

The version may differ, and is optional.

If you need the goal more than once, you may want to edit your settings.xml file
and add the following line(s):

<pluginGroups>
	<pluginGroup>com.googlecode.socofo</pluginGroup>
<pluginGroups>

Then, you can use this syntax:

$ mvn socofo:format

