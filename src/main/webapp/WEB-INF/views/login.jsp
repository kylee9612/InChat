<%--
  Created by IntelliJ IDEA.
  User: iiii4
  Date: 2022-07-21
  Time: 오후 10:54
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="../fix/header.jsp"%>
<head>
    <link rel = 'stylesheet' href = 'css/login.css'>
</head>
<div class = "wrap">
    <div class = "contents">
        <form method="post" id = "login_form" onsubmit="return false;">
            <input id ="id_input" type ="text" placeholder="id" name ="id" required><br>
            <input id ="pw_input" type ="password" placeholder="pw" name ="pw" required><br>
            <input type = "submit">
        </form>
    </div>
</div>
<script>
    let login_form = $("#login_form");

    login_form.submit(()=>{
        let user = {
            "id" : $("#id_input").val(),
            "pw" : $("#pw_input").val(),
        }
        loginAction(user);
    });

    function loginAction(user){
        alert(user.id);
        jQuery.ajax({
            type: "POST",
            url: "/v1/login-user",
            contentType: 'application/json',
            data: JSON.stringify(user),
            dataType: "JSON",
            success: function (e) {
                if(e!==null){
                    sessionStorage.setItem("id",e.id)
                    sessionStorage.setItem("nick",e.nick)
                    alert("Success!")
                    location.href = "/index"
                }
                else{
                    alert("Check your ID & PW")
                }
            },
            error : function (e){
                alert(e)
            }
        });
    }
</script>
<%@ include file="../fix/footer.jsp"%>