package dev.tunks.taxitrips.repositories;

import java.util.Date;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import dev.tunks.taxitrips.model.TaxiTrip;
/**
 * 
 * Paging and sorting repository to query TaxiTrip data
 * 
 * @author ebrimatunkara
 **/
public interface TaxiTripRepository extends PagingAndSortingRepository<TaxiTrip, String> {
	
	@Query(value = "{ 'taxiType' : ?0 }")
	public Page<TaxiTrip> findAllBy(String taxiType, Pageable pageable);

	@Query(value = "{ 'pickupDateTime': {$gte: ?0}, 'dropoffDateTime': {$lte: ?1}}")
	public Page<TaxiTrip> findAllBy( Date pickupDate, Date dropoffDate, PageRequest page);
	
	@Query(value = "{ 'pickupLocationId': ?0, 'dropoffLocationId': ?1 }")
	public Page<TaxiTrip> findAllBy(String pickupLocationId, String dropoffLocationId, Pageable pageable);

}
