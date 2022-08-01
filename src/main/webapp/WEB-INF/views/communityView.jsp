<%--
  Created by IntelliJ IDEA.
  User: iiii4
  Date: 2022-08-01
  Time: 오후 3:51
  To change this template use File | Settings | File Templates.
--%>
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
        <button id="delete_btn" onclick="deleteAction()">삭제</button>
        <button id="submit_btn" onclick="updateAction()">수정하기</button>
    </div>
</div>
<script>
    function updateAction() {
        if ('<c:out value="${log.getNickname()}"/>' === '<c:out value="${board.getWriter()}"/>') {
            location.href="/communityWrite?code="+<c:out value="${board.getCode()}"/>
        } else {
            alert("수정할 권한이 없습니다")
        }
    }
    function deleteAction(){
        if ('<c:out value="${log.getNickname()}"/>' === '<c:out value="${board.getWriter()}"/>') {
            let board = {
                "code" : "${board.getCode()}"
            }
            jQuery.ajax({
                type: "POST",
                url: "/v2/delete-board",
                contentType: 'application/json',
                data: JSON.stringify(board),
                datatype: "JSON",
                success: function (e) {
                    alert("삭제되었습니다!");
                    location.href = "/community"
                }
            });
        } else {
            alert("삭제할 권한이 없습니다")
        }
    }
</script>

<%@ include file="../fix/footer.jsp" %>