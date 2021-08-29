@echo off
title Minecraft - Server
color 08
: incompatibility with 1.12.2 minecraft version
chcp 65001 > nul

--variables
set minram=1G
set maxram=2G

: search the only .jar file to execute (may be fail if other jar file in the root directory)
for %%j in  (*.jar) do (
	echo %%~nxj
	set jar=%%~nxj
)

--code
cls
java.exe -Xms%minram% -Xmx%maxram% -jar -Dfile.encoding=UTF8 %jar% nogui
echo.
echo.
echo Server stopped
pause > nul