<%@ page import="com.inchat.inchat.domain.ChatMessageDTO" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="../fix/header.jsp" %>
<head>
    <link rel="stylesheet" href="css/chatroom.css">
</head>
<%
    int roomCode = request.getParameter("room-code") == null ? 0 : Integer.parseInt(request.getParameter("room-code"));
    List<ChatMessageDTO> chatList = null;
    UserVO user = (UserVO) request.getSession().getAttribute("log");
    if (roomCode == 0) {
        response.sendRedirect("/index");
    } else {
        chatList = (List<ChatMessageDTO>) request.getSession().getAttribute("chatList");
    }
%>
<c:if test="${log==null}">
    <c:redirect url="/index"/>
</c:if>
<div class="wrap">
    <div class="contents">
        <div id="text-area">
            <div id="text-input">
                <%
                    if (chatList != null) {
                        for (ChatMessageDTO messageDTO : chatList) {
                            String target = user.getNickname().equals(messageDTO.getWriter()) ? "first" : "secondary";
                %>
                <div class='alert <%=target%>'>
                    <p><%=messageDTO.getWriter()%> : <%=messageDTO.getMessage()%>
                    </p>
                </div>
                <%
                        }
                    }
                %>
            </div>
            <div id="input_area">
                <input type="text" id="msg" placeholder="Type a message..." required>
                <button type="submit" class="btn" id="button_send" onclick="sendMessage()">전송</button>
            </div>
        </div>
    </div>
</div>
<script src="https://cdnjs.cloudflare.com/ajax/libs/stomp.js/2.3.3/stomp.min.js"></script>
<script>
    const url = new URL(location.href);
    const param = url.searchParams;

    let roomCode = param.get("room-code");
    let username = '${log.getNickname()}';

    let sock = new SockJS("/stomp/chat");
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

            let writer = content.writer;
            let message = content.message;
            let target = '';

            if (writer === username) {
                target = 'first';
            } else {
                target = 'secondary';
            }
            let str = "<div class='alert " + target + "'>";
            str += "<p>" + writer + " : " + message + "</p>";
            str += "</div></div>";
            $("#text-input").append(str);
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