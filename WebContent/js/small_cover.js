function smallCovers() {
	console.log("starting small_cover method to populate page with thumbnails!");
	var API_KEY = "IE2ZINGXDYWASD9NH";
	$("img.cover").each(function() {
		// $(this).addClass( "foo" );
		var artist = $(this);
		var artist_name = $(this).prop('alt');
		// console.log("get name: " + artist_name);
		// console.log(data);
		var artist_name_cleaned = artist_name.replace(" ", "+");
		// console.log("clean name: " + artist_name_cleaned);
		$.ajax({
	        url: "http://developer.echonest.com/api/v4/artist/search?api_key="+ API_KEY +"&format=json&name="+ artist_name_cleaned +"&results=1"
	    }).then(function(data) {
	    // $('.greeting-id').append(data.id);
	    // $('.greeting-content').append(data.content);
    	var response = data.response;
    	// console.log(response);
    	var artists = response.artists;
    	if (artists != null) {
    		// console.log("found artist for this entry with name:
    		// "+artist_name_cleaned);
    		// console.log(artists[0]);
    		var en_artist_id = artists[0].id;
    		// console.log(en_artist_id);	
    		$.ajax({
    			url: "http://developer.echonest.com/api/v4/artist/images?api_key="+ API_KEY +"&id="+ en_artist_id +"&format=json&results=1&start=0&license=unknown"
    			}).then(function(rdata) {
    				// $('.greeting-id').append(data.id);
    				// $('.greeting-content').append(data.content);
    				var rresponse = rdata.response;
			    	// console.log(rresponse);
			    	var images = rresponse.images;
			    	if (images[0] != null && images[0].url.search("myspacecdn.com") == -1) {
			    		// console.log(images[0]);
			    		var en_image_url = images[0].url;
			    		// console.log(en_image_url);
			    		// console.log("found artist and photos for name: "+
						// artist_name_cleaned +" \ going to set photo src.");
			    		artist.prop('src', en_image_url);
			    	}
			    	else {
			    		// console.log("found artist with name: "+
						// artist_name_cleaned + " but he has no photos
						// available.");
			    		artist.prop('src', "images/user.jpg");
			    	}
			    	
			    });
    		}
    	else {
    		// console.log("no artists found in the api with name: "+
			// artist_name_cleaned);
		    // put imagem default
		    artist.prop('src', "images/user.jpg");
		   	}
    	});
	});
}
	
$(document).ready(
		smallCovers()
);
