echo "Stopping all containers..."
docker ps --all --quiet | xargs docker stop
read -n 1 -s -r -p "Press any key to continue..."
echo -e "\n"

