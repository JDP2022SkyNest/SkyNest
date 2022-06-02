cd ../../
echo "Starting all services..."
docker compose up
Write-Host "Press any key to continue..."
[void][System.Console]::ReadKey($true)
