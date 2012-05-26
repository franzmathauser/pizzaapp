package edu.hm.lip.pizza.service.manager;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import edu.hm.lip.pizza.api.object.ApiConstants;
import edu.hm.lip.pizza.api.object.enums.RouteState;
import edu.hm.lip.pizza.api.object.resources.DriverRoute;
import edu.hm.lip.pizza.api.object.resources.Order;
import edu.hm.lip.pizza.common.tsp.PermutationManager;
import edu.hm.lip.pizza.common.tsp.RoundTripPermutation;
import edu.hm.lip.pizza.domain.Address;
import edu.hm.lip.pizza.domain.Edge;
import edu.hm.lip.pizza.domain.MatrixContainer;
import edu.hm.lip.pizza.domain.MatrixContainerAdapter;
import edu.hm.lip.pizza.domain.MatrixContainerAdapter.Measurements;
import edu.hm.lip.pizza.domain.Path;
import edu.hm.lip.pizza.domain.google.GoogleDistanceMatrix;
import edu.hm.lip.pizza.internal.bean.database.IDriverDAOLocal;
import edu.hm.lip.pizza.internal.converter.OrderConverter;
import edu.hm.lip.pizza.internal.object.entities.EntityOrder;
import edu.hm.lip.pizza.service.DistanceMatrixBroker;
import edu.hm.lip.pizza.tsp.TspPermutation;
import edu.hm.lip.pizza.tsp.TspSolver;

/**
 * Manager für die Fahrerroute.
 * 
 * @author Franz Mathauser, Stefan Wörner
 */
@Stateless
public class DriverRouteManager
{

	@EJB
	private IDriverDAOLocal driverDAOBean;

	/**
	 * Erzeugt eine optimale Route der zugeordneten Bestellungen zu einem Fahrer.
	 * 
	 * @param id
	 *            Fahreridentifikator
	 * @return FahrerRoute Ressource-Objekt
	 */
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

		GoogleDistanceMatrix gDistanceMatrix;
		try
		{
			gDistanceMatrix = new DistanceMatrixBroker( destinationList ).requestDistanceMatrix();

			MatrixContainer<Integer> matrix = new MatrixContainerAdapter( gDistanceMatrix, meassurement ).getInstance();

			TspSolver solver = new TspPermutation( matrix, tspPaths );
			Path path = solver.solve();
			System.out.println( "Path: " + path );
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
