package dev.tunks.taxitrips.queries;
import java.util.Date;
import java.util.Optional;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import dev.tunks.taxitrips.model.TaxiType;
import static dev.tunks.taxitrips.util.DataUtil.*;

public class QueryParams {
	private String pickupLocationId;
	private String dropoffLocationId;
    private Date pickupDate;
    private Date dropoffDate;
    private String taxiType;
    private String borough;
    private String zone;
    private String searchTerm;
    
	public Optional<String> getPickupLocationId() {
		return Optional.ofNullable(pickupLocationId);
	}

	public void setPickupLocationId(String pickupLocationId) {
		this.pickupLocationId = pickupLocationId;
	}

	public Optional<String> getDropoffLocationId() {
		return Optional.ofNullable(dropoffLocationId);
	}

	public void setDropoffLocationId(String dropoffLocationId) {
		this.dropoffLocationId = dropoffLocationId;
	}
	
	public Optional<Date> getPickupDate() {
		return Optional.ofNullable(pickupDate);
	}

	public void setPickupDate(Date pickupDate) {
		this.pickupDate = pickupDate;
	}

	public Optional<Date> getDropoffDate() {
		return Optional.ofNullable(dropoffDate);
	}

	public void setDropoffDate(Date dropoffDate) {
		this.dropoffDate = dropoffDate;
	}

	public Optional<String> getTaxiType() {
		return Optional.ofNullable(taxiType);
	}

	public void setTaxiType(String taxiType) {
		this.taxiType = taxiType;
	}

	public Optional<String> getBorough() {
		return Optional.ofNullable(borough);
	}

	public void setBorough(String borough) {
		this.borough = borough;
	}

	public Optional<String> getZone() {
		return Optional.ofNullable(zone);
	}

	public void setZone(String zone) {
		this.zone = zone;
	}

	public Optional<String> getSearchTerm() {
		return Optional.ofNullable(searchTerm);
	}

	public void setSearchTerm(String searchTerm) {
		this.searchTerm = searchTerm;
	}

	public Query toQuery() {
		   Query query = new Query();

		   if(getTaxiType().isPresent()) {
			  query.addCriteria(Criteria.where(TAXI_TYPE).is(taxiType));
		   }
		   
		   if(getPickupLocationId().isPresent()) {
			  query.addCriteria(Criteria.where(PICKUP_LOCATION_ID).is(pickupLocationId));
		   }
		   
		   if(getDropoffLocationId().isPresent()) {
			   query.addCriteria(Criteria.where(DROPOFF_LOCATION_ID).is(dropoffLocationId));
		   }
		   
		   if(getPickupDate().isPresent()) {
			  query.addCriteria(Criteria.where(PICKUP_DATETIME).gte(pickupDate));
		   }
		   
		   if(getDropoffDate().isPresent()) {
			  query.addCriteria(Criteria.where(DROPOFF_DATETIME).lte(dropoffDate));
		   }
		   return query;
	}
	
	public static QueryParamsBuilder newQueryParams() {
		return new QueryParamsBuilder();
	}

    /***
     * QueryParamsBuilder class to create query params
     *  
     */	
	public static class QueryParamsBuilder{
		   private QueryParams queryParams;
		   
		   public QueryParamsBuilder() {
			   queryParams = new QueryParams();
		   }
		   
			public QueryParamsBuilder setPickupLocationId(String pickupLocationId) {
				queryParams.setPickupLocationId(pickupLocationId);
				return this;
			}

			public QueryParamsBuilder setDropoffLocationId(String dropoffLocationId) 
			{
				queryParams.setDropoffLocationId(dropoffLocationId);
				return this;
			}
			
			public QueryParamsBuilder setPickupDate(Date pickupDate) {
				queryParams.setPickupDate(pickupDate);
				return this;
			}

			public QueryParamsBuilder setDropoffDate(Date dropoffDate) {
				queryParams.setDropoffDate(dropoffDate);
				return this;
			}

			public QueryParamsBuilder setTaxiType(TaxiType taxiType) {
				if(taxiType != null) 
				{
				   queryParams.setTaxiType(taxiType.name());
				}
				return this;
		    }
			
			public QueryParamsBuilder setTaxiType(String taxiType) {
				if(taxiType != null) {
				   queryParams.setTaxiType(taxiType);
				}
				return this;
		    }
			
			public QueryParamsBuilder setBorough(String borough) {
				queryParams.setBorough(borough);
				return this;
			}
			
			public QueryParamsBuilder setZone(String zone) {
				queryParams.setZone(zone);
				return this;
			}
			
			public QueryParamsBuilder setSeachTerm(String searchTerm) 
			{
				queryParams.setSearchTerm(searchTerm);
				return this;
			}
			
		    public QueryParams build() {
			   return queryParams;
		    }	   
	}
}
