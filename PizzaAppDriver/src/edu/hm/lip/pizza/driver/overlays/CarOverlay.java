package edu.hm.lip.pizza.driver.overlays;

import java.util.ArrayList;
import java.util.List;

import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;

import android.content.Context;
import android.graphics.drawable.Drawable;

/**
 * @author Stefan Wörner
 */
public class CarOverlay extends ItemizedOverlay<OverlayItem>
{

	private List<OverlayItem> m_overlays = new ArrayList<OverlayItem>();

	private Context m_context;

	/**
	 * @param defaultMarker
	 */
	public CarOverlay( Drawable defaultMarker )
	{
		super( boundCenter( defaultMarker ) );
	}

	/**
	 * @param defaultMarker
	 * @param context
	 */
	public CarOverlay( Drawable defaultMarker, Context context )
	{
		this( defaultMarker );
		m_context = context;
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
	 * @param overlay
	 */
	public void updateOverlay( OverlayItem overlay )
	{
		m_overlays.clear();
		m_overlays.add( overlay );
		populate();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.google.android.maps.ItemizedOverlay#onTap(int)
	 */
	@Override
	protected boolean onTap( int index )
	{
		return super.onTap( index );
	}
}
