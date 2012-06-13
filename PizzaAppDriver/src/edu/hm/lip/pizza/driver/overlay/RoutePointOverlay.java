package edu.hm.lip.pizza.driver.overlay;

import java.util.ArrayList;

import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;

import android.graphics.drawable.Drawable;

/**
 * Overlay für die Routenpunkte (Pizzeria, Kunden) der Fahrer-Route auf der Karte.
 * 
 * @author Franz Mathauser, Stefan Wörner
 */
public class RoutePointOverlay extends ItemizedOverlay<OverlayItem>
{

	private ArrayList<OverlayItem> m_overlays = new ArrayList<OverlayItem>();

	/**
	 * Konstruktor.
	 * 
	 * @param marker
	 *            Symbol welches auf der Karte dargestellt wird.
	 */
	public RoutePointOverlay( Drawable marker )
	{
		super( marker );
	}

	/**
	 * Liefert das Attribut overlays.
	 * 
	 * @return overlays
	 */
	public ArrayList<OverlayItem> getOverlays()
	{
		return m_overlays;
	}

	/**
	 * Setzt das Attribut overlays.
	 * 
	 * @param overlays
	 *            zu setzender Wert für das Attribut overlays
	 */
	public void setOverlays( ArrayList<OverlayItem> overlays )
	{
		m_overlays = overlays;
	}

	/**
	 * Fügt ein neues Overlay-Item hinzu.
	 * 
	 * @param overlay
	 *            Overlay-Item
	 */
	public void addOverlay( OverlayItem overlay )
	{
		m_overlays.add( overlay );
		populate();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.google.android.maps.ItemizedOverlay#createItem(int)
	 */
	@Override
	protected OverlayItem createItem( int i )
	{
		return m_overlays.get( i );
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.google.android.maps.ItemizedOverlay#size()
	 */
	@Override
	public int size()
	{
		return m_overlays.size();
	}

	/**
	 * Hilfsmethode zum Rendern des Overlays. Es wird lediglich die populate-Methode der Super-Klasse
	 * {@link ItemizedOverlay} aufgerufen
	 */
	public void populateOverlay()
	{
		populate();
	}

}
