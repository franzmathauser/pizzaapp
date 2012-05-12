

$(document).ready(function() {
	var refreshCart = function(){
		var cart = $.cookies.get('cart');
		var cart_size = cart.length;
		$('#cart_list li').remove();
		
		for(var i=0; i < cart_size; i++){
			var product = products[cart[i].product_id];
			var size = cart[i].size;
			var price = eval('product.price_'+size);
			var quantity = cart[i].count;
			var sum = euro(quantity * price);
			
			$('#cart_list').append('<li>'
					+ '<a href="#detail">'
					+ '<img	src="'+product.image_url+'" style="width: 84px; height: 84px;" />'
					+ '<h3>'+ quantity + ' x ' +product.name + ' '+size.toUpperCase()+'</h3>'
					+ '<p>Preis je ' + euro(price) + ' / ' + euro(sum) + '</p>'
					+ '</a>'
					+ '<a href="#deleteCartItem" data-rel="dialog" data-transition="pop" row_id="' + i + '" >Artikel l&ouml;schen</a></li>'
			);
			
//			$('#previewProductList').append(
//					'<li>'
//					+ '<img	src="'+product.image_url+'" style="width: 84px; height: 84px;" />'
//					+ '<h3>'+ quantity + ' x ' +product.name + ' '+size.toUpperCase()+'</h3>'
//					+ '<p>Preis je ' + euro(price) + ' / ' + euro(sum) + '</p>'
//					+ '</li>'
//					);
		}
		
		$('a[href="#deleteCartItem"]').click(function(){
			var row_id = $(this).attr('row_id');
			$('#del_row').val(row_id);
		});
		
	};
	var cart = new Array();
	$.cookies.set('cart', cart);

	$('#add_to_cart').click(function(){
		var cart = $.cookies.get('cart');
		var cart_size = cart.length;
		
		var product_id = $('#detail_product_id').val();
		var size = $('input:radio[name=detail_size]:checked').val();
		
		var found = false;
		for(var i=0; i < cart_size; i++){
			if(cart[i].product_id == product_id && cart[i].size == size){
				cart[i].count++;
				found = true;
			}
		}
		if(!found)
			cart[cart_size] = {'product_id':product_id, 'size':size, 'count':1};
		$.cookies.set('cart',cart);
	});
	
	$('a[href="#cart"]').click(refreshCart);
	
	
	
	
	$('#confirm_deleteCartItem').click(function(){
		var del_row = parseInt($('#del_row').val());
		var cart = $.cookies.get('cart');
		var cart_new = new Array();
		var j = 0;
		for(var i=0; i < cart.length; i++){
			if(del_row != i){
				cart_new[j++] = cart[i];
			}
		}
		
		$.cookies.set('cart',cart_new);
		refreshCart();
	});
	
	$(document).bind("pageshow", function(){
         if($("#cart_list:visible").length){
             $('#cart_list').listview('refresh');
         }
     });
});