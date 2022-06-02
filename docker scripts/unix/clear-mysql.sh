echo "Stopping MySQL container..."
docker stop skynest-db
echo "Deleting MySQL container..."
docker rm skynest-db
echo "Pruning everything..."
docker system prune --volumes --force
echo "Deleting MySQL cache..."
sudo rm -rf ../../.db_data/mysql
read -n 1 -s -r -p "Press any key to continue..."
echo -e "\n"

