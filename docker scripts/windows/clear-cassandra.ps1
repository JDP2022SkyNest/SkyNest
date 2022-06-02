echo "Stopping Cassandra container..."
docker stop skynest-token-db skynest-token-db-init
echo "Deleting Cassandra container..."
docker rm skynest-token-db skynest-token-db-init
echo "Pruning everything..."
docker system prune --volumes --force
echo "Deleting Cassandra cache..."
rm -Recurse -Force ../../.db_data/cassandra
Write-Host "Press any key to continue..."
[void][System.Console]::ReadKey($true)
