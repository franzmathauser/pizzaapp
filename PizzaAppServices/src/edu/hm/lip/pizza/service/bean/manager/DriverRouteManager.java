package edu.hm.lip.pizza.service.bean.manager;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import edu.hm.basic.logging.BasicLogger;
import edu.hm.lip.pizza.api.object.ApiConstants;
import edu.hm.lip.pizza.api.object.enumeration.RouteState;
import edu.hm.lip.pizza.api.object.resource.DriverRoute;
import edu.hm.lip.pizza.api.object.resource.Order;
import edu.hm.lip.pizza.internal.bean.database.IDriverDAOLocal;
import edu.hm.lip.pizza.internal.bean.service.manager.IDriverRouteManagerLocal;
import edu.hm.lip.pizza.internal.converter.OrderConverter;
import edu.hm.lip.pizza.internal.object.entity.EntityOrder;
import edu.hm.lip.pizza.tsp.TspPermutation;
import edu.hm.lip.pizza.tsp.TspSolver;
import edu.hm.lip.pizza.tsp.common.PermutationManager;
import edu.hm.lip.pizza.tsp.common.RoundTripPermutation;
import edu.hm.lip.pizza.tsp.domain.Address;
import edu.hm.lip.pizza.tsp.domain.Edge;
import edu.hm.lip.pizza.tsp.domain.MatrixContainer;
import edu.hm.lip.pizza.tsp.domain.MatrixContainerAdapter;
import edu.hm.lip.pizza.tsp.domain.MatrixContainerAdapter.Measurements;
import edu.hm.lip.pizza.tsp.domain.Path;
import edu.hm.lip.pizza.tsp.domain.google.GoogleDistanceMatrix;
import edu.hm.lip.pizza.tsp.service.DistanceMatrixBroker;

/**
 * Manager für die Fahrerroute.
 * 
 * @author Franz Mathauser, Stefan Wörner
 */
@Stateless
public class DriverRouteManager implements IDriverRouteManagerLocal
{

	@EJB
	private IDriverDAOLocal driverDAOBean;

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.lip.pizza.internal.bean.service.manager.IDriverRouteManagerLocal#getRoute(int)
	 */
	@Override
	public DriverRoute getRoute( int id )
	{
		DriverRoute driverRoute = new DriverRoute();
		driverRoute.setOriginLat( ApiConstants.PIZZA_STORE_ORIGIN_LAT );
		driverRoute.setOriginLon( ApiConstants.PIZZA_STORE_ORIGIN_LON );
		Measurements meassurement = Measurements.DURATION;

		List<Address> destinationList = new ArrayList<Address>();
		Address origin = new Address( driverRoute.getOriginLat() + "," + driverRoute.getOriginLon() );
		destinationList.add( origin ); // PizzaStore Location

		List<EntityOrder> eOrderList = driverDAOBean.getUndeliverdOrders( id );
		List<Order> routeOrderList = new ArrayList<Order>();

		for (EntityOrder eOrder : eOrderList)
		{
			Order order = OrderConverter.convertEntityToServiceOrder( eOrder );
			String addressLine = order.getCustomer().getLat() + "," + order.getCustomer().getLon();
			Address address = new Address( addressLine );
			address.setId( order.getId() );
			destinationList.add( address );
		}

		PermutationManager permManager = new RoundTripPermutation( destinationList );
		List<Path> tspPaths = permManager.getPaths();

		GoogleDistanceMatrix gDistanceMatrix = null;

		try
		{
			driverRoute.setStartTime( new Date().getTime() );

			gDistanceMatrix = new DistanceMatrixBroker( destinationList ).requestDistanceMatrix();

			MatrixContainer<Integer> matrix = new MatrixContainerAdapter( gDistanceMatrix, meassurement ).getInstance();

			TspSolver solver = new TspPermutation( matrix, tspPaths );
			Path path = solver.solve();
			BasicLogger.logInfo( this.getClass().getName(), "Path: " + path );

			for (Edge edge : path.getEdges())
			{
				Integer mappedOrderId = edge.getAddress().getId();
				for (EntityOrder eOrder : eOrderList)
				{
					if (eOrder.getId() == mappedOrderId)
					{
						routeOrderList.add( OrderConverter.convertEntityToServiceOrder( eOrder ) );
					}
				}
			}

			driverRoute.setOrders( routeOrderList );
			driverRoute.setMeassurement( meassurement.name() );
			driverRoute.setMeassurementValue( path.getCost() );
			driverRoute.setStatus( RouteState.TSP_SUCCESS );
		}
		catch (Exception e)
		{
			// optimale Route konnte nicht zugeordnet werden.
			driverRoute.setStatus( RouteState.TSP_ERROR );
			driverRoute.setOrders( OrderConverter.convertEntityToServiceOrderList( eOrderList ) );
			driverRoute.setMeassurement( null );
		}

		return driverRoute;
	}
}
