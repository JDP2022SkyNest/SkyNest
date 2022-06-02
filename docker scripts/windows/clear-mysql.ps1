echo "Stopping MySQL container..."
docker stop skynest-db
echo "Deleting MySQL container..."
docker rm skynest-db
echo "Pruning everything..."
docker system prune --volumes --force
echo "Deleting MySQL cache..."
rm -Recurse -Force ../../.db_data/mysql
Write-Host "Press any key to continue..."
[void][System.Console]::ReadKey($true)
