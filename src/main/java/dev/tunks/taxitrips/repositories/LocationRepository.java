package dev.tunks.taxitrips.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import dev.tunks.taxitrips.model.Location;

/**
 * 
 * Paging and sorting repository to query Location data
 * 
 * @author ebrimatunkara
 **/
public interface LocationRepository extends PagingAndSortingRepository<Location,String> {
   
	@Query(value = "{'borough': {$regex : ?0, $options: 'i'}}")
	public Page<Location> findByBorough(String borough, Pageable pageable);

	@Query(value = "{'zone': {$regex : ?0, $options: 'i'}}")
	public Page<Location> findByZone(String borough, Pageable pageable);
	
    public Page<Location> findAllBy(TextCriteria criteria, Pageable pageable);
}
