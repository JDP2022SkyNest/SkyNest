echo "Stopping and removing all containers..."
docker ps --all --quiet | xargs docker stop | xargs docker rm
echo "Pruning everything..."
docker system prune --volumes --force
read -n 1 -s -r -p "Press any key to continue..."
echo -e "\n"

