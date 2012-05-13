var end = new Date();
end.setMinutes ( end.getMinutes() + 2 );


function toSt2(n) {
	s = '';
	if (n < 10) s += '0';
	return (s + n).toString();
	}
	function toSt3(n) {
	s = '';
	if (n < 10) s += '00';
	else if (n < 100) s += '0';
	return (s + n).toString();
	}
	
function countdown() {
	d = new Date();
	count = Math.floor(end.getTime() - d.getTime());
	if (count > 0) {
		count = Math.floor(count / 1000);
		seconds = toSt2(count % 60);
		count = Math.floor(count / 60);
		minutes = toSt2(count % 60);
		count = Math.floor(count / 60);
		hours = toSt2(count % 24);
		
		var output = '';
		
		if(hours > 0){
			output += hours+':';
		}
		if(minutes > 0 || hours > 0){
			output += minutes+':';
		}
		output +=seconds;
		
		$('.time').text(output);

		setTimeout('countdown()', 1000);
	}
}

$(document).ready(function() {
	countdown();
});