package edu.hm.lip.pizza.common.tsp.permutation;

import java.util.List;

import edu.hm.lip.pizza.domain.Edge;
import edu.hm.lip.pizza.domain.Path;

/**
 * Dekorator-Pattern Konstrukt
 * 
 * @author Franz Mathauser
 */
public abstract class PermutationDecorator implements Permutation
{

	protected final Permutation decoratedPermutation;

	public PermutationDecorator( Permutation decoratedPermutation )
	{
		this.decoratedPermutation = decoratedPermutation;
	}

	@Override
	public List<Path> permute( List<Edge> edges )
	{
		return decoratedPermutation.permute( edges );
	}

}
