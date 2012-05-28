package edu.hm.lip.pizza.test.services.rest;

/**
 * Interface für die Standard REST Funktionen.
 * 
 * @author Stefan Wörner
 */
public interface IRestServiceDefaultTestFunctions
{

	/**
	 * Testet die CREATE Funktion.
	 * 
	 * @throws Exception
	 *             Testfehler
	 */
	void testCreate() throws Exception;

	/**
	 * Testet die FIND ALL Funktion.
	 * 
	 * @throws Exception
	 *             Testfehler
	 */
	void testFindAll() throws Exception;

	/**
	 * Testet die FIND Funktion.
	 * 
	 * @throws Exception
	 *             Testfehler
	 */
	void testFind() throws Exception;

	/**
	 * Testet die UPDATE Funktion.
	 * 
	 * @throws Exception
	 *             Testfehler
	 */
	void testUpdate() throws Exception;

	/**
	 * Testet die REMOVE Funktion.
	 * 
	 * @throws Exception
	 *             Testfehler
	 */
	void testRemove() throws Exception;
}
