# This script compile all plugins in plugins directory,
# copy the jar file in the platform (platform/resources/plugins)
# and clean the plugins directories of left compilation files

basedir=$(pwd)
pluginslist=$(ls)

for file in $pluginslist
do
    cd $basedir
    if [[ -d $file ]] && [[ "$file" != "template" ]]; then
	cd $file
	echo; pwd; echo;
	mvn package
	cp -R target/*.jar $basedir/../platform/resources/plugins
	mvn clean
    fi
done
