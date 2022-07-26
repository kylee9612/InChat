<%--
  Created by IntelliJ IDEA.
  User: iiii4
  Date: 2022-07-26
  Time: 오전 10:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="../fix/header.jsp" %>
<head>
    <link rel="stylesheet" href="css/chatroom.css">
</head>
<c:if test="${log==null}">
    <c:redirect url="/login"/>
</c:if>
<div class="wrap">
    <div class="contents">
        <div id="text-area">
            <div id="text-input">
            </div>
            <div id="input_area">
                <input type="text" id="msg" placeholder="Type a message..." required>
                <button type="submit" class="btn" id="button_send" onclick="sendMessage()">전송</button>
            </div>
        </div>
        <div id="user_area">
            <ul id="user_list"></ul>
        </div>
    </div>
</div>
<script>
    const usernick = '${log.getNickname()}';
    const userList = $("#user_list");
    const textArea = $("#text-input");
    const text_input = document.querySelector("#text-input");

    sock = new WebSocket('ws://in-chat.online:8080/ws/chat');
    sock.onopen = onOpen;
    sock.onmessage = onMessage;
    sock.onclose = onClose;

    document.addEventListener("keydown", event => {
        if (event.keyCode === 13) {
            sendMessage();
        } else if (event.keyCode === 116) {
            onClose();
        }
    })

    sock.onerror = function () {
        alert("error");
    };

    function onClose(event) {
        let str = usernick + ":님이 퇴장했습니다";
        console.log(str);
        sock.send(str);
    }

    function onOpen(event) {
        let str = usernick + ":님이 입장했습니다";
        sock.send(str);
    }

    function refreshUserList() {
        userList.html("");
        jQuery.ajax({
            type: "GET",
            url: "/v3/get-user-list",
            contentType: "JSON",
            success: function (e) {
                console.log(e);
                // let temp = JSON.stringify(e).substring(3,this.length-2).split(`","`);
                for (let i = 0; i < e.length; i++) {
                    userList.append("<li>" + e[i] + "</li>");
                }
            },
            error: function (e) {

            }
        })
    }

    function sendMessage() {
        let msg = document.getElementById("msg");
        console.log(usernick + ":" + msg.value);
        if (msg.value !== '') {
            sock.send(usernick + ":" + msg.value);
            msg.value = '';
        }
    }

    function onMessage(msg) {
        let data = msg.data;
        let target;

        /*  메세지 전송한 사람   */
        let sessionId = null;
        let message = "";
        let userListStr;
        let arr = data.split(":")
        for (let i = 0; i < arr.length; i++) {
            console.log('arr[' + i + "] : " + arr[i]);
        }
        if (arr[1] === "님이 입장했습니다" || arr[1] === "님이 퇴장했습니다"){
            refreshUserList();
        }

        let cur_session = '${log.getNickname()}';

        sessionId = arr[0];
        for (let i = 1; i < arr.length; i++) {
            message += arr[i];
            if (i + 1 < arr.length)
                message += ":";
        }

        console.log("sessionID : " + sessionId);
        console.log("cur_session : " + cur_session);

        if (sessionId === cur_session) {
            target = "first";
        } else {
            target = "secondary";
        }
        let str = "<div class='alert " + target + "'>";
        str += "<p>" + sessionId + " : " + message + "</p>";
        str += "</div></div>";
        textArea.append(str);
        text_input.scrollTop = text_input.scrollHeight;
    }

</script>
<%@ include file="../fix/footer.jsp" %>