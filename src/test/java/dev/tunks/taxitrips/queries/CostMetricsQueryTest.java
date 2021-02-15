package dev.tunks.taxitrips.queries;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import dev.tunks.taxitrips.TaxitripServiceApplication;
import dev.tunks.taxitrips.model.TripMetricsEntity;
import dev.tunks.taxitrips.model.TaxiTrip;
import dev.tunks.taxitrips.util.DataUtil;
import static dev.tunks.taxitrips.utils.DummyDataUtil.*;


@SpringBootTest(classes = {TaxitripServiceApplication.class})
@TestInstance(Lifecycle.PER_CLASS)
public class CostMetricsQueryTest {
	@Autowired
	private MongoTemplate mongoTemplate;

    private CostMetricsQuery metricsQuery;
    private String pickupLocationId;
    private String dropoffLocationId;
	
    @BeforeEach
	public void setUp() throws Exception {
		metricsQuery = new CostMetricsQuery();
		pickupLocationId = "10001";
		dropoffLocationId = "10002";
	    saveTrips(mongoTemplate,pickupLocationId, dropoffLocationId,10);
	}

	@AfterAll
	public void cleaup() throws Exception {
		Criteria criteria = Criteria.where(DataUtil.PICKUP_LOCATION_ID).is(pickupLocationId)
                            .and(DataUtil.DROPOFF_LOCATION_ID).is(dropoffLocationId);
        mongoTemplate.remove( new Query(criteria), TaxiTrip.class);
	}

	@Test
	public void testCostMetricsQuery() {
		QueryParams queryParams = QueryParams.newQueryParams()
				                  .setPickupLocationId(pickupLocationId)
				                  .setDropoffLocationId(dropoffLocationId)
				                  .build();
		Aggregation agg = metricsQuery.query(queryParams,Sort.unsorted());
		AggregationResults<TripMetricsEntity> result = mongoTemplate.aggregate(agg, TaxiTrip.class, TripMetricsEntity.class);
		List<TripMetricsEntity> entities = result.getMappedResults();
		assertTrue(entities.size()>0);
		entities.stream().forEach(item->{
			assertEquals(item.getAverageCost(),10);
		});
	}
	
	@Test
	public void testCostMetricsQueryThrowsException() {
		//TODO
		QueryParams queryParams = new QueryParams();
		metricsQuery.query(queryParams,Sort.unsorted());
		//assertThrows(IllegalArgumentException.class,()->{metricsQuery.query(queryParams);});		
	}
	
}
