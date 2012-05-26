package edu.hm.lip.pizza.tsp.common;

import java.util.List;

import edu.hm.lip.pizza.tsp.domain.Path;

/**
 * Repräsentiert eine mögliche Konfiguration von Pfaden.
 * 
 * @author Franz Mathauser
 */
public interface PermutationManager
{

	/**
	 * @return
	 */
	public List<Path> getPaths();

}
