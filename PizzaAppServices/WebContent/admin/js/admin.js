var rootURL = "http://localhost:8080/PizzaAppServices/products";

$.fn.serializeObject = function() {
	var o = {};
	var a = this.serializeArray();
	$.each(a, function() {
		if (o[this.name] !== undefined) {
			if (!o[this.name].push) {
				o[this.name] = [ o[this.name] ];
			}
			o[this.name].push(this.value || '');
		} else {
			o[this.name] = this.value || '';
		}
	});
	return o;
};

function deleteProduct(id) {
    console.log('deleteProduct');
    $.ajax({
        type: 'DELETE',
        url: rootURL + '/' + id,
        success: function(data, textStatus, jqXHR){
            alert('Product deleted successfully');
            findAll();
        },
        error: function(jqXHR, textStatus, errorThrown){
            alert('deleteProduct error');
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
	console.log('addWine');
	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : rootURL,
		dataType : "json",
		data : data,
		success : function(data, textStatus, jqXHR) {
			alert('Product created successfully');
			// $('#btnDelete').show();
			// $('#wineId').val(data.id);
		},
		error : function(jqXHR, textStatus, errorThrown) {
			alert('addProduct error: ' + textStatus);
		}
	});
}

function renderList(data) {

	if(data == null)
		return false;
	var i = 0;
	for (i = 0; i < data.length; i++) {
		$('#products')
				.append(
						'<tr id="'
								+ data[i].id
								+ '">'
								+ '<td><img src="http://www.kenbersupplies.com/product/XDURS2011-03-10/no_image_icon2_150.gif" style="width: 100px; height: 100px" /></td>'
								+ '<td>'
								+ data[i].name
								+ '</td>'
								+ '<td>xxx</td>'
								+ '<td>'
								+ data[i].imageUrl
								+ '</td>'
								+ '<td id="test"><img src="img/edit.png" /></td>'
								+ '<td><img id="huhu" class="delete" src="img/delete.png" /></td>'
								+ '</tr>');
	}

	$('.delete').click(function() {
		var row = $(this).parent().parent();
		row.remove();
		var id =  row.attr('id');
		deleteProduct(id);
		
	});

}

$(document).ready(function() {

	findAll();

	$('#product_form').submit(function() {
		var data = JSON.stringify($(this).serializeObject());
		addProduct(data);
		return false;
	});

});
