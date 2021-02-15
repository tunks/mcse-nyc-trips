### Restful API End points

Find location zone by borough or zone 
	GET /api/zones?borough=&zone=?

Find location zone by borough or zone  with sort ascending option

	GET /api/zones?borough=&zone=?sort=borough,asc 

Search borough by search term

	GET /api/zones/search?term=&size=10

Find trips by query parameters
	
	GET /api/trips?taxi_type=&pickup_id=&dropoff_id=&pickup_date=&dropoff_date 

Find trips by query parameters with sort ascending option

	GET /api/trips?axi_type=&pickup_id=&dropoff_id=&pickup_date=&dropoff_date=&sort=taxi_type,asc

Trip metrics
		
		GET /api/trips/metrics?name=&pickup_id=&dropoff_id=
     
##### Query parameters
   taxi_type: type of taxi vehicle(green, yellow, rfv)<br />
   pickup_id: pickup location id<br />
   dropoff_id - drop off location id <br />
   pickup_date  - pickup date (yyyy-MM-dd) <br/>
   dropoff_date  - drop off date (yyyy-MM-dd)
   