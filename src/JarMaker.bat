@echo off

echo Compiling Java files...
javac unsorted\Main.java
echo Done
echo.

echo Creating Manifest...
echo Manifest-Version: 1.0 > N-Pieces.mf
echo Main-Class: unsorted.Main >> N-Pieces.mf
echo Done
echo.

echo Creating Jar file...
jar cmf N-Pieces.mf N-Pieces.jar unsorted
echo Done
echo.

echo Deleting Manifest...
DEL N-Pieces.mf
echo Done
echo.

echo Deleting class files...
DEL /S unsorted\*.class
echo Done
echo.

echo Jar contains the following:
jar tf N-Pieces.jar
echo.

echo Creating N-Pieces.bat...
echo @echo off > N-Pieces.bat
echo java -jar N-Pieces.jar >> N-Pieces.bat
echo pause >> N-Pieces.bat
echo Done
echo.

echo Run N-Pieces.bat to play the game, enjoy!
pause