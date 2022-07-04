echo "Email of the user you want to add"
read -p "email: " email
echo "Pib of the company you want to add him to"
read -p "pib: " pib
echo "--DB password"
docker exec -it skynest-db mysql -u user -p --execute="USE skynest; 
							UPDATE user SET company_id = (SELECT id FROM company WHERE pib = '$pib') WHERE email = '$email';
							SET @update_count = (SELECT ROW_COUNT());
							SELECT CASE @update_count WHEN 1 THEN 'User $email added to company $pib'  ELSE 'no changes' END AS 'OPERATION RESULT';"
read -n 1 -s -r -p "Press any key to continue..."
echo -e "\n"