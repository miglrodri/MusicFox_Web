
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

<link
	href="//maxcdn.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css"
	rel="stylesheet">
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
				<a class="navbar-brand" href="#">MusicFox</a>

			</div>

			<!-- Collect the nav links, forms, and other content for toggling -->
			<div class="collapse navbar-collapse"
				id="bs-example-navbar-collapse-1">
				<form action="searchController" method="POST" class="navbar-form navbar-left" role="search">
					<div class="form-group">
						<input type="text" class="form-control" name="query" placeholder="Search">
					</div>
					<button type="submit" class="btn btn-default">Submit</button>
				</form>
				<ul class="nav navbar-nav navbar-right">

					<li><p class="navbar-text">by Luís Jerónimo &amp; Miguel Jesus</p></li>

				</ul>
			</div>
			<!-- /.navbar-collapse -->
		</div>
		<!-- /.container-fluid -->
	</nav>

	<div class="container-fluid">
		<div class="row">
			<div class="col-sm-3 col-md-2 sidebar">

				<h1>Genres</h1>
				<ul class="nav nav-sidebar">
					<li><a href="MusicController?genre=Blues">Blues</a></li>
					<li><a href="MusicController?genre=Country">Country</a></li>
					<li><a href="MusicController?genre=Vocal">Vocal</a></li>
					<li><a href="MusicController?genre=Electronic">Electronic</a></li>
					<!-- <li><a href="?genre=International">International</a></li> -->
					<li><a href="MusicController?genre=Jazz">Jazz</a></li>
					<li><a href="MusicController?genre=Pop">Pop</a></li>
					<li><a href="MusicController?genre=Rock">Rock</a></li>
					<li><a href="MusicController?genre=RnB">RnB</a></li>
					<li><a href="MusicController?genre=Rap">Rap</a></li>
					<li><a href="MusicController?genre=Reggae">Reggae</a></li>
				</ul>

				<h1>Decades</h1>
				<ul class="nav nav-sidebar">
					<li><a href="MusicController?decade=1970">1970</a></li>
					<li><a href="MusicController?decade=1980">1980</a></li>
					<li><a href="MusicController?decade=1990">1990</a></li>
					<li><a href="MusicController?decade=2000">2000</a></li>
					<li><a href="MusicController?decade=2010">2010</a></li>
				</ul>

			</div>



			<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
				<h1 class="page-header">
					option:
					<jsp:getProperty property="option" name="mybean" />(<jsp:getProperty
						property="numberItems" name="mybean" />)
				</h1>
				<div class="row placeholders">
					<div class="row">


						<%
						
							System.out.println(mybean.getPageType());
							// ve se é um artist
							if (mybean.getPageType()!=null && mybean.getPageType().equals("artist_page")) {
								Artist temp_artist = mybean.getArtistsArray().get(0);

								String temp_artistname = temp_artist.getName();								
								String temp_artistgenre =  temp_artist.getMainGenre();
								String temp_artistdecade = temp_artist.getDecade();
								String temp_artistgender = temp_artist.getGender();
								String temp_artistvevourl = temp_artist.getVevoUrl();
								int temp_artistvevolm = temp_artist.getVevoViewsLastMonth();
								int temp_artistvevotviews = temp_artist.getVevoViewsTotal();
								String temp_artistturl = temp_artist.getTwitterUrl();
								int temp_artisttfoll = temp_artist.getTwitterFollowers();
								String temp_artistfburl = temp_artist.getFacebookUrl();
								int temp_artistfbpta = temp_artist.getFacebookPeopleTalkingAbout();
								int temp_artistfblikes = temp_artist.getFacebookLikes();
								
								
						%>
						<h1> Artist name:
							<%=temp_artistname%></h1>
							
							
							<table class="table table-hover table-bordered">
							<tr><td><b>Genre</b></td><td><%= temp_artistgenre%></td></tr>
							<tr><td><b>Decade</b></td><td><%= temp_artistdecade%></td></tr>
							<tr><td><b>Gender</b></td><td><%= temp_artistgender%></td></tr>
							<tr><td><b>Vevo url</b></td><td><a href="<%= temp_artistvevourl%>"><%= temp_artistvevourl%></a></td></tr>
							<tr><td><b>Vevo total views</b></td><td><%= temp_artistvevotviews%></td></tr>
							<tr><td><b>Vevo last month views</b></td><td><%= temp_artistvevolm%></td></tr>
							<tr><td><b>Twitter url</b></td><td><a href="<%= temp_artistturl%>"><%= temp_artistturl%></a></td></tr>
							<tr><td><b>Twitter followers</b></td><td><%= temp_artisttfoll %></td></tr>
							<tr><td><b>Facebook url</b></td><td><a href="<%= temp_artistfburl%>"><%= temp_artistfburl%></a></td></tr>
							<tr><td><b>Facebook people talking about</b></td><td><%= temp_artistfbpta %></td></tr>
							<tr><td><b>Facebook likes</b></td><td><%= temp_artistfblikes %></td></tr>
							
							
							
							
								
							
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
								<td><a href="MusicController<%="?albumid=" + temp_albumid%>"
									class="btn btn-default btn-lg" role="button">More info</a></td>
							</tr>
							<%
								}
							%>

						</table>
						
						<%
							}
							// ve se é um album
							else if (mybean.getPageType()!=null && mybean.getPageType().equals("album_page")) {
								Album temp_album = mybean.getAlbum_information();

								String temp_albumtitle = temp_album.getTitle();
								String temp_albumid = temp_album.getId();
								String temp_albumrelease = temp_album.getReleaseDate();
								int temp_albumntracks = temp_album.getNumberOfTracks();
								String temp_albumdecade = temp_album.getDecade();
								
						%>
						<h1>
							Album name:
							<%=temp_albumtitle%></h1>
							
							<table class="table table-hover table-bordered">
							<tr>
								<th>Release Date</th>
								<th>Decade</th>
								<th>Number of Tracks</th>
							</tr>
							<tr>
								<td><%= temp_albumrelease%></td>
								<td><%= temp_albumdecade%></td>
								<td><%= temp_albumntracks%></td>
							</tr>
							
							</table>
							
							
						<table class="table table-hover table-bordered">
							<tr>
								<th>Index</th>
								<th>Track title</th>
								<th>Duration</th>
							</tr>

							<%
								ArrayList<Track> temp_track_list = new ArrayList<Track>();
									temp_track_list = temp_album.getTracksArray();
									for (int i = 0; i < temp_track_list.size(); i++) {
										String temp_trackid = temp_track_list.get(i).getId();
										String temp_tracktitle = temp_track_list.get(i).getTitle();
										String temp_trackindex = temp_track_list.get(i).getTrackIndex();
										String tmep_trackduration = temp_track_list.get(i).getDuration();
										
							%>
							<tr>
								<td><%=temp_trackindex%></td>
								<td><%=temp_tracktitle%></td>
								<td><%=tmep_trackduration%></td>
								
							</tr>
							<%
								}
							%>

						</table>
						<%
							}
							// ve se é genre ou decade e faz listagem
							else if (mybean.getPageType()!=null && mybean.getPageType().equals("artist_list_page")) {
								if (mybean.getNumberItems() > 0) {
									ArrayList<Artist> temp_array = mybean.getArtistsArray();
									for (int i = 0; i < temp_array.size(); i++) {
										String artist_id = temp_array.get(i).getId();
										String artist_name = temp_array.get(i).getName();
										String artist_cover = temp_array.get(i).getCoverUrl();

						%>

						<div class="col-xs-6 col-md-3 thumbnail">
							<a href="#"> <img src="<%=artist_cover%>" style="height: 150px" alt="cenas">
							</a>
							<div class="caption">
								<h3><%=artist_name%>
								</h3>
								<p><%=artist_id%></p>
								<p>
									<a href="MusicController<%="?artistid=" + artist_id%>" class="btn btn-primary"
										role="button">More info</a>
								</p>
							</div>
						</div>
						<%
							}
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



				<h1 class="page-header">
					<small>Artists you may like</small>
				</h1>
				<div class="row placeholders">
					<div class="row">
						<div class="col-xs-6 col-md-3 thumbnail">
							<a href="#"> <img src="images/cenas.png" alt="cenas">
							</a>
							<div class="caption">
								<h3>Thumbnail label</h3>
								<p>asd asd asd asd asd asd asd asd asd</p>
								<p>
									<a href="#" class="btn btn-primary" role="button">Button</a> <a
										href="#" class="btn btn-default" role="button">Button</a>
								</p>
							</div>
						</div>
						<div class="col-xs-6 col-md-3 thumbnail">
							<a href="#"> <img src="images/cenas.png" alt="cenas">
							</a>
							<div class="caption">
								<h3>Thumbnail label</h3>
								<p>asd asd asd asd asd asd asd asd asd</p>
								<p>
									<a href="#" class="btn btn-primary" role="button">Button</a> <a
										href="#" class="btn btn-default" role="button">Button</a>
								</p>
							</div>
						</div>
						<div class="col-xs-6 col-md-3 thumbnail">
							<a href="#"> <img src="images/cenas.png" alt="cenas">
							</a>
							<div class="caption">
								<h3>Thumbnail label</h3>
								<p>asd asd asd asd asd asd asd asd asd</p>
								<p>
									<a href="#" class="btn btn-primary" role="button">Button</a> <a
										href="#" class="btn btn-default" role="button">Button</a>
								</p>
							</div>
						</div>
						<div class="col-xs-6 col-md-3 thumbnail">
							<a href="#"> <img src="images/cenas.png" alt="cenas">
							</a>
							<div class="caption">
								<h3>Thumbnail label</h3>
								<p>asd asd asd asd asd asd asd asd asd</p>
								<p>
									<a href="#" class="btn btn-primary" role="button">Button</a> <a
										href="#" class="btn btn-default" role="button">Button</a>
								</p>
							</div>
						</div>
					</div>
				</div>



				<h1 class="page-header">
					<small>Tracks you may like</small>
				</h1>
				<div class="row placeholders">
					<div class="row">
						<div class="col-xs-6 col-md-3 thumbnail">
							<a href="#"> <img src="images/cenas.png" alt="cenas">
							</a>
							<div class="caption">
								<h3>Thumbnail label</h3>
								<p>asd asd asd asd asd asd asd asd asd</p>
								<p>
									<a href="#" class="btn btn-primary" role="button">Button</a> <a
										href="#" class="btn btn-default" role="button">Button</a>
								</p>
							</div>
						</div>
						<div class="col-xs-6 col-md-3 thumbnail">
							<a href="#"> <img src="images/cenas.png" alt="cenas">
							</a>
							<div class="caption">
								<h3>Thumbnail label</h3>
								<p>asd asd asd asd asd asd asd asd asd</p>
								<p>
									<a href="#" class="btn btn-primary" role="button">Button</a> <a
										href="#" class="btn btn-default" role="button">Button</a>
								</p>
							</div>
						</div>
						<div class="col-xs-6 col-md-3 thumbnail">
							<a href="#"> <img src="images/cenas.png" alt="cenas">
							</a>
							<div class="caption">
								<h3>Thumbnail label</h3>
								<p>asd asd asd asd asd asd asd asd asd</p>
								<p>
									<a href="#" class="btn btn-primary" role="button">Button</a> <a
										href="#" class="btn btn-default" role="button">Button</a>
								</p>
							</div>
						</div>
						<div class="col-xs-6 col-md-3 thumbnail">
							<a href="#"> <img src="images/cenas.png" alt="cenas">
							</a>
							<div class="caption">
								<h3>Thumbnail label</h3>
								<p>asd asd asd asd asd asd asd asd asd</p>
								<p>
									<a href="#" class="btn btn-primary" role="button">Button</a> <a
										href="#" class="btn btn-default" role="button">Button</a>
								</p>
	
	
							</div>
						</div>
					</div>
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

			<!-- /#page-content-wrapper -->

		<!-- /#wrapper -->


</body>

</html>

