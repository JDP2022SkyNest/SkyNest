CREATE KEYSPACE IF NOT EXISTS tokenkeyspace
WITH REPLICATION = { 'class' : 'SimpleStrategy', 'replication_factor' : 1 };