package edu.hm.lip.pizza.common.tsp.permutation;

import java.util.List;

import edu.hm.lip.pizza.domain.Edge;
import edu.hm.lip.pizza.domain.Path;

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
