package edu.hm.lip.pizza.tsp.domain;

import java.util.HashMap;
import java.util.Map;

/**
 * MatrixContainer<T> ist ein generischer Container welcher es ermöglicht Objekte via X-, und Y-Koordinate zu speichern.
 * 
 * @author Franz Mathauser
 * @param <T>
 *            Datenelement Typ
 */
public class MatrixContainer<T>
{

	/**
	 * Matrix Container
	 */
	private Map<Integer, Map<Integer, T>> matrix;

	/**
	 * 
	 */
	public MatrixContainer()
	{
		matrix = new HashMap<Integer, Map<Integer, T>>();
	}

	/**
	 * @param x
	 * @param y
	 * @return
	 */
	public T get( Integer x, Integer y )
	{
		return matrix.get( x ).get( y );
	}

	/**
	 * @param x
	 * @param y
	 * @param value
	 */
	public void set( Integer x, Integer y, T value )
	{
		Map<Integer, T> column = matrix.get( x );

		if (column == null)
		{
			column = new HashMap<Integer, T>();
			matrix.put( x, column );
		}

		column.put( y, value );
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return "MatrixContainer [matrix=" + matrix + "]";
	}

}
