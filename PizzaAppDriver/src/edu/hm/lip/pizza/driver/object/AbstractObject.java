package edu.hm.lip.pizza.driver.object;

import java.io.Serializable;

/**
 * Abstrakte Basisklasse fuer alle Objekte.
 * 
 * @author Franz Mathauser, Stefan Wörner
 */
public abstract class AbstractObject implements Serializable
{

	private static final long serialVersionUID = -9141423914815852540L;

	/**
	 * Iniziierungszahl für den HashCode Builder.
	 */
	protected static final int INITIAL_NON_ZERO_ODD_NUMBER = 17;

	/**
	 * Multiplikator für den HashCode Builder.
	 */
	protected static final int MULTIPLIER_NON_ZERO_ODD_NUMBER = 37;

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public abstract int hashCode();

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public abstract boolean equals( Object obj );

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public abstract String toString();

}
