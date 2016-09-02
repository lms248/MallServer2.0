<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="ThemeBucket">
    <link rel="shortcut icon" href="#" type="image/png">

    <title>Login</title>

    <link href="/res/css/style.css" rel="stylesheet">
    <link href="/res/css/style-responsive.css" rel="stylesheet">

    <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
    <script src="js/html5shiv.js"></script>
    <script src="js/respond.min.js"></script>
    <![endif]-->
</head>

<body class="login-body">

<div class="container">

    <div class="form-signin">
        <div class="form-signin-heading text-center">
            <h1 class="sign-title">沐火工作室</h1>
            <img src="/res/img/login-logo.png" alt=""/>
        </div>
        <div class="login-wrap">
            <input type="text" name="username" class="form-control" placeholder="User ID" autofocus>
            <input type="password" name="password" class="form-control" placeholder="Password">

            <button class="btn btn-lg btn-login btn-block" id="submit" type="submit">
                <i class="fa fa-check"></i>
            </button>

            <div class="registration">
                Not a member yet?
                <a class="" href="registration.html">
                    Signup
                </a>
            </div>
            <label class="checkbox">
                <input type="checkbox" value="remember-me"> Remember me
                <span class="pull-right">
                    <a data-toggle="modal" href="#myModal"> Forgot Password?</a>

                </span>
            </label>

        </div>

        <!-- Modal -->
        <div aria-hidden="true" aria-labelledby="myModalLabel" role="dialog" tabindex="-1" id="myModal" class="modal fade">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                        <h4 class="modal-title">Forgot Password ?</h4>
                    </div>
                    <div class="modal-body">
                        <p>Enter your e-mail address below to reset your password.</p>
                        <input type="text" placeholder="Email" autocomplete="off" class="form-control placeholder-no-fix">

                    </div>
                    <div class="modal-footer">
                        <button data-dismiss="modal" class="btn btn-default" type="button">Cancel</button>
                        <button class="btn btn-primary" type="button">Submit</button>
                    </div>
                </div>
            </div>
        </div>
        <!-- modal -->

    </div>

</div>



<!-- Placed js at the end of the document so the pages load faster -->

<!-- Placed js at the end of the document so the pages load faster -->
<script src="/res/js/jquery-2.2.2.min.js"></script>
<script src="/res/js/bootstrap.min.js"></script>
<script src="/res/js/modernizr.min.js"></script>

<script>
$(document).ready(function(){
	$("#submit").on("click",function(){
		login();
	});
	
	document.onkeydown = function(e){ 
	    var ev = document.all ? window.event : e;
	    if(ev.keyCode==13) {
	    	login();
	     }
	}
	
	function login(){
		var username=$("input[name='username']").val();
		var password=$("input[name='password']").val();
		$.post("/admin/user/login",{username:username,password:password},
				function(data){
			if (data==1) {
       			//$("#loginErrorTip").show();
       			alert("用户名或密码错误");
       		}
       		else if (data==0) {
       			self.location="/admin";
       		}
		});
	}
});
</script>
</body>
</html>
