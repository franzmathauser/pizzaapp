package edu.hm.lip.pizza.internal.object;

/**
 * Abstrakte Basisklasse für alle Entity Objekte.
 * 
 * @author Stefan Wörner
 */
public abstract class AbstractPizzaAppEntityObject extends AbstractPizzaAppInternalObject
{

	private static final long serialVersionUID = -2814230137735050619L;

	/**
	 * Prüft ob es sich um ein gültiges Objekt handelt.
	 */
	public abstract void validate();
}
