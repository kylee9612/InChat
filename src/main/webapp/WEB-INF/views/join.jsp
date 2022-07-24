<%@ page import="java.util.ArrayList" %>
<%--
  Created by IntelliJ IDEA.
  User: iiii4
  Date: 2022-07-21
  Time: 오후 10:53
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="../fix/header.jsp" %>
<head>
    <link rel='stylesheet' href='css/join.css'>
</head>
<div class="wrap">
    <div class="contents">
        <form method="post" id = "join_form" onsubmit="return false;">
            <input id="id_input" type="text" placeholder="id" name="id" required><br>
            <span id="dupId"></span>
            <input id="pw_input" type="password" placeholder="pw" name="pw" required><br>
            <input id="nick_input" type="text" placeholder="name" name="nick" required><br>
            <input type="submit">
        </form>
    </div>
</div>
<script>
    let valid;

    let join_form = $("#join_form");
    function handleSubmitNoRefresh(){}

    join_form.addEventListener("submit",handleSubmitNoRefresh());

    $("#id_input").keyup(() => {
        let user = {
            "id": $("#id_input").val()
        }
        jQuery.ajax({
            type: "POST",
            url: "/v1/search/user",
            contentType: 'application/json',
            data: JSON.stringify(user),
            datatype: "JSON",
            success: function (e) {
                if(e.id!==user.id){
                    valid = true;
                    $("#dupId").html("");
                }else{
                    valid = false;
                    $("#dupId").html("Duplicated ID<br>");
                }
            },
            error: function () {

            }
        });
    });

    join_form.submit(()=> {
        if (valid) {
            let user = {
                "id": $("#id_input").val(),
                "pw": $("#pw_input").val(),
                "nick": $("#nick_input").val()
            }
            joinAction(user)
        }
        else{
            alert("Duplicated ID")
        }
    });
</script>
<%@ include file="../fix/footer.jsp" %>

