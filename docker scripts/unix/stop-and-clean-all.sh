echo "Stopping and removing all containers..."
docker ps --all --quiet | xargs docker stop | xargs docker rm
echo "Pruning everything..."
docker system prune --volumes --force
echo "Removing backend image..."
docker image rm --force skynest/skynest-api
read -n 1 -s -r -p "Press any key to continue..."
echo -e "\n"

