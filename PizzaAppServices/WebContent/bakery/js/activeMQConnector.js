var activeMQClients = new Array();

function connectActiveMQ(destination, messageHandler, errorHandler) {
	var debugMessage = 0;
	var client;
	var url = 'ws://' + window.location.hostname + ':61614/stomp';
	var login = 'guest';
	var passcode = 'password';

	client = Stomp.client(url);

	// this allows to display debug logs directly on the web page
	client.debug = function(str) {
		if(debugMessage % 10 == 0)
			$("#debug").html('');
		
		debugMessage++;
		$("#debug").append(str + "<br />");
	};
	// the client is notified when it is connected to the server.
	var onconnect = function(frame) {
		client.debug("connected to Stomp");
		$('#disconnect').fadeIn();

		// is called on new message
		client.subscribe(destination, function(message) {
			//$("#messages").append("<p>" + message.body + "</p>\n");
			obj = jQuery.parseJSON(message.body);
			// call Handler to postprocess message
			messageHandler(obj);
		});
	};
	client.connect(login, passcode, onconnect, errorHandler);
	activeMQClients[activeMQClients.length] = client;

	return client;
}

function disconnectActiveMQ() {

	jQuery.each(activeMQClients, function(i, val) {
		val.disconnect();
	});
}

var heartbeatTopicEvent = function heartbeatTopic() {
	heartbeatClient.send('/topic/heartbeat', null, '{}');
	setTimeout("heartbeatTopicEvent()", 5000);
};

$(document).ready(
		function() {

			function doNothing(obj) {
			}
			;

			var heartbeatActiveMQErrorHandler = function() {
				heartbeatClient = connectActiveMQ('/topic/heartbeat', doNothing,
						heartbeatActiveMQErrorHandler);
			};

			heartbeatClient = connectActiveMQ('/topic/heartbeat', doNothing,
					heartbeatActiveMQErrorHandler);

			setTimeout("heartbeatTopicEvent()", 5000);

		});