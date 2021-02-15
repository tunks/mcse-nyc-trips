package dev.tunks.taxitrips.service;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Term;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.stereotype.Service;
import dev.tunks.taxitrips.model.Location;
import dev.tunks.taxitrips.queries.QueryParams;
import dev.tunks.taxitrips.repositories.LocationRepository;

/***
 * Query service concrete implementation to find/query Location data
 * 
 * @author ebrimatunkara
 */
@Service
public class LocationService implements QueryService<Location> {
	private static final Logger logger = LoggerFactory.getLogger(LocationService.class);

	@Resource
	private LocationRepository locationRepository;

	@Override
	public Page<Location> findAll(Pageable pageable) {
		return locationRepository.findAll(pageable);
	}

	@Override
	public Page<Location> findAll(QueryParams queryParams, Pageable pageable) {
	    try {
		   return findByQueryParams(queryParams, pageable);
		}
		catch(Exception ex){
			logger.error(ex.getMessage());
		}
		return findAll(pageable);
	}

	private Page<Location> findByQueryParams(QueryParams queryParams, Pageable pageable) {
		if (queryParams.getSearchTerm().isPresent()) {
			return findAllBySearchTerm(queryParams.getSearchTerm().get(), pageable);
		}

		if (queryParams.getBorough().isPresent()) {
			return locationRepository.findByBorough(queryParams.getBorough().get(), pageable);
		}

		if (queryParams.getZone().isPresent()) {
			return locationRepository.findByZone(queryParams.getZone().get(), pageable);
		}

		return locationRepository.findAll(pageable);
	}

	private Page<Location> findAllBySearchTerm(String searchTerm, Pageable pageable) {
		try {
			TextCriteria criteria = new TextCriteria().caseSensitive(false).diacriticSensitive(false)
					              .matching(new Term(searchTerm));
			return locationRepository.findAllBy(criteria, pageable);
		}
		catch(Exception ex) {
			logger.error(ex.getMessage());
		}
		return findAll(pageable);
	}

}
