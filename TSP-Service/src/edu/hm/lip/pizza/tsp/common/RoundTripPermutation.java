package edu.hm.lip.pizza.tsp.common;

import java.util.ArrayList;
import java.util.List;

import edu.hm.lip.pizza.tsp.common.permutation.AddOriginAsReturnpoint;
import edu.hm.lip.pizza.tsp.common.permutation.NoDuplicatesPermutation;
import edu.hm.lip.pizza.tsp.common.permutation.Permutation;
import edu.hm.lip.pizza.tsp.common.permutation.RoundTripPermutations;
import edu.hm.lip.pizza.tsp.domain.Address;
import edu.hm.lip.pizza.tsp.domain.Edge;
import edu.hm.lip.pizza.tsp.domain.Path;

/**
 * Die RoundTripPermutation erstellt aus einer Liste von Adressen Permuationen durch zwei Regeln. Regel 1. Die erste
 * Adresse wird als Endpunkt hinzugefügt Regel 2. Es werden alle Permutationen herausgefiltert die nicht Startpunkt und
 * Endpunkt als gleiche Adresse haben.
 * 
 * @author Franz Mathauser
 */
public class RoundTripPermutation implements PermutationManager
{

	private List<Edge> instanceList;

	/**
	 * @param addressList
	 */
	public RoundTripPermutation( List<Address> addressList )
	{
		instanceList = new ArrayList<Edge>();

		int i = 0;
		for (Address address : addressList)
		{

			instanceList.add( new Edge( i, address ) );
			i++;
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.lip.pizza.tsp.common.PermutationManager#getPaths()
	 */
	@Override
	public List<Path> getPaths()
	{
		Permutation p = new NoDuplicatesPermutation();
		p = new AddOriginAsReturnpoint( p );
		p = new RoundTripPermutations( p );

		List<Path> permutedEdges = p.permute( instanceList );
		int j = 0;
		for (Path edge : permutedEdges)
		{
			System.out.println( j++ + ": " + edge );
		}

		return permutedEdges;
	}

}
