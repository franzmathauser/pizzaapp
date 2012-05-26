var carImage = 'img/pizzaauto.png';
var driver_obj = new Array();

function driverLocationTopic(obj) {
	var index = obj.id;
	pos = new google.maps.LatLng(obj.lat, obj.lon);

	if (typeof driver_obj[index] !== 'undefined' && driver_obj[index] !== null) {
		driver_obj[index].setPosition(pos);
	} else {

		driver_obj[index] = new google.maps.Marker({
			position : pos,
			title : drivers[obj.id].name,
			icon : carImage,
			map : map
		});

		driver_obj[index].setMap(map);
	}

	driver_obj[index].setPosition(pos);

	var bounds = new google.maps.LatLngBounds();
	for ( var key in driver_obj) {
		bounds.extend(driver_obj[key].getPosition());
	}

	// set maximal zoom level to 17
	var listener = google.maps.event.addListener(map, "idle", function() {
		if (map.getZoom() > 17)
			map.setZoom(17);
		google.maps.event.removeListener(listener);
	});

	map.fitBounds(bounds);

}

$(document).ready(
		function() {
			var topic = '/topic/driverlocation';
			
			var driverLocationActiveMQErrorHandler = function() {
				connectActiveMQ(topic, driverLocationTopic,
						driverLocationActiveMQErrorHandler);
			};
			connectActiveMQ(topic, driverLocationTopic,
					driverLocationActiveMQErrorHandler);
		});