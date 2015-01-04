<%@ page import="java.util.ArrayList"%>
<%@ page import="com.musicfox.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<jsp:useBean id="mybean" scope="request" class="com.musicfox.JavaBean"></jsp:useBean>
<html lang="en">

<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">
<title>MUSICFOX</title>

<!-- Bootstrap core CSS -->
<link href="css/bootstrap.min.css" rel="stylesheet">

<!-- Custom styles for this template -->
<link href="css/dashboard.css" rel="stylesheet">

<link href="css/simple-sidebar.css" rel="stylesheet">


<!-- Just for debugging purposes. Don't actually copy these 2 lines! -->
<!--[if lt IE 9]><script src="../../assets/js/ie8-responsive-file-warning.js"></script><![endif]-->
<script src="js/ie-emulation-modes-warning.js"></script>

<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->

<style>
	.tooltip>span {text-align: left;}
	.hidden-page {display: none;}
	.active-page {display: inherit;}
	.hidden-button {display: none;}
	.active-button {display: inherit;}
</style>
<!-- Cookie -->
<script type="text/javascript">

	if(getCookie("tracking") === ""){
		var trackingJSON = {
			genre: {
				
			},
			decade: {
				
			},
			artist: {
				
			},
			album: {
				
			},
			track:  {
				
			}
		}	
	}
	else 
		var trackingJSON = JSON.parse(getCookie("tracking"));
	
	
	function getCookie(cname) {
	    var name = cname + "=";
	    var ca = document.cookie.split(';');
	    for(var i=0; i<ca.length; i++) {
	        var c = ca[i];
	        while (c.charAt(0)==' ') c = c.substring(1);
	        if (c.indexOf(name) == 0) return c.substring(name.length, c.length);
	    }
	    return "";
	}
	
	function setCookie(cname, cvalue, exdays) {
	    var d = new Date();
	    d.setTime(d.getTime() + (exdays*24*60*60*1000));
	    var expires = "expires="+d.toUTCString();
	    document.cookie = cname + "=" + cvalue + "; " + expires;
	}
	
	function setAlbum(id) {
		//var id = _id.hash();
		var t = trackingJSON.album[id];
		if(t === undefined)
			trackingJSON.album[id] = 1;
		else
			trackingJSON.album[id] = t+1;
		setCookie("tracking",JSON.stringify(trackingJSON),1);
	}

	function setTrack(id) {
		//var id = _id.hash();
		var t = trackingJSON.track[id];
		if(t === undefined)
			trackingJSON.track[id] = 1;
		else
			trackingJSON.track[id] = t+1;
		setCookie("tracking",JSON.stringify(trackingJSON),1);
	}
	
	function setArtist(id) {
		//var id = _id.hash();
		//console.log(_id.replace("-", '_'));
		var t = trackingJSON.artist[id];
		if( t === undefined)
			trackingJSON.artist[id] = 1;
		else
			trackingJSON.artist[id] = t+1;
		setCookie("tracking",JSON.stringify(trackingJSON),1);
	}
	
	function setGenre(g) {
		g = g.toLowerCase();
		var t = trackingJSON.genre[g];
		if(t === undefined)
			trackingJSON.genre[g] = 1;
		else
			trackingJSON.genre[g] = t+1;
		setCookie("tracking",JSON.stringify(trackingJSON),1);
	}
	
	function setDecade(d) {
		var t = trackingJSON.decade[d];
		if (t === undefined)
			trackingJSON.decade[d] = 1;
		else
			trackingJSON.decade[d] = t+1;
		setCookie("tracking",JSON.stringify(trackingJSON),1);
	}
	
	
	function search(){
		var t = document.getElementById("semanticSearch").value;
		var splits = t.split(" ");
				
		var array = ["reggae", "ska", "vocals", "jazz", "country",
						"pop", "soul", "r&b", "rock", "rap", "hip hop",
						"latin", "electronica", "dance", "alternative",
						"indie", "soundtracks", "world", "blues", "classical",
						"opera", "new age", "folk"];
		
		for(key in splits){
			if(checkDecade(splits[key])){
				setDecade(splits[key]);
			}
			else if(contains(array, splits[key]))
				setGenre(splits[key]);
		}
	}
	
	function checkDecade(numero){
		
		var x = parseInt(numero);
		if(x === "NaN")
			return false;
		if (x > 1900 && x < 2020 && x%10==0) {
			return true;
		}
	}
	
	function contains(a, obj) {
	    var i = a.length;
	    while (i--) {
	       if (a[i] === obj) {
	           return true;
	       }
	    }
	    return false;
	}
	
	</script>
