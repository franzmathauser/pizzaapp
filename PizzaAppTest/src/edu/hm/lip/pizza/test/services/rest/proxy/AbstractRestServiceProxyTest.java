package edu.hm.lip.pizza.test.services.rest.proxy;

import org.jboss.resteasy.client.ProxyFactory;

import edu.hm.lip.pizza.api.communication.request.ICustomerService;
import edu.hm.lip.pizza.api.communication.request.IDriverService;
import edu.hm.lip.pizza.api.communication.request.IGPSDataService;
import edu.hm.lip.pizza.api.communication.request.IOrderService;
import edu.hm.lip.pizza.api.communication.request.IProductService;
import edu.hm.lip.pizza.api.communication.request.IStatisticService;
import edu.hm.lip.pizza.test.services.rest.AbstractRestServiceTest;

/**
 * Abstrakte Basisklasse für alle REST Service Proxy Tests.
 * 
 * @author Stefan Wörner
 */
public abstract class AbstractRestServiceProxyTest extends AbstractRestServiceTest
{

	/**
	 * Liefert das Proxy-Objekt für den Driver Service.
	 * 
	 * @return DriverService Proxy
	 */
	protected IDriverService getDriverProxy()
	{
		return ProxyFactory.create( IDriverService.class, getRestUrl() );
	}

	/**
	 * Liefert das Proxy-Objekt für den Customer Service.
	 * 
	 * @return CustomerService Proxy
	 */
	protected ICustomerService getCustomerProxy()
	{
		return ProxyFactory.create( ICustomerService.class, getRestUrl() );
	}

	/**
	 * Liefert das Proxy-Objekt für den Product Service.
	 * 
	 * @return ProductService Proxy
	 */
	protected IProductService getProductProxy()
	{
		return ProxyFactory.create( IProductService.class, getRestUrl() );
	}

	/**
	 * Liefert das Proxy-Objekt für den Order Service.
	 * 
	 * @return OrderService Proxy
	 */
	protected IOrderService getOrderProxy()
	{
		return ProxyFactory.create( IOrderService.class, getRestUrl() );
	}

	/**
	 * Liefert das Proxy-Objekt für den GPSData Service.
	 * 
	 * @return GPSDataService Proxy
	 */
	protected IGPSDataService getGPSDataProxy()
	{
		return ProxyFactory.create( IGPSDataService.class, getRestUrl() );
	}

	/**
	 * Liefert das Proxy-Objekt für den Statistic Service.
	 * 
	 * @return StatisticService Proxy
	 */
	protected IStatisticService getStatisticProxy()
	{
		return ProxyFactory.create( IStatisticService.class, getRestUrl() );
	}

}
