package edu.hm.lip.pizza.service.bean.async;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;

import edu.hm.lip.pizza.api.object.ApiConstants;
import edu.hm.lip.pizza.api.object.resource.Order;
import edu.hm.lip.pizza.internal.bean.service.async.IAsyncHomezoneNotificationMailer;
import edu.hm.lip.pizza.internal.interceptor.LoggingInterceptor;
import edu.hm.lip.pizza.internal.util.MailUtility;

/**
 * Interface der Bean für das asynchrone versenden der Homezone Benachrichtigung. Sobald der Fahrer sich in der nähe des
 * Lieferorts befindet, wird diese Nachricht versendet.
 * 
 * @author Franz Mathauser, Stefan Wörner
 */
@Stateless
public class AsyncHomezoneNotificationMailer implements IAsyncHomezoneNotificationMailer
{

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.lip.pizza.internal.bean.service.async.IAsyncHomezoneNotificationMailer#sendHomezoneNotification(edu.hm.lip.pizza.api.object.resource.Order)
	 */
	@Override
	@Asynchronous
	@Interceptors( LoggingInterceptor.class )
	public void sendHomezoneNotification( Order order )
	{
		String receipientAddress = order.getCustomer().getEmail();
		String receipientName = order.getCustomer().getForename() + " " + order.getCustomer().getLastname();
		String subject = "Homezone Notification für Bestellung #" + order.getId();
		String mailContent = null;

		mailContent = createNotificationMessage( order );

		if (mailContent != null)
		{
			MailUtility.sendMail( receipientAddress, receipientName, subject, wrapHeaderAndFooter( receipientName, mailContent ) );
		}

	}

	/**
	 * Erzeugt den HTML-Inhalt der Homezone-Benachrichtigungsmail.
	 * 
	 * @param order
	 *            Ressource-Objekt einer Bestellung
	 * @return HTML-String
	 */
	private String createNotificationMessage( Order order )
	{
		NumberFormat priceFormatter = new DecimalFormat( ",##0.00" );

		StringBuilder sb = new StringBuilder();
		sb.append( "Der Pizzalieferant befindet sich ganz in der Nähe Ihres angegebenen Lieferorts. " ).append( "<br /><br />" );
		sb.append( "Bitte halten sie den Betrag von " ).append( priceFormatter.format( order.getPrice() ) )
				.append( " EUR in Bar bereit." ).append( "<br /><br />" );
		sb.append( "Wir wünschen schon mal einen Guten Appetit." );
		return sb.toString();
	}

	private String wrapHeaderAndFooter( String displayName, String mainContent )
	{

		StringBuilder sb = new StringBuilder();

		sb.append( "Sehr geehrte(r) " ).append( displayName ).append( ",<br /><br />" );

		sb.append( mainContent ).append( "<br /><br />" );
		sb.append( "Mit Besten Grüßen" ).append( "<br /><br />" );
		sb.append( "Ihr PizzaApp-Team" ).append( "<br /><br />" );

		sb.append( "<img src=\"http://" ).append( ApiConstants.PRODUCTION_SERVER_URL ).append( "/img/logo/pizza_hm_logo.png\" >" );
		return sb.toString();

	}
}
