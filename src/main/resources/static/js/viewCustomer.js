$(document).ready(function(){
    var currentCart = {};
    var books = {};

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
                alert("Failed to get bookstores with err:"+err);
            }
        })
    };

    var populateBookstores = function(){
        $.ajax({
            type: "GET",
            url: "/api/bookstores",
            headers: {
                'Accept':'application/json'
            },
            success: function(data){
                // populate table
                let bookstoresTable = $('#bookstores');
                $.each(data, function(indx, bookstore){
                    let shopBookstoreForm = '<form action="/shopBookstore" method="get">';
                    shopBookstoreForm += '<input type="hidden" name="bookstoreId" value="'+bookstore.id+'"/>';
                    shopBookstoreForm += '<input type="hidden" name="customerId" value="'+urlParam('customerId')+'"/>';
                    shopBookstoreForm += '<input type="submit" value="Shop store"/>';
                    shopBookstoreForm += '</form>';
                    bookstoresTable.append('<tr><td>'+bookstore.id+'</td><td>'+shopBookstoreForm+'</td></tr>');
                })
            },
            failure: function(err){
                alert("Failed to get bookstores with err:"+err);
            }
        })
    };

    var populateSales = function(){
        $.ajax({
            type: "GET",
            url: "/api/customers/"+urlParam('customerId')+'/sales',
            headers: {
                'Accept':'application/json'
            },
            success: function(data){
                // populate table
                let salesTable = $('#sales');
                salesTable.empty();
                $.each(data, function(indx, sale){
                    salesTable.append('<tr><td>Sale ID: ' + sale.id+'</td></tr>');
                    $.each(sale.books, function(bookidx,book){
                        salesTable.append('<tr><td>-  Book ID: ' + book.id + ',  Name: ' + book.name + ',  Bookstore: ' + book.bookstore.id + '<td/></tr>');
                    })
                })
            },
            failure: function(err){
                alert("Failed to get bookstores with err:"+err);
            }
        })
    };

    var populateShoppingCart = function(){
        $.ajax({
            type: "GET",
            url: "/api/customers/"+urlParam('customerId')+'/shoppingcart',
            headers: {
                'Accept':'application/json'
            },
            success: function(data){
                // populate table
                updateShoppingCart(data);

            },
            failure: function(err){
                alert("Failed to get bookstores with err:"+err);
            }
        })
    };

    var updateShoppingCart = function(cart){
        currentCart = cart;
        let cartTable = $('#books');
        cartTable.empty();
        $.each(cart.books, function(indx, book){
            let bookName = '<td>'+book.name+'</td>';
            let bookDeleteButton = document.createElement("button");
            bookDeleteButton.setAttribute('id','delete-'+indx);
            bookDeleteButton.addEventListener("click",function(){
                removeBookFromCart(indx);
            });
            bookDeleteButton.innerHTML = "Remove Book from Cart";
            cartTable.append('<tr>'+bookName+"<td>");
            cartTable.append(bookDeleteButton);
            cartTable.append("</td></tr>");
        })
    };

    var removeBookFromCart = function(index){
        let bookToRemove = currentCart.books[index];
        var call = $.ajax({
            type: "DELETE",
            url: "/api/customers/"+urlParam('customerId')+'/shoppingcart',
            data: JSON.stringify(bookToRemove), // serializes the form's elements.
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            success: function (data) {
                updateShoppingCart(data);

            }
            ,
            error: function (err) {
                alert("failed");
            }
        });
    };

    var checkOut = function(e){
        e.preventDefault(); // avoid to execute the actual submit of the form
        var call = $.ajax({
            type: "POST",
            url: "/api/customers/"+urlParam('customerId')+'/checkout',
            headers: {
                'Accept': 'application/json'
            },
            success: function (data) {
                updateShoppingCart(
                    {books:[]}
                );
                populateSales();
                populateBookRecommendations();
            }
            ,
            failure: function (err) {
                alert("failed");
            }
        });
    };

    var populateBookRecommendations = function(){
        $.ajax({
            type: "GET",
            url: "/api/getBookRecommendations/"+urlParam('customerId'),
            headers: {
                'Accept':'application/json'
            },
            success: function(data){
                if (data.length == 0){
                    $('#emptyMessage').show();
                    $('#bookRecommendationTable').empty();
                } else {
                    $('#emptyMessage').hide();
                    let bookRecommendationTable = $('#bookRecommendationTable');
                    books = data;
                    bookRecommendationTable.empty();
                    $.each(data, function(indx, book){
                        let bookName = '<td>'+book.name+'</td>';
                        let addToCartButton = document.createElement("button");
                        addToCartButton.setAttribute('id','add-'+indx);
                        addToCartButton.addEventListener("click",function(){
                            addBookToCart(indx);
                        });
                        addToCartButton.innerHTML = "Add Book to Cart";
                        bookRecommendationTable.append('<tr>'+bookName+'<td>');
                        bookRecommendationTable.append(addToCartButton);
                        bookRecommendationTable.append("</td></tr>");
                    })
                }
            },
            failure: function(err){
                alert("Failed to get book recommendations with err:"+err);
            }
        })
    };

    var addBookToCart = function(index){
        let bookToAdd = books[index];
        var call = $.ajax({
            type: "PUT",
            url: "/api/customers/"+urlParam('customerId')+'/shoppingcart',
            data: JSON.stringify(bookToAdd), // serializes the form's elements.
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            success: function (data) {
                window.location.href='/viewCustomer?customerId='+urlParam('customerId');
            }
            ,
            error: function (err) {
                alert("Book is already in cart");
            }
        });
    };

    $('#checkout').submit(checkOut);
    populateBookstores();
    populateSales();
    populateCustomerInfo();
    populateShoppingCart();
    populateBookRecommendations();
});