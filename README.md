# Snake
Snake is a Java implementation of the famous game "Snake" based
on a brand new plugin framework called MGS.

**TODO files are now respectively in *platform* and *plugins* directories.**
## Usage
See the user manual (in french) located in the manual sub-directory

## Run it
The first thing to do compile and install the framework in your maven repositories.

```
$ cd platform
$ mvn compile
$ mvn install
```

Now, lets compile and package the plugins to get a jar file which will be put in *platform/ressources* :

```
$ cd ../plugins
$ mvn compile
$ mvn package
$ cp target/snake-0.1.jar ../platform/ressources
```

Finnaly, run the platform with **mvn exec:java**.

## Developers
To import the projet in eclipse use:
```
$ mvn eclipse:eclipse
```

## Authors
* Antoine Forgerou
* David Brevet
* Jérémy Bardon
