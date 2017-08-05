# SQL-Auto-Doc

Generate Markdown-documentation from your database. Example:

```bash
$ SqlAutoDoc -url=jdbc:sqlserver://localhost:1401 -user=SA -password=Bla12345 -output=README.md -timeout=20
```

![](screenshot.png)

### Usage
Configuration can be given

- as arguments: 
    ```bash
    $ SqlAutoDoc -url=jdbc:sqlserver://localhost:1401 -user=SA -password=Bla12345 -output=README.md -timeout=20
    ```
- as config-file `sqlautodoc.conf`: 
    ```hocon
    "url": "jdbc:sqlserver://localhost:1401"
    "user": "SA"
    "password": "Bla12345"
    "output": "Table.md"
    "timeout": 20
    ```
- as environment variables: 
    ```bash
    export SQLAUTODOC_URL=jdbc:sqlserver://localhost:1401
    export SQLAUTODOC_USER=SA
    export SQLAUTODOC_PASSWORD=Bla12345
    export SQLAUTODOC_OUTPUT=Table.md
    export SQLAUTODOC_TIMEOUT=20
    ```

If multiple options are given, config-file overrides environment vars and arguments override config-file.

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

