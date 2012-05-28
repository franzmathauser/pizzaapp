package edu.hm.lip.pizza.internal.bean.database;

import java.util.List;

import javax.ejb.Local;

import edu.hm.lip.pizza.internal.object.entity.EntityOrder;
import edu.hm.lip.pizza.internal.object.entity.EntityOrderStage;

/**
 * Interface der Bean für den Datenbankzugriff auf die Order Entität.
 * 
 * @author Franz Mathauser, Stefan Wörner
 */
@Local
public interface IOrderDAOLocal
{

	/**
	 * Estellt eine neue Bestellung in der Datenbank.
	 * 
	 * @param order
	 *            Zu erstellende Bestellung
	 * @return Erstelltes Bestellung
	 */
	EntityOrder create( EntityOrder order );

	/**
	 * Liest alle Bestellungen aus der Datenbank aus.
	 * 
	 * @return Liste mit allen Bestellungen
	 */
	List<EntityOrder> readAll();

	/**
	 * List eine Bestellung anhand des Bestellungsidentifikators aus der Datenbank aus.
	 * 
	 * @param id
	 *            Bestellungsidentifikator
	 * @return Bestellung
	 */
	EntityOrder read( int id );

	/**
	 * Aktualisiert eine Bestellung in der Datenbank.
	 * 
	 * @param order
	 *            Zu aktualisierende Bestellung
	 * @return Aktualiserte Bestellung
	 */
	EntityOrder update( EntityOrder order );

	/**
	 * Entfernt eine Bestellung aus der Datenbank.
	 * 
	 * @param order
	 *            Zu entfernende Bestellung
	 */
	void delete( EntityOrder order );

	/**
	 * Liefter die aktuelle Stage einer Bestellung.
	 * 
	 * @param id
	 *            Bestellungsidentifikator
	 * @return Bestellungs-Stage der Entity-Domäne.
	 */
	EntityOrderStage getCurrentStage( int id );

	/**
	 * Liefert eine Liste mit allen unausgelieferten Bestellungen.
	 * 
	 * @return Liste von Bestellungen.
	 */
	List<EntityOrder> getUndeliveredOrders();

}
