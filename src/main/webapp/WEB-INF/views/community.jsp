<%--
  Created by IntelliJ IDEA.
  User: iiii4
  Date: 2022-08-01
  Time: 오후 12:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="../fix/header.jsp" %>
<head>
    <link rel="stylesheet" href="css/community.css">
</head>
<%
    int pageNo = request.getParameter("page") == null ? 1 : Integer.parseInt(request.getParameter("page"));
%>
<div class="wrap">
    <h2 class="pageTitle">자유게시판</h2>
    <div class="contents">
        <ul>
            <li class="nav">
                <label class="no">번호</label>
                <label class="writer">작성자</label>
                <label class="title">제목</label>
                <label class="createdAt">작성일</label>
                <label class="viewCount">조회수</label>
            </li>
            <c:if test="${boardList.size() != 0}">
                <c:forEach var="board" items="boardList" varStatus="status">
                    <li class = "board" onclick="location.href='/communityView?code=<c:out value="${board.getCode()}"/> '">
                        <label class="no">${board.getCode()}</label>
                        <label class="writer">${board.getWriter()}</label>
                        <label class="title">${board.getTitle()}</label>
                        <label class="createdAt">${board.getCreatedAt()}</label>
                        <label class="viewCount">${board.getViewCount()}</label>
                    </li>
                </c:forEach>
            </c:if>
            <button class="write_btn" onclick="location.href='/communityWrite?page=<%=pageNo%>'">글쓰기</button>
        </ul>
    </div>
</div>

<%@ include file="../fix/footer.jsp" %>