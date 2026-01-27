@echo off
REM Create a package to share with teammate (excludes target, .git, .idea)

echo ================================================
echo Creating Share Package for Teammate
echo ================================================
echo.

cd /d "D:\Study\Github\TSP\Service Oriented Computing\Project\Insurance-Claim-Processing-SOA"

set OUTPUT_ZIP=Insurance-Claim-Processing-SOA-SHARE.zip
set TEMP_DIR=temp_share

REM Create temporary directory
if exist %TEMP_DIR% rmdir /s /q %TEMP_DIR%
mkdir %TEMP_DIR%

echo [1/4] Copying documentation...
xcopy /E /I /Y docs %TEMP_DIR%\docs

echo [2/4] Copying source code...
xcopy /E /I /Y src %TEMP_DIR%\src

echo [3/4] Copying configuration files...
copy /Y pom.xml %TEMP_DIR%\
copy /Y README.md %TEMP_DIR%\
copy /Y *.bat %TEMP_DIR%\ 2>nul

echo [4/4] Creating ZIP file...
powershell Compress-Archive -Path %TEMP_DIR%\* -DestinationPath %OUTPUT_ZIP% -Force

REM Cleanup
rmdir /s /q %TEMP_DIR%

echo.
echo ================================================
echo Share package created: %OUTPUT_ZIP%
echo File location: %cd%\%OUTPUT_ZIP%
echo ================================================
echo.
echo You can now send this ZIP file to your teammate.
echo.

pause
