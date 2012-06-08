package edu.hm.lip.pizza.driver.util.route;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.android.maps.GeoPoint;

import edu.hm.lip.pizza.driver.objects.resource.DriverRoute;
import edu.hm.lip.pizza.driver.objects.resource.Order;
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

	private Integer m_currentRoutePartNumber;

	private Map<Integer, List<GeoPoint>> m_allRouteGeoPoints;

	private RoutePointOverlay m_visiblePizzeriaPointOverlay;

	private RoutePointOverlay m_visibleCustomerPointOverlay;

	private List<RouteOverlay> m_visibleRouteOverlays;

	/**
	 * Konstruktor.
	 */
	private DriverRouteStore()
	{
		m_currentRoutePartNumber = 0;
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
			m_instance.m_visibleRouteOverlays = null;
			m_instance.m_currentRoutePartNumber = null;
			m_instance.m_currentRoute = null;
			m_instance.m_allRouteGeoPoints = null;
			m_instance.m_visibleCustomerPointOverlay = null;
			m_instance.m_visiblePizzeriaPointOverlay = null;
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
	 * Liefert das Attribut currentRoutePartNumber.
	 * 
	 * @return currentRoutePartNumber
	 */
	public Integer getCurrentRoutePartNumber()
	{
		return m_currentRoutePartNumber;
	}

	/**
	 * Setzt das Attribut currentRoutePartNumber.
	 * 
	 * @param currentRoutePartNumber
	 *            zu setzender Wert für das Attribut currentRoutePartNumber
	 */
	public void setCurrentRoutePartNumber( Integer currentRoutePartNumber )
	{
		m_currentRoutePartNumber = currentRoutePartNumber;
	}

	/**
	 * Liefert das Attribut allRouteGeoPoints.
	 * 
	 * @return allRouteGeoPoints
	 */
	public Map<Integer, List<GeoPoint>> getAllRouteGeoPoints()
	{
		return m_allRouteGeoPoints;
	}

	/**
	 * Setzt das Attribut allRouteGeoPoints.
	 * 
	 * @param allRouteGeoPoints
	 *            zu setzender Wert für das Attribut allRouteGeoPoints
	 */
	public void setAllRouteGeoPoints( Map<Integer, List<GeoPoint>> allRouteGeoPoints )
	{
		m_allRouteGeoPoints = allRouteGeoPoints;

		if (allRouteGeoPoints == null)
		{
			m_currentRoutePartNumber = 0;
		}
	}

	/**
	 * Liefert das Attribut visiblePizzeriaPointOverlay.
	 * 
	 * @return visiblePizzeriaPointOverlay
	 */
	public RoutePointOverlay getVisiblePizzeriaPointOverlay()
	{
		return m_visiblePizzeriaPointOverlay;
	}

	/**
	 * Setzt das Attribut visiblePizzeriaPointOverlay.
	 * 
	 * @param visiblePizzeriaPointOverlay
	 *            zu setzender Wert für das Attribut visiblePizzeriaPointOverlay
	 */
	public void setVisiblePizzeriaPointOverlay( RoutePointOverlay visiblePizzeriaPointOverlay )
	{
		m_visiblePizzeriaPointOverlay = visiblePizzeriaPointOverlay;
	}

	/**
	 * Liefert das Attribut visibleCustomerPointOverlay.
	 * 
	 * @return visibleCustomerPointOverlay
	 */
	public RoutePointOverlay getVisibleCustomerPointOverlay()
	{
		return m_visibleCustomerPointOverlay;
	}

	/**
	 * Setzt das Attribut visibleCustomerPointOverlay.
	 * 
	 * @param visibleCustomerPointOverlay
	 *            zu setzender Wert für das Attribut visibleCustomerPointOverlay
	 */
	public void setVisibleCustomerPointOverlay( RoutePointOverlay visibleCustomerPointOverlay )
	{
		m_visibleCustomerPointOverlay = visibleCustomerPointOverlay;
	}

	/**
	 * Liefert das Attribut visibleRouteOverlays.
	 * 
	 * @return visibleRouteOverlays
	 */
	public List<RouteOverlay> getVisibleRouteOverlays()
	{
		return m_visibleRouteOverlays;
	}

	/**
	 * Setzt das Attribut visibleRouteOverlays.
	 * 
	 * @param visibleRouteOverlays
	 *            zu setzender Wert für das Attribut visibleRouteOverlays
	 */
	public void setVisibleRouteOverlays( List<RouteOverlay> visibleRouteOverlays )
	{
		m_visibleRouteOverlays = visibleRouteOverlays;
	}

	/**
	 * Fügt die Overlays im Store hinzu.
	 * 
	 * @param visibleRouteOverlays
	 *            Hinzuzufügende Overlays
	 */
	public void addVisibleRouteOverlays( List<RouteOverlay> visibleRouteOverlays )
	{
		if (m_visibleRouteOverlays == null)
		{
			m_visibleRouteOverlays = new ArrayList<RouteOverlay>();
		}

		m_visibleRouteOverlays.addAll( visibleRouteOverlays );
	}

	/**
	 * Wechsel zum nächsten Streckenabschnitt, falls möglich.
	 */
	public void nextRoutePart()
	{
		if (m_currentRoutePartNumber + 1 < m_allRouteGeoPoints.size())
		{
			m_currentRoutePartNumber++;
		}
	}

	/**
	 * Nächster Streckenabschnitt verfügbar.
	 * 
	 * @return Verfügbar
	 */
	public boolean isNextRoutePartNumberAvailable()
	{
		return m_allRouteGeoPoints != null && m_currentRoutePartNumber + 1 < m_allRouteGeoPoints.size();
	}

	/**
	 * Liefert die GepPoints zum aktuellen Routenabschnitt.
	 * 
	 * @return GeoPoints des aktuellen Routenabschnitts
	 */
	public List<GeoPoint> getCurrentRoutePartGeoPoints()
	{
		if (m_allRouteGeoPoints != null && m_allRouteGeoPoints.size() > m_currentRoutePartNumber)
		{
			return m_allRouteGeoPoints.get( m_currentRoutePartNumber );
		}
		else
		{
			return null;
		}
	}

	/**
	 * Gibt die aktuell auszuliefernde Bestellung zurück.
	 * 
	 * @return Aktuell auszuliefernde Bestellung
	 */
	public Order getCurrentOrder()
	{
		if (m_currentRoute != null && m_currentRoute.getOrders() != null
				&& m_currentRoute.getOrders().size() > m_currentRoutePartNumber)
		{
			return m_currentRoute.getOrders().get( m_currentRoutePartNumber );
		}
		else
		{
			return null;
		}
	}

	/**
	 * Gibt die Infos zur aktuell auszuliefernde Bestellung zurück.
	 * 
	 * @return Bestellinfos
	 */
	public String getCurrentOrderInfoString()
	{
		Order currentOrder = getCurrentOrder();

		if (currentOrder != null)
		{
			DecimalFormat priceFormatter = new DecimalFormat( "#0.00€" );

			StringBuilder sb = new StringBuilder();
			sb.append( "- " ).append( currentOrder.getCustomer().getNameAsString() );
			sb.append( "\n" );
			sb.append( "- " ).append( currentOrder.getCustomer().getAddressAsString() );
			sb.append( "\n" );
			sb.append( "- " ).append( priceFormatter.format( Double.parseDouble( currentOrder.getPrice() ) ) );

			return sb.toString();
		}
		else
		{
			return "";
		}
	}

	/**
	 * Setzt den Routenstore zurück.
	 */
	public void resetStore()
	{
		m_allRouteGeoPoints = null;
		m_currentRoute = null;
		m_currentRoutePartNumber = 0;
		// m_visibleCustomerPointOverlay = null;
		// m_visiblePizzeriaPointOverlay = null;
		// m_visibleRouteOverlays = null;
	}

}
