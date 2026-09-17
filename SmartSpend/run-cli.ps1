$ErrorActionPreference = "Stop"
if (Test-Path cli-out) { Remove-Item cli-out -Recurse -Force }
New-Item -ItemType Directory cli-out | Out-Null
javac -encoding UTF-8 -d cli-out src\Main.java src\CLI.java src\model\*.java src\repository\*.java src\service\*.java src\util\*.java src\report\*.java
java -cp cli-out Main --cli
