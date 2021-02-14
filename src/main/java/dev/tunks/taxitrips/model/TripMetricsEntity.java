package dev.tunks.taxitrips.model;


public class TripMetricsEntity{
	private Location pickupLocation;
	private Location dropoffLocation;
	private String taxiType;
	private double averageCost;
	private String tripHourOfDay;
	private String tripDayOfWeek;
	
    public Location getPickupLocation() {
		return pickupLocation;
	}

	public void setPickupLocation(Location pickupLocation) {
		this.pickupLocation = pickupLocation;
	}

	public Location getDropoffLocation() {
		return dropoffLocation;
	}

	public void setDropoffLocation(Location dropoffLocation) {
		this.dropoffLocation = dropoffLocation;
	}

	public String getTaxiType() {
		return taxiType;
	}

	public void setTaxiType(String taxiType) {
		this.taxiType = taxiType;
	}

	public double getAverageCost() {
		return averageCost;
	}

	public void setAverageCost(double averageCost) {
		this.averageCost = averageCost;
	}
	
	public String getTripHourOfDay() {
		return tripHourOfDay;
	}

	public void setTripHourOfDay(String tripHourOfDay) {
		this.tripHourOfDay = tripHourOfDay;
	}

	public String getTripDayOfWeek() {
		return tripDayOfWeek;
	}

	public void setTripDayOfWeek(String tripDayOfWeek) {
		this.tripDayOfWeek = tripDayOfWeek;
	}

	@Override
	public String toString() {
		return "TripMetricsEntity [pickupLocation=" + pickupLocation + ", dropoffLocation=" + dropoffLocation
				+ ", taxiType=" + taxiType + ", averageCost=" + averageCost + ", tripHourOfDay=" + tripHourOfDay
				+ ", tripDayOfWeek=" + tripDayOfWeek + "]";
	}
}
