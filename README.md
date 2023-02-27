## How to run

You need to execute the command in the project folder

docker-compose up

## End-points

Postman collection in the file postman_collection.json

### Upload file

POST: localhost:8080/deviceInfo

params:
    
* file - your file

### Search by params

GET: localhost:8080/deviceInfo?page=0&size=10&sortingField=newspaperName&newspaperName=abb&width=1280&height=752&dpi=160

params:
* page - number of page. Required field
* size - page size. Required field
* sortingField - Sorting by field. Required field
* newspaperName - data field. Optional
* width - data field. Optional
* height - data field. Optional
* dpi - data field. Optional

