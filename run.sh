#!/bin/bash

# Sauvegarde du CLASSPATH actuel
OLD_CLASSPATH=$CLASSPATH

# Definition du CLASSPATH
echo "Definition du CLASSPATH..."
CLASSPATH="lib/*:bin:.:$CLASSPATH"
export CLASSPATH
echo "CLASSPATH defini a \"$CLASSPATH\""

# Compilation
echo "Compilation en cours..."
javac -cp "$CLASSPATH" @compile.list -d bin -encoding UTF-8
if [ $? -ne 0 ]; then
	echo "Erreur lors de la compilation."
	exit 1
fi
echo "Compilation terminee."

# Execution
echo "Execution en cours..."
java -cp "$CLASSPATH" Traitement_images_distribue.Controleur
if [ $? -ne 0 ]; then
	echo "Erreur lors de l'execution."
	exit 1
fi
echo "Execution terminee - FIN DU PROGRAMME"

# Restauration de l'ancien CLASSPATH
CLASSPATH=$OLD_CLASSPATH
export CLASSPATH
echo "CLASSPATH restaure a \"$CLASSPATH\""
