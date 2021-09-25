function initializeContent() {
    $("#messages").empty();
    let phoneNumber = $("#phoneNumber").val();
    connect(phoneNumber);
    drawContent(phoneNumber);
}


var stompClient = null;


function drawContent(phoneNumber) {
    $.get(`/messages/${phoneNumber}`, (messages) => {
        for (let i = 0; i < messages.length; i++) {
            let message = messages[i];
            $("#messages").append(`<div id=${message.id}><span>From <strong>${message.from}</strong></span><p>${message.receivedAt}</p><pre>${message.text}</pre></div>`);
        }
    });

}


function connect(phoneNumber) {
    let connection = new SockJS("/phone-emulator-websocket");
    stompClient = Stomp.over(connection);

    stompClient.connect({}, (frame) => {
        stompClient.subscribe(`/topic/messages/${phoneNumber}`, (message) => {
                message = JSON.parse(message.body);
                $("#messages").prepend(`<div id=${message.id}><span>From <strong>${message.from}</strong></span><p>${message.receivedAt}</p><pre>${message.text}</pre></div>`);
            }
        );
    });
}


function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
}


function submitForm() {
    var resultSelector = $("#resultText");
    let from, to, text;
    from = $("#from").val();
    to = $("#to").val();
    text = $("#text").val();

    let data = {from: from, to: to, text: text};

    $.ajax({
        url: "/messages/send",
        method: "POST",
        data: data,
        success: function (data) {
            resultSelector.attr("class", "text-success");
            resultSelector.text("Message was successfully sent");
            $("#message-form").get(0).reset();
        },
        error: function (xhr) {
            let responseMessage = xhr.responseJSON.message;
            resultSelector.attr("class", "text-danger");
            resultSelector.text(responseMessage);
        }
    });

}







