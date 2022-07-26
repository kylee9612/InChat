<%--
  Created by IntelliJ IDEA.
  User: iiii4
  Date: 2022-07-21
  Time: 오후 10:54
  To change this template use File | Settings | File Templates.
--%>
<%@ include file="../fix/header.jsp" %>
<head>
    <link rel='stylesheet' href='css/login.css'>
</head>
<div class="wrap">
    <div class="contents">
        <form method="post" id="login_form" onsubmit="return false;">
            <h2>Log In</h2>
            <input id="id_input" type="text" placeholder="id" name="id" required><br>
            <input id="pw_input" type="password" placeholder="pw" name="pw" required><br>
            <input type="submit">
        </form>
    </div>
</div>
<script>
    let login_form = $("#login_form");
    let submit_flag = false;
    login_form.submit(() => {
        if (submit_flag) {
            return submit_flag;
        } else {
            let user = {
                "id": $("#id_input").val(),
                "pw": $("#pw_input").val(),
            }
            submit_flag = true;
            loginAction(user);
        }
    });

    function loginAction(user){
        const uri = "/v1/login-user"
        const redirect_uri = "/index"
        jQuery.ajax({
            type: "POST",
            url: uri,
            contentType: 'application/json',
            data: JSON.stringify(user),
            dataType: "JSON",
            success: function (e) {
                if(e !== null) {
                    alert("Login Success")
                    location.href = redirect_uri
                }
                else{
                    alert("Check your ID & PW");
                    submit_flag = false;
                }
            },
            error : function (request,error){
                alert("Check your ID & PW");
                submit_flag = false;
            }
        });
    }
</script>
<%@ include file="../fix/footer.jsp" %>