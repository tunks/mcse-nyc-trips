package dev.tunks.taxitrips.queries;

import org.springframework.data.domain.Sort;

/**
 * MetricQuery interface
 * 
 *  @author ebrimatunkara
 */
public interface MetricsQuery<R> {
       public R query(QueryParams queryParams, Sort sort);
}
