$(document).ready(
		function() {

			var carImage = 'images/pizzaauto.png';

			var driver = new Array();
			var client, destination;

			var url = $("#connect_url").val();
			var login = $("#connect_login").val();
			var passcode = $("#connect_passcode").val();
			destination = $("#destination").val();

			client = Stomp.client(url);

			// this allows to display debug logs directly on the web page
			client.debug = function(str) {
				$("#debug").append(str + "\n");
			};
			// the client is notified when it is connected to the server.
			var onconnect = function(frame) {
				client.debug("connected to Stomp");
				$('#connect').fadeOut({
					duration : 'fast'
				});
				$('#disconnect').fadeIn();
				$('#send_form_input').removeAttr('disabled');

				// is called on new message
				client.subscribe(destination, function(message) {
					$("#messages").append("<p>" + message.body + "</p>\n");

					obj = jQuery.parseJSON(message.body);
					var index = obj.id;
					pos = new google.maps.LatLng(obj.lat, obj.lon);

					if (typeof driver[index] !== 'undefined'
							&& driver[index] !== null) {
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

				});
			};
			client.connect(login, passcode, onconnect);
 
			// disconnection handler
			$('#disconnect_form').submit(function() {
				client.disconnect(function() {
					$('#disconnect').fadeOut({
						duration : 'fast'
					});
					$('#connect').fadeIn();
					$('#send_form_input').addAttr('disabled');
				});
				return false;
			});

		});