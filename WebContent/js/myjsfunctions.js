$(document).ready(function() {		
		  $('body').popover({
			  selector: "[data-toggle=popover]",
			  container: "body",
			  html : true,
		  	  title : "<strong>Some help to start searching!</strong>",
		  	  content : "<i><b>Search syntax</b></i><br/>[<span style=\"color:gray;\">artist</span>s | <span style=\"color:blue;\">album</span>s | <span style=\"color:orange;\">track</span>s]<br/>[<span style=\"color:green;\">genre</span>] ex. rock<br/>[<span style=\"color:green;\">decade</span>] ex. 1990<br/>[<span style=\"color:green;\">search_string</span>] ex. black friday<br/><br/><i><b>Search examples</b></i><br/>rock artist<br/>artist cher<br/>country artist 2010<br/>rock album 2010<br/>paris<br/>1920 tracks<br/>rock \"track\"<br/>"
			  });
		  
		  
		  $.post("RecommendationController", {'track' : JSON.stringify(trackingJSON)},
				   function(data) {
				     				
				     var LIMIT = 8;
				     var artist_counter = 0;
				     var track_counter = 0;
				     
				     var obj = jQuery.parseJSON(data);
				     //alert(data);
				     
				     var current_url = ($(location).attr('pathname') + $(location).attr('search'));
				     current_url = current_url.replace("/MusicFox_web/", "");
				     //alert(current_url);
				     
				     function divs(name, url, id){
				    	 return "<div class=\"col-xs-6 col-md-3 thumbnail\" style=\"height: 200px\">"+
				    	 "<a href=\""+url+"\" onclick=\"setArtist('"+id+"')\"> <img class=\"cover\" "+ 
				    	 " src=\"images/user.jpg\" style=\"height: 100px;\" alt=\""+name+"\"></a>"+
				    	 "<div class=\"caption\"><h3><a href=\""+url+"\" onclick=\"setArtist('"+id+"')\">"+name+"</a></h3></div></div>";
				     }
				     
				     // create html for ARTISTS
				     var temp_artist = "";
				     var temp_track = "";
				     var count = 0;
				     for (cenas in obj) {
				    	 count++;
				    	 console.log("#: " + cenas);
				    	 //console.log("url: "  +obj[cenas]);
				    	 var temp_url = String(obj[cenas]);
				    	 var temp_id = temp_url.substr(temp_url.indexOf("=") + 1, temp_url.length);
				    	 if (cenas.charAt(0) == 'a' && current_url != temp_url) { 
				    		 if (artist_counter< LIMIT) {
				    			 temp_artist += divs(cenas.substr(1,cenas.length), temp_url, temp_id);
				    			 artist_counter++;
				    		 }
				    	 }
				    	 else if (current_url != temp_url) {
				    		 console.log("track: " + cenas.substr(1,cenas.length));
				    		 if (track_counter < LIMIT) {
				    			 temp_track += divs(cenas.substr(1,cenas.length), temp_url, temp_id);
				    			 track_counter++;
				    		 }
				    	 }
				     }
				     
				     console.log("# of items: "+ count);
				     
				     $("#recommended-artists").html(temp_artist);
				     $("#recommended-tracks").html(temp_track);
				     
				     
				     console.log("finished to create recommended elements!");
				     
				     
				   });
		  
		  
		  
		  
		  
	});