import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.hm.lip.pizza.common.tsp.PermutationManager;
import edu.hm.lip.pizza.common.tsp.RoundTripPermutation;
import edu.hm.lip.pizza.domain.Address;
import edu.hm.lip.pizza.domain.MatrixContainer;
import edu.hm.lip.pizza.domain.MatrixContainerAdapter;
import edu.hm.lip.pizza.domain.MatrixContainerAdapter.Measurements;
import edu.hm.lip.pizza.domain.Path;
import edu.hm.lip.pizza.domain.google.GoogleDistanceMatrix;
import edu.hm.lip.pizza.service.DistanceMatrixBroker;
import edu.hm.lip.pizza.tsp.TspPermutation;
import edu.hm.lip.pizza.tsp.TspSolver;

/**
 * Dieses Beispiel zeigt wie der TSP-Solver eingesetzt werden kann.
 * 
 * @author Franz Mathauser
 */
final class Main
{

	private Main()
	{

	}

	/**
	 * Main methode zum starten der Java-Anwendung.
	 * 
	 * @param args
	 *            String argumente (werden nicht ausgewertet)
	 * @throws IOException
	 *             falls beim lesen des JSONS ein Fehler auftritt
	 */
	public static void main( String[] args ) throws IOException
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

		System.out.println( matrix );

		TspSolver solver = new TspPermutation( matrix, tspPaths );
		System.out.println( "\n" + solver.solve() );

	}
}
