package dev.tunks.taxitrips.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.time.LocalDate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import dev.tunks.taxitrips.TaxitripServiceApplication;
import dev.tunks.taxitrips.queries.QueryFactory.MetricsName;


@SpringBootTest(classes = TaxitripServiceApplication.class)
@AutoConfigureMockMvc
@WebAppConfiguration
public class TaxiTripControllerTest {
	@Autowired
	private MockMvc mvc;	
	private static final String API_URL = "/api/trips";

	@BeforeEach
	public void setUp() throws Exception {
	}

	@AfterEach
	public void tearDown() throws Exception {
	}

	@Test
	public void testFindAllTrips() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(API_URL)
                .param("pickup_id", "1")
                .param("dropoff_id", "2")
                .param("pickup_date", LocalDate.now().minusDays(1).toString())
                .param("dropoff_date", LocalDate.now().toString());
		mvc.perform(requestBuilder).andExpect(status().is(200));
	}

	@Test
	public void testGetTripMetrics() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(API_URL+"/metrics")
				.param("name", MetricsName.COST.getValue())
                .param("pickup_id", "1")
                .param("dropoff_id", "2");
		mvc.perform(requestBuilder).andExpect(status().is(200));
	}
	
	@Test
	public void testGetTripMetricsNotFoundException() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(API_URL+"/metrics")
				.param("name","duration")
                .param("pickup_id", "1")
                .param("dropoff_id", "2");
		mvc.perform(requestBuilder).andExpect(status().isNotFound());
	}
	
}
