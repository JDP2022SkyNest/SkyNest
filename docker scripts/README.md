# Convenience scripts

- <code>list-containers</code> shows all containers
- <code>start-all</code> starts up everything
- <code>stop-all</code> stops all containers
- <code>clean-all</code> removes all containers and prunes everything
- <code>stop-and-clean-all</code> combines <code>stop-all</code> and <code>clean-all</code>
- <code>clear-mysql</code> stops/deletes the MySQL container, deletes MySQL's data <code>.db_data/mysql/</code> and prunes everything
- <code>clear-cassandra</code> does the same as <code>clear-mysql</code>, but for Cassandra

Pruning/cleaning does not clear the databases. To clear the databases, use the clear scripts or delete <code>.db_data/</code> manually.
