# This script create a main plugin for the MGS framework
# in the current directory after collecting the 
# necessary informations

echo "This script will create a main plugin skeleton for MGS framework"
echo

echo "Your plugin name.."
read pluginName
echo

echo "Full path to your plugin class (like com.univ.MyPlugin).."
read pathMainClass
echo

echo "Plugin description.."
read pluginDesc
echo

# Split main class full path
IFS="."
set -- $pathMainClass
list=($@)

IFS=""

# Get package path (without class name)
sz=$((${#list[@]} - 1))

packages=$(printf "%s," "${list[@]}" | cut -d "," -f 1-$sz | tr ',' '.')
lastpackage=${list[@]:(-2):1}
mainClass=${list[@]:(-1)}

# Create directories
mkdir $pluginName; cd $pluginName

# Generate maven configuration
cat > pom.xml << EOF
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>

	<groupId>$packages</groupId>
	<artifactId>$lastpackage</artifactId>
	<version>0.1</version>
	<packaging>jar</packaging>

	<name>$lastpackage</name>

	<properties>
		<project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
	</properties>

  	<build>
		<plugins>
		  <plugin>
		    <groupId>org.apache.maven.plugins</groupId>
		    <artifactId>maven-compiler-plugin</artifactId>
		    <version>3.2</version>
		    <configuration>
		      <source>1.7</source>
		      <target>1.7</target>
		    </configuration>
		  </plugin>
		</plugins>
	</build>

	<dependencies>
		<dependency>
		    <groupId>fr.univnantes.mgsframework</groupId>
		    <artifactId>mgsframework</artifactId>
		    <version>0.1</version>
		</dependency>
	</dependencies>  
</project>

EOF

# Generate directories
mkdir src; cd src
mkdir main; cd main

mkdir resources; cd resources
cat > plugin.txt << EOF
runnable=1
mainClass=$pathMainClass
description=$pluginDesc
category=fr.univnantes.mgsframework.MGSApplication

EOF

cd ..
mkdir java; cd java
IFS="."
for i in $packages; do
	mkdir $i; cd $i
done
IFS=""

# Generate main class
mkdir interfaces
cat > ${mainClass}.java << EOF
package $packages;

import java.io.IOException;
import java.util.Collection;
import java.util.Set;

import fr.univnantes.mgsframework.MGSApplication;
import fr.univnantes.mgsframework.Plugin;

// A Runnabe plugin have to implement MGSApplication interface
public class $mainClass extends MGSApplication {

	@Override
	public void run() {
		
		// Get the current plugin, here it is runnableplugin-version.jar
		System.out.println("\n" + this.currentPlugin);
		
		// List all categories provided by this plugin
		System.out.println("List of categories:");
		Set<String> categories = this.pluginsLoader.getMainPluginCategories();
		for (String string : categories) {
			System.out.println(string);
		}		
		
		// List all available plugins
		System.out.println("\nList of available plugins:");		
		Collection<Plugin> pluginList = this.pluginsLoader.getClassicPlugins();

		for(Plugin plugin : pluginList){
			System.out.println(plugin);
		}		
	}
}

EOF
