var end = new Date();
end.setMinutes ( end.getMinutes() + 2 );

function toSt2(n) {
	s = '';
	if (n < 10) s += '0';
	return (s + n).toString();
	}
	function toSt3(n) {
	s = '';
	if (n < 10) s += '00';
	else if (n < 100) s += '0';
	return (s + n).toString();
	}
	
function countdown() {
	d = new Date();
	count = Math.floor(end.getTime() - d.getTime());
	if (count > 0) {
		count = Math.floor(count / 1000);
		seconds = toSt2(count % 60);
		count = Math.floor(count / 60);
		minutes = toSt2(count % 60);
		count = Math.floor(count / 60);
		hours = toSt2(count % 24);
		
		var output = '';
		
		if(hours > 0){
			output += hours+':';
		}
		if(minutes > 0 || hours > 0){
			output += minutes+':';
		}
		output +=seconds;
		
		$('.time').text(output);

		setTimeout('countdown()', 1000);
	}
}

var ordersBaseURL = getBaseURL()+"/orders";
var productsBaseURL = getBaseURL()+"/products";

var products = new Array();

function findAllProducts() {
	$.ajax({
		type : 'GET',
		url : productsBaseURL,
		dataType : "json", // data type of response
		success : function(data){
			for(var i = 0; i < data.length; i++){
				products[data[i].id] = data[i];
			}
			getUndeliveredOrders();
		}
	});
}

function getUndeliveredOrders() {
	$.ajax({
		type : 'GET',
		url : ordersBaseURL + '/undelivered',
		dataType : "json",
		success : function(data) {
			renderOrders(data);
		}
	});
}

function renderOrders(data){
	if (data == null)
		return false;
	
	for (var i = 0; i < data.length; i++) {
		var order = data[i];
		var html = '<li data-role="list-divider">'+order.customer.street+', '+order.customer.city+' <span class="ui-li-count" style="font-size:1.1em;">'+euro(123.12)+'</span>'
				+'</li>'
				+'<li style="background-color: #0000ff;"><a href="index.html">'
				+'		<div style="float: left; margin: 0px; padding-right: 0.5em; height:50px">'
				+'			<svg height="60px" width="60px" xmlns="http://www.w3.org/2000/svg">'
				+'			    <circle id="redcircle" cx="25" cy="25" r="25" fill="#1874CD" />'
				+'			</svg>'
				+'			<br />'
				+'		</div>'
				+'		<div style="float: left;">';
			for(var j = 0; j < order.order_lines.length; j++){
				var orderLine = order.order_lines[j];
				html +='		<h3 class="pizza">'+orderLine.quantity+' x '+products[orderLine.product_id].name+' ' + orderLine.size.toUpperCase()+ '</h3>';	
			}
			
			
			html +='		</div>'
				+'		<div style="float: right;">'
				+'			<div class="orderTime">12 min</div>'
				+'		</div>'
				+'</a></li>';
		
		$('#order_list').append(html);
	}
	$('#order_list:visible').listview('refresh');
}

$(document).ready(function() {
	countdown();
	findAllProducts();
	
	
});