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

				<a href="#menu-toggle" class="navbar-brand" id="menu-toggle"><i
					class="fa fa-ellipsis-v"></i> MusicFox</a>
			</div>

			<!-- Collect the nav links, forms, and other content for toggling -->
			<div class="collapse navbar-collapse"
				id="bs-example-navbar-collapse-1">
				<form class="navbar-form navbar-left" role="search">
					<div class="form-group">
						<input type="text" class="form-control" placeholder="Search">
					</div>
					<button type="submit" class="btn btn-default">Submit</button>
				</form>
				<ul class="nav navbar-nav navbar-right">
					<p class="navbar-text">by Luís Jerónimo &amp; Miguel Jesus</p>
				</ul>
			</div>
			<!-- /.navbar-collapse -->
		</div>
		<!-- /.container-fluid -->
	</nav>

	<div id="wrapper">

		<!-- Sidebar -->
		<div id="sidebar-wrapper">
			<ul class="sidebar-nav">
				<li class="sidebar-brand"><a href="#">Genre </a></li>
				<li><a href="?genre=Blues">Blues</a></li>
				<li><a href="?genre=Country">Country</a></li>
				<li><a href="?genre=Vocal">Vocal</a></li>
				<li><a href="?genre=Electronic">Electronic</a></li>
				<li><a href="?genre=Jazz">Jazz</a></li>
				<li><a href="?genre=Pop">Pop</a></li>
				<li><a href="?genre=Rock">Rock</a></li>
				<li><a href="?genre=R%26B">R&amp;B</a></li>
				<li><a href="?genre=Rap">Rap</a></li>
				<li><a href="?genre=Reggae">Reggae</a></li>
				<li class="sidebar-brand"><a href="#">Decades </a></li>
				<li><a href="#">1970's</a></li>
				<li><a href="#">1980's</a></li>
				<li><a href="#">1990's</a></li>
				<li><a href="#">2000's</a></li>
				<li><a href="#">2010's</a></li>
			</ul>
		</div>
		<!-- Page Content -->
		<div id="page-content-wrapper">
			<div class="container-fluid">
				<div class="row">
					<div class="col-lg-12">
						<h1>
							option:
							<jsp:getProperty property="option" name="mybean" /></h1>
						<!--RESULTS-->
						<div class="row placeholders">
							<div class="row">
								<c:forEach items="${mybean.resultsMap}" var="entry">
									<div class="col-xs-6 col-md-3 thumbnail">
										<a href="?artist_id=${fn:replace(entry.key,'#','%23')}" > <img src="images/cenas.png"
											alt="cenas" /></a>
										<div class="caption">
											<br>
											<h3>${fn:substringBefore(entry.value,"^^")}</h3>
											<!--p>asd asd asd asd asd asd asd asd asd</p-->
											<p>
												<a href="#" class="btn btn-primary" role="button">Button</a>
												<a href="#" class="btn btn-default" role="button">Button</a>
											</p>
										</div>
									</div>
								</c:forEach>
							</div>
							<h1>
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
												<a href="#" class="btn btn-primary" role="button">Button</a>
												<a href="#" class="btn btn-default" role="button">Button</a>
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
												<a href="#" class="btn btn-primary" role="button">Button</a>
												<a href="#" class="btn btn-default" role="button">Button</a>
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
												<a href="#" class="btn btn-primary" role="button">Button</a>
												<a href="#" class="btn btn-default" role="button">Button</a>
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
												<a href="#" class="btn btn-primary" role="button">Button</a>
												<a href="#" class="btn btn-default" role="button">Button</a>
											</p>
										</div>
									</div>
								</div>
							</div>

							<h1>
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
												<a href="#" class="btn btn-primary" role="button">Button</a>
												<a href="#" class="btn btn-default" role="button">Button</a>
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
												<a href="#" class="btn btn-primary" role="button">Button</a>
												<a href="#" class="btn btn-default" role="button">Button</a>
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
												<a href="#" class="btn btn-primary" role="button">Button</a>
												<a href="#" class="btn btn-default" role="button">Button</a>
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
												<a href="#" class="btn btn-primary" role="button">Button</a>
												<a href="#" class="btn btn-default" role="button">Button</a>
											</p>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<!-- /#page-content-wrapper -->

		</div>
		<!-- /#wrapper -->

		<!-- jQuery -->
		<script src="js/jquery.js"></script>


		<!-- Menu Toggle Script -->
		<script>
			$("#menu-toggle").click(function(e) {
				e.preventDefault();
				$("#wrapper").toggleClass("toggled");
			});
		</script>
</body>

</html>
