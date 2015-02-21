# This script compile all plugins in plugins directory,
# copy the jar file in the platform (platform/resources/plugins)
# and clean the plugins directories of left compilation files
#
# NOTE: You can pass a specific plugin as first arg
#

basedir=$(pwd)
pluginslist=$(ls)

if [ $# -eq 1 ]; then
    pluginslist=$1
fi

for file in $pluginslist
do
    cd $basedir
    if [[ -d $file ]]; then  
    	if [[ "$file" == "templates" ]]; then  
    		continue
    	fi

		cd $file
		echo; pwd;

		echo "Create package..."
		mvn package -q

		echo "Copy jar into platform..."
		cp -R target/*.jar $basedir/../platform/resources/plugins

		echo "Clean plugin directory..."
		mvn clean -q
	elif [ $# -eq 1 ]; then
		echo "Plugin $file does not exist"
    fi
done
