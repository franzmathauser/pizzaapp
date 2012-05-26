package edu.hm.lip.pizza.tsp.domain;

import edu.hm.lip.pizza.tsp.domain.google.GoogleDistanceElement;
import edu.hm.lip.pizza.tsp.domain.google.GoogleDistanceMatrix;
import edu.hm.lip.pizza.tsp.domain.google.GoogleDistanceRow;

/**
 * MatrixContainerAdapter bietet die Möglichkeit eine GoogleDistanceMatrix in ein generischeres MatrixContainer-Format
 * umzuwandeln.
 * 
 * @author Franz Mathauser
 */
public class MatrixContainerAdapter
{

	/**
	 * Messwerttypen.
	 * 
	 * @author Franz Mathauser
	 */
	public enum Measurements
	{
		/**
		 * Distanz.
		 */
		DISTANCE,

		/**
		 * Dauer.
		 */
		DURATION;
	}

	private final MatrixContainer<Integer> convertedMatrix;

	/**
	 * Creates a MatrixContainerAdapter.
	 * 
	 * @param distanceMatrix
	 *            GoogleDistanceMatrix Information
	 * @param measurement
	 *            Messwerttyp
	 */
	public MatrixContainerAdapter( GoogleDistanceMatrix distanceMatrix, Measurements measurement )
	{
		convertedMatrix = convertGoogleDistanceMatrix( distanceMatrix, measurement );
	}

	/**
	 * Convertierung der GoogleDistanceMatrix Representation in ein generisches Format.
	 * 
	 * @param distanceMatrix
	 *            GoogleDistanceMatrix
	 * @param measurement
	 *            Messwerttyp
	 * @return gernerische DistanceMatrix
	 */
	private MatrixContainer<Integer> convertGoogleDistanceMatrix( GoogleDistanceMatrix distanceMatrix, Measurements measurement )
	{

		MatrixContainer<Integer> returnMatrix = new MatrixContainer<Integer>();

		int x = 0;
		for (GoogleDistanceRow row : distanceMatrix.getDistanceRows())
		{
			int y = 0;
			for (GoogleDistanceElement element : row.getElements())
			{

				Integer value = 0;
				if (measurement == Measurements.DISTANCE)
				{
					value = Integer.parseInt( element.getDistance().getValue() );
				}
				else if (measurement == Measurements.DURATION)
				{
					value = Integer.parseInt( element.getDuration().getValue() );
				}

				returnMatrix.set( x, y, value );
				y++;
			}
			x++;
		}

		return returnMatrix;
	}

	/**
	 * @return
	 */
	public MatrixContainer<Integer> getInstance()
	{
		return convertedMatrix;
	}

}
