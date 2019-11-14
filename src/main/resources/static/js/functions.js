toastr.options = {
    "closeButton": true,
    "debug": false,
    "newestOnTop": true,
    "progressBar": true,
    "positionClass": "toast-top-center",
    "preventDuplicates": false,
    "onclick": null,
    "showDuration": "300",
    "hideDuration": "1000",
    "timeOut": "5000",
    "extendedTimeOut": "1000",
    "showEasing": "swing",
    "hideEasing": "linear",
    "showMethod": "fadeIn",
    "hideMethod": "fadeOut"
};

window.onload = function () {

    let topSellersHash = 0;
    let newBooksHash = 0;
    let editBookHash = 0;
    let addBookHash = 0;
    let deleteBookHash = 0;

    let topSellersStream = new EventSource("/topSellers");
    topSellersStream.onerror = function (event) {

    };
    topSellersStream.onopen = function (event) {

    };
    topSellersStream.onmessage = async function (event) {
        if(event.data !== '0') showServiceSwitch('block', 'none');
        else showServiceSwitch('none', 'block');

        if (event.data !== topSellersHash) {
            let call = await fetch("/getTopSellers", {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Accept': 'application/json'
                },
            });
            let result = await call.json();

            document.getElementById('topSellersDeck').innerHTML = null;


            for (let book of result) {
                let bookCategory;

                if(book.bookCategory == null) bookCategory = 'No genre';
                else bookCategory = book.bookCategory.name;

                let template = '<div class="card border-primary" id="topSeller' + book.id + '" style="min-width: 225px; min-height: 400px; max-width: 225px; margin:5px">\
                    <img onerror="this.src=\'/images/placeholder.png\'" class="card-img-top" style="padding-left: auto; \
                    padding-right: auto;" src="http://localhost:8081/images/book' + book.id + '.png">\
                    <div class="card-body">\
                    <h5 id="topSellerName' + book.id + '" class="card-title">' + book.author + '. ' + book.name + '</h5>\
                    <p id="topSellerCategory' + book.id + '" class="card-text">Genre: '+ bookCategory + '</p>\
                    <p id="topSellerPrice' + book.id + '" class="card-text">Price: '+ book.price + '$</p>\
                    <p id="topSellerAmountLeft' + book.id + '" class="card-text">Amount left: '+ book.amount + '</p>\
                    </div>\
                    </div>';
                document.getElementById('topSellersDeck').innerHTML += template;

            }
            topSellersHash = event.data;
        }
    };


    let newBooksStream = new EventSource("/newBooks");
    newBooksStream.onerror = function (event) {

    };
    newBooksStream.onopen = function (event) {

    };
    newBooksStream.onmessage = async function (event) {
        if(event.data !== '0') showServiceSwitch('block', 'none');
        else showServiceSwitch('none', 'block');

        if (event.data !== newBooksHash) {
            let call = await fetch("/getNewBooks", {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Accept': 'application/json'
                },
            });
            let result = await call.json();

            document.getElementById('newBooksDeck').innerHTML = null;



            let counter = 0;

            for (let book of result) {
                let bookCategory;

                if(book.bookCategory == null) bookCategory = 'No genre';
                else bookCategory = book.bookCategory.name;

                let template = '<div class="card border-primary" id="newBookId' + book.id + '" style="min-width: 225px; min-height: 400px; max-width: 225px; margin:5px">\
                    <img onerror="this.src=\'/images/placeholder.png\'" class="card-img-top" style="padding-left: auto; \
                    padding-right: auto;" src="http://localhost:8081/images/book' + book.id + '.png">\
                    <div class="card-body">\
                    <h5 id="newBookName' + book.id + '" class="card-title">' + book.author + '. ' + book.name + '</h5>\
                    <p id="newBookCategory' + book.id + '"class="card-text">Genre: '+ bookCategory + '</p>\
                    <p id="newBookPrice' + book.id + '" class="card-text">Price: '+ book.price + '$</p>\
                    <p id="newBookAmountLeft' + book.id + '" class="card-text">Amount left: '+ book.amount + '</p>\
                    </div>\
                    </div>';
                document.getElementById('newBooksDeck').innerHTML += template;

            }
            }
            newBooksHash = event.data;
        }
    };


    let editBookStream = new EventSource("/editBook");
    editBookStream.onerror = function (event) {

    };
    editBookStream.onopen = function (event) {

    };
    editBookStream.onmessage = async function (event) {
        if (event.data !== editBookHash) {
            let call = await fetch("/getEditBook", {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Accept': 'application/json'
                },
            });
            let result = await call.json();




            for (let book of result) {
                let bookCategory;

                if(book.bookCategory == null) bookCategory = 'No genre';
                else bookCategory = book.bookCategory.name;

                document.getElementById('topSellerName' + book.id).innerText = book.author + '. ' + book.name;
                document.getElementById('topSellerPrice' + book.id).innerText = 'Price: ' + book.price + '$';
                document.getElementById('topSellerCategory' + book.id).innerText = 'Genre: ' + bookCategory;
                document.getElementById('topSellerAmountLeft' + book.id).innerText = 'Amount left: ' + book.amount;
                document.getElementById('newBookName' + book.id).innerText = book.author + '. ' + book.name;
                document.getElementById('newBookPrice' + book.id).innerText = 'Price: ' + book.price + '$';
                document.getElementById('newBookCategory' + book.id).innerText = 'Genre: ' +bookCategory;
                document.getElementById('newBookAmountLeft' + book.id).innerText = 'Amount left: ' +book.amount;

            }
            editBookHash = event.data;
        }
    };


    let deleteBookStream = new EventSource("/deleteBook");
    deleteBookStream.onerror = function (event) {

    };
    deleteBookStream.onopen = function (event) {

    };
    deleteBookStream.onmessage = async function (event) {
        if (event.data !== deleteBookHash) {
            let call = await fetch("/getDeleteBook", {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Accept': 'application/json'
                },
            });
            let result = await call.json();
            for (let bookId of result) {
                document.getElementById('topSeller' + bookId).remove();
                document.getElementById('newBook' + bookId).remove();
            }
            deleteBookHash = event.data;
        }
    };

    let addBookStream = new EventSource("/addBook");
    addBookStream.onerror = function (event) {

    };
    addBookStream.onopen = function (event) {

    };
    addBookStream.onmessage = async function (event) {
        if (event.data !== addBookHash) {
            let call = await fetch("/getAddBook", {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Accept': 'application/json'
                },
            });
            let result = await call.json();
            for (let book of result) {
                let bookCategory;

                if(book.bookCategory == null) bookCategory = 'No genre';
                else bookCategory = book.bookCategory.name;

                let template = '<div class="card border-primary" id="topSeller' + book.id + '" style="min-width: 225px; min-height: 400px; max-width: 225px; margin:5px">\
                    <img onerror="this.src=\'/images/placeholder.png\'" class="card-img-top" style="padding-left: auto; \
                    padding-right: auto;" src="http://localhost:8081/images/book' + book.id + '.png">\
                    <div class="card-body">\
                    <h5 class="card-title">' + book.author + '. ' + book.name + '</h5>\
                    <p class="card-text">Genre: '+ bookCategory + '</p>\
                    <p class="card-text">Price: '+ book.price + '$</p>\
                    <p class="card-text">Amount left: '+ book.amount + '</p>\
                    </div>\
                    </div>';

                let previous = document.getElementById('newBooksDeck').innerHTML;
                document.getElementById('newBooksDeck').innerHTML = template + previous;
            }
            addBookHash = event.data;
        }
    };


function showServiceSwitch(serviceOn, serviceOff) {
    document.getElementById("serviceOn").style.display = serviceOn;
    document.getElementById("serviceOff").style.display = serviceOff;
}



//TODO IF STREAMS RETURNS ZERO/NULL ITS PROBABLY THAT SERVERS ARE DEAD FOR NOW, DO SOMETHING ON FRONT