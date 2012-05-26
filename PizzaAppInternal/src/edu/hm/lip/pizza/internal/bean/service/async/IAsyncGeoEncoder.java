package edu.hm.lip.pizza.internal.bean.service.async;

import edu.hm.lip.pizza.internal.object.entity.EntityCustomer;

/**
 * @author Stefan Wörner
 */
public interface IAsyncGeoEncoder
{

	/**
	 * Fügt einem EntityCustomer die Geokoordinaten bzgl. seiner Adresse hinzu. Die Methode wird asynchron ausgeführt.
	 * 
	 * @param eCustomer
	 *            Datenbank-Objekt eines Kunden
	 */
	void doGeoEncoding( EntityCustomer eCustomer );

}
