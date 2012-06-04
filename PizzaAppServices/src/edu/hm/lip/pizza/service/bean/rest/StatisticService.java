package edu.hm.lip.pizza.service.bean.rest;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;

import edu.hm.lip.pizza.api.communication.request.IStatisticService;
import edu.hm.lip.pizza.api.object.resource.heatmap.GPSData;
import edu.hm.lip.pizza.api.object.resource.heatmap.Heatmap;
import edu.hm.lip.pizza.internal.bean.AbstractBean;
import edu.hm.lip.pizza.internal.bean.database.ICustomerDAOLocal;
import edu.hm.lip.pizza.internal.interceptor.LoggingInterceptor;
import edu.hm.lip.pizza.internal.object.entity.EntityCustomer;

/**
 * REST-Service für die Statistik-Daten. Verfügbare Aktionen: GET
 * 
 * @author Franz Mathauser
 */
@Stateless
@Interceptors( LoggingInterceptor.class )
public class StatisticService extends AbstractBean implements IStatisticService
{

	@EJB
	private ICustomerDAOLocal customerDAOBean;

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.lip.pizza.api.communication.request.IStatisticService#findAllHeatmapData()
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
