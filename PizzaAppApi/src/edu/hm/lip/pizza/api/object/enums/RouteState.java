package edu.hm.lip.pizza.api.object.enums;

/**
 * Enumeration für den Status der Routenberechnung.
 * 
 * @author Franz Mathauser, Stefan Wörner
 */
public enum RouteState
{
	/**
	 * optimale Route konnte berechnet werden.
	 */
	TSP_SUCCESS,
	/**
	 * optimale Route konnte wurde nicht berechnet.
	 */
	TSP_ERROR;
}
