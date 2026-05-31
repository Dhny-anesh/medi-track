@echo off
cd /d %~dp0
if "%~1"=="build" goto build
if "%~1"=="run" goto run
echo Usage: do.cmd build ^| run
exit /b 1
:build
call build.bat
goto end
:run
call run.bat
goto end
:end
