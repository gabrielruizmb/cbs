@echo off
echo Creating backup of Cris Ballon Atelie project...

:: Get current date and time for backup filename
for /f "tokens=2 delims==" %%a in ('wmic OS Get localdatetime /value') do set "dt=%%a"
set "YY=%dt:~2,2%" & set "YYYY=%dt:~0,4%" & set "MM=%dt:~4,2%" & set "DD=%dt:~6,2%"
set "HH=%dt:~8,2%" & set "Min=%dt:~10,2%" & set "Sec=%dt:~12,2%"
set "datestamp=%YYYY%-%MM%-%DD%_%HH%-%Min%-%Sec%"

:: Create backup filename
set "backup_name=cris_ballon_backup_%datestamp%.zip"

:: Create the ZIP file using PowerShell
powershell -command "Compress-Archive -Path '.\src', '.\target', '.\pom.xml', '.\README.md', '.\users.dat', '.\.gitignore' -DestinationPath '.\%backup_name%' -Force"

if exist "%backup_name%" (
    echo.
    echo ✓ Backup created successfully: %backup_name%
    echo.
    echo You can now share this file with your friends!
    echo File location: %cd%\%backup_name%
) else (
    echo.
    echo ✗ Error creating backup file
)

echo.
pause