$(document).ready(function() {

    var frm = $("#addAddressBookButton");
    frm.submit(function (e) {
        e.preventDefault();
        $.ajax({
            type: frm.attr('method'),
            url: "http://localhost:8081/api/newAddressBook",
            data: frm.serialize()
        }).then(function(data) {
            $.ajax({
                type: "GET",
                url: "http://localhost:8081/api/getAddressBooks",
                data: frm.serialize()
            }).then(function(data) {
                $("#addressBookTable").empty();
                for (var i in data){
                    console.log(data[i]);
                    $("#addressBookTable").append("<tr><td><a href=\"viewAddressBook.html?id=" + data[i].id + "\">" + data[i].id + "</a></td></tr>");
                }
            })
        })
    });
});

