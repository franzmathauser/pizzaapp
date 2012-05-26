var statisticsBaseURL = getBaseURL() + "/statistics";


function findHeatmapData() {
	$.ajax({
		type : 'GET',
		url : statisticsBaseURL+'/heatmap',
		dataType : "json", // data type of response
		success : function(data){
			var objData = data.gpsDatas;
			var staticData;
			var dataArr = new Array();
			
			for(var i = 0; i < objData.length; i++){
				var json = objData[i];
				json.count = 1;
				
				dataArr[i] = json;
			}
			
			var staticData = {
					max : 3,
					data : dataArr
				};

			 	// Die Daten dürfen nicht zu früh gesetzt werden, da die lat/lng - pixel projektion ansonsten nicht funktioniert
				google.maps.event.addListenerOnce(map, "idle", function() {
					heatmap.setDataSet(staticData);
				});
		}
	});
}


$(document).ready(function() {

	findHeatmapData();
});