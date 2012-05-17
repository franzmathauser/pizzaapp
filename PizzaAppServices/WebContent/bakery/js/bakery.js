var end = new Date();
var order_dates = new Array();

end.setMinutes(end.getMinutes() + 2);

function toSt2(n) {
	s = '';
	if (n < 10)
		s += '0';
	return (s + n).toString();
}
function toSt3(n) {
	s = '';
	if (n < 10)
		s += '00';
	else if (n < 100)
		s += '0';
	return (s + n).toString();
}

function countup() {

	jQuery.each(order_dates, function(i, val) {

		var order_date = new Date(order_dates[i]);
        var current_date = new Date();
        
        count = Math.floor(current_date.getTime() - order_date.getTime());
        count = Math.floor(count / 1000);
        seconds = toSt2(count % 60);
        count = Math.floor(count / 60);
        minutes = toSt2(count % 60);
        count = Math.floor(count / 60);
        hours = toSt2(count);
        
        var output = '';
        
        if (hours > 0) {
        	output += hours + ' h ';
        }
        if (minutes > 0 || hours > 0) {
        	output += minutes + ' min';
        }
        // output += seconds;
        
        eval("$('#orderTime-"+i+"').text(output);");
    });
	


	
	setTimeout('countup()', 5000);

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

		if (hours > 0) {
			output += hours + ':';
		}
		if (minutes > 0 || hours > 0) {
			output += minutes + ':';
		}
		output += seconds;

		$('.time').text(output);

		setTimeout('countdown()', 1000);
	}
}

var ordersBaseURL = getBaseURL() + "/orders";
var productsBaseURL = getBaseURL() + "/products";
var driversBaseURL = getBaseURL() + "/drivers";

var products = new Array();

function findAllProducts() {
	$.ajax({
		type : 'GET',
		url : productsBaseURL,
		dataType : "json", // data type of response
		success : function(data) {
			for ( var i = 0; i < data.length; i++) {
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

function findAllDrivers() {
	$.ajax({
		type : 'GET',
		url : driversBaseURL,
		dataType : "json",
		success : function(data) {
			renderDrivers(data);
		}
	});
}

function renderOrders(data) {
	if (data == null)
		return false;

	for ( var i = 0; i < data.length; i++) {
		var order = data[i];
		order_dates[order.id] = order.order_date;
		var html = '<li data-role="list-divider">'
				+ order.customer.street
				+ ', '
				+ order.customer.city
				+ ' <span class="ui-li-count" style="font-size:1.1em;">'
				+ euro(order.price)
				+ '</span>'
				+ '</li>'
				+ '<li style="background-color: #0000ff;"><a href="index.html">'
				+ '		<div style="float: left; margin: 0px; padding-right: 0.5em; height:50px">'
				+ '			<svg height="60px" width="60px" xmlns="http://www.w3.org/2000/svg">'
				+ '			    <circle id="redcircle" cx="25" cy="25" r="25" fill="#1874CD" />'
				+ '			</svg>' + '			<br />' + '		</div>'
				+ '		<div style="float: left;">';
		for ( var j = 0; j < order.order_lines.length; j++) {
			var orderLine = order.order_lines[j];
			html += '		<h3 class="pizza">' + orderLine.quantity + ' x '
					+ products[orderLine.product_id].name + ' '
					+ orderLine.size.toUpperCase() + '</h3>';
		}

		html += '		</div>' + '		<div style="float: right;">'
				+ '			<div class="orderTime"><span id="orderTime-' + order.id
				+ '"></span></div>' + '		</div>' + '</a></li>';

		$('#order_list').append(html);
	}
	$('#order_list:visible').listview('refresh');
}

function renderDrivers(data){
	for(var i = 0; i < data.length; i++){
		var driver = data[i];
		var html = '<span class="driverName">'
			+ driver.name +'</span>'
			+ '<img src="img/stack-2.png" style="float: left" />'
			+ '<div class="time">ca. 4:59</div>';
		eval('$("#driver_line-'+i+'").html(html)');
	}
}

$(document).ready(function() {
	countup();
	// countdown();
	findAllProducts();
	findAllDrivers();

});