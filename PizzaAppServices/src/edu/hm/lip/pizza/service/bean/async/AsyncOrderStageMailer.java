package edu.hm.lip.pizza.service.bean.async;

import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;

import edu.hm.lip.pizza.api.object.ApiConstants;
import edu.hm.lip.pizza.api.object.resource.Order;
import edu.hm.lip.pizza.internal.bean.service.async.IAsyncOrderStageMailer;
import edu.hm.lip.pizza.internal.interceptor.LoggingInterceptor;
import edu.hm.lip.pizza.internal.util.MailUtility;

/**
 * Interface der Bean für das asynchrone versenden der OrderStages einer Bestellung.
 * 
 * @author Franz Mathauser, Stefan Wörner
 */
@Stateless
public class AsyncOrderStageMailer implements IAsyncOrderStageMailer
{

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.lip.pizza.internal.bean.service.async.IAsyncOrderStageMailer#sendCurrentStage(edu.hm.lip.pizza.api.object.resource.Order)
	 */
	@Override
	@Asynchronous
	@Interceptors( LoggingInterceptor.class )
	public void sendCurrentStage( Order order )
	{
		String receipientAddress = order.getCustomer().getEmail();
		String receipientName = order.getCustomer().getForename() + " " + order.getCustomer().getLastname();
		String subject = "Ihre Bestellung #" + order.getId() + " PizzaApp - Statusänderung: ";
		String mailContent = null;

		switch (order.getCurrentStage())
		{
			case ORDERED:
				subject += "ist eingegangen";
				mailContent = orderedMessage();
				break;
			case IN_PREPARATION:
				subject += "wird vorbereitet";
				mailContent = inPreperationMessage();
				break;
			case IN_STOVE:
				subject += "wird gebacken";
				mailContent = inStoveMessage();
				break;
			case IN_DELIVERY:
				subject += "wird ausgeliefert";
				mailContent = inDeliveryMessage();
				break;
			case DELIVERED:
				// TODO evtl. delivered nachricht für feedback zusenden.
				break;

			default:
				// do noting;
				break;
		}

		if (mailContent != null)
		{
			MailUtility.sendMail( receipientAddress, receipientName, subject, wrapHeaderAndFooter( receipientName, mailContent ) );
		}
	}

	private String orderedMessage()
	{
		StringBuilder sb = new StringBuilder();

		sb.append( "vielen Dank für Ihre Bestellung. <br /><br />" );
		sb.append( "<img src=\"http://" ).append( ApiConstants.PRODUCTION_SERVER_URL )
				.append( "/img/orderstages/orderstage-1.png\" > " );

		return sb.toString();
	}

	private String inPreperationMessage()
	{
		StringBuilder sb = new StringBuilder();

		sb.append( "Ihre Bestellung wurde soeben auf den Status: \"In Bearbeitung\" (PREP) gesetzt. <br /><br />" );
		sb.append( "<img src=\"http://" ).append( ApiConstants.PRODUCTION_SERVER_URL )
				.append( "/img/orderstages/orderstage-2.png\" > " );

		return sb.toString();
	}

	private String inStoveMessage()
	{
		StringBuilder sb = new StringBuilder();

		sb.append( "Ihre Bestellung wurde soeben auf den Status: \"Baken\" gesetzt. <br /><br />" );
		sb.append( "<img src=\"http://" ).append( ApiConstants.PRODUCTION_SERVER_URL )
				.append( "/img/orderstages/orderstage-3.png\" > " );

		return sb.toString();
	}

	private String inDeliveryMessage()
	{
		StringBuilder sb = new StringBuilder();

		sb.append( "Ihre Bestellung wurde soeben auf den Status: \"Auslieferung\" gesetzt. <br /><br />" );
		sb.append( "<img src=\"http://" ).append( ApiConstants.PRODUCTION_SERVER_URL )
				.append( "/img/orderstages/orderstage-4.png\" > " ).append( "<br /><br />" );
		sb.append( "Wir wünschen ihnen schonmal einen guten Appetit." );

		return sb.toString();
	}

	private String wrapHeaderAndFooter( String displayName, String mainContent )
	{
		StringBuilder sb = new StringBuilder();

		sb.append( "Sehr geehrte(r) " ).append( displayName ).append( ",<br /><br />" );

		sb.append( mainContent ).append( "<br /><br />" );
		sb.append( "Mit Besten Grüßen" ).append( "<br /><br />" );
		sb.append( "Ihr PizzaApp-Team" );

		return sb.toString();
	}

}
