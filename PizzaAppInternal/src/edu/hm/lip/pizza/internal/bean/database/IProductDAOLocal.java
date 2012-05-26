package edu.hm.lip.pizza.internal.bean.database;

import java.util.List;

import javax.ejb.Local;

import edu.hm.lip.pizza.internal.object.entity.EntityProduct;

/**
 * Interface der Bean für den Datenbankzugriff auf die Product Entität.
 * 
 * @author Franz Mathauser, Stefan Wörner
 */
@Local
public interface IProductDAOLocal
{

	/**
	 * Estellt ein neues Produkt in der Datenbank.
	 * 
	 * @param product
	 *            Zu erstellendes Produkt
	 * @return Erstelltes Produkt
	 */
	EntityProduct create( EntityProduct product );

	/**
	 * Liest alle Produkte aus der Datenbank aus.
	 * 
	 * @return Liste mit allen Produkten
	 */
	List<EntityProduct> readAll();

	/**
	 * Liest ein Produkt anhand des Produktidentifikators aus der Datenbank aus.
	 * 
	 * @param id
	 *            Produktidentifikator
	 * @return Produkt
	 */
	EntityProduct read( int id );

	/**
	 * Aktualisiert ein Produkt in der Datenbank.
	 * 
	 * @param product
	 *            Zu aktualisierendes Produkt
	 * @return Aktualisertes Produkt
	 */
	EntityProduct update( EntityProduct product );

	/**
	 * Entfernt ein Produkt aus der Datenbank.
	 * 
	 * @param product
	 *            Zu entfernendes Produkt
	 */
	void delete( EntityProduct product );

}
