package edu.hm.lip.pizza.internal.bean.database;

import java.util.List;

import javax.ejb.Local;

import edu.hm.lip.pizza.internal.object.entity.EntityDriver;
import edu.hm.lip.pizza.internal.object.entity.EntityOrder;

/**
 * Interface der Bean für den Datenbankzugriff auf die Driver Entität.
 * 
 * @author Franz Mathauser, Stefan Wörner
 */
@Local
public interface IDriverDAOLocal
{

	/**
	 * Estellt einen neuen Fahrer in der Datenbank.
	 * 
	 * @param driver
	 *            Zu erstellender Fahrer
	 * @return Erstellter Fahrer
	 */
	EntityDriver create( EntityDriver driver );

	/**
	 * Liest alle Fahrer aus der Datenbank aus.
	 * 
	 * @return Liste mit allen Fahrern
	 */
	List<EntityDriver> readAll();

	/**
	 * Liest einen Fahrer anhand des Fahreridentifikators aus der Datenbank aus.
	 * 
	 * @param id
	 *            Fahreridentifikator
	 * @return Fahrer
	 */
	EntityDriver read( int id );

	/**
	 * Aktualisiert einen Fahrer in der Datenbank.
	 * 
	 * @param driver
	 *            Zu aktualisierender Fahrer
	 * @return Aktualiserter Fahrer
	 */
	EntityDriver update( EntityDriver driver );

	/**
	 * Entfernt einen Fahrer aus der Datenbank.
	 * 
	 * @param driver
	 *            Zu entfernender Fahrer
	 */
	void delete( EntityDriver driver );

	/**
	 * @param id
	 * @return
	 */
	List<EntityOrder> getUndeliverdOrders( int id );
}
