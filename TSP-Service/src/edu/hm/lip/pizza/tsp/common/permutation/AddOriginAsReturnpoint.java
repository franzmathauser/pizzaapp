package edu.hm.lip.pizza.tsp.common.permutation;

import java.util.List;

import edu.hm.lip.pizza.tsp.domain.Edge;
import edu.hm.lip.pizza.tsp.domain.Path;

/**
 * Dekorator fügt die erste Ecke aus der Liste an das Ende der Liste an.
 * 
 * @author Franz Mathauser
 */
public class AddOriginAsReturnpoint extends AbstractPermutationDecorator
{

	/**
	 * @param decoratedPermutation
	 */
	public AddOriginAsReturnpoint( Permutation decoratedPermutation )
	{
		super( decoratedPermutation );
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.lip.pizza.tsp.common.permutation.AbstractPermutationDecorator#permute(java.util.List)
	 */
	@Override
	public List<Path> permute( List<Edge> edges )
	{
		edges.add( edges.get( 0 ) );
		return super.permute( edges );
	}

}
