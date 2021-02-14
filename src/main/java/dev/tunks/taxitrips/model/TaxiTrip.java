package dev.tunks.taxitrips.model;

import java.util.Date;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 
*/
@Document
public class TaxiTrip {
	@Id
	private String id;
	@Indexed
	private Date pickupDateTime;
	@Indexed
	private Date dropoffDateTime;
	@Indexed
	private TaxiType taxiType;
	private double distance;
	@Indexed
	private double totalAmount;
	@Indexed
	private String pickupLocationId;
	@Indexed
	private String dropoffLocationId;
	//duration in minutes
	private double duration; 

	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public Date getPickupDateTime() {
		return pickupDateTime;
	}
	
	public void setPickupDateTime(Date pickupDateTime) {
		this.pickupDateTime = pickupDateTime;
	}
	
	public Date getDropoffDateTime() {
		return dropoffDateTime;
	}
	
	public void setDropoffDateTime(Date dropoffDateTime) {
		this.dropoffDateTime = dropoffDateTime;
	}
	
	public double getDistance() {
		return distance;
	}
	
	public void setDistance(double distance) {
		this.distance = distance;
	}
	
	
	public String getPickupLocationId() {
		return pickupLocationId;
	}

	public void setPickupLocationId(String pickupLocationId) {
		this.pickupLocationId = pickupLocationId;
	}

	public String getDropoffLocationId() {
		return dropoffLocationId;
	}

	public void setDropoffLocationId(String dropoffLocationId) {
		this.dropoffLocationId = dropoffLocationId;
	}

	public TaxiType getTaxiType() {
		return taxiType;
	}

	public void setTaxiType(TaxiType taxiType) {
		this.taxiType = taxiType;
	}
	
	public double getTotalAmount() {
		return totalAmount;
	}
	
	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public double getDuration() {
		return duration;
	}

	public void setDuration(double duration) {
		this.duration = duration;
	}

	@Override
	public String toString() {
		return "VehicleTrip [id=" + id + ", pickupDateTime=" + pickupDateTime + ", dropoffDateTime=" + dropoffDateTime
				+ ", distance=" + distance + ", totalAmount=" + totalAmount + ", pickupLocationId=" + pickupLocationId 
				+ ", dropoffLocationId=" + dropoffLocationId +"]";
	}
}
