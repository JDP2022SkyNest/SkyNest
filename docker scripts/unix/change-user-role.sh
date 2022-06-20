echo "available roles:"
echo -e "\trole_worker"
echo -e "\trole_manager"
echo -e "\trole_admin"
read -p "new role: role_" role
read -p "email: " username
echo "--DB password"
docker exec -it skynest-db mysql -u user -p --execute="USE skynest; CALL change_user_role('$username','role_$role',@updated_count)"
read -n 1 -s -r -p "Press any key to continue..."
echo -e "\n"

