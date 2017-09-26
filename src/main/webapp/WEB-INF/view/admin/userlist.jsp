<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户列表</title>
<link href="../static/css/bootstrap.css" rel='stylesheet'
	type='text/css' />

</head>
<body>
	<div class="panel panel-success table-responsive">
		<div class="panel-heading" style="height: 40px;">
			<div class="pull-left">用户列表</div>
			<div class="pull-right">
				<label>欢迎用户！${user.username }</label>
				<a href="/wonders/login/loginout">用户注销</a>
			</div>
		</div>
		<div class="panel-body table-responsive">
			<table class="table table-hover">
				<thead>
					<tr>
						<th>编号</th>
						<th>用户名</th>
						<th>用户类型</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody id="tbody">

				</tbody>
			</table>
		</div>
	</div>
	<div id="uinfo" class="modal fade" tabindex="-1" role="dialog">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">修改用户</h4>
				</div>
				<div class="modal-body">

					<form class="form-horizontal" id="updateform">
						<div class="form-group">
							<label for="inputEmail3" class="col-sm-2 control-label">用户名</label>
							<div class="col-sm-10">
							<input type="hidden" name="id" id="uid">
								<input type="email" class="form-control" name="username" id="username"
									placeholder="username">
							</div>
						</div>
						<div class="form-group">
						<label for="inputEmail3" class="col-sm-2 control-label">用户类型</label>
						<select id="usertype" name="usertype" class="form-control" style="width: 80%;margin-left: 10px;">
							<option value="0">普通用户</option>
							<option value="1">管理员</option>
						</select>
						</div>
					</form>





				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" onclick="qrupdate()" class="btn btn-primary">确认修改</button>
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal-dialog -->
	</div>
	<!-- /.modal -->
	<script src="../static/js/jquery.min.js"></script>
	<script src="../static/js/bootstrap.min.js"></script>
	<script type="text/javascript">
		$(function() {
			$
					.ajax({
						url : "/wonders/user/userlist",
						data : {},
						type : "GET",
						success : function(data) {
							var str = "";
							$
									.each(
											data.data,
											function(index, v) {
												str += "<tr>"
														+ "<td>"
														+ (index + 1)
														+ "</td>"
														+ "<td>"
														+ v.username
														+ "</td>"
														+ "<td>"
														+ (v.usertype == "1" ? "管理员"
																: "普通用户")
														+ "</td>"
														+ "<td>"
														+ "<button type='button' class='btn btn-danger' onclick=\"del(\'"
														+ v.id
														+ "\')\">删除</button>&nbsp;"
														+ "<button type='button' class='btn btn-success' onclick=\"updateuser(\'"
														+ v.id
														+ "\')\">修改用户</button>&nbsp;"
														+ "<button type='button' class='btn btn-info' onclick=\"resetpassword(\'"
														+ v.id
														+ "\')\">重置密码</button>"
														+ "</td>" + "</tr>"
											});
							$("#tbody").html(str);
						},
						error : function(data) {
							console.log(data);
						}
					});
		});
		function del(id) {
			if (confirm("是否要删除该用户？")) {
				$.post("/wonders/user/delbyid", {
					"id" : id
				}, function(data) {
					if (data.data == "success") {
						alert("删除成功");
						location.reload();
					} else {
						alert("删除失败,请检查代码");
					}
				});
			}
		}

		function resetpassword(id) {
			if (confirm("是否要该重置用户的密码（123456）？")) {
				$.post("/wonders/user/resetpasswordbyid", {
					"id" : id
				}, function(data) {
					if (data.data == "success") {
						alert("重置成功");
						location.reload();
					} else {
						alert("重置失败");
					}
				});
			}
		}
		
		function updateuser(id){
			$.get("/wonders/user/userinfo", {
				"id" : id
			}, function(data) {
				$("#uid").val(id);
				$("#username").val(data.data.username);
				$("#usertype option[value='"+data.data.usertype+"']").attr("selected",true);
			});
			$("#uinfo").modal("show");
		}
		
		function qrupdate(){
				$.post("/wonders/user/resetpasswordbyid", $("#updateform").serialize(), function(data) {
					if (data.data == "success") {
						alert("修改成功");
						location.reload();
					} else {
						alert("修改失败");
					}
				});
		}
	</script>
</body>
</html>