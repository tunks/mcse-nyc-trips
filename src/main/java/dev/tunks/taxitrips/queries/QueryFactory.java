package dev.tunks.taxitrips.queries;

import org.springframework.data.mongodb.core.aggregation.Aggregation;

/***
 * Query factory to provide instances of metrics query options
 * 
 *  @author ebrimatunkara
 */
public class QueryFactory {
   
	public MetricsQuery<Aggregation> createMetricsQuery(MetricsName name)
    {
    	 switch(name) {
    	   case COST:
    		  return new CostMetricsQuery();
    	   case HOUR_OF_DAY:
     		  return new HourCostMetricsQuery();
    	   case DAY_OF_WEEK:
      		  return new WeekDayCostMetricsQuery();
    	 }
    	return null; 
    }
	
	public static enum MetricsName{
		COST("cost"),
		DAY_OF_WEEK("day"),
		HOUR_OF_DAY("hour");
		
		private String value;
		
		MetricsName(String value) {
			this.value = value;
		}
		
		public String getValue() {
			return value;
		}
		
		public static MetricsName getName(String name) {
			if(name == null || name.isEmpty()) 
			{
				return null;
			}
			switch(name.toLowerCase()) {
			  case "cost":
			  case "by_cost":
				 return MetricsName.COST;
			  case "day":
			  case "by_day":
				  return MetricsName.DAY_OF_WEEK;
			  case "hour":
			  case "by_hour":
				  return MetricsName.HOUR_OF_DAY; 
			  default:
				  return null;
			}
		}
	}
}
