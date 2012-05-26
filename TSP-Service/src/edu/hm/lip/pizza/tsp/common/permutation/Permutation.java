package edu.hm.lip.pizza.tsp.common.permutation;

import java.util.List;

import edu.hm.lip.pizza.tsp.domain.Edge;
import edu.hm.lip.pizza.tsp.domain.Path;

/**
 * @author Franz Mathauser
 */
public interface Permutation
{

	/**
	 * @param edges
	 * @return
	 */
	List<Path> permute( List<Edge> edges );

}
