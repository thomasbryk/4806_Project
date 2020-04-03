
    $(document).ready(function () {
        var books = [];
        var customer = {};
        var bookstore = {};
        $('#customerId').val(urlParam('customerId'))

        var populateCustomerInfo = function(){
            $.ajax({
                type: "GET",
                url: "/api/customers/"+urlParam('customerId'),
                headers: {
                    'Accept':'application/json'
                },
                success: function(data){
                    // populate table
                    let infoDiv = $("#customerInfo")
                    infoDiv.append("<p>Customer username: "+data.username+"</p>");
                    infoDiv.append("<p>Customer name: "+data.name+'</p>')
                    
                },
                failure: function(err){
                    alert("Failed to get customer with err:"+err);
                }
            })
        }

        var populateBookstoreInfo = function(){
            $.ajax({
                type: "GET",
                url: "/api/bookstores/"+urlParam('bookstoreId'),
                headers: {
                    'Accept':'application/json'
                },
                success: function(data){
                    // populate table
                    let infoDiv = $("#bookstoreInfo")
                    infoDiv.append("<p>Bookstore ID: "+data.id+"</p>");
                    infoDiv.append("<p>Bookstore owner name: "+data.bookstoreOwner.name+'</p>')
                    
                },
                failure: function(err){
                    alert("Failed to get bookstore with err:"+err);
                }
            })
        }

        var populateBooks = function () {
            $.ajax({
                type: "GET",
                url: "/api/bookstores/" + urlParam('bookstoreId') + "/books",
                headers: {
                    'Accept': 'application/json'
                },
                success: function (data) {
                    // populate table
                    populateBooksTable(data);
                },
                error: function (err) {
                    alert("Failed to get books with err:" + err);
                }
            })
        }

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
                if(book.available){
                booksTable.append('<tr>' +
                    '<td>' + book.id + '</td>' +
                    '<td>' + book.name + '</td>' +
                    '<td>' + book.isbn + '</td>' +
                    '<td>' + book.picture + '</td>' +
                    '<td>' + book.description + '</td>' +
                    '<td>' + book.author + '</td>' +
                    '<td>' + book.publisher + '</td>' +
                    '<td>' + book.available + '</td>' +
                    '<td><button id="' + buttonId + '" class="button3">Add to Cart</button></td>' +
                    '</tr>');
                $('#' + buttonId).click(function () {
                    addBookToCart(book);
                });
            }
            });
            $(booksTable).show();
        };

        var addBookToCart = function(bookToAdd){
            var call = $.ajax({
                type: "PUT",
                url: "/api/customers/"+urlParam('customerId')+'/shoppingcart',
                data: JSON.stringify(bookToAdd), // serializes the form's elements.
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                success: function (data) {
                    window.location.href='/viewCustomer?customerId='+urlParam('customerId')
                    
                }
                ,
                error: function (err) {
                    alert("Book is already in cart");
                }
            });
        
        }

        populateBooks();
        populateBookstoreInfo();
        populateCustomerInfo();
    })