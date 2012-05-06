package edu.hm.lip.pizza.internal.bean.database;

import java.util.List;

import javax.ejb.Local;

import edu.hm.lip.pizza.internal.object.entities.EntityGPSData;

/**
 * @author Franz Mathauser
 */
@Local
public interface IGPSDataDAOLocal
{

	EntityGPSData create( EntityGPSData entityGPSData );

	List<EntityGPSData> readAll();

	EntityGPSData read( int id );

	EntityGPSData update( EntityGPSData entityGPSData );

	void delete( EntityGPSData entityGPSData );
}
