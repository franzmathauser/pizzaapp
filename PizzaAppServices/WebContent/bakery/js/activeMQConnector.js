var activeMQClients = new Array();
var client;

/**
 * Diese Methode verbindet sich über ein Websocket mit der ActiveMQ über das
 * Stomp-Protokoll.
 * 
 * @param destination
 *            subscriber Topic
 * @param messageHandler
 *            Handling bei eingehenden Nachrichten
 * @param errorHandler
 *            Fehler Callback
 */
function connectActiveMQ(destination, messageHandler, errorHandler) {

	var url = 'ws://' + window.location.hostname + ':61614/stomp';
	var login = 'guest';
	var passcode = 'password';

	client = Stomp.client(url);

	// this allows to display debug logs directly on the web page
	client.debug = function(str) {
		if (debug) {
			$("#messages").append(str + "<br />");
		}
	};
	// the client is notified when it is connected to the server.
	var onconnect = function(frame) {
		client.debug("connected to Stomp");

		// is called on new message
		client.subscribe(destination, function(message) {
			if (debug == true) {
				$("#messages").append("<p>" + message.body + "</p>\n");
			}

			obj = jQuery.parseJSON(message.body);
			// call Handler to postprocess message
			messageHandler(obj);
		});

		// starte heartbeat
		setTimeout('heartbeat()', 5000);
	};
	client.connect(login, passcode, onconnect, errorHandler);

	activeMQClients[activeMQClients.length] = client;
}

/**
 * Schließt alle alle WebSocket-Connections zu diesem Topic.
 */
function disconnectActiveMQ() {

	jQuery.each(activeMQClients, function(i, val) {
		val.disconnect();
	});
}

/**
 * Message Handler, wenn eine neue Nachricht im Topic "pizzaapp" eingeht.
 * 
 * @param messageContainer
 *            Nachrichten-Container
 */
function pizzaAppTopic(messageContainer) {

	if (messageContainer == null || messageContainer.messageType == null) {
		return;
	}

	var message = messageContainer.message;

	switch (messageContainer.messageType) {
	case 'DRIVER_LOCATION':
		driverLocationTopic(message);
		break;
	case 'DRIVER_ARRIVAL':
		setDriverArrival(message);
		break;
	case 'ORDER':
		ordersTopic(message);
		break;
	case 'HEARTBEAT':
		setTimeout("heartbeat()", 5000);
		break;
	default:
		alert('No message handling');
		break;
	}

}

$(document).ready(function() {
	var topic = '/topic/pizzaapp';

	var pizzaAppActiveMQErrorHandler = function() {
		connectActiveMQ(topic, pizzaAppTopic, pizzaAppActiveMQErrorHandler);
	};
	connectActiveMQ(topic, pizzaAppTopic, pizzaAppActiveMQErrorHandler);

});
