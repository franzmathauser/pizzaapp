
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