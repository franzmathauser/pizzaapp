function ordersTopic(order) {
	
	var html = renderOrderLine(order);
	$('#order_list').append(html);
	$('#order_list:visible').listview('refresh');
	$('li[order_id|="'+order.id+'"]').click(
		function(){
		orderLineClickHandler(order.id, $(this).attr('order_stage'));
	});
}

$(document).ready(function() {

	connectActiveMQ('/topic/orders', ordersTopic);
});