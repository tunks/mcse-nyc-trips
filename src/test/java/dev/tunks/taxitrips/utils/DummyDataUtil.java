package dev.tunks.taxitrips.utils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.mongodb.core.MongoTemplate;

import dev.tunks.taxitrips.model.Location;
import dev.tunks.taxitrips.model.TaxiType;
import dev.tunks.taxitrips.model.TaxiTrip;

public class DummyDataUtil {

	public static List<TaxiTrip> saveTrips(MongoTemplate mongoTemplate, String pickupLocationId,
			String dropoffLocationId, double amount) {

		saveLocation(mongoTemplate,pickupLocationId);
		saveLocation(mongoTemplate,dropoffLocationId);

		List<TaxiTrip> trips = createTaxiTrips(pickupLocationId,dropoffLocationId,amount);

		return mongoTemplate.insertAll(trips).stream().collect(Collectors.toList());
	}
	
	public static Location saveLocation(MongoTemplate mongoTemplate, String locationId) {
		String borough = "Borough_"+locationId;
		String zone = "Zone_"+locationId;
		return mongoTemplate.save(new Location(locationId,borough,zone));
	}

	public static List<TaxiTrip> createTaxiTrips(String pickupLocationId, String dropoffLocationId,
			double amount) {
		List<TaxiTrip> trips = new ArrayList<TaxiTrip>();
		// yellow trips
		for (int i = 1; i <= 10; i++) {
			trips.add(createTaxiTrip(pickupLocationId, dropoffLocationId, TaxiType.YELLOW, amount));
		}
		// green trips
		for (int i = 1; i <= 10; i++) {
			trips.add(createTaxiTrip(pickupLocationId, dropoffLocationId, TaxiType.GREEN, amount));
		}

		// green trips
		for (int i = 1; i <= 10; i++) {
			trips.add(createTaxiTrip(pickupLocationId, dropoffLocationId, TaxiType.RHV, amount));
		}
		return trips;
	}

	public static TaxiTrip createTaxiTrip(String pickupLocationId, String dropoffLocationId, TaxiType taxiType,
			double amount) {
		LocalDateTime currentTime = LocalDateTime.now();
		TaxiTrip trip = new TaxiTrip();
		trip.setPickupLocationId(pickupLocationId);
		trip.setDropoffLocationId(dropoffLocationId);
		trip.setTaxiType(taxiType);
		trip.setTotalAmount(amount);
		trip.setPickupDateTime(toDate(currentTime.minusMinutes(30)));
		trip.setDropoffDateTime(toDate(currentTime));
		trip.setDuration(60);
		return trip;
	}

	public static Date toDate(LocalDateTime localDateTime) {
		return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
	}
}
