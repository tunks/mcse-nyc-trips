package dev.tunks.taxitrips.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import java.util.Collections;
import java.util.List;
import javax.annotation.Resource;
import dev.tunks.taxitrips.model.TaxiTrip;
import dev.tunks.taxitrips.model.TripMetricsEntity;
import dev.tunks.taxitrips.queries.MetricsQuery;
import dev.tunks.taxitrips.queries.QueryFactory;
import dev.tunks.taxitrips.queries.QueryFactory.MetricsName;
import dev.tunks.taxitrips.queries.QueryParams;
import dev.tunks.taxitrips.repositories.TaxiTripRepository;

/***
 * Query service concrete implementation to find/query TaxiTrip data
 * 
 * @author ebrimatunkara
 */
@Service
public class TaxiTripService implements QueryService<TaxiTrip> {
	private static final Logger logger = LoggerFactory.getLogger(TaxiTripService.class);

	@Resource
	private TaxiTripRepository tripRepository;

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public Page<TaxiTrip> findAll(QueryParams queryParams, Pageable pageable) {
		Query query = queryParams.toQuery();
        query = pageable.isUnpaged()?query.with(PageRequest.of(0, 20)): query.with(pageable);
		List<TaxiTrip> result = mongoTemplate.find(query, TaxiTrip.class);
		return new PageImpl<TaxiTrip>(result, pageable, count());
	}

	@Override
	public Page<TaxiTrip> findAll(Pageable pageable) {
		return tripRepository.findAll(pageable);
	}
	
	public List<TripMetricsEntity> getTripMetrics(MetricsName name, QueryParams queryParams) 
	{
		try {
			QueryFactory queryFactory = new QueryFactory();
			MetricsQuery<Aggregation> query = queryFactory.createMetricsQuery(name);
			return mongoTemplate.aggregate(query.query(queryParams,Sort.unsorted()),TaxiTrip.class,TripMetricsEntity.class)
					            .getMappedResults();
		}
		catch(Exception ex) {
			logger.error(ex.getMessage());
		}
		return Collections.emptyList();
	}
	
	private long count() {
		return tripRepository.count();
	}

}
