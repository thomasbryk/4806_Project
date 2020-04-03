$(document).ready(function () {
    var populateBookstoreOwnerInfo = function(){
        $.ajax({
            type: 'GET',
            url: "/api/bookstoreowners/"+urlParam('bookstoreOwnerId'),
            headers: {
                'Accept':'application/json'
            },
            success: function(data){
                let infoDiv = $("#bookstoreOwnerInfo")
                infoDiv.append("<p>Bookstore Owner: "+data.name+"</p>");
                infoDiv.append("<p>Bookstore Owner ID: "+data.id+"</p>");
            },
            failure: function(err){
                alert("Failed to get owner info with err:"+err);
            }
        })
    };

    var populateBookstores = function (data){
        let bookstoreList = $('#bookstores')
        $.ajax({
            type: "GET",
            url: "/api/bookstoreowners/"+urlParam('bookstoreOwnerId')+"/bookstores",
            headers: {
                'Accept':'application/json'
            },
            success: function (data) {
                populateBookstoreTable(data);
            },
            failure: function(err){
                alert("couldn't get bookstores");
            }
        })
    };

    var populateBookstoreTable = function(data){
        let bookstoreTable = $('#bookstores');
        bookstoreTable.empty();
        $.each(data, function(indx, bookstore){
            let buttonId = 'delete-' + indx;
            bookstoreTable.append('<tr>' +
                '<td>'+ '<a href="/editBookstore">'+ bookstore.id + '</a>' +  '</td>' +
                '<td><button id="' + buttonId  + '">Remove Bookstore</button></td>' +
                '<tr>');
            $('#' + buttonId).click(function () {
                removeBookstore(bookstore);
            });
        });
        $(bookstoreTable).show();
    };

    var createNewBookstore = function(e) {
        e.preventDefault();
        var form =$(this);
        var url = form.attr('action');
        var textObj = form.serializeObject();
        var text = JSON.stringify(textObj);

        var call = $.ajax({
            type: "PUT",
            url: "/api/bookstoreowners/"+urlParam('bookstoreOwnerId')+"/bookstores",
            data: text,
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            success: function (data) {
                populateBookstoreTable(data);
            },
            failure: function (err) {
                alert("failed to add a new bookstore");
            }
        });

    };

    var removeBookstore = function(bookstore){
        var call = $.ajax({
            type: "DELETE",
            url: "/api/bookstores",
            data: JSON.stringify(bookstore),
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            success: function (data) {
                populateBookstores();
            },
            failure: function () {
                alert("Failed to remove the bookstore");
            }
        });
    };

    $('#create-store-form').submit(createNewBookstore);

    populateBookstoreOwnerInfo();
    populateBookstores();
});