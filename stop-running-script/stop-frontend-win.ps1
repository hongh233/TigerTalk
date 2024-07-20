# Check if running as administrator
if (-not ([Security.Principal.WindowsPrincipal] [Security.Principal.WindowsIdentity]::GetCurrent()).IsInRole([Security.Principal.WindowsBuiltInRole]::Administrator)) {
  Write-Warning "This script needs to be run as an administrator."
  Start-Process powershell.exe "-File `"$PSCommandPath`"" -Verb RunAs
  exit
}

# Find the PID of the npm start process and kill it along with its children
$npmProcesses = Get-Process -Name node -ErrorAction SilentlyContinue | Where-Object { $_.Path -like "*npm*" -and $_.Path -like "*start*" }
if ($null -eq $npmProcesses) {
  Write-Output "No npm start process found."
} else {
  Write-Output "Killing npm start processes..."
  foreach ($process in $npmProcesses) {
    Stop-Process -Id $process.Id -Force
  }
}

# Ensure no process is using port 3000
$portProcess = Get-NetTCPConnection -LocalPort 3000 -ErrorAction SilentlyContinue | Select-Object -ExpandProperty OwningProcess
if ($null -eq $portProcess) {
  Write-Output "No process found using port 3000."
} else {
  Write-Output "Killing process on port 3000 with PID: $portProcess"
  Stop-Process -Id $portProcess -Force
}