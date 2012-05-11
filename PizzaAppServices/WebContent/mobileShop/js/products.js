var productsBaseURL = "http://localhost:8080/PizzaAppServices/products";

var products = new Array();

function findById(id) {
	$.ajax({
		type : 'GET',
		url : productsBaseURL + '/' + id,
		dataType : "json",
		success : function(data) {
			renderDetails(data);
		}
	});
}

function findAll() {
	$.ajax({
		type : 'GET',
		url : productsBaseURL,
		dataType : "json", // data type of response
		success : renderProductList
	});
}

function renderDetails(obj) {
	if (obj == null)
		return false;

	$('#detail_product_id').val(obj.id);
	$('#detail_name').html(obj.name);
	$('#detail_price_l').html(obj.price_l + ' &euro;');
	$('#detail_price_xl').html(obj.price_xl + ' &euro;');
	$('#detail_price_xxl').html(obj.price_xxl + ' &euro;');
	$('#detail_description').html(obj.description);
	$('#detail_image_url').attr('src', obj.image_url);

}

function renderProductList(data) {

	if (data == null)
		return false;

	var i = 0;
	for (i = 0; i < data.length; i++) {
		products[data[i].id] = data[i];
		$('#product_list').append(
				'<li class="product" product_id="' + data[i].id
						+ '"><a href="#detail" data-icon="arrow-r">'
						+ '<img	src="' + data[i].image_url
						+ '" style="width: 84px; height: 84px;" />' + '<h3>'
						+ data[i].name + '</h3>' + '<p>ab ' + data[i].price_l
						+ ' &euro;</p>' + '</a>' + '</li>');
	}

	$('#product_list').listview('refresh');

	$('.product').click(function() {
		var row = $(this);
		var id = row.attr('product_id');

		renderDetails(products[id]);
		setCookie('detail_product_id', id);

	});

	// var last_product_id = getCookie('detail_product_id');
	// if(last_product_id != null){
	// renderDetails(products[last_product_id]);
	// }
}

function initCookieValues() {
	var lastname = getCookie('lastname');
	var forename = getCookie('forename');
	var zipcode = getCookie('zipcode');
	var street = getCookie('street');
	var city = getCookie('city');
	var email = getCookie('email');
	var phone = getCookie('phone');

	if (lastname != null) {
		$('#lastname').val(lastname);
	}
	if (forename != null) {
		$('#forename').val(forename);
	}
	if (street != null) {
		$('#street').val(street);
	}
	if (zipcode != null) {
		$('#zipcode').val(zipcode);
	}
	if (city != null) {
		$('#city').val(city);
	}
	if (email != null) {
		$('#email').val(email);
	}
	if (phone != null) {
		$('#phone').val(phone);
	}
}


$(document).ready(function() {

	findAll();

	$('a[href~="#orderPreview"]').click(function() {

		var lastname = $('#lastname').val();
		var forename = $('#forename').val();
		var zipcode = $('#zipcode').val();
		var street = $('#street').val();
		var city = $('#city').val();
		var email = $('#email').val();
		var phone = $('#phone').val();

		$('#customer_data_name').html(forename + ' ' + lastname);
		$('#customer_data_street').html(street);
		$('#customer_data_city').html(zipcode + ', ' + city);
		$('#customer_data_email').html(email);
		$('#customer_data_phone').html(phone);

		setCookie('forename', forename);
		setCookie('lastname', lastname);
		setCookie('street', street);
		setCookie('zipcode', zipcode);
		setCookie('city', city);
		setCookie('email', email);
		setCookie('phone', phone);
	});

	initCookieValues();

});
