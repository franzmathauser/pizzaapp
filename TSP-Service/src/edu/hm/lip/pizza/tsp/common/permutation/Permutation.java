package edu.hm.lip.pizza.tsp.common.permutation;

import java.util.List;

import edu.hm.lip.pizza.tsp.domain.Edge;
import edu.hm.lip.pizza.tsp.domain.Path;

/**
 * Beschreibt die Schnittstellen für Permuationsklassen.
 * 
 * @author Franz Mathauser
 */
public interface Permutation
{

	/**
	 * Erzeugt eine Permuation (Anordnungen) der eingegebenen Liste aus Ecken.
	 * 
	 * @param edges
	 *            Ecken-Liste
	 * @return Liste möglicher Pfade
	 */
	List<Path> permute( List<Edge> edges );

}
