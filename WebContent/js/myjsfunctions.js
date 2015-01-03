$(document).ready(function() {
	$('body').popover({
		selector : "[data-toggle=popover]",
		container : "body",
		html : true,
		title : "<strong>Some help to start searching!</strong>",
		content : "<i><b>Search syntax</b></i><br/>[<span style=\"color:gray;\">artist</span>s | <span style=\"color:blue;\">album</span>s | <span style=\"color:orange;\">track</span>s]<br/>[<span style=\"color:green;\">genre</span>] ex. rock<br/>[<span style=\"color:green;\">decade</span>] ex. 1990<br/>[<span style=\"color:green;\">search_string</span>] ex. black friday<br/><br/><i><b>Search examples</b></i><br/>rock artist<br/>artist cher<br/>country artist 2010<br/>rock album 2010<br/>paris<br/>1920 tracks<br/>rock \"track\"<br/>"
	});

	$("#button1").click(function() {
		//alert("button clicked!!");
		//alert( "Handler for .click() called." );
		var activepage = $("#recommended-artists .active-page");
		if (typeof  activepage.next(".page").html() == 'undefined') {
			// next é null
			activepage.removeClass("active-page");
			activepage.addClass("hidden-page");
			$("#recommended-artists .page").first().addClass("active-page");
		}
		else {
			activepage.removeClass("active-page");
			activepage.addClass("hidden-page");
			activepage.next(".page").addClass("active-page");
		}
	});
	
	$("#button2").click(function() {
		//alert("button clicked!!");
		//alert( "Handler for .click() called." );
		var activepage = $("#recommended-tracks .active-page");
		if (typeof  activepage.next(".page").html() == 'undefined') {
			// next é null
			activepage.removeClass("active-page");
			activepage.addClass("hidden-page");
			$("#recommended-tracks .page").first().addClass("active-page");
		}
		else {
			activepage.removeClass("active-page");
			activepage.addClass("hidden-page");
			activepage.next(".page").addClass("active-page");
		}
	});
	
	$("#button-refresh").click(function() {
		//alert("button clicked!!");
		//alert( "Handler for .click() called." );
		var activepage = $("#main-container .active-page");
		if (typeof  activepage.next(".page").html() == 'undefined') {
			// next é null
			//activepage.removeClass("active-page");
			//activepage.addClass("hidden-page");
			//$("#main-container .page").first().addClass("active-page");
			$("#button-refresh").hide();
		}
		else {
			activepage.next(".page").fadeIn();
			activepage.removeClass("active-page");
			//activepage.addClass("hidden-page");
			activepage.next(".page").addClass("active-page");
			activepage.next(".page").removeClass("hidden-page");
			var element = activepage.next(".page");
			if (typeof  element.next(".page").html() == 'undefined') {
				$("#button-refresh").hide();
			}
		}
	});
	
	$.post("RecommendationController",{'track' : JSON.stringify(trackingJSON)},function(data) {
		console.log("here!");
		var LIMIT = 4;
		var artist_counter = 0;
		var track_counter = 0;
		
		var obj = jQuery.parseJSON(data);
		// alert(data);

		var current_url = ($(location).attr(
				'pathname') + $(location).attr(
				'search'));
		current_url = current_url.replace(
				"/MusicFox_web/", "");
		// alert(current_url);

		function divs(name, url, id) {
			return "<div class=\"col-xs-6 col-md-3 thumbnail\" style=\"height: 200px\">"
					+ "<a href=\""
					+ url
					+ "\" onclick=\"setArtist('"
					+ id
					+ "')\"> <img class=\"cover\" "
					+ " src=\"images/user.jpg\" style=\"height: 100px;\" alt=\""
					+ name
					+ "\"></a>"
					+ "<div class=\"caption\"><h3><a href=\""
					+ url
					+ "\" onclick=\"setArtist('"
					+ id
					+ "')\">"
					+ name
					+ "</a></h3></div></div>";
		}

		// create html for ARTISTS
		var temp_artist = "";
		var temp_track = "";
		var count = 0;
		
		temp_artist += "<div class=\"page active-page\">";
		temp_track += "<div class=\"page active-page\">";
		
		for (cenas in obj) {
			//console.log("#iteration: " + count);
			//console.log("#item: " + cenas);
			//console.log("url: " +obj[cenas]);
			var temp_url = String(obj[cenas]);
			var temp_id = temp_url.substr(temp_url.indexOf("=") + 1, temp_url.length);
			
			if (cenas.charAt(0) == 'a' && current_url != temp_url) {
				// ARTIST
				if (artist_counter % LIMIT == 0 && artist_counter != 0) {
					//console.log("got :"+ LIMIT +" elements and close page. artist counter at: " + artist_counter);
					temp_artist += "</div>";
					temp_artist += "<div class=\"page hidden-page\">";
				}
				
				temp_artist += divs(cenas.substr(1,	cenas.length), temp_url, temp_id);
				artist_counter++;
				
			} else if (current_url != temp_url) {
				// TRACK
				if (track_counter % LIMIT == 0 && track_counter != 0) {
					//console.log("got :"+ LIMIT +" elements and close page. track counter at: " + track_counter);
					temp_track += "</div>";
					temp_track += "<div class=\"page hidden-page\">";
				}
				console.log("#item: " + cenas);
				console.log("url: " +obj[cenas]);
				temp_track += divs(cenas.substr(1, cenas.length), temp_url, temp_id);
				track_counter++;
			}
			
			count++;
		}
		
		temp_artist += "</div>";
		temp_track += "</div>";

		console.log("# of total items(artists + tracks): " + count);
		
		var footer = "";
		
		$(".hidden-button").addClass("active-button").removeClass("hidden-button");

		$("#recommended-artists").html(temp_artist+footer);
		$("#recommended-tracks").html(temp_track+footer);

		console.log("finished to create recommended elements!");
		smallCovers();

	});
	
	

});