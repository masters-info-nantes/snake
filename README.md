# Snake
Snake is a Java implementation of the famous game "Snake" based
on a brand new plugin framework called MGS.

**TODO files are now respectively in *platform* and *plugins* directories.**

## Usage
See the user manual (in french) located in the manual sub-directory

## Run it
The first thing to do is to install the framework in your maven repositories.

```
$ cd platform
$ mvn install
```

### Compile plugins
Change your plugins path in *platform/resources/settings.txt*.
Now, lets install the plugin game to be able to import all plugins after:

```
$ cd ../plugins/snakecore
$ mvn install

```

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
