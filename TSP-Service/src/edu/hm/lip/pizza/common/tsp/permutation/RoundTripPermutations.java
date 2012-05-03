package edu.hm.lip.pizza.common.tsp.permutation;

import java.util.ArrayList;
import java.util.List;

import edu.hm.lip.pizza.domain.Edge;
import edu.hm.lip.pizza.domain.Path;

/**
 * Fügt die Erste Adresse an das Ende der Liste hinzu
 * 
 * @author Franz Mathauser
 */
public class RoundTripPermutations extends PermutationDecorator
{

	public RoundTripPermutations( Permutation decoratedPermutation )
	{
		super( decoratedPermutation );
	}

	@Override
	public List<Path> permute( List<Edge> edges )
	{
		List<Path> permutationPaths = super.permute( edges );
		List<Path> returnPermutations = new ArrayList<Path>();

		for (Path path : permutationPaths)
		{
			if (path.getEdge( 0 ).equals( edges.get( 0 ) )
					&& path.getEdge( path.size() - 1 ).equals( edges.get( path.size() - 1 ) ))
			{
				returnPermutations.add( path );
			}
		}
		return returnPermutations;
	}
}
