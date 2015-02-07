# Snake


Snake is a Java implementation of the famous game "Snake" based
on a brand new plugin framework called MGS.

## MGS architecture
### Configuration
The framework configuration is stored in the *settings.txt* file which is placed
in *ressources* directory.


```
pluginspath=src/fr/univnantes/snake/plugins
startplugin=snakecore
```

It contains two informations: 
* *pluginspath*: path to the directory which contains plugins folders
* *startplugin*:  plugin which will be started automatically (must **extend from MGSApplication**)

### Plugins
To create a plugin, first create a new directory in *plugins* folder and add a file 
called *plugin.txt* which will contains the configuration.

```
mainClass=fr.univnantes.snake.plugins.snakecore.Snake
```

Parameters:
* *mainClass*: if the plugin contains a class which **extends from MGSApplication**, you have to 
declare it.

## Create a new application
* Create a folder in *plugins* directory
* Create your main class which **must extend** from MGSApplication
* Create a file called *plugin.txt* inside and set *mainClass* to your main class name (full qualified)
* Register your plugin as *startplugin* in the MGS config file

Authors
-------
* Antoine Forgerou
* David Brevet
* Jérémy Bardon
