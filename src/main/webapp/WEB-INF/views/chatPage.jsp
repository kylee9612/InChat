<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="../fix/header.jsp" %>
<head>
    <link rel="stylesheet" href="css/chatroom.css">
</head>
<%
    if (request.getParameter("room-code") == null) {
        response.sendRedirect("/login");
    }
%>
<c:if test="${log==null}">
    <c:redirect url="/login"/>
</c:if>
<div class="wrap">
    <div class="contents">
        <div id="text-area">
            <div id="text-input">
                <c:forEach varStatus="status" var="chat" items="${chatList}">
                    <c:choose>
                        <c:when test="${chat.getWriter() eq log.getNickname()}">
                            <c:set var="target" value="first"/>
                        </c:when>
                        <c:otherwise>
                            <c:set var="target" value="secondary"/>
                        </c:otherwise>
                    </c:choose>
                    <div class='alert ${target}'>
                        <p>
                            <c:out value="${chat.getWriter()}"/> : <c:out value="${chat.getMessage()}"/>
                            <br>
                            <span>
                        <c:out value="${fn:split(chat.getCreatedAt(),'T')[1].substring(0,5)}"/>
                    </span>
                        </p>
                    </div>
                </c:forEach>
            </div>
            <div id="input_area">
                <input type="text" id="msg" placeholder="Type a message..." required>
                <button type="submit" class="btn" id="button_send" onclick="sendMessage()">전송</button>
            </div>
        </div>
    </div>
</div>
<script src="https://cdnjs.cloudflare.com/ajax/libs/stomp.js/2.3.3/stomp.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/sockjs-client@1/dist/sockjs.min.js"></script>
<script>
    const url = new URL(location.href);
    const param = url.searchParams;
    const textArea = $("#text-input");
    const text_input = document.querySelector("#text-input");

    let roomCode = param.get("room-code");
    let username = '${log.getNickname()}';

    sock = new SockJS("/stomp/chat");
    let stomp = Stomp.over(sock);

    function sendMessage() {
        let msg = document.getElementById("msg");
        console.log(username + ":" + msg.value);
        if (msg.value !== '') {
            stomp.send('/pub/chat/message', {}, JSON.stringify({
                room_code: roomCode,
                message: msg.value,
                writer: username
            }));
            msg.value = '';
        }
    }

    stomp.connect({}, function () {
        stomp.subscribe("/sub/chat/rooms/" + roomCode, function (chat) {
            let content = JSON.parse(chat.body);
            console.log(chat);

            let writer = content.writer;
            let message = content.message;
            let curtime = content.createdAt.split("T")[1].substring(0,5);
            let target = '';

            if (writer === username) {
                target = 'first';
            } else {
                target = 'secondary';
            }
            let str = "<div class='alert " + target + "'>";
            str += "<p>" + writer + " : " + message + "<br><span>" + curtime;
            str +="</span></p>";
            str += "</div></div>";
            textArea.append(str);
            text_input.scrollTop = text_input.scrollHeight;
        })
        stomp.send('pub/chat/enter', {}, JSON.stringify(
            {room_code: roomCode, writer: username}));
    });

    document.addEventListener("keydown", event => {
        if (event.keyCode === 13) {
            sendMessage();
        }
    })
</script>
<%@ include file="../fix/footer.jsp" %>