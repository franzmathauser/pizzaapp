package edu.hm.lip.pizza.service.bean.async;

import javax.ejb.Asynchronous;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;

import edu.hm.lip.pizza.api.object.ApiConstants;
import edu.hm.lip.pizza.api.object.enumeration.Stage;
import edu.hm.lip.pizza.api.object.resource.Customer;
import edu.hm.lip.pizza.api.object.resource.Order;
import edu.hm.lip.pizza.api.object.resource.OrderLine;
import edu.hm.lip.pizza.internal.bean.database.IProductDAOLocal;
import edu.hm.lip.pizza.internal.bean.service.async.IAsyncBillingPrintMailer;
import edu.hm.lip.pizza.internal.config.ConfigurationConstants;
import edu.hm.lip.pizza.internal.config.ConfigurationSingleton;
import edu.hm.lip.pizza.internal.interceptor.LoggingInterceptor;
import edu.hm.lip.pizza.internal.object.entity.EntityProduct;
import edu.hm.lip.pizza.internal.util.MailUtility;

/**
 * Interface der Bean für das asynchrone versenden der OrderStages einer Bestellung.
 * 
 * @author Franz Mathauser, Stefan Wörner
 */
@Stateless
public class AsyncBillingPrintMailer implements IAsyncBillingPrintMailer
{

	@EJB
	private IProductDAOLocal productDAOBean;

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.lip.pizza.internal.bean.service.async.IAsyncBillingPrintMailer#sendOrderBilling(edu.hm.lip.pizza.api.object.resource.Order)
	 */
	@Override
	@Asynchronous
	@Interceptors( LoggingInterceptor.class )
	public void sendOrderBilling( Order order )
	{
		if (order.getCurrentStage() == Stage.IN_STOVE)
		{
			ConfigurationSingleton configuration = ConfigurationSingleton.getInstance();

			String receipientAddress = configuration.getString( ConfigurationConstants.MAIL_SENDER_ADDRESS );
			String receipientName = configuration.getString( ConfigurationConstants.MAIL_SENDER_NAME );

			String subject = "Rechnungsdruck: #" + order.getId();
			String mailContent = getBillingMessage( order );

			MailUtility.sendMail( receipientAddress, receipientName, subject, mailContent );
		}

	}

	/**
	 * Erzeugt eine HTML-Nachricht über die Rechnungsinformationen.
	 * 
	 * @param order
	 *            Ressource-Objekt einer Bestellung
	 * @return Rechnung als HTML-String
	 */
	private String getBillingMessage( Order order )
	{
		Customer customer = order.getCustomer();
		String displayName = customer.getForename() + " " + customer.getLastname();
		StringBuilder sb = new StringBuilder();

		sb.append( "<img src=\"http://" ).append( ApiConstants.PRODUCTION_SERVER_URL ).append( "/img/logo/pizza_hm_logo.png\"" )
				.append( " style=\"float:right;\" >" );
		sb.append( "PizzaApp" ).append( "<br />" );
		sb.append( "Hochschule München" ).append( "<br />" );
		sb.append( "Lothstr. 64" ).append( "<br /><br />" );
		sb.append( "80335 München" ).append( "<br /><br /><br />" );

		sb.append( displayName ).append( "<br/>" );
		sb.append( customer.getStreet() ).append( "<br /><br />" );
		sb.append( customer.getZipcode() ).append( " " ).append( customer.getCity() ).append( "<br /><br /><br />" );

		sb.append( "<h1>Rechnung #" ).append( order.getId() ).append( "</h1>" );
		sb.append( "Sehr geehrte(r) " ).append( displayName ).append( ",<br /><br />" );
		sb.append( "vielen Dank für Ihre Bestellung bei PizzaApp-Store." ).append( "<br /><br />" );

		sb.append( "<table border=\"1\">" );

		for (OrderLine orderLine : order.getOrderLines())
		{
			EntityProduct product = productDAOBean.read( orderLine.getProductId() );
			sb.append( "<tr>" );
			sb.append( "<td>" ).append( orderLine.getQuantity() ).append( "</td>" );
			sb.append( "<td>" ).append( product.getName() ).append( "</td>" );
			sb.append( "<td>" ).append( orderLine.getSize() ).append( "</td>" );
			sb.append( "<td>" ).append( orderLine.getPrice() ).append( "</td>" );
			sb.append( "<td>" ).append( orderLine.getPrice() * orderLine.getQuantity() ).append( "</td>" );

			sb.append( "</tr>" );
		}

		sb.append( "</table>" ).append( "<br />" );

		sb.append( "Gesamtsumme: " ).append( order.getPrice() ).append( " EUR" );

		return sb.toString();
	}
}
