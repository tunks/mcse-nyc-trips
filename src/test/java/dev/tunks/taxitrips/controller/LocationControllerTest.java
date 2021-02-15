package dev.tunks.taxitrips.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import dev.tunks.taxitrips.TaxitripServiceApplication;
import dev.tunks.taxitrips.model.Location;

@SpringBootTest(classes = TaxitripServiceApplication.class)
@AutoConfigureMockMvc
@WebAppConfiguration
public class LocationControllerTest {
	@Autowired
	private MockMvc mvc;
	@Autowired
	private MongoTemplate mongoTemplate;
	
	private static final String API_URL = "/api/zones";
	private String locationId;
	private String borough;
	private String zone;
	
	
	@BeforeEach
	public void setUp() throws Exception {
		locationId = "10001";
		borough = "Grand Concourse";
		zone = "Bronx";
		mongoTemplate.save(new Location(locationId,borough,zone));

	}

	@AfterEach
	public void tearDown() throws Exception {
		Criteria criteria = Criteria.where("_id").is(locationId);
        mongoTemplate.remove( new Query(criteria), Location.class);
	}

	@Test
	public void testFindLocationByBoroughAndZone() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(API_URL)
                .param("borough", borough);
		mvc.perform(requestBuilder).andExpect(status().is(200));
	}

	@Test
	public void testFindLocationsBySearchTerm() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(API_URL+"/search")
                .param("term", borough);
		mvc.perform(requestBuilder).andExpect(status().is(200));
	}

}
