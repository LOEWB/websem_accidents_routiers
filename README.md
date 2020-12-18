
This application aims to fetch lethal french traffic accidents data in the year 2019 and give panel to make some search about it.
The data comes from two sources : 
- https://www.data.gouv.fr/fr/datasets/accidents-de-la-route/
- https://geo.api.gouv.fr/adresse

###1 Insert Data
- Create database in Fuseki
- Create or modify the file : /resources/static/connexion.json and set your own database as follow
```json
{
  "url" : "http://localhost",
  "port" : 3030,
  "dataset" : "myDataset",
  "endPoint" : "sparql",
  "update" : "update",
  "graph" : "data"
}
```

- run the InsertData class to initialize data into your fuseki


###2 Run Spring App
**First** open terminal at content root

for Windows : 

```shell script
mvnw.cmd spring-boot:run
```
for Linux :
```shell script
./mvnw spring-boot:run
```

- Go to <http://localhost:8080/> to search data
- Go to <http://localhost:8080/insert> to add 1 data
- Go to <http://localhost:8080/all> to see all


###3 Run Tests (optional)
for Windows
```shell script
mvnw.cmd test
```

for Linux :
```shell script
./mvnw test
```