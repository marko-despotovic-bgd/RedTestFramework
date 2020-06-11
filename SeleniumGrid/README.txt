Selenium Grid set-up:

1. Download and install JDK on both hub and node(s) from https://www.oracle.com/java/technologies/javase-downloads.html
2. Pick up a machine to serve as a Selenium hub and download a Selenium server from https://www.selenium.dev/downloads/
3. Create a folder SeleniumGrid on the C drive and move the downloaded jar file into it
4. Repeat the entire process on the node machine (download, create a folder, move)
5. Add the folder to the environment Path variable (while here, check if JAVA_HOME is set properly)
6. Create a hubConfig.json file in the SeleniumGrid folder (you can find it in git repo)
7. Start hub by opening command prompt in SeleniumGrid folder and executing command:  java -jar selenium-server-standalone-3.141.59.jar -role hub –hubConfig hubConfig.json (or simply double-click start_selenium_grid_hub.bat)
8. Go to the node machine and add the SeleniumGrid folder to the Path variable
9. Download web drivers for Chrome, Firefox, and Edge browsers and store it to the SeleniumGrid folder (https://github.com/mozilla/geckodriver/releases, https://sites.google.com/a/chromium.org/chromedriver/downloads, https://selenium-release.storage.googleapis.com/index.html)
10. Create a new nodeCofnig.json file and store it to SeleniumGrid folder (also part of project artifacts)
11. Start node by opening command prompt in SeleniumGrid folder and executing command: Java -Dwebdriver.chrome.driver="chromedriver.exe" -Dwebdriver.ie.driver="IEDriverServer.exe" -Dwebdriver.gecko.driver="geckodriver.exe" -jar selenium-server-standalone-3.141.59.jar -role node -nodeConfig nodeConfig.json (or simply double-click start_selenium_grid_node.bat)
12. Go back to the hub machine and refresh the http://localhost:4444/grid/console address