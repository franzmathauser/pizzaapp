package edu.hm.basic.object;

import java.io.Serializable;

/**
 * Abstrakte Basisklasse fuer alle Objekte.
 * 
 * @author Stefan Wörner
 */
public abstract class AbstractBasicObject implements Serializable
{

	private static final long serialVersionUID = -5208979301282804055L;

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
