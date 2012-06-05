var carImage = 'img/pizzaauto.png';
var driver_obj = new Array();

/**
 * Updated die Google-Maps Karte mit den neuen GPS-Daten des Fahrers. Es der
 * Zoom-Level der Karte wird so eingestellt, dass sich alle Fahrer im sichtbaren
 * bereich der Karte befinden.
 * 
 * @param driverLocation
 */
function driverLocationTopic(driverLocation) {
	var index = driverLocation.id;
	pos = new google.maps.LatLng(driverLocation.lat, driverLocation.lon);

	if (typeof driver_obj[index] !== 'undefined' && driver_obj[index] !== null) {
		driver_obj[index].setPosition(pos);
	} else {

		driver_obj[index] = new google.maps.Marker({
			position : pos,
			title : drivers[driverLocation.id].name,
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
	

	map.fitBounds(bounds);
	
	// set maximal zoom level to 17
	var listener = google.maps.event.addListener(map, "idle", function() {
		if (map.getZoom() > 17)
			map.setZoom(17);
		google.maps.event.removeListener(listener);
	});

}