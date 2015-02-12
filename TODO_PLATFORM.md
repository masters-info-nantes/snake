# Travaux sur la plateforme MGS
## API et conventions
Voir le manuel dans [manuel/manuel.pdf](https://github.com/masters-info-nantes/snake/raw/master/manuel/manuel.pdf) qui décrit le framework, son organisation, quelques détails de fonctionnement et un example de création d'un plugin

## Todo
* Ajouter une méthode pour avoir tout les plugins d'une catégorie
* Ajouter un système de dépendance qui oblige ne charge pas un plugin principal si ces dépendances ne peuvent être satisfaites
* Passer le projet sous maven
* Séparer les plugins dans un autre projet

## Done
* Scan du dossier des plugins
* Chargement de la configuration des plugins
* Lancement d'un plugin principal au démarrage
* Début d'API accessible par le plugin principal
* Définition d'interfaces pour le plugin principal (= catégories)
* Ajouter une description pour les plugins

## Developpeur
```
Jérémy Bardon
```