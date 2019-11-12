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

    let topSellersStream = new EventSource("/topSellers");
    topSellersStream.onerror = function (event) {
        toastr.error("Connection lost!");
    };
    topSellersStream.onopen = function (event) {
        toastr.success("Connection to server established!")
    };
    topSellersStream.onmessage = async function (event) {
        if (event.data !== topSellersHash) {
            let call = await fetch("/getTopSellers", {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Accept': 'application/json'
                },
            });
            let result = await call.json();
            document.getElementById('topSellers').innerHTML += result;
            topSellersHash = event.data;
        }
    };


    let newBooksStream = new EventSource("/newBooks");
    newBooksStream.onerror = function (event) {
        toastr.error("Connection lost!");
    };
    newBooksStream.onopen = function (event) {
        toastr.success("Connection to server established!")
    };
    newBooksStream.onmessage = async function (event) {
        if (event.data !== newBooksHash) {
            let call = await fetch("/getNewBooks", {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Accept': 'application/json'
                },
            });
            let result = await call.json();
            document.getElementById('newBooks').innerHTML += result;
            newBooksHash = event.data;
        }
    };


    let editBookStream = new EventSource("/editBook");
    editBookStream.onerror = function (event) {
        toastr.error("Connection lost!");
    };
    editBookStream.onopen = function (event) {
        toastr.success("Connection to server established!")
    };
    editBookStream.onmessage = function (event) {
        document.getElementById('editBook').innerHTML += event.data + '\n';
    };


    let deleteBookStream = new EventSource("/deleteBook");
    deleteBookStream.onerror = function (event) {
        toastr.error("Connection lost!");
    };
    deleteBookStream.onopen = function (event) {
        toastr.success("Connection to server established!")
    };
    deleteBookStream.onmessage = function (event) {
        document.getElementById('deleteBook').innerHTML += event.data + '\n';
    };
};
