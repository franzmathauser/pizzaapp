package edu.hm.lip.pizza.internal.bean.service.manager;

import javax.ejb.Local;

import edu.hm.lip.pizza.api.object.resource.DriverRoute;

/**
 * Interface für die Manager Bean der Fahrerroute.
 * 
 * @author Franz Mathauser, Stefan Wörner
 */
@Local
public interface IDriverRouteManagerLocal
{

	/**
	 * Erzeugt eine optimale Route der zugeordneten Bestellungen zu einem Fahrer.
	 * 
	 * @param id
	 *            Fahreridentifikator
	 * @return FahrerRoute Ressource-Objekt
	 */
	DriverRoute getRoute( int id );

}
