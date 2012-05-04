package edu.hm.lip.pizza.tsp;

import edu.hm.lip.pizza.domain.Path;

/**
 * 
 * Das Problem des Handlungsreisenden (auch Rundreiseproblem, engl. Traveling Salesman Problem oder Traveling
 * Salesperson Problem (TSP)) ist ein kombinatorisches Optimierungsproblem des Operations Research und der theoretischen
 * Informatik. Die Aufgabe besteht darin, eine Reihenfolge für den Besuch mehrerer Orte so zu wählen, dass die gesamte
 * Reisestrecke des Handlungsreisenden nach der Rückkehr zum Ausgangsort möglichst kurz ist.
 * 
 * @author Franz Mathauser
 */
public interface TspSolver
{

	/**
	 * Solves the TSP Problem.
	 * 
	 * @return shortests Path
	 */
	public Path solve();

}