/**
 * Finde alle Fahrer via Rest-WS call an das Backend. Trigger Render Fahrer.
 */
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

var driver_arrivals = new Array();

/**
 * Countdown Funktion. Die Ankunftszeit wird aus dem localStorage des Browsers
 * gelesen und mit der aktuellen Zeit verglichen. Die verbleibende Zeit wird bei
 * Fahrer aktualisiert.
 */
function countdown() {

	setTimeout('countdown()', 2000);
	var driver_arrivals = localStorage.driver_arrivals;
	if (driver_arrivals != undefined) {
		driver_arrivals = JSON.parse(driver_arrivals);
	} else {
		return;
	}

	jQuery.each(driver_arrivals, function(i, val) {

		if (val != null) {
			var driverId = i;
			var d = new Date().getTime();
			var end = val.end;

			count = Math.floor(end - d);
			if (count > 0) {
				count = Math.floor(count / 1000);
				seconds = toSt2(count % 60);
				count = Math.floor(count / 60);
				minutes = toSt2(count % 60);
				count = Math.floor(count / 60);
				hours = toSt2(count % 24);

				var output = '';

				if (hours > 0) {
					output += hours + ':';
				}
				if (minutes > 0 || hours > 0) {
					output += minutes + ':';
				}
				output += seconds;

				$('#driver_time-' + driverId).text(output);
			} else {
				$('#driver_time-' + driverId).text('back');
			}
		}
	});

}

/**
 * ActiveMQ - Message Handler bei eingehender Nachrichten für MessageType
 * "DRIVER_ARRIVAL". Die Fahrtzeit wird in den LocalStorage geschrieben.
 * 
 * @param message
 *            Message-Body
 */
function setDriverArrival(message) {
	var driverId = message.id;
	var end = new Date();
	end.setSeconds(message.seconds);

	var driver_arrivals = localStorage.driver_arrivals;
	if (driver_arrivals == undefined) {
		driver_arrivals = new Array();
	} else {
		driver_arrivals = JSON.parse(driver_arrivals);
	}

	driver_arrivals[driverId] = {
		"end" : end.getTime()
	};

	localStorage.driver_arrivals = JSON.stringify(driver_arrivals);

}

/**
 * Initialer Start des Fahrer-Countdowns.
 */
$(document).ready(function() {
	countdown();
});