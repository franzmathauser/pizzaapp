package edu.hm.lip.pizza.tsp.common;

import java.util.List;

import edu.hm.lip.pizza.tsp.domain.Path;

/**
 * Repräsentiert eine mögliche Konfiguration von Pfaden.
 * 
 * @author Franz Mathauser, Stefan Wörner
 */
public interface PermutationManager
{

	/**
	 * Liefert alle möglichen Pfadde zurück.
	 * 
	 * @return Pfade
	 */
	List<Path> getPaths();

}
