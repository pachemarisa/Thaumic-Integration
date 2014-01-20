@echo off
:: Variables
set FORGE=1.6.4-9.11.1.965
set PATH=%PATH%;./gnuwin32
title Thaumic Integration Development Workspace Setup (Forge %FORGE%)

:: Download Forge
IF NOT EXIST forge-%FORGE%-src.zip (
echo Downloading Forge %FORGE%...
curl -O --progress-bar http://files.minecraftforge.net/maven/net/minecraftforge/forge/%FORGE%/forge-%FORGE%-src.zip
) ELSE (
echo Forge download skipped, already downloaded as forge-%FORGE%-src.zip
echo.
goto :ask

:: Ask installation method
:ask
echo Choose an installation type:
echo.
echo [D]ownload Assets (new/fresh install or updating MC version)
echo [D]ownload Assets (new/fresh install or updating MC version)
echo.
choice /C:DS /N /m "Installation Type: "
echo.
if %ERRORLEVEL%==1 goto :dlassets_forge
cls
echo Invalid selection.
echo.
goto :ask

:: Forge Installation Method - Download Assets
:dlassets_forge
IF EXIST forge (
echo Deleting Forge...
RD /S /Q "forge"
)
echo Extracting Forge %FORGE%...
unzip -q forge-%FORGE%-src.zip
echo.
pushd forge
.\fml\python\python_fml install.py
popd
goto :ask_eclipse

:: Ask user if the eclipse workspace should be reinstalled.
:ask_eclipse
echo.
echo Would you like to reinstall your eclipse workspace?
choice /C:YN /N /m "[Y/N] "
echo.
if %ERRORLEVEL%==1 goto :install_eclipse
if %ERRORLEVEL%==2 goto :finish
echo Invalid selection.
goto :ask_eclipse

:: Delete old eclipse workspace and extract eclipse.zip
:install_eclipse
echo.
IF EXIST eclipse (
echo Deleting eclipse...
RD /S /Q "eclipse"
)
echo Extracting eclipse workspace...
unzip -q eclipse.zip
echo Finished eclipse setup.
goto :finish

:: Finish!
:finish
echo.
pause
exit