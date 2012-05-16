package edu.hm.lip.pizza.common.tsp.permutation;

import java.util.List;

import edu.hm.lip.pizza.domain.Edge;
import edu.hm.lip.pizza.domain.Path;

/**
 * Dekorator-Pattern Konstrukt.
 * 
 * @author Franz Mathauser
 */
public abstract class AbstractPermutationDecorator implements Permutation
{

	/**
	 *
	 */
	protected final Permutation decoratedPermutation;

	/**
	 * @param decoratedPermutation
	 */
	public AbstractPermutationDecorator( Permutation decoratedPermutation )
	{
		this.decoratedPermutation = decoratedPermutation;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.lip.pizza.common.tsp.permutation.Permutation#permute(java.util.List)
	 */
	@Override
	public List<Path> permute( List<Edge> edges )
	{
		return decoratedPermutation.permute( edges );
	}

}
