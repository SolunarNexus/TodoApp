# Simple REST API for To-Do app

## How to run
The API is written using **Java 21** and built with **Maven**, so in case you want to write locally, these are the prerequisites. 

Then the app with Java Collection Framework database can be built with
```bash
mvn compile
```

and run with
```bash
mvn spring-boot:run
```

In branch `prototype/db-switch` is prepared a version of the app with a persistent storage. Docker and `docker compose` are prerequisites. You can run it by
```bash
docker compose up
```
Be patient with mysql server health check (takes ~20s), then the application starts.

The app is available at `localhost:8080` and you can experiment comforably at `http://localhost:8080/swagger-ui/index.html`

## Glossary
- **Work item** = represents a singular task in a to-do list

## Overview
This is a simple API supporting operations:
- CRUD (Create, Read, Update, Remove)
- Bulk load of multiple work items
- List all completed work items grouped by days
- List all completed work items per specified day

The app is divided into multiple layers for better separation of concerns and isolation of change. 

### 1. Persistence Layer
Encapsulates the logic behind storing data. Concrete implementation is hidden beneath an interface which forms a contract. This makes testing and change of underlying implementation much easier. This concept of abstracting implementation details is preserved at other layers of application as well.

Initially, the `HashMap` was used as a data carrier from Java Collections Framework. The choice is justified by constant read and write complexity, in most cases. Even with collisions, from Java 8, elements inside a single bucket are converted into a balanced tree which makes the worst-case read/write efficiency `O(logn)`. When talking about data storages, speed of retrieval/writing is a priority, therefore I think that `HashMap` is a suitable choice.

In later stages of the project, in-memory collection was switched for a truly persistent storage, that is discussed [later](#database-technology-switch)

The data model can be considered as a part of this layers as well. The model of work item consists of attributes such as _id_, _title_, _description_, _status_, _priority_, etc. The work item is considered completed when the attribute _completedAt_ has some concrete value. This effectively encodes two pieces of information: date and _isCompleted_ flag. 

### 2. Service Layer
This layer normally encapsulates the business logic i.e., the unique logic intrinsic to the application. In this project there is not much besides computing some statistical data. In retrospect, I think this is a more suitable place for raising exceptions in cases of e.g. non-existent entities. In my opinion, validation with this flavour is rather closer to business than persistence logic. However, this was not worked in this layer in current version. The rules for changing status can be checked as well during an attempt to udpdate work item using a machine automata for example in future.

### 3. Controller Layer
This layer exposes the endpoints performing operations mentioned earlier. The API is documented altough it can be more detailed. Nevertheless, for _simple_ API I think it is sufficient at this stage. For performing a more complex operations, such as update, I chose to represent those request in a DTO objects. They are more lightweight, do not expose internal details, and are harder to misuse in contrast with the raw `WorkItemModel`. In retrospect, again, I think it would be a good idea to represent the answer from API with additional DTO. For more comfortable and efficient mapping between model and DTO objects, the libraries `modelmapper` or `MapStruct` can be used. Here I would put the input validation and sanitization as well before sending it to layers below.

## Database technology switch
> You can find the functional prototype in [prototype/switch-db](https://github.com/SolunarNexus/TodoApp/tree/prototype/db-switch) branch

The final stage of project discussed the possibility of switching to a truly persistent storage (e.g. PostgreSQL). Firstly, for this application I would choose MySQL. MySQL is a light-weight but fairly powerful and fast database management system, especially for read-heavy web applications with simple operations such as management of to-do list.

Regarding the actual implemntation with minimal impact on existing code, the interfaces used throughout the layers come into play. With interfaces we can easily switch implementation without neighbouring layers noticing - all they do care about is what is done, not how. For persistent storage I chose the Repository pattern which is suitable for collections of items - such as to-do list work items. I created an adapter which implements the same contract as the old HashMap storage implementation - `WorkItemStorage`. Internally this adapter uses the new repository to read/write data. That means, no change to the existing service layer. Of course, we need to decide which implementation to choose because type-wise, these two implementations are totally same (of type `WorkItemStorage`). For that purpose I introtuced profiles `inmemory-collection` and `mysql`. Switching these, in `application.properties`, effectively changes the underlying implementation.

You can find the functional prototype in `prototype/switch-db` branch 

As a bonus, I dockerized the entire application. With one command, the mysql server spins up and API attaches to it. In future versions I can imagine implementing a mechanism to spin-up the mysql server only when profile is `mysql`.
