package edu.hm.lip.pizza.driver.util.route;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.android.maps.GeoPoint;

import edu.hm.lip.pizza.driver.objects.resource.DriverRoute;
import edu.hm.lip.pizza.driver.overlay.RouteOverlay;
import edu.hm.lip.pizza.driver.overlay.RoutePointOverlay;

/**
 * Store für die Fahrerroute und alle Informationen die damit zusammenhängen.
 * 
 * @author Stefan Wörner
 */
public final class DriverRouteStore
{

	private static DriverRouteStore m_instance;

	private DriverRoute m_currentRoute;

	private Map<Integer, List<GeoPoint>> m_currentRoutePartsGeoPoints;

	private RoutePointOverlay m_pizzeriaPointOverlay;

	private RoutePointOverlay m_customerPointOverlay;

	private Integer m_currentlyVisibleRoutePart;

	private List<RouteOverlay> m_currentlyVisibleRouteOverlays;

	/**
	 * Konstruktor.
	 */
	private DriverRouteStore()
	{
		m_currentlyVisibleRoutePart = Integer.MIN_VALUE;
	}

	/**
	 * Gibt die aktuelle oder falls noch nicht vorhanden eine neue Instanz des DriverRouteStore zurück.
	 * 
	 * @return Instanz des DriverRouteStore
	 */
	public static DriverRouteStore getInstance()
	{
		if (m_instance == null)
		{
			m_instance = new DriverRouteStore();
		}
		return m_instance;
	}

	/**
	 * Zerstört die Singletoninstanz.
	 */
	public static void destroyInstance()
	{
		if (m_instance != null)
		{
			m_instance.m_currentlyVisibleRouteOverlays = null;
			m_instance.m_currentlyVisibleRoutePart = null;
			m_instance.m_currentRoute = null;
			m_instance.m_currentRoutePartsGeoPoints = null;
			m_instance.m_customerPointOverlay = null;
			m_instance.m_pizzeriaPointOverlay = null;
			m_instance = null;
		}
	}

	/**
	 * Liefert das Attribut currentRoute.
	 * 
	 * @return currentRoute
	 */
	public DriverRoute getCurrentRoute()
	{
		return m_currentRoute;
	}

	/**
	 * Setzt das Attribut currentRoute.
	 * 
	 * @param currentRoute
	 *            zu setzender Wert für das Attribut currentRoute
	 */
	public void setCurrentRoute( DriverRoute currentRoute )
	{
		m_currentRoute = currentRoute;
	}

	/**
	 * Liefert das Attribut currentRoutePartsGeoPoints.
	 * 
	 * @return currentRoutePartsGeoPoints
	 */
	public Map<Integer, List<GeoPoint>> getCurrentRoutePartsGeoPoints()
	{
		return m_currentRoutePartsGeoPoints;
	}

	/**
	 * Setzt das Attribut currentRoutePartsGeoPoints.
	 * 
	 * @param currentRoutePartsGeoPoints
	 *            zu setzender Wert für das Attribut currentRoutePartsGeoPoints
	 */
	public void setCurrentRoutePartsGeoPoints( Map<Integer, List<GeoPoint>> currentRoutePartsGeoPoints )
	{
		m_currentRoutePartsGeoPoints = currentRoutePartsGeoPoints;

		if (currentRoutePartsGeoPoints == null)
		{
			m_currentlyVisibleRoutePart = 0;
		}
	}

	/**
	 * Liefert das Attribut currentlyVisibleRoutePart.
	 * 
	 * @return currentlyVisibleRoutePart
	 */
	public Integer getCurrentlyVisibleRoutePart()
	{
		return m_currentlyVisibleRoutePart;
	}

	/**
	 * Setzt das Attribut currentlyVisibleRoutePart.
	 * 
	 * @param currentlyVisibleRoutePart
	 *            zu setzender Wert für das Attribut currentlyVisibleRoutePart
	 */
	public void setCurrentlyVisibleRoutePart( Integer currentlyVisibleRoutePart )
	{
		m_currentlyVisibleRoutePart = currentlyVisibleRoutePart;
	}

	/**
	 * Wechsel zum nächsten Streckenabschnitt, falls möglich.
	 * 
	 * @return Wechsel möglich
	 */
	public boolean nextVisiblePartAvailable()
	{
		if (m_currentlyVisibleRoutePart + 1 < m_currentRoutePartsGeoPoints.size())
		{
			m_currentlyVisibleRoutePart++;
			return true;
		}

		return false;
	}

	/**
	 * Liefert das Attribut pizzeriaPointOverlay.
	 * 
	 * @return pizzeriaPointOverlay
	 */
	public RoutePointOverlay getPizzeriaPointOverlay()
	{
		return m_pizzeriaPointOverlay;
	}

	/**
	 * Setzt das Attribut pizzeriaPointOverlay.
	 * 
	 * @param pizzeriaPointOverlay
	 *            zu setzender Wert für das Attribut pizzeriaPointOverlay
	 */
	public void setPizzeriaPointOverlay( RoutePointOverlay pizzeriaPointOverlay )
	{
		m_pizzeriaPointOverlay = pizzeriaPointOverlay;
	}

	/**
	 * Liefert das Attribut customerPointOverlay.
	 * 
	 * @return customerPointOverlay
	 */
	public RoutePointOverlay getCustomerPointOverlay()
	{
		return m_customerPointOverlay;
	}

	/**
	 * Setzt das Attribut customerPointOverlay.
	 * 
	 * @param customerPointOverlay
	 *            zu setzender Wert für das Attribut customerPointOverlay
	 */
	public void setCustomerPointOverlay( RoutePointOverlay customerPointOverlay )
	{
		m_customerPointOverlay = customerPointOverlay;
	}

	/**
	 * Liefert das Attribut currentlyVisibleRouteOverlays.
	 * 
	 * @return currentlyVisibleRouteOverlays
	 */
	public List<RouteOverlay> getCurrentlyVisibleRouteOverlays()
	{
		return m_currentlyVisibleRouteOverlays;
	}

	/**
	 * Setzt das Attribut currentlyVisibleRouteOverlays.
	 * 
	 * @param currentlyVisibleRouteOverlays
	 *            zu setzender Wert für das Attribut currentlyVisibleRouteOverlays
	 */
	public void setCurrentlyVisibleRouteOverlays( List<RouteOverlay> currentlyVisibleRouteOverlays )
	{
		m_currentlyVisibleRouteOverlays = currentlyVisibleRouteOverlays;
	}

	/**
	 * Fügt die Overlays im Store hinzu.
	 * 
	 * @param currentlyVisibleRouteOverlays
	 *            Hinzuzufügende Overlays
	 */
	public void addCurrentlyVisibleRouteOverlays( List<RouteOverlay> currentlyVisibleRouteOverlays )
	{
		if (m_currentlyVisibleRouteOverlays == null)
		{
			m_currentlyVisibleRouteOverlays = new ArrayList<RouteOverlay>();
		}
		m_currentlyVisibleRouteOverlays.addAll( currentlyVisibleRouteOverlays );
	}

}
