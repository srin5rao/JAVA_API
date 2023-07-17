
$(document).ready(function () {

    $('#createOrderButton').click(function(){

                                    var flag = 0
                                    var customerName = $("#customerName").val();
                                    var productName = $("#productName").val();
                                    var fullAddress = $("#fullAddress").val();
                                    var paymentType = $("#paymentType").val();

                                    if(customerName == ""){
                                    document.getElementById("errorAlert").style.visibility  = "visible";
                                    document.getElementById("errorAlert").innerHTML += "<p>Enter customer Name</p>";
                                    var flag = 1
                                    }

                                    if(productName == ""){
                                    document.getElementById("errorAlert").style.visibility  = "visible";
                                    document.getElementById("errorAlert").innerHTML += "<p>Enter Product Name</p>";
                                    var flag = 1
                                    }

                                    if(fullAddress == ""){
                                    document.getElementById("errorAlert").style.visibility  = "visible";
                                    document.getElementById("errorAlert").innerHTML += "<p>Enter Full Address</p>";
                                    var flag = 1
                                    }

                                    if(paymentType == ""){
                                    document.getElementById("errorAlert").style.visibility  = "visible";
                                    document.getElementById("errorAlert").innerHTML += "<p>Enter Payment Type</p>";
                                    var flag = 1
                                    }

                        if(flag == 0){
                                    var sendInfo = {
                                            "customer_name": customerName,
                                            "full_address": fullAddress,
                                            "product_name": productName,
                                            "payment_type": paymentType
                                        };

                                    $.ajax({
                                                    url : '/order/createOrder',
                                                    type : 'POST',
                                                    dataType : 'json',
                                                    contentType: 'application/json',
                                                    processData: false,
                                                    data: JSON.stringify({ "customer_name": $("#customerName").val(), "full_address": $('#fullAddress').val(), "product_name": $('#productName').val(), "payment_type": $('#paymentType').val() }),
                                                    success : function(msg) {
                                                        if(msg.statusCode == 400){
                                                            document.getElementById("errorAlert").style.visibility  = "visible";
                                                            document.getElementById("errorAlert").innerHTML += "<p>"+msg.message+"</p>";
                                                            document.getElementById("successAlert").style.visibility  = "hidden";

                                                        }
                                                        else{
                                                            document.getElementById("successAlert").style.visibility  = "visible";
                                                            document.getElementById("successAlert").innerHTML += "<p>NEW ORDER CREATED</p>";
                                                            document.getElementById("errorAlert").style.visibility  = "hidden";
                                                        }
                                                    }
                                                });
                                    }

                                                  });




	$('#getAllOrdersButton').click(function(){
                                $.ajax({
                                                url : '/order/getallorder',
                                                type : 'GET',
                                                dataType : 'json',
                                                success : function(data) {
                                                    assignToEventsColumns(data);
                                                }
                                            });

                                              });



    function assignToEventsColumns(data) {
                var table = $('#orderTable').dataTable({
                    "bAutoWidth" : false,
                    "aaData" : data,
                    "columns" : [ {
                        "data" : "order_id"
                    }, {
                        "data" : "transaction_id"
                    }, {
                        "data" : "customer_name"
                    }, {
                        "data" : "discount"
                    },{
                                              "data" : "price"
                                          },
                     {
                        "data" : "full_address"
                          },
                          {
                               "data" : "payment_type"
                                     },
                                     {
                                                                    "data" : "order_status"
                                                                          }]
                })
            }

});
