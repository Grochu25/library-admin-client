# Library admin client
This is a part of a library application created to understand JWT tokens and the OAuth2 authentication server. I also used this project to learn JTE templates.

The server acquire JWT token from auth server and uses it to get data from resource server. It allows to add, edit and delete authors, books, copies and borrows in library database.

Other parts of application:
- [Auth server](https://github.com/Grochu25/authServer)
- [Resource server](https://github.com/Grochu25/library-resource)
- client app (in progress)

Server allows only for login from admin account.

## Requirements
To run admin client requires working auth server and resource server.
Both serwers addresses and ports are specified in environment variables.

## Environment variables
To allow changing the addresses and ports of all servers that make up the library app, they are specified in environmental variables. The basic setup is placed in the **library.env** file:
- MYSQL_HOSTNAME=localhost
- ADMIN_HOSTNAME=127.0.0.1
- AUTH_HOSTNAME=localhost
- RESOURCE_HOSTNAME=localhost
- CLIENT_HOSTNAME=localhost
- MYSQL_PORT=3306
- ADMIN_PORT=8000
- AUTH_PORT=9000
- RESOURCE_PORT=8080
- CLIENT_PORT=5173

## Docker
To dockerize this application segment there is ready to use **Dockerfile** inside project root folder.

To use whole application with all segments there is a **docker-compose.yml**, that pulls all necessary images and sets up the network between them. Only thing user have to do to use compose file without changes is to add alias AUTH_HOSTNAME for localhost
