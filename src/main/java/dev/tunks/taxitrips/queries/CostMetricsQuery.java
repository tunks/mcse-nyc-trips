package dev.tunks.taxitrips.queries;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import static dev.tunks.taxitrips.util.DataUtil.*;
/**
 * Metrics aggregate query implementation to calculate the average taxi trip cost between two location zones. 
 *  
 * @author ebrimatunkara
 */
public class CostMetricsQuery extends AggregateMetricsQuery{	
   
	@Override
	protected GroupOperation getGroupOperations() {
		return group(bind("pickupLocation",PICKUP_LOCATION_ID)
                .and("dropoffLocation", DROPOFF_LOCATION_ID)
                .and(TAXI_TYPE, TAXI_TYPE))
                .first(TAXI_TYPE).as(TAXI_TYPE)
                .first("pickupLocation").as("pickupLocation")
                .first("dropoffLocation").as("dropoffLocation")
		        .avg("$totalAmount").as("averageCost");
	}
}
