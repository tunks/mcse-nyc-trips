### Take Home Engineering Challenge - Microsoft CSE Team
This is a coding challenge to develop a Restful API solution for NYC TLC taxi travelers. The solution provides Restful APIs for users to find Taxi zone locations,trips and cost travel metrics for different type of taxi vehicle in the city. 

I have prioritized the implementation on certain key features and requirements due to the limited time constraint. I spent some time to understand the requirements and relevant data set required for the solution.
 
#### Code Dependencies
- Java 1.8+
- Apache Maven 3.x
- MongoDB database (Not required for Junit test cases, it uses an embedded MongoDB for execute test units)

#### Problem requirements
- Distance of a taxi trip between two borough zones
- Cost of a taxi trip between two borough zones
- Find taxi trips between different borough zones
- Filter taxi trips based on yellow cab, green cab and for-hire vehicles
- Filter taxi trips based on pickup and drop off dates
- Provide few interesting metrics for taxi trips
 
#### Approach
1. Create an feature plan for implementation the key user stories
2. Code implementation and deployment       
3. Documentation for future improvements 

#### Implementation
One of major requirements of this challenge is to provide a solution for users to query relevant taxi trips information. 
I decoupled and implemented the data parsing and Restful API query operations into two separate micro-services.  

1. This Restful API service is implemented using Spring Boot and its provides Restful end points for users to find locations, trips and metrics.

2. The data processing service uses Spring Batch for parsing and storing of the required data sets in Mongodb server.
The batch data processor is in a separate Github repository [tunks/msce-tlc-data-processor](https://github.com/tunks/msce-tlc-data-processor).

Junit5 unit cases are implemented as part of the code to test the required features

     
#### Build
Build the project with the test unit using Apache maven. The repository is integrated with Azure Dev Ops for CI/CD and the build is automatically after each commit

To build the code locally
	
	mvn clean install
 
#### Run
1. Using java -jar command to start the application
	
		java -jar target/mcse-nyc-trips-0.0.1-SNAPSHOT.jar 

2. OR, using Spring boot maven plugin
	
		mvn spring-boot:run

#### Restful API End points

The jar application is running in my Azure VM server with MongoBD server for user testing. 

 - API Host - http://nyctaxi.api.tunkz.net:8080

Find location zone by borough or zone 
	
	GET /api/zones?borough=&zone=?
	GET http://nyctaxi.api.tunkz.net:8080/api/zones?borough=&zone=?

Find location zone by borough or zone  with sort ascending option

	GET /api/zones?borough=&zone=?sort=borough,asc 
	GET http://nyctaxi.api.tunkz.net:8080/api/zones?borough=&zone=?sort=borough,asc

Search borough by search term

	GET /api/zones/search?term=&size=10
	GET http://nyctaxi.api.tunkz.net:8080/api/zones/search?term=&size=10

Find trips by query parameters
	
	GET /api/trips?taxi_type=&pickup_id=&dropoff_id=&pickup_date=&dropoff_date 
	GET http://nyctaxi.api.tunkz.net:8080/api/trips?....

Find trips by query parameters with sort ascending option

	GET /api/trips?axi_type=&pickup_id=&dropoff_id=&pickup_date=&dropoff_date=&sort=taxi_type,asc
	GET http://nyctaxi.api.tunkz.net:8080/api/trips?axi_type=&pickup_id=...&sort=taxi_type,asc

Get trip metrics by cost between two locations
		
	GET /api/trips/metrics?name=cost&pickup_id=&dropoff_id=
	GET http://nyctaxi.api.tunkz.net:8080/api/trips/metrics?name=cost&pickup_id=&dropoff_id=

Get trip metrics by hour of day cost between two locations (1 to 23)
		
	GET /api/trips/metrics?name=hour&pickup_id=&dropoff_id=
	GET http://nyctaxi.api.tunkz.net:8080/api/trips/metrics?name=hour&pickup_id=&dropoff_id=
	
Get trip metrics by day of week cost between two locations (Sunday to Saturday)
		
	GET /api/trips/metrics?name=day&pickup_id=&dropoff_id=
	GET http://nyctaxi.api.tunkz.net:8080/api/trips/metrics?name=day&pickup_id=&dropoff_id=
     
##### Query parameters
   taxi_type: type of taxi vehicle(green, yellow, rfv)<br />
   pickup_id: pickup location id<br />
   dropoff_id - drop off location id <br />
   pickup_date  - pickup date (yyyy-MM-dd) <br/>
   dropoff_date  - drop off date (yyyy-MM-dd)