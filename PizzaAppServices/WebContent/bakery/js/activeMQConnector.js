var activeMQClients = new Array();

function connectActiveMQ(destination, messageHandler) {

	var client;
	var url = 'ws://' + window.location.hostname + ':61614/stomp';
	var login = 'guest';
	var passcode = 'password';

	client = Stomp.client(url);

	// this allows to display debug logs directly on the web page
	client.debug = function(str) {
		$("#debug").append(str + "\n");
	};
	// the client is notified when it is connected to the server.
	var onconnect = function(frame) {
		client.debug("connected to Stomp");
		$('#disconnect').fadeIn();

		// is called on new message
		client.subscribe(destination, function(message) {
			$("#messages").append("<p>" + message.body + "</p>\n");
			obj = jQuery.parseJSON(message.body);
			// call Handler to postprocess message
			messageHandler(obj);
		});
	};
	client.connect(login, passcode, onconnect);
	activeMQClients[activeMQClients.length] = client;
}

function disconnectActiveMQ() {

	jQuery.each(activeMQClients, function(i, val) {
		val.disconnect();
	});
}