package edu.hm.lip.pizza.tsp.common.permutation;

import java.util.ArrayList;
import java.util.List;

import edu.hm.lip.pizza.tsp.domain.Edge;
import edu.hm.lip.pizza.tsp.domain.Path;

/**
 * Filtert alle Permutationen, die nicht das gleiche Objekt am Anfang und Ende des Pfades haben.
 * 
 * @author Franz Mathauser, Stefan Wörner
 */
public class RoundTripPermutations extends AbstractPermutationDecorator
{

	/**
	 * Konstruktor.
	 * 
	 * @param decoratedPermutation
	 *            Dekorierte Permuation
	 */
	public RoundTripPermutations( Permutation decoratedPermutation )
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
			if (path.getEdge( 0 ).equals( edges.get( 0 ) ) && path.getEdge( 0 ).equals( path.getEdge( path.size() - 1 ) ))
			{
				returnPermutations.add( path );
			}
		}
		return returnPermutations;
	}
}
