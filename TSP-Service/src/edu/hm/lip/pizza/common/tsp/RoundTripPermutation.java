package edu.hm.lip.pizza.common.tsp;

import java.util.ArrayList;
import java.util.List;

import edu.hm.lip.pizza.common.tsp.permutation.AddOriginAsReturnpoint;
import edu.hm.lip.pizza.common.tsp.permutation.NoDuplicatesPermutation;
import edu.hm.lip.pizza.common.tsp.permutation.Permutation;
import edu.hm.lip.pizza.common.tsp.permutation.RoundTripPermutations;
import edu.hm.lip.pizza.domain.Address;
import edu.hm.lip.pizza.domain.Edge;
import edu.hm.lip.pizza.domain.Path;

public class RoundTripPermutation implements PermutationManager {

    List<Edge> instanceList;

    public RoundTripPermutation(List<Address> addressList) {
        instanceList = new ArrayList<Edge>();

        int i = 0;
        for (Address address : addressList) {

            instanceList.add(new Edge(i, address));
            i++;
        }

    }

    /*
     * (non-Javadoc)
     * @see edu.hm.lip.pizza.common.tsp.PermutationManager#getPaths()
     */
    @Override
    public List<Path> getPaths() {

        Permutation p = new NoDuplicatesPermutation();
        p = new AddOriginAsReturnpoint(p);
        p = new RoundTripPermutations(p);

        List<Path> permutedEdges = p.permute(instanceList);
        int j = 0;
        for (Path edge : permutedEdges) {
            System.out.println(j++ + ": " + edge);
        }

        return permutedEdges;

    }

}
