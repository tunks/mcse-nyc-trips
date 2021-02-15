#### Source Data
The data for trip trips for each month is available [NYC TLC](https://www1.nyc.gov/site/tlc/about/tlc-trip-record-data.page).
For this project, the data-processor service parsed and process the dataset for January 2020.

 The following relevants fields from the dataset will be process.
- Taxi Zone:
  - LocationID: id of taxi trip zone
  - borough: name of the borough for a taxi trip (a borough has multiple zones locations)
  - Zone:  name of the taxi zone 
  
- Green Taxi Trips:
  -  PULocationID: id of pickup taxi location zone
  -  DOLocationID: id of drop off taxi location zone
  -  lpep_pickup_datetime: passenger pickup off date and time
  -  lpep_dropoff_datetime: passenger drop off date and time
  -  Total_amount: total amount charged for trip
  
- Yellow Taxi Trips:
  - PULocationID: id of pickup taxi location zone
  - DOLocationID: id of drop off taxi location zone
  - tpep_pickup_datetime: passenger pickup off date and time
  - tpep_dropoff_datetime: passenger drop off date and time
  - Total_amount: total amount charged for trip
  	
- FHV Taxi Trips:
  -  PULocationID: iid of pickup taxi location zone
  -  DOLocationID: id of drop off taxi location zone
  -  Pickup_datetime: passenger pickup off date and time
  -  DropOff_datetime: passenger drop off date and time
  
*Note: FHV dataset does not contain Total_Amount for metrics cost query*
