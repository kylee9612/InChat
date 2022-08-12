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
    <link rel=stylesheet href="css/boardWrite.css">
</head>
<c:if test="${log==null}">
    <c:redirect url="/login"/>
</c:if>
<c:if test="${pageNo eq null}">
    <c:set var="pageNo" value="1"/>
</c:if>
<div class="wrap">
    <h2 class="pageTitle">글 작성하기</h2>
    <div class="cons">
        <c:choose>
            <c:when test="${board ne null}">
                <input name="title" id="title" style="width : 600px;" type="text"
                       value="<c:out value="${board.getTitle()}"/>" placeholder="Title">
                <br>
                <textarea name="contents" id="contents" placeholder="Contents"><c:out
                        value="${board.getContents()}"/></textarea>
                <br>
                <button id="submit_btn" onclick="boardUpdate()" style="left:12%;">수정하기</button>
            </c:when>
            <c:otherwise>
                <input name="title" id="title" style="width : 600px;" type="text" placeholder="Title">
                <br>
                <textarea name="contents" id="contents" placeholder="Contents"></textarea>
                <br>
                <button id="submit_btn" onclick="boardWrite()" style="left:12%;">작성하기</button>
            </c:otherwise>
        </c:choose>
    </div>
</div>
<script>
    function boardWrite() {
        let board = {
            "writer": '${log.getNickname()}',
            "title": $("#title").val(),
            "contents": $("#contents").val()
        }
        jQuery.ajax({
            type: "POST",
            url: "/v2/create-board",
            contentType: 'application/json',
            data: JSON.stringify(board),
            datatype: "JSON",
            success: function (e) {
                alert("성공적으로 글이 작성되었습니다.")
                location.href = "/community?page=${pageNo}"
            },
            error: function (e) {
                alert("에러")
            }
        });
    }

    function boardUpdate() {
        let hi = '${board}';
        if (hi !== ''){
            let board = {
                "code": '${board.getCode()}',
                "writer": '${log.getNickname()}',
                "title": $("#title").val(),
                "contents": $("#contents").val(),
                "viewCount": '${board.viewCount}'
            }
            jQuery.ajax({
                type: "POST",
                url: "/v2/update-board",
                contentType: 'application/json',
                data: JSON.stringify(board),
                datatype: "JSON",
                success: function (e) {
                    alert("성공적으로 글이 수정되었습니다.")
                    location.href = "/community?page=${pageNo}"
                },
                error: function (e) {
                    alert("에러")
                }
            });
        }
    }

</script>
<%@ include file="../fix/footer.jsp" %>