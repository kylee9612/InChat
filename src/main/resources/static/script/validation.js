function handleSubmitNoRefresh(){}

function loginAction(user){
    alert(user.id);
    jQuery.ajax({
        type: "POST",
        url: "/v1/login-user",
        contentType: 'application/json',
        data: JSON.stringify(user),
        dataType: "JSON",
        success: function (e) {
            if(e!==null){
                sessionStorage.setItem("id",e.id)
                sessionStorage.setItem("nick",e.nick)
                alert("Success!")
                location.href = "/index"
            }
            else{
                alert("Check your ID & PW")
            }
        },
        error : function (e){
            alert(e)
        }
    });
}

function joinAction(user){
    jQuery.ajax({
        type: "POST",
        url: "/v1/adduser",
        contentType: 'application/json',
        data: JSON.stringify(user),
        dataType: "JSON",
        success: function (e) {
            if(e){
                alert("Join Success!")
                location.href = "/index"
            }
        },
        error : function (){
            alert("error")
        }
    });
}