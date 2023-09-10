/**
 * 
 */
console.log("this is script file")
//alert("hiiiii....")

const t1 = () => {
	
	if($(".sidebar").is(":visible"))
	{
		$(".sidebar").css("display","none");
		$(".content").css("margin-left","0%");
		
	}
	
	else{
		$(".sidebar").css("display","block");
		$(".content").css("margin-left","20%");
	}
	
	
};


const search=()=>
{
	
	let query=$("#search-input").val();
	
	
	if(query=='')
	{
		$(".search-result").hide();
	}
	else{
		console.log(query);
		
		let url=`http://localhost:8191/search/${query}`;
		
		fetch(url).then((response)=>{
			return response.json();
		}).then((data)=>
		{
			console.log(data);
			
			let text=`<div class='list-group'>`
			
			data.forEach(contact=>
			
			{
				text+=`<a href='/user/${contact.cid}/contact' class='list-group-item list-group-action'>${contact.cname} </a>`
			});
			
			
			text += `</div>`;
			
			$(".search-result").html(text);
			$(".search-result").show();
			
		});
		
		
		
		
	}
	
	
};





const paymentstart=()=>
{
	console.log("Payment started");
	let amount=$("#payment_field").val();
	console.log(amount);
	if(amount==''|| amount==null)
	{
		swal("Failed!", "Amount is required", "error");
		return;
	}
	
	
	
	$.ajax(
		
		{
			
			url : "/user/create_order",
			data:JSON.stringify({amount:amount,info:'order_request'}),
			contentType:'application/json',
			type:'POST',
			dataType:'json',
			success:function(response){
				console.log(response);
				if(response.status=="created")
				{
					let options={
						
						key:'rzp_test_l3HtCZnAksZm5W',
						amount:response.amount,
						currency:'INR',
						name:'SMART CONTACT MANGER DONATION',
						description:'you can donate also',
						image:'https://learncodewithdurgesh.com/_next/image?url=%2F_next%2Fstatic%2Fmedia%2Flcwd_logo.45da3818.png&w=640&q=75',
						
						order_id:response.id,
						handler:function(response)
						{
							console.log(response.razorpay_payment_id);
							console.log(response.razorpay_order_id);
							console.log(response.razorpay_signature);
							console.log('payment successful');
							
							
							updatepayment(response.razorpay_payment_id,response.razorpay_order_id,'paid');
							
							
							
						},
						prefill: {
                   name: "",
                  email: "",
                contact: ""
                       },
                       
                       
                       notes: {
                  address: "Razorpay Corporate Office"

                          },
                          
                          theme: {
                   color: "#3399cc"
                   },
							
					};
					let rzp= new Razorpay(options);
					
					rzp.on('payment.failed', function (response){
console.log(response.error.code);
console.log(response.error.description);
console.log(response.error.source);
console.log(response.error.step);
console.log(response.error.reason);
console.log(response.error.metadata.order_id);
console.log(response.error.metadata.payment_id);

swal("Failed!", "Oops payment failed", "error");
});
					
					
					
					rzp.open();
					
					
					
					
				}
				
			},error:function(error)
			{
				console.log(error);
				alert("Something went wrong!!");
			}
			
	
	
	
}
)
};



function updatepayment(payment_id, order_id,status)

{
	$.ajax({
		
			url : "/user/update_order",
			data:JSON.stringify({payment_id: payment_id,order_id: order_id,status:status}),
			contentType:'application/json',
			type:'POST',
			dataType:'json',
		     success:function(response)
		     {
				 swal("Good job!", "Thank you for donating!", "success");
				 
				 
				 
			 },error:function(error){
				 
				 
				 swal("Failed!", "Payment done, but still not updated on db", "error");
			 },
		     
		
		
		
	});
}