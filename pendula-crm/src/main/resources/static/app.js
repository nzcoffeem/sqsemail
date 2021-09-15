var stompClient = null;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#response").html("");
}

function connect() {
    var socket = new SockJS('/pendula-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/newcustomers', function (customer) {
            showCustomerCreated(JSON.parse(customer.body));
        });
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function register() {
    stompClient.send("/app/register", {},
        JSON.stringify(
            {
                'firstName': $("#firstName").val(),
                'lastName': $("#lastName").val(),
                'email': $("#email").val(),
                'phone': $("#phone").val(),
                'postcode': $("#postcode").val(),
            }
            ));
}

function showCustomerCreated(customer) {
    console.log(customer);
    $("#response").append("<tr><td>" + customer.firstName + " has been successfully registered! </td></tr>");
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#registerCustomer" ).click(function() { register(); });
});