</head>

<body>

	<nav class="navbar navbar-default navbar-fixed-top" role="navigation">
		<div class="container-fluid">
			<!-- Brand and toggle get grouped for better mobile display -->
			<div class="navbar-header">

				<button type="button" class="navbar-toggle collapsed"
					data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="HomeView.jsp">MusicFox</a>

			</div>

			<!-- Collect the nav links, forms, and other content for toggling -->
			<div class="collapse navbar-collapse"
				id="bs-example-navbar-collapse-1">
				<!-- <p class="glyphicon glyphicon-info-sign" data-placement="bottom" data-toggle="popover" id="example"></p> -->
				<form action="searchController" method="POST"
					class="navbar-form navbar-left" role="search">
					<div class="input-group">
						<input type="text" class="form-control" name="query"
							placeholder="Search" id="semanticSearch"> <span
							class="input-group-btn">
							<button onclick="search()" type="submit" class="btn btn-default">Submit</button>
						</span>
					</div>
					<span class="glyphicon glyphicon-info-sign" data-placement="bottom"
						data-toggle="popover" id="example"></span>
				</form>


				<ul class="nav navbar-nav navbar-right">

					<li><p class="navbar-text">by Luís Jerónimo &amp; Miguel
							Jesus</p></li>

				</ul>
			</div>
			<!-- /.navbar-collapse -->
		</div>
		<!-- /.container-fluid -->
	</nav>

	<div class="container-fluid">
		<div class="row">
			<div class="col-sm-3 col-md-2 sidebar">

				<h3>Genres</h3>
				<ul class="nav nav-sidebar">
					<li><a href="MusicController?genre=Blues"
						onclick="setGenre('Blues')">Blues <span class="badge">24</span></a></li>
					<li><a href="MusicController?genre=Country"
						onclick="setGenre('Country')">Country <span class="badge">31</span></a></li>
					<li><a href="MusicController?genre=Electronica"
						onclick="setGenre('Electronica')">Electronica <span
							class="badge">34</span></a></li>
					<!-- <li><a href="?genre=International">International</a></li> -->
					<li><a href="MusicController?genre=Jazz"
						onclick="setGenre('Jazz')">Jazz <span class="badge">59</span></a></li>
					<li><a href="MusicController?genre=Pop"
						onclick="setGenre('Pop')">Pop <span class="badge">51</span></a></li>
					<li><a href="MusicController?genre=Rock"
						onclick="setGenre('Rock')">Rock <span class="badge">32</span></a></li>
					<li><a href="MusicController?genre=RnB"
						onclick="setGenre('RnB')">RnB <span class="badge">46</span></a></li>
					<li><a href="MusicController?genre=Rap"
						onclick="setGenre('Rap')">Rap <span class="badge">51</span></a></li>
					<li><a href="MusicController?genre=Reggae"
						onclick="setGenre('Reggae')">Reggae <span class="badge">41</span></a></li>
					<li><a href="MusicController?genre=Soul"
						onclick="setGenre('Soul')">Soul <span class="badge">46</span></a></li>

				</ul>

				<h3>Decades</h3>
				<ul class="nav nav-sidebar">
					<li><a href="MusicController?decade=1970"
						onclick="setDecade(1970)">1970 <span class="badge">121</span></a></li>
					<li><a href="MusicController?decade=1980"
						onclick="setDecade(1980)">1980 <span class="badge">153</span></a></li>
					<li><a href="MusicController?decade=1990"
						onclick="setDecade(1990)">1990 <span class="badge">232</span></a></li>
					<li><a href="MusicController?decade=2000"
						onclick="setDecade(2000)">2000 <span class="badge">318</span></a></li>
					<li><a href="MusicController?decade=2010"
						onclick="setDecade(2010)">2010 <span class="badge">244</span></a></li>
				</ul>

			</div>



			<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">

				<%
					if (mybean.getPageType() != null
											&& (mybean.getPageType().equals("artist_page")
													|| mybean.getPageType().equals("album_page") || mybean
													.getPageType().equals("track_page"))) {
										// para já nada
									} else if (mybean.getPageType() != null
											&& mybean.getPageType().equals("semantic_search_page")) {
				%>
				<h3 class="page-header">
					Search:
					<%=mybean.getOption()%>
					(<%=mybean.getNumberItems()%>)
				</h3>
				<%
					} else if (mybean.getPageType() != null
											&& mybean.getPageType().equals("artist_list_page")) {
				%>
				<h3 class="page-header">
					Artists:
					<%=mybean.getOption()%>
					(<%=mybean.getNumberItems()%>)
				</h3>
				<%
					} else {
				%>
				<div class="jumbotron">
					<div class="container">
						<h1>Welcome to MusicFox!</h1>
						<p>Navigate through our Artists, Albums and Tracks database.</p>
						<p>
							<a class="btn btn-primary btn-lg"
								href="MusicController?genre=rock" onclick="setGenre('Rock')"
								role="button">See some Rock artists right away!</a>
						</p>
					</div>
				</div>


				<%
					}
				%>

				<div class="row placeholders">
					<div id="main-container" class="row">


						<%
							if (mybean.getPageType() != null
										&& mybean.getPageType().equals("artist_page")) {
									Artist temp_artist = mybean.getArtistsArray().get(0);

									String temp_artistname = temp_artist.getName();
									String temp_artistgenre = temp_artist.getMainGenre();
									String temp_artistdecade = temp_artist.getDecade();
									String temp_artistgender = temp_artist.getGender();
									String temp_artistvevourl = temp_artist.getVevoUrl();
									long temp_artistvevolm = temp_artist.getVevoViewsLastMonth();
									long temp_artistvevotviews = temp_artist.getVevoViewsTotal();
									String temp_artistturl = temp_artist.getTwitterUrl();
									long temp_artisttfoll = temp_artist.getTwitterFollowers();
									String temp_artistfburl = temp_artist.getFacebookUrl();
									long temp_artistfbpta = temp_artist
											.getFacebookPeopleTalkingAbout();
									long temp_artistfblikes = temp_artist.getFacebookLikes();
%>

						<ol class="breadcrumb">
							<li><a href="HomeView.jsp">Home</a></li>
							<li class="active">Artist: <%=temp_artistname%></li>
						</ol>
						<script type="text/javascript">						
							setGenre("<%=temp_artistgenre%>");
						</script>

						<div class="page-header">
							<img class="cover-large"
								src="images/user.jpg"
								style="height: 250px;" alt="<%=temp_artistname%>">

							<h2>
								<%=temp_artistname%>
								<small><%=temp_artistgenre%></small>
							</h2>
						</div>

						<table class="table table-hover table-bordered">
							<tr>
								<td><b>Genre</b></td>
								<td><%=temp_artistgenre%></td>
							</tr>
							<tr>
								<td><b>Decade</b></td>
								<td><%=temp_artistdecade%></td>
							</tr>
							<tr>
								<td><b>Gender</b></td>
								<td><%=temp_artistgender%></td>
							</tr>
							<tr>
								<td><b>Vevo url</b></td>
								<%
									if (temp_artistvevourl != null && !temp_artistvevourl.equals("null")) {
								%>
								<td><a href="<%=temp_artistvevourl%>" target="_blank"><%=temp_artistvevourl%></a></td>
								<%
									} else {
								%>
								<td>n/a</td>
								<%
									}
								%>
							</tr>
							<tr>
								<td><b>Vevo total views</b></td>
								<td><%=temp_artistvevotviews%></td>
							</tr>
							<tr>
								<td><b>Vevo last month views</b></td>
								<td><%=temp_artistvevolm%></td>
							</tr>
							<tr>
								<td><b>Twitter url</b></td>
								<%
									if (temp_artistturl != null && !temp_artistturl.equals("null")) {
								%>
								<td><a href="<%=temp_artistturl%>" target="_blank"><%=temp_artistturl%></a></td>
								<%
									} else {
								%>
								<td>n/a</td>
								<%
									}
								%>
							</tr>
							<tr>
								<td><b>Twitter followers</b></td>
								<td><%=temp_artisttfoll%></td>
							</tr>
							<tr>
								<td><b>Facebook url</b></td>
								<%
									if (temp_artistfburl != null && !temp_artistfburl.equals("null")) {
								%>
								<td><a href="<%=temp_artistfburl%>" target="_blank"><%=temp_artistfburl%></a></td>
								<%
									} else {
								%>
								<td>n/a</td>
								<%
									}
								%>
							</tr>
							<tr>
								<td><b>Facebook people talking about</b></td>
								<td><%=temp_artistfbpta%></td>
							</tr>
							<tr>
								<td><b>Facebook likes</b></td>
								<td><%=temp_artistfblikes%></td>
							</tr>






						</table>


						<table class="table table-hover table-bordered">
							<tr>
								<th>Album name</th>
								<th></th>
							</tr>

							<%
								ArrayList<Album> temp_album_list = new ArrayList<Album>();
																			temp_album_list = temp_artist.getAlbumsArray();
																			for (int i = 0; i < temp_album_list.size(); i++) {
																				String temp_albumid = temp_album_list.get(i).getId();
																				String temp_albumtitle = temp_album_list.get(i).getTitle();
							%>
							<tr>
								<td><%=temp_albumtitle%></td>
								<td><a
									href="MusicController<%="?albumid=" + temp_albumid%>"
									onclick="setAlbum('<%=temp_albumid%>')" class="btn btn-default"
									role="button">More info</a></td>
							</tr>
							<%
								}
							%>

						</table>

						<%
							} else if (mybean.getPageType() != null
																	&& mybean.getPageType().equals("album_page")) {
																Album temp_album = mybean.getAlbum_information();

																String temp_albumtitle = temp_album.getTitle();
																String temp_albumid = temp_album.getId();
																String temp_albumrelease = temp_album.getReleaseDate();
																int temp_albumntracks = temp_album.getNumberOfTracks();
																String temp_albumdecade = temp_album.getDecade();
																String temp_albumartistid = temp_album.getArtistId();
																String temp_albumartistname = temp_album.getArtistName();
						%>

						<ol class="breadcrumb">
							<li><a href="HomeView.jsp">Home</a></li>
							<li><a
								href="MusicController?artistid=<%=temp_albumartistid%>"
								onclick="setArtist('<%=temp_albumartistid%>')">Artist: <%=temp_albumartistname%></a></li>
							<li class="active">Album: <%=temp_albumtitle%></li>
						</ol>

						<div class="page-header">
							<h2>
								<%=temp_albumtitle%>
								<small>by <%=temp_albumartistname%></small>
							</h2>
						</div>

						<table class="table table-hover table-bordered">
							<tr>
								<td><b>Release Date</b></td>
								<td><%=temp_albumrelease%></td>
							</tr>
							<tr>
								<td><b>Decade</b></td>
								<td><%=temp_albumdecade%></td>
							</tr>
							<tr>
								<td><b>Number of tracks</b></td>
								<td><%=temp_albumntracks%></td>
							</tr>

						</table>


						<table class="table table-hover table-bordered">
							<tr>
								<th>Index</th>
								<th>Track title</th>
								<th>More info</th>
							</tr>

							<%
								ArrayList<Track> temp_track_list = new ArrayList<Track>();
																			temp_track_list = temp_album.getTracksArray();
																			for (int i = 0; i < temp_track_list.size(); i++) {
																				String temp_trackid = temp_track_list.get(i).getId();
																				String temp_tracktitle = temp_track_list.get(i).getTitle();
																				String temp_trackindex = temp_track_list.get(i)
																						.getTrackIndex();
							%>
							<tr>
								<td><%=temp_trackindex%></td>
								<td><%=temp_tracktitle%></td>
								<td><a
									href="MusicController<%="?trackid=" + temp_trackid%>"
									onclick="setTrack('<%=temp_trackid%>')" class="btn btn-default"
									role="button">More info</a></td>

							</tr>
							<%
								}
							%>

						</table>
						<%
							}

															else if (mybean.getPageType() != null
																	&& mybean.getPageType().equals("track_page")) {
																Track temp_track = mybean.getTrack_information();

																String temp_tracktitle = temp_track.getTitle();
																String temp_trackindex = temp_track.getTrackIndex();
																String temp_trackduration = temp_track.getDuration();
																String temp_trackartistid = temp_track.getArtistId();
																String temp_trackartistname = temp_track.getArtistName();
																String temp_trackalbumid = temp_track.getAlbumId();
																String temp_trackalbumname = temp_track.getAlbumName();
						%>
						<ol class="breadcrumb">
							<li><a href="HomeView.jsp">Home</a></li>
							<li><a
								href="MusicController?artistid=<%=temp_trackartistid%>"
								onclick="setArtist('<%=temp_trackartistid%>')">Artist: <%=temp_trackartistname%></a></li>
							<li><a href="MusicController?albumid=<%=temp_trackalbumid%>"
								onclick="setAlbum('<%=temp_trackalbumid%>')">Album: <%=temp_trackalbumname%></a></li>
							<li class="active">Track: <%=temp_tracktitle%></li>
						</ol>

						<div class="page-header">
							<h2>
								<%=temp_tracktitle%>
								<small>by <%=temp_trackartistname%></small>
							</h2>
						</div>

						<table class="table table-hover table-bordered">
							<tr>
								<td><b>Track index</b></td>
								<td><%=temp_trackindex%></td>
							</tr>
							<tr>
								<td><b>Duration (seconds)</b></td>
								<td><%=temp_trackduration%> seg</td>
							</tr>

						</table>
						<%
							}

															else if (mybean.getPageType() != null
																	&& mybean.getPageType().equals("artist_list_page")) {
																if (mybean.getNumberItems() > 0) {
																	ArrayList<Artist> temp_array = mybean.getArtistsArray();
																	%>
						<div class="page active-page">
																	<%
																	for (int i = 0; i < temp_array.size(); i++) {
																		if (i % 8 == 0 && i != 0) {
																			%>
						</div>
						<div class="page hidden-page">
																			<%		
																		}
																		String artist_id = temp_array.get(i).getId();
																		String artist_name = temp_array.get(i).getName();
																		//String artist_cover = temp_array.get(i).getCoverUrl();
						%>

						<div class="col-xs-6 col-md-3 thumbnail" style="height: 200px">
							<a href="MusicController<%="?artistid=" + artist_id%>"
								onclick="setArtist('<%=artist_id%>')"> <img class="cover"
								src="images/user.jpg"
								style="height: 100px;" alt="<%=artist_name%>">
							</a>
							<div class="caption" style="">
								<h3>
									<a href="MusicController<%="?artistid=" + artist_id%>"
										onclick="setArtist('<%=artist_id%>')"><%=artist_name%></a>
								</h3>
								<%--p>
									<a href="MusicController<%="?artistid=" + artist_id%>"
										class="btn btn-primary" role="button">More info</a>
								</p--%>
							</div>
						</div>
						<%
																	}
																	%>
						</div>
						<nav><ul class="pager"><li><button id="button-refresh" type="button" class=" btn btn-default"><span class="glyphicon glyphicon-chevron-down"></span> Show more artists</button></li></ul></nav>
																	<%
																} else {
																	// no items to show!!
																}

															}

															// ve se é uma semantic search
															else if (mybean.getPageType() != null
																	&& mybean.getPageType().equals("semantic_search_page")) {

																
																if (mybean.getNumberItems() > 0) {
						%>

						<table class="table table-hover table-bordered">


							<%
								ArrayList<SemanticResult> temp_array = mybean
																						.getSemanticArray();
																				//
																				// Implement ordem no array, para ficar com artists, albums e tracks
																				//
																				
																				for (int i = 0; i < temp_array.size(); i++) {
																					String temp_resource_url = temp_array.get(i)
																							.getResource_url();
																					String temp_resource_name = temp_array.get(i)
																							.getResource_name();
																					String class_label = "label_danger";
																					String label_name = "SOME";
																					String ref = "REF";
																					
																					String temp_id = temp_resource_url.substring(temp_resource_url.indexOf("=") + 1, temp_resource_url.length());
																					
																					if (temp_resource_name.charAt(0) == 'a') {
																						class_label = "label-default";
																						label_name = "ARTIST";
																						ref = "onclick=\"setArtist('"+temp_id+"')\"";
																					} else if (temp_resource_name.charAt(0) == 'b') {
																						class_label = "label-info";
																						label_name = "ALBUM";
																						ref = "onclick=\"setAlbum('"+temp_id+"')\"";
																					} else if (temp_resource_name.charAt(0) == 'c') {
																						class_label = "label-warning";
																						label_name = "TRACK";
																						ref = "onclick=\"setTrack('"+temp_id+"')\"";
																					}
							%>
							<tr>
								<td><span class="label <%=class_label%>"><%=label_name%></span>
									<%=temp_resource_name.substring(1,
								temp_resource_name.length())%></td>
								<td><a href="<%=temp_resource_url%>"
									class="btn btn-default" role="button" <%=ref%>>More info</a></td>


							</tr>

							<%
								}
							%>

						</table>

						<%
							} else {
																	// no items to show!!
																}

															}

															else {
																// nao sei o que mostrar
															}
						%>



					</div>
				</div>



				<h3 class="page-header">
					<small>Artists you may like</small>
				</h3>
				<div class="row placeholders">
					<div id="recommended-artists" class="row">
						<div><p><span id="paginate-artists-button" class="glyphicon glyphicon-tasks"></span> Loading artist recommendations...</p></div>
					</div>
					<nav><ul class="pager"><li><button id="button1" type="button" class=" btn btn-default hidden-button"><span class="glyphicon glyphicon-refresh"></span> More artist recommendations</button></li></ul></nav>
				</div>



				<h3 class="page-header">
					<small>Tracks you may like</small>
				</h3>
				<div class="row placeholders">
					<div id="recommended-tracks" class="row">
						<div><p><span class="glyphicon glyphicon-tasks"></span> Loading track recommendations...</p></div>
					</div>
					<nav><ul class="pager"><li><button id="button2" type="button" class=" btn btn-default hidden-button"><span class="glyphicon glyphicon-refresh"></span> More track recommendations</button></li></ul></nav>
				</div>



			</div>

		</div>
	</div>

	<!-- Bootstrap core JavaScript
    ================================================== -->
	<!-- Placed at the end of the document so the pages load faster -->
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
	<script src="js/bootstrap.min.js"></script>

	<script src="js/myjsfunctions.js"></script>
	
	<script src="js/small_cover.js"></script>
	<script src="js/large_cover.js"></script>
	<!-- /#page-content-wrapper -->

	<!-- /#wrapper -->


</body>

</html>