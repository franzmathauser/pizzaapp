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

function euro(price){
	
	price = parseFloat(price.toString());
	return  price.toFixed(2) + ' €';
}

function getBaseURL(){
	var protocol = window.location.protocol;
	var host = window.location.host;
	
	return protocol+'//'+host;
}