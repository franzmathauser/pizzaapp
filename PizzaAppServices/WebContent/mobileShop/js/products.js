var productsBaseURL = getBaseURL() + "/products";

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
	$('#detail_price_l').html(euro(obj.price_l));
	$('#detail_price_xl').html(euro(obj.price_xl));
	$('#detail_price_xxl').html(euro(obj.price_xxl));
	$('#detail_description').html(obj.description);
	$('#detail_image_url').attr('src', '/productImages/' + obj.image_url);

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
						+ '<img	src="/productImages/' + data[i].image_url
						+ '" style="width: 84px; height: 84px;" />' + '<h3>'
						+ data[i].name + '</h3>' + '<p>ab '
						+ euro(data[i].price_l) + '</p>' + '</a>' + '</li>');
	}

	$('#product_list:visible').listview('refresh');

	$('.product').click(function() {
		var row = $(this);
		var id = row.attr('product_id');

		renderDetails(products[id]);
		localStorage.detail_product_id = id;

	});

	var last_product_id = localStorage.detail_product_id;
	if (last_product_id != null) {
		renderDetails(products[last_product_id]);
	}

	refreshCart();

	if ($("#cart_list:visible").length) {
		$('#cart_list').listview('refresh');
	}
	if ($("#previewProductList:visible").length) {
		$('#previewProductList').listview('refresh');
	}
}

function initLocalStorageValues() {

	var lastname = localStorage.lastname;
	var forename = localStorage.forename;
	var zipcode = localStorage.zipcode;
	var street = localStorage.street;
	var city = localStorage.city;
	var email = localStorage.email;
	var phone = localStorage.phone;

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

	$(document).bind("pageshow", function() {
		if ($("#product_list:visible").length) {
			$('#product_list').listview('refresh');
		}
	});

	$('a[href~="#orderPreview"]').click(function() {

		if (!$('#customer_form').valid()) {

			return false;
		}

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

		localStorage.forename = forename;
		localStorage.lastname = lastname;
		localStorage.street = street;
		localStorage.zipcode = zipcode;
		localStorage.city = city;
		localStorage.email = email;
		localStorage.phone = phone;
	});

	initLocalStorageValues();

});
