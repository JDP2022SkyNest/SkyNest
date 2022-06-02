echo "Stopping Cassandra container..."
docker stop skynest-token-db skynest-token-db-init
echo "Deleting Cassandra container..."
docker rm skynest-token-db skynest-token-db-init
echo "Pruning everything..."
docker system prune --volumes --force
echo "Deleting Cassandra cache..."
sudo rm -rf ../../.db_data/cassandra
read -n 1 -s -r -p "Press any key to continue..."
echo -e "\n"

