<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!-- page start-->	
<div class="row">
	<div class="col-lg-12">
		<section class="panel">
			<header class="panel-heading"> Dynamic Table </header>
			<table class="table table-striped border-top" id="sample_1">
				<thead>
					<tr>
						<th style="width: 8px;"><input type="checkbox"
							class="group-checkable" data-set="#sample_1 .checkboxes" /></th>
						<th>Username</th>
						<th class="hidden-phone">Email</th>
						<th class="hidden-phone">Points</th>
						<th class="hidden-phone">Joined</th>
						<th class="hidden-phone"></th>
					</tr>
				</thead>
				<tbody id="newsdb"> </tbody>
			</table>
		</section>
	</div>
</div>
<!-- page end-->
    
<script id="theTmpl" type="text/x-jsrender">
<tr class="odd gradeX">
	<td><input type="checkbox" class="checkboxes" value="1" /></td>
	<td>{{:username}}</td>
	<td class="hidden-phone">
		<a href="#">{{:email}}</a>
	</td>
	<td class="hidden-phone">{{:points}}</td>
	<td class="center hidden-phone">{{:time}}</td>
	<td class="hidden-phone">
		<span class="label label-success">Approved</span>
	</td>
</tr>
</script>

<script>
var data = [
  {
    "username": "Robert",
    "email": "Bob1",
    "points": "Bob",
    "time": "Bob",
  },
  {
	"username": "Robert",
	"email": "Bob2",
	"points": "Bob",
	"time": "Bob",
  },
  {
	"username": "Robert",
	"email": "Bob",
	"points": "Bob3",
	"time": "Bob",
  },
  {
	"username": "Robert",
	"email": "Bob",
	"points": "Bob",
	"time": "Bob5",
  }
];

var template = $.templates("#theTmpl");

var htmlOutput = template.render(data);

$("#newsdb").html(htmlOutput);
</script>