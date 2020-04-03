$(document).ready(function () {
    var bookstoreId = urlParam('bookstoreId');
    $('#ownerId').val(urlParam('bookstoreOwnerId'));

    var populateBooks = function () {
        $.ajax({
            type: "GET",
            url: "/api/bookstores/" + bookstoreId + "/books",
            headers: {
                'Accept': 'application/json'
            },
            success: function (data) {
                // populate table
                populateBooksTable(data);

            },
            failure: function (err) {
                alert("Failed to get bookstores with err:" + err);
            }
        })

    };

    var populateBookstoreInfo = function () {
        $.ajax({
            type: "GET",
            url: "/api/bookstores/" + bookstoreId,
            headers: {
                'Accept': 'application/json'
            },
            success: function (data) {
                // populate table
                let infoDiv = $("#bookstoreInfo")
                infoDiv.append("<p>Bookstore ID: " + data.id + "</p>");
                infoDiv.append("<p>Bookstore owner name: " + data.bookstoreOwner.name + '</p>')

            },
            failure: function (err) {
                alert("Failed to get bookstore with err:" + err);
            }
        })
    };

    var populateBooksTable = function (books) {
        let booksTable = $('#books');
        let booksMessage = $("#booksMessage");
        booksTable.empty();

        if (books == null || books.length == 0) {
            $(booksMessage).show();
            $(booksTable).hide();
            return;
        }
        $(booksMessage).hide();

        booksTable.append('<tr> ' +
            '<th>ID</th> ' +
            '<th>Name</th>' +
            '<th>ISBN</th>' +
            '<th>Picture</th>' +
            '<th>Description</th>' +
            '<th>Author</th>' +
            '<th>Publisher</th>' +
            '<th>Available</th>' +
            '<th></th>' +
            '</tr>');

        $.each(books, function (indx, book) {
            let buttonId = 'delete-' + indx;
            booksTable.append('<tr>' +
                '<td>' + book.id + '</td>' +
                '<td>' + book.name + '</td>' +
                '<td>' + book.isbn + '</td>' +
                '<td>' + book.picture + '</td>' +
                '<td>' + book.description + '</td>' +
                '<td>' + book.author + '</td>' +
                '<td>' + book.publisher + '</td>' +
                '<td>' + book.available + '</td>' +
                '<td><button id="' + buttonId + '" class="deleteButton">Remove Book</button></td>' +
                '</tr>');
            $('#' + buttonId).click(function () {
                deleteBook(book);
            });
        });
        $(booksTable).show();
    };

    var addBook = function (e) {
        e.preventDefault();
        var form = $(this);
        var url = form.attr('action');
        var textObj = form.serializeObject();
        var text = JSON.stringify(textObj);

        var call = $.ajax({
            type: "PUT",
            url: "/api/bookstores/" + bookstoreId + "/books",
            data: text,
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            success: function (data) {
                // populate table
                populateBooksTable(data);

            },
            failure: function (err) {
                alert("failed");
            }
        });
    };

    var deleteBook = function (book) {
        var call = $.ajax({
            type: "DELETE",
            url: "/api/books",
            data: JSON.stringify(book), // serializes the form's elements.
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            success: function (data) {
                populateBooks();
            },
            failure: function (err) {
                alert("Book removal failed with err:" + err);
            }
        });

    };

    $('#addBook').submit(addBook);
    populateBooks();
    populateBookstoreInfo();
});