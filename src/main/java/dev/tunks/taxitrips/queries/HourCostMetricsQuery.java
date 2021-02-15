package dev.tunks.taxitrips.queries;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;
import java.util.Arrays;
import java.util.List;
import org.springframework.data.mongodb.core.aggregation.AddFieldsOperation;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.DateOperators;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.DateOperators.Hour;
import static dev.tunks.taxitrips.util.DataUtil.*;

/**
 * Metrics aggregate query implementation to calculate the hourly average cost of taxi trips between two location zones. 
 *  
 * @author ebrimatunkara
 */
public class HourCostMetricsQuery extends AggregateMetricsQuery{
	/***
	 * Group operation to group by pickuplocationId, dropoffLocation,taxiType and tripHourOfDay
	 * 
	 * @return GroupOperation
	 */
	@Override
	protected GroupOperation getGroupOperations() {
	   return group(bind("pickup",PICKUP_LOCATION_ID)
                .and("dropoff", DROPOFF_LOCATION_ID)
                .and(TAXI_TYPE, TAXI_TYPE)
                .and(HOUR_OF_DAY,HOUR_OF_DAY))
                .first(TAXI_TYPE).as(TAXI_TYPE)
	            .first("pickupLocation").as("pickupLocation")
	            .first("dropoffLocation").as("dropoffLocation")
	            .first(HOUR_OF_DAY).as(HOUR_OF_DAY)
			    .avg("$totalAmount").as("averageCost");
	};
	
	@Override
	protected List<AddFieldsOperation> getFieldsOperations() {
		Hour hourOfDay  = DateOperators.dateOf(PICKUP_DATETIME).hour();
		AddFieldsOperation operation = Aggregation.addFields().addFieldWithValue(HOUR_OF_DAY, hourOfDay).build();
		return Arrays.asList(operation);
	};

}
