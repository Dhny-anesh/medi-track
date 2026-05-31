@echo off
setlocal enabledelayedexpansion
cd /d %~dp0
if exist out rd /s /q out
mkdir out
set "sources="
for /R src\main\java %%f in (*.java) do (
    set "sources=!sources! "%%f""
)
javac -d out %sources%
if errorlevel 1 (
    echo.
    echo Build failed.
    exit /b 1
)
echo.
echo Build succeeded.
java -cp out com.airtribe.meditrack.Main
