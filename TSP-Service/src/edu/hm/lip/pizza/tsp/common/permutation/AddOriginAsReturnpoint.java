package edu.hm.lip.pizza.tsp.common.permutation;

import java.util.ArrayList;
import java.util.List;

import edu.hm.lip.pizza.tsp.domain.Edge;
import edu.hm.lip.pizza.tsp.domain.Path;

/**
 * Dekorator fügt die erste Ecke aus der Liste an das Ende der Liste an.
 * 
 * @author Franz Mathauser, Stefan Wörner
 */
public class AddOriginAsReturnpoint extends AbstractPermutationDecorator
{

	/**
	 * Erzeugt einen PermutationDecorator. siehe Decorator-Pattern.
	 * 
	 * @param decoratedPermutation
	 *            Dekorierte Permuation
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

		List<Path> permutationPaths = super.permute( edges );
		List<Path> returnPermutations = new ArrayList<Path>();

		for (Path path : permutationPaths)
		{
			path.addEdge( path.getEdge( 0 ) );
			returnPermutations.add( path );
		}

		return returnPermutations;
	}

}
