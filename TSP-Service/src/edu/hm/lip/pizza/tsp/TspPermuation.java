package edu.hm.lip.pizza.tsp;

import java.util.List;

import edu.hm.lip.pizza.domain.MatrixContainer;
import edu.hm.lip.pizza.domain.Path;

/**
 * Traveling Salesman Problem Solver
 * 
 * @author <a href="mailto:mrohrmoser@gmx-gmbh.de">Marcus Rohrmoser </a>
 * @version $Id: codetemplates.xml,v 1.1 2005/09/21 10:02:15 rohrmoser Exp
 *          www-data $
 */
public class TspPermuation implements TspSolver {

    private final MatrixContainer<Integer> distanceMatrix;

    private final List<Path> pathList;

    public TspPermuation(MatrixContainer<Integer> distanceMatrix,
            List<Path> pathList) {
        this.distanceMatrix = distanceMatrix;
        this.pathList = pathList;
    }

    /*
     * (non-Javadoc)
     * @see edu.hm.lip.pizza.tsp.TspSolver#solve()
     */
    @Override
    public Path solve() {

        Path minPath = null;
        Long minPathNumeric = new Long(0);
        long pathNumeric = 0;

        for (Path path : pathList) {
            pathNumeric = 0;
            for (int i = 0; i < path.size() - 1; i++) {
                Integer x = path.getEdge(i).getId();
                Integer y = path.getEdge(i + 1).getId();
                pathNumeric += distanceMatrix.get(x, y);

            }
            // System.out.println(pathNumeric);
            if (minPathNumeric == 0 || pathNumeric < minPathNumeric) {
                minPathNumeric = pathNumeric;
                minPath = path;
                minPath.setCost(minPathNumeric);
            }

        }
        return minPath;

    }

}
