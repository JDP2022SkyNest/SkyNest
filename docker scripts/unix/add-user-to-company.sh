echo "Email of the user you want to add"
read -p "email: " email
echo "Pib of the company you want to add him to"
read -p "pib: " pib
echo "--DB password"
docker exec -it skynest-db mysql -u user -p --execute="USE skynest; UPDATE user SET company_id = (SELECT id FROM company WHERE pib = '$pib') WHERE email = '$email';
							SELECT id, name, surname, email, company_id FROM user WHERE email = '$email';"
read -n 1 -s -r -p "Press any key to continue..."
echo -e "\n"