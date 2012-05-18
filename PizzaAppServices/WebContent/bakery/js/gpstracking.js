var carImage = 'img/pizzaauto.png';
var driver = new Array();

function driverLocationTopic(obj) {
	var index = obj.id;
	pos = new google.maps.LatLng(obj.lat, obj.lon);

	if (typeof driver[index] !== 'undefined' && driver[index] !== null) {
		driver[index].setPosition(pos);
	} else {

		driver[index] = new google.maps.Marker({
			position : pos,
			title : obj.name,
			icon : carImage,
			map : map
		});

		driver[index].setMap(map);
	}

	driver[index].setPosition(pos);
	map.setCenter(pos);
}

$(document).ready(function() {

	connectActiveMQ('/topic/driverlocation', driverLocationTopic)
});