# Getting Started

### Requirements
- Java 21 for app
- Docker for external services (e.g. db)

### Start the project
- DB could be started for compose file
- App is classic SpringBoot app and requires configuring environment variables (locally can be run under `dev` profile to have everything setup)

### Configurable parameters

**Database:**
```
${DB_URL} - JDBC URL to Databes for saving data 
${DB_USERNAME} - Username of Database User
${DB_PASSWORD} - Password of Database User
```

**Application Parameters:**
```
${EXCHANGE_SERVER_APP_ID} - id or token for exchange server url
${EXCHANGE_SERVER_URL} – exchange server url that contains schema, host and port if needed
```

### Trying endpoints
All endpoints could be tried from IDEA using [http requests](documentation/requests.http)



