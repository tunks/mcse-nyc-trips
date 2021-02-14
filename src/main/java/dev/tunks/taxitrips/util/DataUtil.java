package dev.tunks.taxitrips.util;

import org.bson.Document;
import org.springframework.data.mongodb.core.query.CriteriaDefinition;
import org.springframework.data.mongodb.core.query.Query;

public class DataUtil {
	public static final String PICKUP_LOCATION_ID = "pickupLocationId";
	public static final String DROPOFF_LOCATION_ID = "dropoffLocationId";
	public static final String PICKUP_DATETIME = "pickupDateTime";
	public static final String DROPOFF_DATETIME = "dropoffDateTime";
	public static final String TAXI_TYPE = "taxiType";
	public static final String DAY_OF_WEEK = "tripDayOfWeek";
	public static final String HOUR_OF_DAY = "tripHourOfDay";
	/**
	 * Convert Query to CriteriaDefinition for MongoDB aggregate pipeline operations
	 * 
	 * @param query: Query
	 * @return CriteriaDefinition
	 */
	public static CriteriaDefinition toQueryCriteria(Query query) {
		return new CriteriaDefinition() {
			@Override
			public Document getCriteriaObject() {
				return query.getQueryObject();
			}

			@Override
			public String getKey() {
				return null;
			}
		};
	}
}
