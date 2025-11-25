Stephen's changes 11/24/25:

-In Cart.java
	-In checkOut() method:
		-Fixed taxrate/taxRate variable mismatch in checkOut() method
		-Added customer address confirmation and function for updating customer's address.
		-Added menu display and input for customer payment information.
	-In cancelTransaction() method:
		-Fixed iteration error. Cannot remove items directly from item list while iterating through it.
		 Created temporary array at beginning of loop to iterate through.
	-In addItems() method:
		-Added check to verify that the inventory has sufficient quantity in stock and print error message otherwisee.
		-Added loop to add selected quantity to customer's cart.
		-Added function to decrement the amount of items in inventory when customer adds to cart.

-In OnlineStore.java
	-In CreateAccount() method:
		-Changed default tax rate from 0% to 9%.
		-Added address parameter.
		
-In Customer.java
	-Changed Customer constructor to include address parameter.
	
-In Main.java
	-Added menu items to accept customer's address at time of account creation.
	-Added method DisplayCustomerHomepage().
	
		
	