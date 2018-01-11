irfanstore-logging-route-service
===============
This is the sample routing service writting to run in the Pivotal Cloud Foundry. This application logs the incoming request to the standard `STDOUT`.

## Running application inside Pivotal Cloud Foundry

### Push the application
After creating the services push the application using the `cf push` command. 

### Bind the client application
To bind the client application to route the call through the routing service do the following steps:
* Create service instance. Run the following command `cf cups ostore-logging-routing-service -r https://irfanstore-logging-route-service.cfapps.io` 
* Bind the service. Run the following command: `cf bind-route-service cfapps.io ostore-logging-routing-service --hostname myClientApplication`
