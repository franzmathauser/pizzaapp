package edu.hm.lip.pizza.internal.bean.database;

import java.util.List;

import javax.ejb.Local;

import edu.hm.lip.pizza.internal.object.entity.EntityCustomer;

/**
 * Interface der Bean für den Datenbankzugriff auf die Customer Entität.
 * 
 * @author Franz Mathauser, Stefan Wörner
 */
@Local
public interface ICustomerDAOLocal
{

	/**
	 * Estellt einen neuen Kunden in der Datenbank.
	 * 
	 * @param customer
	 *            Zu erstellender Kunde
	 * @return Erstellter Kunde
	 */
	EntityCustomer create( EntityCustomer customer );

	/**
	 * Liest alle Kunden aus der Datenbank aus.
	 * 
	 * @return Liste mit allen Kunden
	 */
	List<EntityCustomer> readAll();

	/**
	 * Liest einen Kunden anhand des Kundenidentifikators aus der Datenbank aus.
	 * 
	 * @param id
	 *            Kundenidentifikator
	 * @return Kunde
	 */
	EntityCustomer read( int id );

	/**
	 * Aktualisiert einen Kunden in der Datenbank.
	 * 
	 * @param customer
	 *            Zu aktualisierender Kunde
	 * @return Aktualiserter Kunde
	 */
	EntityCustomer update( EntityCustomer customer );

	/**
	 * Entfernt einen Kunden aus der Datenbank.
	 * 
	 * @param customer
	 *            Zu entfernender Kunde
	 */
	void delete( EntityCustomer customer );
}
