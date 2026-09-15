$ErrorActionPreference = "Stop"
if (Test-Path test-out) { Remove-Item test-out -Recurse -Force }
New-Item -ItemType Directory test-out | Out-Null
javac -encoding UTF-8 -d test-out src\Main.java src\model\*.java src\repository\*.java src\service\*.java src\util\*.java src\report\*.java tests\*.java
java -cp test-out TransactionTest
java -cp test-out BudgetTest
java -cp test-out AnalyticsTest
Write-Host "All available legacy tests passed."
