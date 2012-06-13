package edu.hm.lip.pizza.driver.overlay;

import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;

import android.graphics.drawable.Drawable;

/**
 * Overlay für die aktuelle Position des Fahrers auf der Karte.
 * 
 * @author Franz Mathauser, Stefan Wörner
 */
public class DriverOverlay extends ItemizedOverlay<OverlayItem>
{

	private OverlayItem m_overlay;

	/**
	 * Konstruktor.
	 * 
	 * @param marker
	 *            Symbol welches auf der Karte dargestellt wird.
	 */
	public DriverOverlay( Drawable marker )
	{
		super( boundCenter( marker ) );
	}

	/**
	 * Liefert das Attribut overlay.
	 * 
	 * @return overlay
	 */
	public OverlayItem getOverlay()
	{
		return m_overlay;
	}

	/**
	 * Setzt das Attribut overlay.
	 * 
	 * @param overlay
	 *            zu setzender Wert für das Attribut overlay
	 */
	public void setOverlay( OverlayItem overlay )
	{
		m_overlay = overlay;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.google.android.maps.ItemizedOverlay#createItem(int)
	 */
	@Override
	protected OverlayItem createItem( int i )
	{
		return m_overlay == null ? null : m_overlay;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.google.android.maps.ItemizedOverlay#size()
	 */
	@Override
	public int size()
	{
		return m_overlay == null ? 0 : 1;
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
