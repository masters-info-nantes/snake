# Snake
Snake is a Java implementation of the famous game "Snake" based
on a brand new plugin framework called MGS.

**TODO files are now respectively in *platform* and *plugins* directories.**

## Repository organization
* manuel - Platform guide (in french)
* report - Report on the snake game (in french)
* platform - Source code of the platform
* plugins - Source code of plugins

## Run it
The first thing to do is to install the framework in your maven repositories.

```
$ cd platform
$ mvn install
```

### Compile plugins
Now, lets install the core plugin to be able to import all plugins after:

```
$ cd ../plugins/snakecore
$ mvn install
```

Note this step is required because we developed secondary plugins with maven.

### Import plugins in platform
Once, the main plugins installed, import all plugins in the platform :

```
$ cd ../
$ ./importPluginsOnPlatform.sh
```

### Run the platform
The last thing to do is to run the platform :

```
$ cd ../platform
$ mvn exec:java
```

## Authors
* Antoine Forgerou
* Jérémy Bardon
