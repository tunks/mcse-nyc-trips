package dev.tunks.taxitrips.service;

import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import dev.tunks.taxitrips.TaxitripServiceApplication;
import dev.tunks.taxitrips.model.TaxiTrip;
import dev.tunks.taxitrips.model.TaxiType;
import dev.tunks.taxitrips.model.TripMetricsEntity;
import dev.tunks.taxitrips.queries.QueryFactory.MetricsName;
import dev.tunks.taxitrips.queries.QueryParams;
import dev.tunks.taxitrips.util.DataUtil;
import static dev.tunks.taxitrips.utils.DummyDataUtil.*;

@SpringBootTest(classes = {TaxitripServiceApplication.class})
public class TaxiTripServiceTest {
    @Autowired
	private TaxiTripService taxiTripService;
    
    @Autowired
    private MongoTemplate mongoTemplate;
    private String pickupLocationId;
	private String dropoffLocationId;
	
	private List<TaxiTrip> trips;

    @BeforeEach
	public void setUp() throws Exception {
    	pickupLocationId = "4000";
    	dropoffLocationId = "4001";
		trips = saveTrips(mongoTemplate,pickupLocationId, dropoffLocationId, 10);
	}

	@AfterEach
	public void cleanup() throws Exception {
		Criteria criteria = Criteria.where(DataUtil.PICKUP_LOCATION_ID).is(pickupLocationId)
				                   .and(DataUtil.DROPOFF_LOCATION_ID).is(dropoffLocationId);
		mongoTemplate.remove( new Query(criteria), TaxiTrip.class);
	}
	
	@Test
	public void findAllPageable() {
		Page<TaxiTrip> result  = taxiTripService.findAll(PageRequest.of(0,Integer.MAX_VALUE));
		assertEquals(trips.size(),result.getNumberOfElements());
	}
	
	/***
	 * Query with invalid query parameters and expected results are empty
	 */
	@Test
	public void findAllWithInvalidQueryParams() {
		QueryParams queryParams = QueryParams.newQueryParams().setPickupLocationId("00001").build();
		Page<TaxiTrip> result = taxiTripService.findAll(queryParams, PageRequest.of(0, 10));
		assertEquals(0, result.getNumberOfElements());
	}
	
	/***
	 * Query with valid zone location query parameters and expected results are not empty 
	 */
	@Test
	public void findAllWithLocationQueryParams() {
		//Query parameters with pickupLocationId and the expected result is not empty
		QueryParams queryParams = QueryParams.newQueryParams().setPickupLocationId(pickupLocationId).build();
		Page<TaxiTrip> result = taxiTripService.findAll(queryParams, PageRequest.of(0, 10));
		assertEquals(10, result.getNumberOfElements());
		
		//Query parameters with pickupLocationId,dropoffLocationId and the expected result is not empty
		queryParams = QueryParams.newQueryParams().setPickupLocationId(pickupLocationId)
				                                  .setDropoffLocationId(dropoffLocationId)
				                                  .build();
		result = taxiTripService.findAll(queryParams, PageRequest.of(0, 10));
		assertEquals(10, result.getNumberOfElements());
	}

	/***
	 * Query with date query parameters and expected results are not empty 
	 */
	@Test
	public void findAllWithDateQueryParams() {
		//Query parameters with pickupDate  and the expected result is not empty
		Date pickupDate = toDate(LocalDateTime.now().minusMinutes(60));
		QueryParams queryParams = QueryParams.newQueryParams().setPickupDate(pickupDate).build();
		Page<TaxiTrip> result = taxiTripService.findAll(queryParams, PageRequest.of(0, 10));
		assertEquals(10, result.getNumberOfElements());
		
		//Query parameters with pickupDate,dropoffDate and the expected result is not empty
		Date dropoffDate = toDate(LocalDateTime.now());

		queryParams = QueryParams.newQueryParams().setPickupDate(pickupDate)
				                 .setDropoffDate(dropoffDate).build();
		result = taxiTripService.findAll(queryParams, PageRequest.of(0, 10));
		assertEquals(10, result.getNumberOfElements());
		
		//Query parameters with future pickupDate and the expected result is empty (zero)
		pickupDate = toDate(LocalDateTime.now().plusDays(1));
		queryParams = QueryParams.newQueryParams().setPickupDate(pickupDate).build();
		result = taxiTripService.findAll(queryParams, PageRequest.of(0, 10));
		assertEquals(0, result.getNumberOfElements());
	}

	/***
	 * Query with taxi type (green,yellow, rhi) query parameters and expected results are not empty 
	 */
	@Test
	public void findAllWithTaxiTypeQueryParams() {
		//Query parameters with 'GREEN' taxi type  and the expected result is not empty
		QueryParams queryParams = QueryParams.newQueryParams().setTaxiType(TaxiType.GREEN).build();
		Page<TaxiTrip> result = taxiTripService.findAll(queryParams, PageRequest.of(0, 10));
		assertEquals(10, result.getNumberOfElements());
		
		//Query parameters with 'YELLOW' taxi type  and the expected result is not empty
		queryParams = QueryParams.newQueryParams().setTaxiType(TaxiType.YELLOW).build();
		result = taxiTripService.findAll(queryParams, PageRequest.of(0, 10));
		assertEquals(10, result.getNumberOfElements());
		
		//Query parameters with 'RHV' taxi type  and the expected result is not empty
		queryParams = QueryParams.newQueryParams().setTaxiType(TaxiType.RHV).build();
		result = taxiTripService.findAll(queryParams, PageRequest.of(0, 10));
		assertEquals(10, result.getNumberOfElements());
	}

   
	/***
	 * 
	 * Query trip metrics with name query parameter
	 */
	@Test
	public void testGetTripMetrics() {
		QueryParams queryParams = QueryParams.newQueryParams()
				                    .setPickupLocationId(pickupLocationId)
				                    .setDropoffLocationId(dropoffLocationId).build();							
		List<TripMetricsEntity> result = taxiTripService.getTripMetrics(MetricsName.COST, queryParams);
		//expected 3 groups (green, yellow , rhv)
		assertEquals(3,result.size());
	}
}
