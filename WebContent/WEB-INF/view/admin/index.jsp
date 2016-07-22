<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
  <meta name="keywords" content="admin, dashboard, bootstrap, template, flat, modern, theme, responsive, fluid, retina, backend, html5, css, css3">
  <meta name="description" content="">
  <meta name="author" content="ThemeBucket">
  <link rel="shortcut icon" href="#" type="image/png">

  <title>AdminEx</title>

  <!--common-->
  <link href="/res/css/style.css" rel="stylesheet">
  <link href="/res/css/style-responsive.css" rel="stylesheet">




  <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
  <!--[if lt IE 9]>
  <script src="js/html5shiv.js"></script>
  <script src="js/respond.min.js"></script>
  <![endif]-->
</head>

<body class="sticky-header">

<section>
    <!-- left side start-->
    <div class="left-side sticky-left-side">

        <!--logo and iconic logo start-->
        <div class="logo">
            <a href="/admin"><img src="/res/img/logo.png" alt=""></a>
        </div>

        <div class="logo-icon text-center">
            <a href="/admin"><img src="/res/img/logo_icon.png" alt=""></a>
        </div>
        <!--logo and iconic logo end-->

        <div class="left-side-inner">

            <!-- visible to small devices only -->
            <div class="visible-xs hidden-sm hidden-md hidden-lg">
                <div class="media logged-user">
                    <img alt="" src="/res/img/user-avatar.png" class="media-object">
                    <div class="media-body">
                        <h4><a href="#">John Doe</a></h4>
                        <span>"Hello There..."</span>
                    </div>
                </div>

                <h5 class="left-nav-title">Account Information</h5>
                <ul class="nav nav-pills nav-stacked custom-nav">
                  <li><a href="#"><i class="fa fa-user"></i> <span>Profile</span></a></li>
                  <li><a href="#"><i class="fa fa-cog"></i> <span>Settings</span></a></li>
                  <li><a href="#"><i class="fa fa-sign-out"></i> <span>Sign Out</span></a></li>
                </ul>
            </div>

            <!--sidebar nav start-->
            <ul id="sidebar" class="nav nav-pills nav-stacked custom-nav">
                <li class="active"><a href="/admin"><i class="fa fa-home"></i> <span>Home</span></a></li>
                <li><a href="#" uri="authority"><i class="fa fa-cogs"></i> <span>权限管理</span></a></li>
                <li class="menu-list"><a href=""><i class="fa fa-laptop"></i> <span>Layouts</span></a>
                    <ul class="sub-menu-list">
                        <li><a href="#" uri="authority"> Blank Page</a></li>
                        <li><a href="#"> Boxed Page</a></li>
                        <li><a href="#"> Sidebar Collapsed</a></li>
                        <li><a href="#"> Horizontal Menu</a></li>

                    </ul>
                </li>
                <li class="menu-list"><a href=""><i class="fa fa-book"></i> <span>UI Elements</span></a>
                    <ul class="sub-menu-list">
                        <li><a href="#"> General</a></li>
                        <li><a href="#"> Buttons</a></li>
                        <li><a href="#"> Tabs & Accordions</a></li>
                        <li><a href="#"> Typography</a></li>
                        <li><a href="#"> Slider</a></li>
                        <li><a href="#"> Panels</a></li>
                    </ul>
                </li>
                <li class="menu-list"><a href=""><i class="fa fa-envelope"></i> <span>Mail</span></a>
                    <ul class="sub-menu-list">
                        <li><a href="#"> Inbox</a></li>
                        <li><a href="#"> Compose Mail</a></li>
                        <li><a href="#"> View Mail</a></li>
                    </ul>
                </li>
                <li class="menu-list"><a href=""><i class="fa fa-tasks"></i> <span>Forms</span></a>
                    <ul class="sub-menu-list">
                        <li><a href="#"> Form Layouts</a></li>
                        <li><a href="#"> Advanced Components</a></li>
                        <li><a href="#"> Form Wizards</a></li>
                        <li><a href="#"> Form Validation</a></li>
                        <li><a href="#"> Editors</a></li>
                        <li><a href="#"> Inline Editors</a></li>
                        <li><a href="#"> Pickers</a></li>
                        <li><a href="#"> Dropzone</a></li>
                        <li><a href="#"> More</a></li>
                    </ul>
                </li>
                <li class="menu-list"><a href=""><i class="fa fa-bar-chart-o"></i> <span>Charts</span></a>
                    <ul class="sub-menu-list">
                        <li><a href="#"> Flot Charts</a></li>
                        <li><a href="#"> Morris Charts</a></li>
                        <li><a href="#"> Chartjs</a></li>
                        <li><a href="#"> C3 Charts</a></li>
                    </ul>
                </li>
                <li class="menu-list"><a href="#"><i class="fa fa-th-list"></i> <span>Data Tables</span></a>
                    <ul class="sub-menu-list">
                        <li><a href="#"> Basic Table</a></li>
                        <li><a href="#"> Advanced Table</a></li>
                        <li><a href="#"> Responsive Table</a></li>
                        <li><a href="#"> Edit Table</a></li>
                    </ul>
                </li>
                <li class="menu-list"><a href=""><i class="fa fa-file-text"></i> <span>Extra Pages</span></a>
                    <ul class="sub-menu-list">
                        <li><a href="#"> 404 Error</a></li>
                        <li><a href="#"> 500 Error</a></li>
                    </ul>
                </li>
                <li><a href="/admin/login"><i class="fa fa-sign-in"></i> <span>Login Page</span></a></li>

            </ul>
            <!--sidebar nav end-->

        </div>
    </div>
    <!-- left side end-->
    
    <!-- main content start-->
    <div class="main-content" >

        <!-- header section start-->
        <div class="header-section">

            <!--toggle button start-->
            <a class="toggle-btn"><i class="fa fa-bars"></i></a>
            <!--toggle button end-->

            <!--search start-->
            <!-- <form class="searchform" action="index.html" method="post">
                <input type="text" class="form-control" name="keyword" placeholder="Search here..." />
            </form> -->
            <!--search end-->

            <!--notification menu start -->
            <div class="menu-right">
                <ul class="notification-menu">
                    <li>
                        <a href="#" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                            <img src="/res/img/user-avatar.png" alt="" />
                            John Doe
                            <span class="caret"></span>
                        </a>
                        <ul class="dropdown-menu dropdown-menu-usermenu pull-right">
                            <li><a href="#"><i class="fa fa-user"></i>  Profile</a></li>
                            <li><a href="#"><i class="fa fa-cog"></i>  Settings</a></li>
                            <li><a href="#"><i class="fa fa-sign-out"></i> Log Out</a></li>
                        </ul>
                    </li>

                </ul>
            </div>
            <!--notification menu end -->

        </div>
        <!-- header section end-->

        <!-- page heading start-->
        <div class="page-heading">
            <h3>
                Home 主页
            </h3>
            <ul class="breadcrumb">
                <li>
                    <a href="#">Home</a>
                </li>
                <li class="active"> My Home </li>
            </ul>
            <div class="state-info">
                <section class="panel">
                    <div class="panel-body">
                        <div class="summary">
                            <span>yearly expense</span>
                            <h3 class="red-txt">$ 45,600</h3>
                        </div>
                        <div id="income" class="chart-bar"></div>
                    </div>
                </section>
                <section class="panel">
                    <div class="panel-body">
                        <div class="summary">
                            <span>yearly  income</span>
                            <h3 class="green-txt">$ 45,600</h3>
                        </div>
                        <div id="expense" class="chart-bar"></div>
                    </div>
                </section>
            </div>
        </div>
        <!-- page heading end-->

        <!--body wrapper start-->
        <div class="wrapper"></div>
        <!--body wrapper end-->

        <!--footer section start-->
        <footer>
            2016 &copy; AdminEx by <a href="http://www.dbfor.com/" target="_blank">dbfor</a>
        </footer>
        <!--footer section end-->


    </div>
    <!-- main content end-->
</section>

<!-- Placed js at the end of the document so the pages load faster -->
<script src="/res/js/jquery-2.2.2.min.js"></script>
<script src="/res/js/jquery-ui-1.9.2.custom.min.js"></script>
<script src="/res/js/jquery-migrate-1.2.1.min.js"></script>
<script src="/res/js/bootstrap.min.js"></script>
<script src="/res/js/modernizr.min.js"></script>
<script src="/res/js/jquery.nicescroll.js"></script>

<!--common scripts for all pages-->
<script src="/res/js/common-scripts.js"></script>

</body>
</html>
