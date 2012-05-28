/**
 * Heartbeat Funktion, sendet eine Nachricht an das Topic "pizzaapp"
 */
function heartbeat() {
	client.send('/topic/pizzaapp', null,
			'{"messageType":"HEARTBEAT", "message":"beat"}');
}