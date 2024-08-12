# Check if running as administrator
if (-not ([Security.Principal.WindowsPrincipal] [Security.Principal.WindowsIdentity]::GetCurrent()).IsInRole([Security.Principal.WindowsBuiltInRole]::Administrator)) {
  Write-Warning "This script needs to be run as an administrator."
  Start-Process powershell.exe "-File `"$PSCommandPath`"" -Verb RunAs
  exit
}

# Find the PID of the Java application process and kill it
$javaProcess = Get-Process -Name java -ErrorAction SilentlyContinue | Where-Object { $_.Path -like "*Tiger_Talks-0.0.1-SNAPSHOT.jar*" }
if ($null -eq $javaProcess) {
  Write-Output "No Java application process found."
} else {
  Write-Output "Killing Java application process with PID: $($javaProcess.Id)"
  Stop-Process -Id $javaProcess.Id -Force
}