package edu.hm.lip.pizza.api.object.enumeration;

/**
 * Enumeration für die unterschiedlichen Stufen bei der Herstellung und Auslieferung von Bestellungen.
 * 
 * @author Stefan Wörner
 */
public enum Stage
{
	/**
	 * Bestellung wurde angenommen.
	 */
	ORDERED,

	/**
	 * Bestellung ist in Bearbeitung: Zugehörige Produkte werden hergestellt.
	 */
	IN_PREPARATION,

	/**
	 * Bestellung befindet sich im Ofen und wird gebacken.
	 */
	IN_STOVE,

	/**
	 * Bestellung wird aktuell ausgeliefert.
	 */
	IN_DELIVERY,

	/**
	 * Bestellung wurde ausgefahren.
	 */
	DELIVERED;

}
