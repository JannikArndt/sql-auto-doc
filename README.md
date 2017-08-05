# SQL-Auto-Doc

Generate Markdown-documentation from your database. Example:

![](screenshot.png)

### Supported Databases

- MSSQL (SQL Server)
- ~~Oracle~~ (planned)
- ~~PostgreSQL~~ (planned)
- ~~MySQL/MariaDB~~ (planned)

## Testing

### MSSQL / SQL Server

1. Run SQL Server in Docker

```bash
$ docker pull microsoft/mssql-server-linux
$ docker run -e 'ACCEPT_EULA=Y' -e 'MSSQL_SA_PASSWORD=Bla12345' -e 'MSSQL_PID=Developer' --cap-add SYS_PTRACE -p 1401:1433 -d microsoft/mssql-server-linux
``` 

### Aim: Maven- or Flyway-Plugin

### Similar Projects

- https://github.com/vokal/pg-table-markdown (for PostgreSQL)
- https://stackoverflow.com/questions/186392/how-do-you-document-your-database-structure
- Redgate SQL Doc (http://www.red-gate.com/products/sql-development/sql-doc/)
- ApexSQL Doc (https://www.apexsql.com/sql_tools_doc.aspx)
- SchemaSpy (http://schemaspy.sourceforge.net)

