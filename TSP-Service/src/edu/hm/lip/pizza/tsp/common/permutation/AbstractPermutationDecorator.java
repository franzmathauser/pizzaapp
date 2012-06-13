package edu.hm.lip.pizza.tsp.common.permutation;

import java.util.List;

import edu.hm.lip.pizza.tsp.domain.Edge;
import edu.hm.lip.pizza.tsp.domain.Path;

/**
 * Dekorator-Pattern Konstrukt.
 * 
 * @author Franz Mathauser, Stefan Wörner
 */
public abstract class AbstractPermutationDecorator implements Permutation
{

	/**
	 * Dekorierte Permutation.
	 */
	protected final Permutation m_decoratedPermutation;

	/**
	 * Erzeugt eine Kapselung einer Permutation. Decorator-Pattern
	 * 
	 * @param decoratedPermutation
	 *            Permutation-Objekt
	 */
	public AbstractPermutationDecorator( Permutation decoratedPermutation )
	{
		this.m_decoratedPermutation = decoratedPermutation;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.lip.pizza.tsp.common.permutation.Permutation#permute(java.util.List)
	 */
	@Override
	public List<Path> permute( List<Edge> edges )
	{
		return m_decoratedPermutation.permute( edges );
	}

}
