@echo off
cd /d %~dp0
if not exist out (
    echo Output folder not found. Building project first...
    call build.bat
    if errorlevel 1 exit /b 1
)
java -cp out com.airtribe.meditrack.Main
