package edu.hm.lip.pizza.api.object.enumeration;

/**
 * Enumeration für ActiveMQ-Message Typen.
 * 
 * @author Franz Mathauser, Stefan Wörner
 */
public enum MessageType
{
	/**
	 * GPS-Daten des Fahrers.
	 */
	DRIVER_LOCATION,
	
	/**
	 * Ankunftszeit des Fahrers.
	 */
	DRIVER_ARRIVAL,
	
	/**
	 * Neuer Besellungs-bon.
	 */
	ORDER,
	
	/**
	 * Heartbeat Nachricht, damit die Verbindung bestehen bleibt.
	 */
	HEARTBEAT;

}
