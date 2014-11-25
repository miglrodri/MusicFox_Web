<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="ISO-8859-1"%>
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

    <!-- Just for debugging purposes. Don't actually copy these 2 lines! -->
    <!--[if lt IE 9]><script src="../../assets/js/ie8-responsive-file-warning.js"></script><![endif]-->
    <script src="js/ie-emulation-modes-warning.js"></script>

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>

<body>

    <nav class="navbar navbar-default navbar-fixed-top" role="navigation">
        <div class="container-fluid">
            <!-- Brand and toggle get grouped for better mobile display -->
            <div class="navbar-header">
                <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="#">MusicFox</a>
            </div>

            <!-- Collect the nav links, forms, and other content for toggling -->
            <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                <form class="navbar-form navbar-left" role="search">
                    <div class="form-group">
                        <input type="text" class="form-control" placeholder="Search">
                    </div>
                    <button type="submit" class="btn btn-default">Submit</button>
                </form>
                <ul class="nav navbar-nav navbar-right">
                    <p class="navbar-text">by Luís Jerónimo & Miguel Jesus</p>
                </ul>
            </div>
            <!-- /.navbar-collapse -->
        </div>
        <!-- /.container-fluid -->
    </nav>

    <div class="container-fluid">
        <div class="row">
            <div class="col-sm-3 col-md-2 sidebar">
                <ul class="nav nav-sidebar">
                    <li class="active"><a href="?genre=Blues">Blues</a>
                    </li>
                    <li><a href="?genre=Country">Country</a>
                    </li>
                    <li><a href="?genre=Vocal">Vocal</a>
                    </li>
                    <li><a href="?genre=Electronic">Electronic</a>
                    </li>
                    <li><a href="?genre=International">International</a>
                    </li>
                    <li><a href="?genre=Jazz">Jazz</a>
                    </li>
                    <li><a href="?genre=Pop">Pop</a>
                    </li>
                    <li><a href="?genre=Rock">Rock</a>
                    </li>
                    <li><a href="?genre=R&B">R&B</a>
                    </li>
                    <li><a href="?genre=Rap">Rap</a>
                    </li>
                    <li><a href="?genre=Reggae">Reggae</a>
                    </li>
                </ul>
                
            </div>



            <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
                <h1 class="page-header">option: <jsp:getProperty property="option" name="mybean"/></h1>
                <div class="row placeholders">
                    <div class="row">
                        <div class="col-xs-6 col-md-3 thumbnail">
                            <a href="#">
                                <img src="images/cenas.png" alt="cenas">
                            </a>
                            <div class="caption">
                                <h3>Thumbnail label</h3>
                                <p>asd asd asd asd asd asd asd asd asd</p>
                                <p><a href="#" class="btn btn-primary" role="button">Button</a>  <a href="#" class="btn btn-default" role="button">Button</a>
                                </p>
                            </div>
                        </div>
                        <div class="col-xs-6 col-md-3 thumbnail">
                            <a href="#">
                                <img src="images/cenas.png" alt="cenas">
                            </a>
                            <div class="caption">
                                <h3>Thumbnail label</h3>
                                <p>asd asd asd asd asd asd asd asd asd</p>
                                <p><a href="#" class="btn btn-primary" role="button">Button</a>  <a href="#" class="btn btn-default" role="button">Button</a>
                                </p>
                            </div>
                        </div>
                        <div class="col-xs-6 col-md-3 thumbnail">
                            <a href="#">
                                <img src="images/cenas.png" alt="cenas">
                            </a>
                            <div class="caption">
                                <h3>Thumbnail label</h3>
                                <p>asd asd asd asd asd asd asd asd asd</p>
                                <p><a href="#" class="btn btn-primary" role="button">Button</a>  <a href="#" class="btn btn-default" role="button">Button</a>
                                </p>
                            </div>
                        </div>
                        <div class="col-xs-6 col-md-3 thumbnail">
                            <a href="#">
                                <img src="images/cenas.png" alt="cenas">
                            </a>
                            <div class="caption">
                                <h3>Thumbnail label</h3>
                                <p>asd asd asd asd asd asd asd asd asd</p>
                                <p><a href="#" class="btn btn-primary" role="button">Button</a>  <a href="#" class="btn btn-default" role="button">Button</a>
                                </p>
                            </div>
                        </div>
                    </div>
                </div>



                <h1 class="page-header"><small>Artists you may like</small></h1>
                <div class="row placeholders">
                    <div class="row">
                        <div class="col-xs-6 col-md-3 thumbnail">
                            <a href="#">
                                <img src="images/cenas.png" alt="cenas">
                            </a>
                            <div class="caption">
                                <h3>Thumbnail label</h3>
                                <p>asd asd asd asd asd asd asd asd asd</p>
                                <p><a href="#" class="btn btn-primary" role="button">Button</a>  <a href="#" class="btn btn-default" role="button">Button</a>
                                </p>
                            </div>
                        </div>
                        <div class="col-xs-6 col-md-3 thumbnail">
                            <a href="#">
                                <img src="images/cenas.png" alt="cenas">
                            </a>
                            <div class="caption">
                                <h3>Thumbnail label</h3>
                                <p>asd asd asd asd asd asd asd asd asd</p>
                                <p><a href="#" class="btn btn-primary" role="button">Button</a>  <a href="#" class="btn btn-default" role="button">Button</a>
                                </p>
                            </div>
                        </div>
                        <div class="col-xs-6 col-md-3 thumbnail">
                            <a href="#">
                                <img src="images/cenas.png" alt="cenas">
                            </a>
                            <div class="caption">
                                <h3>Thumbnail label</h3>
                                <p>asd asd asd asd asd asd asd asd asd</p>
                                <p><a href="#" class="btn btn-primary" role="button">Button</a>  <a href="#" class="btn btn-default" role="button">Button</a>
                                </p>
                            </div>
                        </div>
                        <div class="col-xs-6 col-md-3 thumbnail">
                            <a href="#">
                                <img src="images/cenas.png" alt="cenas">
                            </a>
                            <div class="caption">
                                <h3>Thumbnail label</h3>
                                <p>asd asd asd asd asd asd asd asd asd</p>
                                <p><a href="#" class="btn btn-primary" role="button">Button</a>  <a href="#" class="btn btn-default" role="button">Button</a>
                                </p>
                            </div>
                        </div>
                    </div>
                </div>



                <h1 class="page-header"><small>Tracks you may like</small></h1>
                <div class="row placeholders">
                    <div class="row">
                        <div class="col-xs-6 col-md-3 thumbnail">
                            <a href="#">
                                <img src="images/cenas.png" alt="cenas">
                            </a>
                            <div class="caption">
                                <h3>Thumbnail label</h3>
                                <p>asd asd asd asd asd asd asd asd asd</p>
                                <p><a href="#" class="btn btn-primary" role="button">Button</a>  <a href="#" class="btn btn-default" role="button">Button</a>
                                </p>
                            </div>
                        </div>
                        <div class="col-xs-6 col-md-3 thumbnail">
                            <a href="#">
                                <img src="images/cenas.png" alt="cenas">
                            </a>
                            <div class="caption">
                                <h3>Thumbnail label</h3>
                                <p>asd asd asd asd asd asd asd asd asd</p>
                                <p><a href="#" class="btn btn-primary" role="button">Button</a>  <a href="#" class="btn btn-default" role="button">Button</a>
                                </p>
                            </div>
                        </div>
                        <div class="col-xs-6 col-md-3 thumbnail">
                            <a href="#">
                                <img src="images/cenas.png" alt="cenas">
                            </a>
                            <div class="caption">
                                <h3>Thumbnail label</h3>
                                <p>asd asd asd asd asd asd asd asd asd</p>
                                <p><a href="#" class="btn btn-primary" role="button">Button</a>  <a href="#" class="btn btn-default" role="button">Button</a>
                                </p>
                            </div>
                        </div>
                        <div class="col-xs-6 col-md-3 thumbnail">
                            <a href="#">
                                <img src="images/cenas.png" alt="cenas">
                            </a>
                            <div class="caption">
                                <h3>Thumbnail label</h3>
                                <p>asd asd asd asd asd asd asd asd asd</p>
                                <p><a href="#" class="btn btn-primary" role="button">Button</a>  <a href="#" class="btn btn-default" role="button">Button</a>
                                </p>
                            </div>
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
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
</body>

</html>
