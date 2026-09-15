$ErrorActionPreference = "Stop"
if (Test-Path out) { Remove-Item out -Recurse -Force }
New-Item -ItemType Directory out | Out-Null
javac -encoding UTF-8 -d out src\Main.java src\model\*.java src\repository\*.java src\service\*.java src\util\*.java src\report\*.java
java -cp out Main
