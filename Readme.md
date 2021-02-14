## Take Home Engineering Challenge - Microsoft CSE Team
This is a coding challenge to develop a Restful API solution for NYC TLC taxi travelers. The solution provides Restful APIs for users to find Taxi zone locations,trips and cost travel metrics for different type of taxi vehicle in the city. 

I have prioritized the implementation on certain key features and requirements due to the limited time constraint. I spent some time to understand the requirements and relevant data set required for the solution.
 
### Code Dependencies
- Apache Maven 3.x
- MongoDB database (Not required for Junit test cases, it uses an embedded MongoDB for execute test units)

### Problem requirements
- Distance of a taxi trip between two borough zones
- Cost of a taxi trip between two borough zones
- Find taxi trips between different borough zones
- Filter taxi trips based on yellow cab, green cab and for-hire vehicles
- Filter taxi trips based on pickup and drop off dates
- Provide few interesting metrics for taxi trips
 
### Approach
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