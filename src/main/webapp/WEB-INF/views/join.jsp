<%@ page import="java.util.ArrayList" %>
<%--
  Created by IntelliJ IDEA.
  User: iiii4
  Date: 2022-07-21
  Time: 오후 10:53
  To change this template use File | Settings | File Templates.
--%>
<%@ include file="../fix/header.jsp" %>
<head>
    <link rel='stylesheet' href='css/join.css'>
</head>
<div class="wrap">
    <div class="contents">
        <form method="post" id = "join_form" onsubmit="return false;">
            <h2>Join Us!</h2>
            <input id="id_input" type="text" placeholder="id" name="id" required><br>
            <span id="dupId"></span>
            <input id="pw_input" type="password" placeholder="pw" name="pw" required><br>
            <input id="nick_input" type="text" placeholder="name" name="nick" required><br>
            <span id="invalidNick"></span>
            <input type="submit">
        </form>
    </div>
</div>
<script>
    let validId;
    let validNick;
    let join_form = $("#join_form");

    const nickInput = $("#nick_input");
    const idInput = $("#id_input");
    const dupId = $("#dupId");

    idInput.keyup(() => {
        let user = {
            "id": $("#id_input").val()
        }

        if(regExp.test(user.id)){
            dupId.html("Invalid User ID<br>");
        }
        else{
            dupId.html("");
            jQuery.ajax({
                type: "POST",
                url: "/v1/get-user",
                contentType: 'application/json',
                data: JSON.stringify(user),
                datatype: "JSON",
                success: function (e) {
                    if(e.id!==user.id){
                        validId = true
                        dupId.html("")
                    }else{
                        validId = false
                        dupId.html("Duplicated ID<br>")
                    }
                },
                error: function () {
                    validId = true
                    dupId.html("")
                }
            });
        }
    });

    nickInput.keydown(()=>{
        if(regExp.test(nickInput.val())){
            validNick = false;
            $("#invalidNick").html("Invalid Nickname<br>");
        }else {
            validNick = true;
            $("#invalidNick").html("");
        }
    })

    join_form.submit(()=> {
        if (validId && validNick) {
            let user = {
                "id": $("#id_input").val(),
                "pw": $("#pw_input").val(),
                "nickname": $("#nick_input").val()
            }
            joinAction(user)
        }
        else{
            alert("Duplicated ID or Invalid Nickname")
        }
    });
</script>
<%@ include file="../fix/footer.jsp" %>

