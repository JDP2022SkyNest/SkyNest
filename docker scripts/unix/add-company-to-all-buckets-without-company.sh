echo "HTEC group pib:"
echo -e "\t105793138\n"
echo "Pib of the company you want to add buckets to"
read -p "pib: " pib
echo "--DB password"
docker exec -it skynest-db mysql -u user -p --execute="USE skynest;
							UPDATE bucket SET company_id = (SELECT id FROM company WHERE pib = '$pib') WHERE company_id IS NULL;"
read -n 1 -s -r -p "Press any key to continue..."
echo -e "\n"