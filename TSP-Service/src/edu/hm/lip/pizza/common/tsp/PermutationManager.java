package edu.hm.lip.pizza.common.tsp;

import java.util.List;

import edu.hm.lip.pizza.domain.Path;

/**
 * Repräsentiert eine mögliche Konfiguration von Pfaden.
 * 
 * @author Franz Mathauser
 */
public interface PermutationManager
{

	public List<Path> getPaths();

}