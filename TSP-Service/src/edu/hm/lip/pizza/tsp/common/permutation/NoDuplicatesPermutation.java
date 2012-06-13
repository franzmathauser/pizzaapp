package edu.hm.lip.pizza.tsp.common.permutation;

import java.util.ArrayList;
import java.util.List;

import edu.hm.lip.pizza.tsp.domain.Edge;
import edu.hm.lip.pizza.tsp.domain.Path;

/**
 * Erzeugt Permutationen ausgehend von Ecken ohne Duplikate zu erzeugen.
 * 
 * @author Franz Mathauser, Stefan Wörner
 */
public class NoDuplicatesPermutation implements Permutation
{

	private List<Path> permutations;

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.lip.pizza.tsp.common.permutation.Permutation#permute(java.util.List)
	 */
	@Override
	public List<Path> permute( List<Edge> edges )
	{
		permutations = new ArrayList<Path>();

		generatePermutation( new ArrayList<Edge>(), edges );
		return permutations;
	}

	/**
	 * Rekursive Erzeugung möglicher Permuationen aus der Eckenliste.
	 * 
	 * @param beginning
	 *            Leere Liste
	 * @param ending
	 *            Eckenliste
	 */
	public void generatePermutation( List<Edge> beginning, List<Edge> ending )
	{
		if (ending.size() <= 1)
		{
			ArrayList<Edge> list = new ArrayList<Edge>( beginning );
			list.addAll( ending );
			permutations.add( new Path( list ) );

		}
		else
		{
			for (int i = 0; i < ending.size(); i++)
			{
				try
				{

					List<Edge> newList = new ArrayList<Edge>( ending.subList( 0, i ) );
					newList.addAll( ending.subList( i + 1, ending.size() ) );

					List<Edge> newBeginning = new ArrayList<Edge>( beginning );
					newBeginning.add( ending.get( i ) );

					generatePermutation( newBeginning, newList );
				}
				catch (StringIndexOutOfBoundsException exception)
				{
					exception.printStackTrace();
				}
			}
		}
	}

}
