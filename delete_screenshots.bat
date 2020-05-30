Echo Deleting failure_screenshots folder...
@RD /S /Q "C:\Users\Marko\Documents\Automation\RED\RedTestFramework\failure_screenshots"
TIMEOUT /T 10 /NOBREAK

#/s parameter will delete all files contained in the directory subfolders. However if you do not want to delete files from subfolders, remove /s parameter.

# /f parameter ignores any read-only setting.

# /q “quiet mode,” meaning you won’t be prompted Yes/No

# Use Windows Task Scheduler to make this job occurring every day, or several days to keep disk clean