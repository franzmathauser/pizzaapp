package edu.hm.lip.pizza.driver.overlay;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.Projection;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

/**
 * Overlay für die aktuelle Route des Fahrers.
 * 
 * @author Franz Mathauser, Stefan Wörner
 */
public class RouteOverlay extends Overlay
{

	private GeoPoint m_gp1;

	private GeoPoint m_gp2;

	private int m_color;

	/**
	 * Konstruktor.
	 * 
	 * @param gp1
	 *            GeoPoint 1
	 * @param gp2
	 *            GeoPoint 2
	 * @param color
	 *            Linienfarbe
	 */
	public RouteOverlay( GeoPoint gp1, GeoPoint gp2, int color )
	{
		m_gp1 = gp1;
		m_gp2 = gp2;
		m_color = color;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.google.android.maps.Overlay#draw(android.graphics.Canvas, com.google.android.maps.MapView, boolean,
	 *      long)
	 */
	@Override
	public boolean draw( Canvas canvas, MapView mapView, boolean shadow, long when )
	{
		Projection projection = mapView.getProjection();

		if (!shadow)
		{
			Paint paint = new Paint();
			paint.setAntiAlias( true );
			Point point = new Point();
			projection.toPixels( m_gp1, point );

			if (m_color == 999)
			{
				paint.setColor( Color.RED );
			}
			else
			{
				paint.setColor( m_color );
			}

			Point point2 = new Point();
			projection.toPixels( m_gp2, point2 );
			paint.setStrokeWidth( 5 );
			paint.setAlpha( 120 );
			canvas.drawLine( point.x, point.y, point2.x, point2.y, paint );
		}

		return super.draw( canvas, mapView, shadow, when );
	}

}
