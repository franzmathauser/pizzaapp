package edu.hm.lip.pizza.driver.objects.resource.enumeration;

/**
 * Enumeration für den Status der Routenberechnung.
 * 
 * @author Franz Mathauser, Stefan Wörner
 */
public enum RouteState
{
	/**
	 * Optimale Route konnte berechnet werden.
	 */
	TSP_SUCCESS,

	/**
	 * Optimale Route konnte wurde nicht berechnet.
	 */
	TSP_ERROR;

}
