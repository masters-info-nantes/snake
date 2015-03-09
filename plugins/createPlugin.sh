# This script create plugins for the MGS framework
# in the current directory after collecting the 
# necessary informations

#------------------- Functions definition -------------------
# $1=packagesMainClass, $2=lastPackageMainClass $3=packageCategory $4=lastPackageCategory
function GenerateMaven { 
cat > pom.xml << EOF
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>

	<groupId>$1</groupId>
	<artifactId>$2</artifactId>
	<version>0.1</version>
	<packaging>jar</packaging>

	<name>$2</name>

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
EOF

if [ "$#" -eq 2 ]; then
	echo "			<groupId>fr.univnantes.mgsframework</groupId>" >> pom.xml;
	echo "			<artifactId>mgsframework</artifactId>" >> pom.xml;	
else
	echo "			<groupId>$3</groupId>" >> pom.xml;
	echo "			<artifactId>$4</artifactId>" >> pom.xml;
fi
	
echo "			<version>0.1</version>" >> pom.xml;

cat >> pom.xml << EOF
		</dependency>
	</dependencies>  
</project>

EOF
}

# $1=runnable $2=mainClass $3=description $4=category
function GeneratePluginConfig {
cat > plugin.txt << EOF
runnable=$1
mainClass=$2
description=$3
category=$4

EOF
}

function GeneratePackagesDir { # $1=packages $2=runnable
	cd ..
	mkdir java; cd java
	IFS="."
	for i in $1; do
		mkdir $i; cd $i
	done
	IFS=""

	if [[ "$isRunnable" = true ]]; then
		mkdir interfaces
	fi
}

function GenerateRunnableClass { # $1=mainClass $2=package
cat > $1.java << EOF
package $2;

import java.io.IOException;
import java.util.Collection;
import java.util.Set;

import fr.univnantes.mgsframework.MGSApplication;
import fr.univnantes.mgsframework.Plugin;

// A Runnabe plugin have to extends MGSApplication class
public class $1 extends MGSApplication {

	@Override
	public void run() {
		
		// Get the current plugin, here it is runnableplugin-version.jar
		System.out.println("\n" + this.currentPlugin);
		
		// List all categories provided by this plugin
		System.out.println("List of categories:");
		Set<String> categories = this.currentPlugin.getCategories();
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
}

function GeneratePluginClass { # $1=mainClass $2=package $3=category $4=categoryClass
cat > $1.java << EOF
package $2;

// The interface defined by category
import $3;

public class $1 implements $4 {

}

EOF
}

#------------------- Gather informations on plugin -------------------
echo "This script will create a plugin skeleton for MGS framework"
echo

echo "Your plugin name (will be folder name):"
read pluginName
echo
#pluginName="snake"

echo "Is it a runnable plugin ? (y or n)"
read isRunnable
echo
#isRunnable="n"

case "$isRunnable" in
	yes|y) 
		isRunnable=true
		category="fr.univnantes.mgsframework.MGSApplication"
	;;

 	*) 
		isRunnable=false
		echo "Full qualified name of plugin category (like com.plugin.interface.MyInterface):"
		read category		
		echo
		#category="com.snake.interfaces.MonInterface"
	;;
esac

echo "Full qualified name of your plugin class (like com.plugin.MyPlugin):"
read pathMainClass
#pathMainClass="com.plugin.SnakeMain"
echo

echo "Plugin description:"
read pluginDesc
#pluginDesc="Description du plugin"
echo

echo "Generating plugin skeleton...."

#------------------- Split main class and category -------------------
# Split main class full path
IFS="."
set -- $pathMainClass
list=($@)
IFS=""

sz=$((${#list[@]} - 1))

packagesMainClass=$(printf "%s," "${list[@]}" | cut -d "," -f 1-$sz | tr ',' '.')
lastPackageMainClass=${list[@]:(-2):1}
mainClassName=${list[@]:(-1)}

# Split category
if [[ "$isRunnable" = false ]]; then
	IFS="."
	set -- $category
	list=($@)
	IFS=""

	sz=$((${#list[@]} - 2))	
	packagesCategory=$(printf "%s," "${list[@]}" | cut -d "," -f 1-$sz | tr ',' '.')
	lastPackageCategory=${list[@]:(-3):1}	
	categoryClassName=${list[@]:(-1)}
fi

#------------------- Create directories, maven and plugin configuration -------------------
# Generate maven configuration
mkdir $pluginName; cd $pluginName

if [[ "$isRunnable" = false ]]; then
	GenerateMaven $packagesMainClass $lastPackageMainClass $packagesCategory $lastPackageCategory
else 
	GenerateMaven $packagesMainClass $lastPackageMainClass
fi

# Generate plugin configuration
mkdir src; cd src
mkdir main; cd main

mkdir resources; cd resources
GeneratePluginConfig $isRunnable $pathMainClass $pluginDesc $category

#------------------- Create package directories and java class -------------------
GeneratePackagesDir $packagesMainClass $isRunnable

if [[ "$isRunnable" = false ]]; then
	GeneratePluginClass $mainClassName $packagesMainClass $category $categoryClassName
else
	GenerateRunnableClass $mainClassName $packagesMainClass
fi

echo "Plugin $pluginName has been created in current directory"
