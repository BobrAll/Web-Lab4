let canvas = document.getElementById("graph-canvas");

function sendRequest(event) {
    let rect = canvas.getBoundingClientRect();
    let r = $("#r").val();
    let rPixels = rect.width / 7 * 3;
    let x = ((event.clientX - rect.left - rect.width / 2) / rPixels);
    let y = (-(event.clientY - rect.top - rect.height / 2) / rPixels);

    $.ajax({
        url: "/main",
        method: "GET",
        dataType: "html",
        data: {x: x, y : y, r : r},
        success: function (data) {
            location.href="main";
        }
    });
}

$("#graph-canvas").on("click", function (e) {
    sendRequest(e);
})

$("#clear").on("click", function () {
    $.ajax({
        url: "/main/clear",
        method: "GET",
        success: function (data) {
            location.href="main";
        }
    });
})