package dev.tunks.taxitrips.repositories;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import javax.annotation.Resource;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
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
import org.springframework.data.mongodb.core.query.Term;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.test.context.ActiveProfiles;

import dev.tunks.taxitrips.TaxitripServiceApplication;
import dev.tunks.taxitrips.model.Location;
import static dev.tunks.taxitrips.utils.DummyDataUtil.*;

@SpringBootTest(classes = {TaxitripServiceApplication.class})
@TestInstance(Lifecycle.PER_CLASS)
@ActiveProfiles("dev")
public class LocationRepositoryTest {

	@Resource
	private LocationRepository locationRepository;
	
	@Autowired
	private MongoTemplate mongoTemplate;
	private String locationId;
	@BeforeAll
	public void setUp() throws Exception {
		locationId = "20001";
		saveLocation(mongoTemplate, locationId);
	}

	@AfterAll
	public void cleanup() throws Exception {
		Criteria criteria = Criteria.where("_id").is(locationId);
        mongoTemplate.remove( new Query(criteria), Location.class);
	}

	@Test
	public void findAllByBorough() throws InterruptedException {
		String borough = "Borough_20001";
        Page<Location> result = locationRepository.findByBorough(borough,PageRequest.of(0, 1));
        assertTrue(result.stream().findFirst().isPresent());
        assertEquals(1,result.getNumberOfElements());
	}
	
	@Test
	public void findAllBySearchTerms() throws InterruptedException {
		TextCriteria criteria = new TextCriteria()
				                 .caseSensitive(false)
				                 .diacriticSensitive(false)
				                 .matching(new Term("borough_20001"))
				                 .matching("20001");
        Page<Location> result = locationRepository.findAllBy(criteria, PageRequest.of(0, 1));
        assertTrue(result.get().findFirst().isPresent());
        assertEquals(1,result.getTotalElements());
	}
}
