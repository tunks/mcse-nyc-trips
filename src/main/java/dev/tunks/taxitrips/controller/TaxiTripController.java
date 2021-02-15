package dev.tunks.taxitrips.controller;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import dev.tunks.taxitrips.model.TaxiTrip;
import dev.tunks.taxitrips.model.TripMetricsEntity;
import dev.tunks.taxitrips.queries.QueryFactory.MetricsName;
import dev.tunks.taxitrips.queries.QueryParams;
import dev.tunks.taxitrips.service.TaxiTripService;

/**
 * 
 *  Taxi trip controller to provide Rest Web API to query taxi trips and metrics 
 *  
 *  @author ebrimatunkara
 **/
@RestController
@RequestMapping("/api/trips")
public class TaxiTripController {
	private static final Logger logger = LoggerFactory.getLogger(LocationController.class);

	@Autowired
	private TaxiTripService taxiTripService;
    
    /***
     * GET /api/trips
     * GET /api/trips?sort=taxi_type,asc
     * GET /api/trips?taxi_type=&pickup_id=&dropoff_id=&pickup_date=&dropoff_date
     * 
     * Find all trips by query parameters with pageable option
     *  
     * @param taxi_type - type of taxi vehicle(green, yellow, hire | rfv| for-hire)
     * @param pickup_id  - pickup location id
     * @param dropoff_id - drop off location id 
     * @param pickup_date  - pickup date (yyyy-MM-dd)
     * @param dropoff_date  - drop off date (yyyy-MM-dd) 
     * @param page option
     * @return page list of taxi trips
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public Page<TaxiTrip> findAllTrips(@RequestParam(name="taxi_type", required=false) String taxiType,
			@RequestParam( name="pickup_id", required=false) String pickupLocationId,
			@RequestParam(name="dropoff_id", required=false) String dropoffLocationId,
			@RequestParam(name="pickup_date", required=false) @DateTimeFormat(iso=DateTimeFormat.ISO.DATE) Date pickupDate, 
			@RequestParam(name="dropoff_date", required=false) @DateTimeFormat(iso=DateTimeFormat.ISO.DATE) Date dropoffDate,
			Pageable pageable)
    {
		QueryParams queryParams = QueryParams.newQueryParams()
				                            .setTaxiType(taxiType)
							                .setPickupLocationId(pickupLocationId)
							                .setDropoffLocationId(dropoffLocationId)
							                .setPickupDate(pickupDate)
							                .setDropoffDate(dropoffDate)
							                .build();
		return taxiTripService.findAll(queryParams, pageable);
    }
	
    /***
     * GET /api/trips/metrics?name
     *       
     * Get trip metrics query result
     * 
     * @param metrics - name of metrics query (cost, hour, day)
     * @param pickup_id  - pickup location id
     * @param dropoff_id - drop off location id  
     * @param page option
     * @return page list of taxi trips
     */
	@GetMapping(value="/metrics")
	public List<TripMetricsEntity> getTripMetrics(@RequestParam("name") String name,
			@RequestParam("pickup_id") String pickupLocationId, @RequestParam("dropoff_id") String dropoffLocationId) 
	{
		Optional<MetricsName> metricsName = Optional.ofNullable(MetricsName.getName(name));
		if(metricsName.isPresent()) {
		    QueryParams queryParams = QueryParams.newQueryParams()
							                .setPickupLocationId(pickupLocationId)
							                .setDropoffLocationId(dropoffLocationId)
							                .build();
		    return taxiTripService.getTripMetrics(metricsName.get(), queryParams);
		}
		else {
			logger.error("Metrics not found");
			 throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Metrics not found");
		}
	}
}
