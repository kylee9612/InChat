<%--
  Created by IntelliJ IDEA.
  User: iiii4
  Date: 2022-08-01
  Time: 오후 3:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="../fix/header.jsp" %>
<head>
    <link rel=stylesheet href="css/boardView.css">
    <link rel="stylesheet" href="css/boardWrite.css">
</head>
<div class="wrap">
    <div class="cons">
        <input name="title" id="title" style="width : 600px;" type="text" placeholder="Title" readonly
               value='<c:out value="${board.getTitle()}"/>'>
        <br>
        <textarea name="contents" id="contents" placeholder="Contents" readonly><c:out value="${board.getContents()}"/>
    </textarea>
        <br>
        <button id="submit_btn" onclick="updateAction()">수정하기</button>
    </div>
</div>
<script>
    function updateAction() {
        if ('<c:out value="${log.getNickname()}"/>' === '<c:out value="${board.getWriter()}"/>') {
            location.href="/communityWrite"
        } else {
            alert("수정할 권한이 없습니다")
        }
    }
</script>

<%@ include file="../fix/footer.jsp" %>