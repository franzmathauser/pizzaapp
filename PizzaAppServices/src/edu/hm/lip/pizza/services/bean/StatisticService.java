package edu.hm.lip.pizza.services.bean;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import edu.hm.lip.pizza.api.communication.request.IStatisticServiceLocal;
import edu.hm.lip.pizza.api.object.resources.heatmap.GPSData;
import edu.hm.lip.pizza.api.object.resources.heatmap.Heatmap;
import edu.hm.lip.pizza.internal.bean.AbstractBean;
import edu.hm.lip.pizza.internal.bean.database.ICustomerDAOLocal;
import edu.hm.lip.pizza.internal.object.entities.EntityCustomer;

/**
 * REST-Service für die Statistik-Daten. Verfügbare Aktionen: GET
 * 
 * @author Franz Mathauser
 */
@Stateless
public class StatisticService extends AbstractBean implements IStatisticServiceLocal
{

	@EJB
	private ICustomerDAOLocal customerDAOBean;

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.lip.pizza.api.communication.request.IStatisticServiceLocal#findAllCustomerLocations()
	 */
	@Override
	public Heatmap findAllHeatmapData()
	{
		Heatmap heatmap = new Heatmap();

		List<EntityCustomer> eCustomerList = customerDAOBean.readAll();
		for (EntityCustomer eCustomer : eCustomerList)
		{
			heatmap.add( new GPSData( eCustomer.getLat(), eCustomer.getLon() ) );
		}
		return heatmap;
	}

}
