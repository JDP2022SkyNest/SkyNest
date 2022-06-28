cd ../../
echo "Starting all services..."
docker compose up skynest-be
read -n 1 -s -r -p "Press any key to continue..."
echo -e "\n"
