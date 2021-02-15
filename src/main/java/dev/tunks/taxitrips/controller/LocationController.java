package dev.tunks.taxitrips.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import dev.tunks.taxitrips.model.Location;
import dev.tunks.taxitrips.queries.QueryParams;
import dev.tunks.taxitrips.service.LocationService;
/**
 * 
 *  Location zone controller to provide Rest Web API to query locations with parameters
 *  
 *  @author ebrimatunkara
 **/
@RestController
@RequestMapping("/api/zones")
public class LocationController {
	private static final Logger logger = LoggerFactory.getLogger(LocationController.class);

    @Autowired
	private LocationService locationService;
	
    @GetMapping()
	public Page<Location> findAllLocations(@RequestParam(required=false, name="borough") String borough,
			@RequestParam(required=false, name="zone") String zone, Pageable pageable){
		QueryParams queryParams = QueryParams.newQueryParams().setBorough(borough).setZone(zone).build();
         
		return locationService.findAll(queryParams, pageable);
    }
	
	@GetMapping("/search")
	public Page<Location> findLocationsByTerm(@RequestParam(name="term") String searchTerm, Pageable pageable)
	{
		QueryParams queryParams = QueryParams.newQueryParams().setSeachTerm(searchTerm).build();
		return locationService.findAll(queryParams, pageable);
    }
}
