package dev.tunks.taxitrips.repositories;

import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.annotation.Resource;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import dev.tunks.taxitrips.TaxitripServiceApplication;
import dev.tunks.taxitrips.model.TaxiTrip;
import dev.tunks.taxitrips.util.DataUtil;
import static dev.tunks.taxitrips.utils.DummyDataUtil.*;


@SpringBootTest(classes = { TaxitripServiceApplication.class })
@TestInstance(Lifecycle.PER_CLASS)
public class TaxiTripRepositoryTest {
	@Resource
	private TaxiTripRepository tripRepository;
	@Autowired
	private MongoTemplate mongoTemplate;
	private String pickupLocationId;
	private String dropoffLocationId;
	private List<TaxiTrip> trips;

	@BeforeAll
	public void setUp() throws Exception {
		pickupLocationId = "3001";
		dropoffLocationId = "3002";
		trips = saveTrips(mongoTemplate, pickupLocationId, dropoffLocationId, 10);
	}

    @AfterAll
	public void cleanup() {
    	Criteria criteria = Criteria.where(DataUtil.PICKUP_LOCATION_ID).is(pickupLocationId)
                                   .and(DataUtil.DROPOFF_LOCATION_ID).is(dropoffLocationId);
         mongoTemplate.remove( new Query(criteria), TaxiTrip.class);
	}
	
	@Test
	public void findByPickupAndDropoffLocation_RecordFound() {
		Page<TaxiTrip> result = tripRepository.findAllBy(pickupLocationId, dropoffLocationId, PageRequest.of(0, 1));
		Optional<TaxiTrip> record = result.get().findFirst();
		assertTrue(record.isPresent());
		assertEquals(trips.size(), result.getTotalElements());
		assertEquals(pickupLocationId, record.get().getPickupLocationId());
		assertEquals(dropoffLocationId, record.get().getDropoffLocationId());
	}
	
	@Test
	public void findByDate_RecordNotFound() {
		Date pickupDate = toDate(LocalDateTime.now().minusMinutes(10));
		Date dropoffDate = toDate(LocalDateTime.now());

		Page<TaxiTrip> result = tripRepository.findAllBy(pickupDate,
				dropoffDate, PageRequest.of(0, 1));
		Optional<TaxiTrip> record = result.get().findFirst();
		assertEquals(false,record.isPresent());
	}

	@Test
	public void findByDate() {
		Date pickupDate = toDate(LocalDateTime.now().minusMinutes(40));
		Date dropoffDate = toDate(LocalDateTime.now().plusMinutes(10));
		Page<TaxiTrip> result = tripRepository.findAllBy(pickupDate,dropoffDate, PageRequest.of(0, 1));
		Optional<TaxiTrip> record = result.get().findFirst();
		assertTrue(record.isPresent());
	}

}
