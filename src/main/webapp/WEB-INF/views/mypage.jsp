<%--
  Created by IntelliJ IDEA.
  User: iiii4
  Date: 2022-07-25
  Time: 오후 3:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../fix/header.jsp" %>
<head>
    <link rel="stylesheet" href="css/mypage.css">
</head>
<div class="wrap">
    <c:if test="${log==null}">
        <c:redirect url="/index"/>
    </c:if>
    <div class="tabs">
        <ul>
            <li onclick="myPage()"><span>내 정보</span></li>
            <li><span onclick="">채팅 목록</span></li>
            <li onclick="deleteUser()"><span>회원 탈퇴</span></li>
        </ul>
    </div>
    <div id="contents">
        <form id = "update_form" onsubmit="return false;">
            <h2>내 정보 수정</h2>
            <input id = "id_input" type="text" readonly placeholder = "id" value = "<c:out value = '${log.getId()}'/>" ><br>
            <input id = "pw_input" type="password" placeholder = "pw"><br>
            <input id = "nick_input" type="text" placeholder = "nickname" value = "<c:out value = '${log.getNickname()}'/>"><br>
            <button id = "submit_box" onclick="updateEvent()">제출</button>
        </form>
    </div>
</div>
<script>
    const container = $("#contents")

    function updateEvent(){
        let user = {
            "id": $("#id_input").val(),
            "pw": $("#pw_input").val(),
            "nickname": $("#nick_input").val()
        }
        updateAction(user);
    }

    function deleteCheck(){
        let input = $("#del_input")
        let error = $("#error_box")
        if(input.val() === 'delete <c:out value = "${log.getId()}"/>'){
            deleteAction('<c:out value = "${log.getId()}"/>')
        }else{
            error.html("Typed Text Does not match")
        }
    }

    function myPage(){
        container.html(`
        <form id = "update_form" onsubmit="return false;">
            <h2>내 정보 수정</h2>
            <input id = "id_input" type="text" readonly placeholder = "id" value = "<c:out value = '${log.getId()}'/>" ><br>
            <input id = "pw_input" type="password" placeholder = "pw"><br>
            <input id = "nick_input" type="text" placeholder = "nickname" value = "<c:out value = '${log.getNickname()}'/>"><br>
            <button id = "submit_box" onclick="updateEvent()">제출</button>
        </form>`
        )
    }

    function deleteUser(){
        container.html(`
            <h2>회원 탈퇴</h2>
            <p>Please Type Next Word</p>
            <input id="del_input" type="text" placeholder="delete <c:out value = '${log.getId()}'/>"><br>
            <span id = "error_box"></span>
            <button id = "submit_box" onclick='deleteCheck()'">삭제</button>`
        )
    }

</script>
<%@ include file="../fix/footer.jsp" %>