package dev.tunks.taxitrips.queries;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.data.mongodb.core.aggregation.AddFieldsOperation;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.ComparisonOperators;
import org.springframework.data.mongodb.core.aggregation.ComparisonOperators.Eq;
import org.springframework.data.mongodb.core.aggregation.ConditionalOperators;
import org.springframework.data.mongodb.core.aggregation.ConditionalOperators.Switch.CaseOperator;
import org.springframework.data.mongodb.core.aggregation.DateOperators;
import org.springframework.data.mongodb.core.aggregation.DateOperators.DayOfWeek;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import static dev.tunks.taxitrips.util.DataUtil.*;

/**
 * Metrics aggregate query implementation to calculate the week day average cost of taxi trips between two location zones. 
 *  
 * @author ebrimatunkara
 */
public class WeekDayCostMetricsQuery extends AggregateMetricsQuery{	

	/***
	 * Group operation to group by pickuplocationId, dropoffLocation,taxiType and tripDayOfWeek
	 * 
	 * @return GroupOperation
	 */
	@Override
	protected GroupOperation getGroupOperations() {
		List<CaseOperator> cases = dayOfWeekCases(DAY_OF_WEEK);
	   return group(bind("pickup",PICKUP_LOCATION_ID)
                .and("dropoff", DROPOFF_LOCATION_ID)
                .and(TAXI_TYPE, TAXI_TYPE)
                .and(DAY_OF_WEEK,DAY_OF_WEEK))
                .first(TAXI_TYPE).as(TAXI_TYPE)
	            .first("pickupLocation").as("pickupLocation")
	            .first("dropoffLocation").as("dropoffLocation")
	            .first(ConditionalOperators.switchCases(cases)).as(DAY_OF_WEEK)
			    .avg("$totalAmount").as("averageCost");
	}
	
	@Override
	protected List<AddFieldsOperation> getFieldsOperations() {
		DayOfWeek dayOfWeek  = DateOperators.dateOf(PICKUP_DATETIME).dayOfWeek();
		AddFieldsOperation operation = Aggregation.addFields().addFieldWithValue(DAY_OF_WEEK, dayOfWeek).build();
		return Arrays.asList(operation);
	};

	private List<CaseOperator> dayOfWeekCases(String fieldReference) {
		List<CaseOperator> cases = new ArrayList<>();
		Eq operator = ComparisonOperators.Eq.valueOf(fieldReference);
		cases.add(CaseOperator.when(operator.equalToValue(1)).then("Sunday"));
		cases.add(CaseOperator.when(operator.equalToValue(2)).then("Monday"));
		cases.add(CaseOperator.when(operator.equalToValue(3)).then("Tuesday"));
		cases.add(CaseOperator.when(operator.equalToValue(4)).then("Wednesday"));
		cases.add(CaseOperator.when(operator.equalToValue(5)).then("Thursday"));
		cases.add(CaseOperator.when(operator.equalToValue(6)).then("Friday"));
		cases.add(CaseOperator.when(operator.equalToValue(7)).then("Saturday"));
		return cases;
	}
}
