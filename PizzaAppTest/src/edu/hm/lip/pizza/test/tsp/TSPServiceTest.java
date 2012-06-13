package edu.hm.lip.pizza.test.tsp;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import edu.hm.lip.pizza.test.AbstractTest;
import edu.hm.lip.pizza.tsp.TspPermutation;
import edu.hm.lip.pizza.tsp.TspSolver;
import edu.hm.lip.pizza.tsp.common.PermutationManager;
import edu.hm.lip.pizza.tsp.common.RoundTripPermutation;
import edu.hm.lip.pizza.tsp.domain.Address;
import edu.hm.lip.pizza.tsp.domain.MatrixContainer;
import edu.hm.lip.pizza.tsp.domain.MatrixContainerAdapter;
import edu.hm.lip.pizza.tsp.domain.MatrixContainerAdapter.Measurements;
import edu.hm.lip.pizza.tsp.domain.Path;
import edu.hm.lip.pizza.tsp.domain.google.GoogleDistanceMatrix;
import edu.hm.lip.pizza.tsp.service.DistanceMatrixBroker;

import junit.framework.Assert;

/**
 * Dieses Beispiel zeigt wie der TSP-Solver eingesetzt werden kann.
 * 
 * @author Franz Mathauser, Stefan Wörner
 */
public class TSPServiceTest extends AbstractTest
{

	/**
	 * Testet die TSP-Service Library.
	 * 
	 * @throws Exception
	 *             falls beim lesen des JSON-String ein Fehler auftritt
	 */
	@Test
	public void testTSP() throws Exception
	{
		List<Address> destinationList = new ArrayList<Address>();
		Address address1 = new Address( "Oberbiberger Str. 10 München" );
		Address address2 = new Address( "Adams-Lehmannstr. 16, München" );
		Address address3 = new Address( "Lothstr.64, München" );
		destinationList.add( address1 );
		destinationList.add( address2 );
		destinationList.add( address3 );

		PermutationManager permManager = new RoundTripPermutation( destinationList );
		List<Path> tspPaths = permManager.getPaths();

		Assert.assertEquals( "Nummer of Possible Paths", 2, tspPaths.size() );
		Assert.assertEquals( "Nummer Edges of Path 1", 4, tspPaths.get( 0 ).size() );
		Assert.assertEquals( "Nummer Edges of Path 2", 4, tspPaths.get( 1 ).size() );

		GoogleDistanceMatrix gDistanceMatrix = new DistanceMatrixBroker( destinationList ).requestDistanceMatrix();

		MatrixContainer<Integer> matrix = new MatrixContainerAdapter( gDistanceMatrix, Measurements.DURATION ).getInstance();

		Assert.assertNotNull( "Google Distance Matrix", matrix );
		Assert.assertNotNull( "Distance Matrix 0x0", matrix.get( 0, 0 ) );
		Assert.assertNotNull( "Distance Matrix 1x0", matrix.get( 1, 0 ) );
		Assert.assertNotNull( "Distance Matrix 2x0", matrix.get( 2, 0 ) );
		Assert.assertNotNull( "Distance Matrix 0x1", matrix.get( 0, 1 ) );
		Assert.assertNotNull( "Distance Matrix 1x1", matrix.get( 1, 1 ) );
		Assert.assertNotNull( "Distance Matrix 2x1", matrix.get( 2, 1 ) );
		Assert.assertNotNull( "Distance Matrix 0x2", matrix.get( 0, 2 ) );
		Assert.assertNotNull( "Distance Matrix 1x2", matrix.get( 1, 2 ) );
		Assert.assertNotNull( "Distance Matrix 2x2", matrix.get( 2, 2 ) );

		TspSolver solver = new TspPermutation( matrix, tspPaths );

		Path tspPath = solver.solve();

		Assert.assertEquals( "Nummer Edges im TSP-Path", 4, tspPath.getEdges().size() );
		Assert.assertEquals( "Nummer Edges im TSP-Path", 4, tspPath.getEdges().size() );

		Assert.assertEquals( address1, tspPath.getEdges().get( 0 ).getAddress() );
		Assert.assertEquals( address3, tspPath.getEdges().get( 1 ).getAddress() );
		Assert.assertEquals( address2, tspPath.getEdges().get( 2 ).getAddress() );
		Assert.assertEquals( address1, tspPath.getEdges().get( 3 ).getAddress() );

	}

}
