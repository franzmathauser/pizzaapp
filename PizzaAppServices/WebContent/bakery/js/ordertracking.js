/**
 * ActiveMQ MessageHandler bei eingehenden Bestellungen. Eine neu eingehende
 * Bestellung wird in die GUI eingefügt.
 * 
 * @param order
 *            Bestellungsobjekt
 */
function ordersTopic(order) {

	var html = renderOrderLine(order);
	$('#order_list').append(html);
	$('#order_list:visible').listview('refresh');
	$('li[order_id|="' + order.id + '"]').click(function() {
		orderLineClickHandler(order.id, $(this).attr('order_stage'));
	});

	playWaiterVoice(order);
}

function playWaiterVoice(order) {
	var text = 'Neue Bestellung erhalten: \n';
	for ( var i = 0; i < order.orderLines.length; i++) {
		var orderLine = order.orderLines[i];
		if (orderLine.quantity == '1') {
			text += 'Ein mal';
		} else {
			text += orderLine.quantity + ' mal';
		}
		text += products[orderLine.productId].name + ' ';
		text += orderLine.size + '. ..\n';
	}

	text = encodeURI(text);

	$('#audio')
			.attr(
					'src',
					'http://api.ispeech.org/api/rest?voice=eurgermanfemale&action=convert&text='
							+ text
							+ '&apikey=8d1e2e5d3909929860aede288d6b974e&speed=0');
}