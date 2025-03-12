@echo off

REM Sauvegarde du CLASSPATH actuel
set "OLD_CLASSPATH=%CLASSPATH%"

REM Definition du CLASSPATH
echo Definition du CLASSPATH...
set "CLASSPATH=lib\*;bin;.;%CLASSPATH%"
echo CLASSPATH defini a "%CLASSPATH%"

REM Compilation
echo Compilation en cours...
javac -cp "%CLASSPATH%" @compile.list -d bin -encoding UTF-8
if %ERRORLEVEL% NEQ 0 (
	echo Erreur lors de la compilation.
	goto end
)
echo Compilation terminee.

REM Execution
echo Execution en cours...
java -cp "%CLASSPATH%" Traitement_images_distribue.Controleur
if %ERRORLEVEL% NEQ 0 (
	echo Erreur lors de l'execution.
	goto end
)
echo Execution terminee - FIN DU PROGRAMME

:end
REM Restauration de l'ancien CLASSPATH
set "CLASSPATH=%OLD_CLASSPATH%"
echo CLASSPATH restaure a "%CLASSPATH%"