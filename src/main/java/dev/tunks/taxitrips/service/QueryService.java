package dev.tunks.taxitrips.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import dev.tunks.taxitrips.queries.QueryParams;

/***
 *  Base query service
 *  
 *  @author ebrimatunkara
 */
public interface QueryService<T> 
{
	public Page<T> findAll(Pageable pageable);
	
	public Page<T> findAll(QueryParams queryParams, Pageable pageable);
}
