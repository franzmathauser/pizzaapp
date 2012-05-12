var rootURL = "http://localhost:8080/PizzaAppServices/products";

function deleteProduct(id) {
	console.log('deleteProduct');
	$.ajax({
		type : 'DELETE',
		url : rootURL + '/' + id,
		success : function(data, textStatus, jqXHR) {
			successMessage('Product deleted successfully');
			refreshProducts();
		},
		error : function(jqXHR, textStatus, errorThrown) {
			errorMessage('Product deleted error: ' + textStatus);
		}
	});
}


function findById(id) {
	$.ajax({
		type : 'GET',
		url : rootURL + '/' + id,
		dataType : "json",
		success : function(data) {
			renderFormDetails(data);
		}
	});
}

function findAll() {
	$.ajax({
		type : 'GET',
		url : rootURL,
		dataType : "json", // data type of response
		success : renderList
	});
}

function addProduct(data) {
	console.log('addProduct');
	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : rootURL,
		dataType : "json",
		data : data,
		success : function(data, textStatus, jqXHR) {
			refreshProducts();
			swipeForm();
			successMessage('Product created successfully');
		},
		error : function(jqXHR, textStatus, errorThrown) {
			errorMessage('Product create error: ' + textStatus);
		}
	});
}

function updateProduct(data) {
	$.ajax({
		type : 'PUT',
		contentType : 'application/json',
		url : rootURL + '/' + $('#productId').val(),
		dataType : "json",
		data : data,
		success : function(data, textStatus, jqXHR) {
			refreshProducts();
			swipeForm();
			successMessage('Product updated successfully');
			
		},
		error : function(jqXHR, textStatus, errorThrown) {
			errorMessage('Product update error: ' + textStatus);
		}
	});
}

function renderFormDetails(obj) {
	if (obj == null)
		return false;

	
	$('#productId').val(obj.id);
	$('#name').val(obj.name);
	$('#price_l').val(obj.price_l);
	$('#price_xl').val(obj.price_xl);
	$('#price_xxl').val(obj.price_xxl);
	$('#description').val(obj.description);
	$('#image_url').val(obj.image_url);
}

function renderList(data) {

	if (data == null)
		return false;
	var i = 0;
	for (i = 0; i < data.length; i++) {
		$('#products')
				.append(
						'<tr id="'
								+ data[i].id
								+ '">'
								+ '<td><img src="'+data[i].image_url+'" style="width: 100px; height: 100px" /></td>'
								+ '<td>'
								+ data[i].name
								+ '</td>'
								+ '<td>'
								+ data[i].price_l
								+ '; '
								+ data[i].price_xl
								+ '; '
								+ data[i].price_xxl
								+ '</td>'
								+ '<td>'
								+ data[i].image_url
								+ '</td>'
								+ '<td><img class="update" src="img/edit.png" /></td>'
								+ '<td><img id="huhu" class="delete" src="img/delete.png" /></td>'
								+ '</tr>');
	}

	$('.delete').click(function() {
		var row = $(this).parent().parent();
		var id = row.attr('id');

		deleteProduct(id);

	});

	$('.update').click(function() {
		var row = $(this).parent().parent();
		var id = row.attr('id');

		findById(id);

	});

}

function swipeForm(){
	$('#productId').val("");
	$('#name').val("");
	$('#price_l').val("");
	$('#price_xl').val("");
	$('#price_xxl').val("");
	$('#description').val("");
	$('#image_url').val("");
}
function refreshProducts() {
	$(document).ready(function() {
		$("#products").find("tr:gt(0)").remove();
	});

	findAll();
}

function successMessage(text) {
	$('#success_message').html(text);
	$('#success_box').fadeIn('slow').delay(5000).fadeOut('slow');
}

function errorMessage(text) {
	$('#error_message').html(text);
	$('#error_box').fadeIn('slow').delay(5000).fadeOut('slow');
}

$(document).ready(function() {

	refreshProducts();

	$('#product_form').submit(function() {
		var data = JSON.stringify($(this).serializeObject());
		if($('#productId').val() == ""){
			addProduct(data);
		}else{
			updateProduct(data);
		}
		return false;
	});

});
