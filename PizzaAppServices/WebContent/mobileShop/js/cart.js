var orderssBaseURL = getBaseURL() + "/orders";

var refreshCart = function() {
	var cart;
	if (localStorage.cart == null) {
		cart = new Array();
	} else {
		cart = JSON.parse(localStorage.cart);
	}
	var cart_size = cart.length;
	$('#cart_list li').remove();
	$('#previewProductList li.orderedProducts').remove();
	var orderSum = 0;
	for ( var i = 0; i < cart_size; i++) {
		var product = products[cart[i].product_id];
		var size = cart[i].size;
		var price = eval('product.price_' + size.toLowerCase());
		var quantity = cart[i].quantity;
		var sum = euro(quantity * price);
		orderSum += quantity * price;

		$('#cart_list')
				.append(
						'<li class="cart_product_line" product_id="'
								+ product.id
								+ '">'
								+ '<a href="#detail">'
								+ '<img	src="/productImages/'
								+ product.image_url
								+ '" style="width: 84px; height: 84px;" />'
								+ '<h3>'
								+ quantity
								+ ' x '
								+ product.name
								+ ' '
								+ size.toUpperCase()
								+ '</h3>'
								+ '<p>Preis je '
								+ euro(price)
								+ ' / '
								+ euro(sum)
								+ '</p>'
								+ '</a>'
								+ '<a href="#deleteCartItem" data-rel="dialog" data-transition="pop" row_id="'
								+ i + '" >Artikel l&ouml;schen</a></li>');

		$('#previewProductList').prepend(
				'<li class="orderedProducts">' + '<img	src="/productImages/'
						+ product.image_url
						+ '" style="width: 84px; height: 84px;" />' + '<h3>'
						+ quantity + ' x ' + product.name + ' '
						+ size.toUpperCase() + '</h3>' + '<p>Preis je '
						+ euro(price) + ' / ' + euro(sum) + '</p>' + '</li>');
	}
	$('.order_sum').text(euro(orderSum));

	$('a[href="#deleteCartItem"]').click(function() {
		var row_id = $(this).attr('row_id');
		$('#del_row').val(row_id);
	});

	$('.cart_product_line').click(function() {
		var row = $(this);
		var id = row.attr('product_id');

		renderDetails(products[id]);
		localStorage.detail_product_id = id;

	});

};

function addOrder(data) {
	console.log('addOrder');
	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : orderssBaseURL,
		dataType : "json",
		data : data,
		success : function(data, textStatus, jqXHR) {
			// TODO remove reset cart in localstore
			// refresh cart
			console.log('sent order to server');
			$("#lnk_orderSuccess").click();
		},
		error : function(jqXHR, textStatus, errorThrown) {
			// errorMessage('Product create error: ' + textStatus);
			console.error('order count not be saved');
			$("#lnk_orderError").click();

		}
	});
}

$(document).ready(function() {

	var cart;
	if (localStorage.cart == null) {
		cart = new Array();
		localStorage.cart = JSON.stringify(cart);
	} else {
		cart = JSON.parse(localStorage.cart);
	}

	$('#add_to_cart').click(function() {
		var cart = JSON.parse(localStorage.cart);
		var cart_size = cart.length;

		var product_id = $('#detail_product_id').val();
		var size = $('input:radio[name=detail_size]:checked').val();

		var found = false;
		for ( var i = 0; i < cart_size; i++) {
			if (cart[i].product_id == product_id && cart[i].size == size) {
				cart[i].quantity++;
				found = true;
			}
		}
		if (!found)
			cart[cart_size] = {
				'product_id' : product_id,
				'size' : size,
				'quantity' : 1
			};
		localStorage.cart = JSON.stringify(cart);
	});

	$('a[href="#cart"]').click(refreshCart);

	$('#confirm_deleteCartItem').click(function() {
		var del_row = parseInt($('#del_row').val());
		var cart = JSON.parse(localStorage.cart);
		var cart_new = new Array();
		var j = 0;
		for ( var i = 0; i < cart.length; i++) {
			if (del_row != i) {
				cart_new[j++] = cart[i];
			}
		}

		localStorage.cart = JSON.stringify(cart_new);
		refreshCart();
	});

	$('#lnk_ordered').click(function() {

		var note = $('#note').val();
		var orderLines = JSON.parse(localStorage.cart);

		var json = JSON.stringify({
			"customer" : {
				"company" : $('#company').val(),
				"department" : $('#department').val(),
				"lastname" : $('#lastname').val(),
				"forename" : $('#forename').val(),
				"street" : $('#street').val(),
				"zipcode" : $('#zipcode').val(),
				"city" : $('#city').val(),
				"street" : $('#street').val(),
				"level" : $('#level').val(),
				"phone" : $('#phone').val(),
				"email" : $('#email').val()
			},
			"note" : note,
			"order_lines" : orderLines
		});

		console.log(json);
		addOrder(json);
	});

	$(document).bind("pageshow", function() {
		if ($("#cart_list:visible").length) {
			$('#cart_list').listview('refresh');
		}
		if ($("#previewProductList:visible").length) {
			$('#previewProductList').listview('refresh');
		}
	});

});