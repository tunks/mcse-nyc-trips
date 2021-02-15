package dev.tunks.taxitrips.queries;

import static dev.tunks.taxitrips.util.DataUtil.DROPOFF_LOCATION_ID;
import static dev.tunks.taxitrips.util.DataUtil.PICKUP_LOCATION_ID;
import static dev.tunks.taxitrips.util.DataUtil.toQueryCriteria;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.lookup;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.match;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.project;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.unwind;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregationOptions;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.aggregation.AddFieldsOperation;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.Fields;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.LookupOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.query.CriteriaDefinition;
/**
 * 
 * Abstract metrics class to create and extend MongoDB aggregate pipeline queries  
 * 
 * @author ebrimatunkara
 */
public abstract class AggregateMetricsQuery  implements MetricsQuery<Aggregation>{
	
	@Override
	public Aggregation query(QueryParams params, Sort sort) {
		List<AggregationOperation> operations = new ArrayList<AggregationOperation>();
		CriteriaDefinition criteria = toQueryCriteria(params.toQuery());
		GroupOperation groupOps = getGroupOperations();
		ProjectionOperation projectOps = project(Fields.fields()).andExclude("_id");
		operations.add(match(criteria));
		operations.addAll(getFieldsOperations());
		operations.addAll(getLookupOperations());
		operations.addAll(Arrays.asList(unwind("$dropoffLocation"), unwind("$dropoffLocation"), groupOps, projectOps));
		return newAggregation(operations)
				.withOptions(newAggregationOptions().allowDiskUse(true).build());
	}
	
	protected List<LookupOperation> getLookupOperations(){
		return Arrays.asList(lookup("location", PICKUP_LOCATION_ID, "_id", "pickupLocation"),
				             lookup("location", DROPOFF_LOCATION_ID, "_id", "dropoffLocation"));
	}
	
	protected abstract GroupOperation getGroupOperations();
	
	protected  List<AddFieldsOperation> getFieldsOperations()
	{
		return Collections.emptyList();
	}
	
}
