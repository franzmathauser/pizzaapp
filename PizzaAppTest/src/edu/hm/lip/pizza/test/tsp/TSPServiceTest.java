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

/**
 * Dieses Beispiel zeigt wie der TSP-Solver eingesetzt werden kann.
 * 
 * @author Franz Mathauser
 */
public class TSPServiceTest extends AbstractTest
{

	/**
	 * Testet die TSP-Service Library.
	 * 
	 * @throws Exception
	 *             falls beim lesen des JSONS ein Fehler auftritt
	 */
	@Test
	public void testTSP() throws Exception
	{
		List<Address> destinationList = new ArrayList<Address>();
		destinationList.add( new Address( "Oberbiberger Str. 10 München" ) );
		destinationList.add( new Address( "Adams-Lehmannstr. 16, München" ) );
		destinationList.add( new Address( "Lothstr.64, München" ) );
		destinationList.add( new Address( "Schatzelweg 12, Schliersee" ) );
		destinationList.add( new Address( "Pestalozzistr. 50, München" ) );
		destinationList.add( new Address( "Schloßhof 14, Seefeld" ) );
		destinationList.add( new Address( "Tölzerstr. 1, Grünwald" ) );

		PermutationManager permManager = new RoundTripPermutation( destinationList );
		List<Path> tspPaths = permManager.getPaths();

		GoogleDistanceMatrix gDistanceMatrix = new DistanceMatrixBroker( destinationList ).requestDistanceMatrix();
		MatrixContainer<Integer> matrix = new MatrixContainerAdapter( gDistanceMatrix, Measurements.DURATION ).getInstance();

		//System.out.println( matrix );

		TspSolver solver = new TspPermutation( matrix, tspPaths );
		System.out.println( "\n" + solver.solve() );
	}

}
