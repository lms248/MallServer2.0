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

  <title>MallServer Admin</title>

  <!--common-->
  <link href="/res/css/style.css" rel="stylesheet">
  <link href="/res/css/style-responsive.css" rel="stylesheet">
  <link href="/res/css/page.css" rel="stylesheet">

  <!--dynamic table-->
  <link href="/res/AdminEx/js/advanced-datatable/css/demo_page.css" rel="stylesheet" />
  <link href="/res/AdminEx/js/advanced-datatable/css/demo_table.css" rel="stylesheet" />
  <link href="/res/AdminEx/js/data-tables/DT_bootstrap.css" rel="stylesheet" />


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

            <!--sidebar nav start-->
            <ul id="sidebar" class="nav nav-pills nav-stacked custom-nav">
                <!-- <li class="menu-1"><a href="#" uri="authority"><i class="fa fa-cogs"></i> <span>权限管理</span></a></li> -->
                <li class="menu-1 active"><a href="#" uri="user"><i class="fa fa-user"></i> <span>用户</span></a></li>
                <li class="menu-1"><a href="#" uri="shop"><i class="fa fa-th-large"></i> <span>店铺</span></a></li>
                <li class="menu-1"><a href="#" uri="goods"><i class="fa fa-tasks"></i> <span>商品</span></a></li>
                <li class="menu-1"><a href="#" uri="activity"><i class="fa fa-flag"></i> <span>活动</span></a></li>
                <!-- <li class="menu-list"><a href=""><i class="fa fa-tasks"></i> <span>商品</span></a>
                    <ul class="sub-menu-list">
                        <li><a href="#" uri="goods"> 推荐商品</a></li>
                        <li><a href="#"> 热销商品</a></li>
                        <li><a href="#"> 全部</a></li>
                    </ul>
                </li> -->
                <li class="menu-list"><a href=""><i class="fa fa-bar-chart-o"></i> <span>订单</span></a>
                    <ul class="sub-menu-list">
                        <li><a href="#"> 待付款</a></li>
                        <li><a href="#"> 待收货</a></li>
                        <li><a href="#"> 已收货</a></li>
                        <li><a href="#"> 申请退货</a></li>
                        <li><a href="#"> 全部</a></li>
                    </ul>
                </li>
                <li class="menu-1"><a href="#"><i class="fa fa-envelope"></i> <span>反馈</span></a></li>
                <li class="menu-list"><a href="#"><i class="fa fa-file-text"></i> <span>日志</span></a>
                    <ul class="sub-menu-list">
                        <li><a href="#"> 404 Error</a></li>
                        <li><a href="#"> 500 Error</a></li>
                    </ul>
                </li>

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
            <form class="searchform" action="index.html" method="post">
                <input type="text" class="form-control" name="keyword" placeholder="Search here..." />
            </form>
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
                            <li><a href="#"><i class="fa fa-user"></i> <span>个人中心</span></a></li>
                  			<li><a href="#"><i class="fa fa-cog"></i> <span>设置</span></a></li>
                  			<li><a href="#"><i class="fa fa-sign-out"></i> <span>注销登录</span></a></li>
                        </ul>
                    </li>

                </ul>
            </div>
            <!--notification menu end -->

        </div>
        <!-- header section end-->

        <!-- page heading start-->
        <div class="page-heading" style="display: none;">
            <h3>
                Home 主页
            </h3>
            <ul class="breadcrumb">
                <li>
                    <a href="#">Home</a>
                </li>
                <li class="active"> My Home </li>
            </ul>
            <div class="state-info" style="display: none;">
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
        <footer style="display: none;">
            2016 &copy; Admin by <a href="http://www.dbfor.com/" target="_blank">dbfor</a>
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
<script src="/res/js/jquery.isotope.js"></script>
<script src="/res/js/jsrender.min.js"></script>

<!--common scripts for all pages-->
<script src="/res/js/common-scripts.js"></script>

<!--dynamic table-->
<script src="/res/AdminEx/js/advanced-datatable/js/jquery.dataTables.js"></script>
<script src="/res/AdminEx/js/data-tables/DT_bootstrap.js"></script>
<!--dynamic table initialization -->
<script src="/res/AdminEx/js/dynamic_table_init.js"></script>

<script>
$(".wrapper").load("admin/user",{});
</script>

</body>
</html>
