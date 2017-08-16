#!/usr/bin/env bash

# Make sure docker is installed
if [ -z "$(docker info)" ]; then
    echo -e "\033[31mDocker not running. Please start docker first!\033[0m"
    exit 1
fi

docker_id=$(docker ps -qf "ancestor=microsoft/mssql-server-linux")

# Make sure mssql is running
if [ -z  $docker_id ]; then
    echo "Docker image for 'microsoft/mssql-server-linux' not running. Starting image…"
    docker_id=$(docker run -e 'ACCEPT_EULA=Y' -e 'MSSQL_SA_PASSWORD=Bla12345' -e 'MSSQL_PID=Developer' --cap-add SYS_PTRACE -p 1401:1433 -d microsoft/mssql-server-linux)
    echo -e "\033[0;32mDocker image 'microsoft/mssql-server-linux' started with id $docker_id.\033[0m"
else
    echo -e "\033[0;32mDocker image for 'microsoft/mssql-server-linux' found ($docker_id).\033[0m"
fi

echo "Copying script to docker container..."

# Copy sql script to docker container
docker cp src/main/resources/create_mssql_example.sql $docker_id:/create.sql

echo "Executing script in container..."

# Execute script
docker exec -it $docker_id /opt/mssql-tools/bin/sqlcmd -S localhost -U sa -P Bla12345 -i create.sql

echo -e "\033[0;32mDone! Connect to the database via jdbc:sqlserver://localhost:1401 with user SA and password Bla12345\033[0m"
