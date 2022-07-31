<%--
  Created by IntelliJ IDEA.
  User: iiii4
  Date: 2022-08-01
  Time: 오후 2:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="../fix/header.jsp" %>
<head>
    <link rel = stylesheet href="css/boardWrite.css">
</head>
<c:if test="${log==null}">
    <c:redirect url="/login"/>
</c:if>
<div class = "wrap">
    <h2 class="pageTitle">글 작성하기</h2>
    <form>
        <input name = "title" class= "title" style = "width : 600px;" type="text" placeholder="Title">
        <br>
        <textarea name = "contents" class = "contents" placeholder="Contents"></textarea>
        <br>
        <button class = "submit_btn" onclick="boardWriteAction()">작성하기</button>
    </form>
</div>
<script>
    function boardWriteAction(){
        const title = $("#title").val();
        const contents = $("#contents").val();
        const board = {
            "writer" : ${log.getNickname},
            "title" : title,
            "contents" : contents
        }
        jQuery.ajax({
            type: "POST",
            url: "/v2/create-board",
            contentType: 'application/json',
            data: JSON.stringify(board),
            datatype: "JSON",
        });
    }
</script>
<%@ include file="../fix/footer.jsp" %>