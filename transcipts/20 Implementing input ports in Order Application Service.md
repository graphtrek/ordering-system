Instructor: Hi, everyone.

Now, it is time to create the Implementation Classes

in the OrderApplicationService.

Remember, I need to implement the input parts here,

in the OrderApplicationService.

First, I will create OrderApplicationServiceInput class.

And, implement order application service interface,

and then override the methods.

In this class I will use Spring Service annotation

and mark this class as a Spring Bean.

As you see, I started using Spring Framework abilities

on the application service.

Also, I will let validated annotation

to enable valid annotations

on the interface methods that we have set.

Finally, I will add Slf4j annotation

to use a login variable automatically using Slf4j.

One thing to notice here is that,

I can use package-private access modifier

in class level instead of using public.

So, I will expose the interface to the client

of this domain layer,

but actually, I will not give the details

of the implementation.

Here, having a package-private access modifier is enough,

because the interface and the implementation

are in the same package.

In this class, I need to implement

the create order and track order methods.

I will delegate these two tasks to different classes

to keep this service shorter.

So, I will create, OrderCreateCommandHandler class.

Then I will let component annotation

to make this class a Spring-managed Bean.

Then also, I will let Slf4j annotation.

I will create a create order method

with create order command parameter

and return create order response.

And then, I will create OrderTrackCommandHandler

and use the same components and Slf4j annotations.

Then, I will create track order methods

with track order query parameter

and return track order response.

I will implement these commands and query classes in a bit.

Now, in the OrderApplicationServiceInput class,

I will inject the two command handle classes

using Constructor Injection.

And then, use OrderCreateCommandHandler

in the create order methods.

And similarly, I will use OrderTrackCommandHandler

in the track order methods.

Now, I can go to OrderCreateCommandHandler class.

Here, I will use the input commands object

and call the required methods to create an order.

First, I will inject the required dependencies.

Remember that, I need to call the domain service

from this application service.

So, first, I will create order domain service field.

Then I will create the repository fields.

I need order repository to save the order into database.

I also need customer and restaurant repositories

to complete the business logic.

And, I need to use data mapper class

to convert the data transfer object

to domain objects and vice versa.

Let's use Constructor Injection to inject these fields.

Now, in the create order methods.

First, I will let Spring Transactional annotation

because, this create order operation

has to be a transactional operation.

Let's start with the implementation.

First, I want to check if customer of this order exists.

So, I will create a method, check customer,

and parse the customer id

getting it from the create order command.

In the check customer methods

I will call, find customer methods of customer repository,

using the customer id's parameter

and getting an optional customer object.

If this optional object is empty

I will directly throw an order domain exception

after logging a warn level log message.

Note that, if I had to do more business checks

with the customer object,

I would pass it to the domain service

and do the business logic checks there.

However, just to check the availability of a customer,

I don't need to pass it to the domain service.

If it doesn't exist, just throw an exception here

early in the data flow.

Then I will create a check restaurant methods

and parse the create order command object as parameter

and return back a restaurant object.

In these methods,

first, I need to convert create order commands

into a restaurant object.

To do that, I will create a methods

in the data mapper class named,

create order commands to restaurant.

So, I will take a create order commands

here in this method and return, restaurant object.

I will use restaurant builder methods and set restaurant id.

And then, set a list of products by getting the id's

from the product objects in the order item

which is in, create order commands object.

As you see, I only set the id of the product here

as I don't have the product price information.

That, I will get from the restaurants repository.

For this, I will create a new constructor

in Product entity, only with the id property.

Then, I will convert the stream

to a list of product entities using collect methods here.

Let's call the data mapper methods

and get the restaurant object.

Then, I will call find restaurant information methods

of restaurant repository

and parse the restaurant object as parameter.

This will return an optional restaurant.

If it is empty, I will throw an order domain exception

with a proper message here.

And, before that, I will log a warning message.

Then, I will return the restaurant object

by getting it from the optional object here.

Now, I can return to create order methods.

To validate and save an order,

I want to create an order object

from the create order command.

To do that, I will add another methods

in the data mapper class, create order commands to order,

and then create an order objects in these methods.

As you see, I'm using this order data mapper class

to create the domain objects

from the inputs data transfer objects and vice versa.

In the domain-driven terms

you may see this as the Factory because,

I delegate the object conversion and creation operations

to this mapper class.

Now, to create the order object,

I will use the builder.

Set customer id and then restaurant id.

As you see, I create and set the value objects

to hold the customer and restaurant id values.

And, I will set the delivery address.

And, for that, I will create a new private method here,

order address to street address.

And, convert order address

to the street address object by getting a random UUID,

and setting street, also code, and city string values.

Then, I will set price by creating a money value object.

And finally, I will set the order items.

For this one, I will create a new methods,

order items to order item entities.

And, in this methods I will set the order items.

I will map the inputs order item list

to an order item.

And, set products using product entity

and with the product's id value objects.

Then, set the price using money value object.

And then, set quantity.

And subtotal, again, using money value object.

Finally, I will collect these order items

into a list of order item entities.

Then, I build the order entity and return.

As you see here, I haven't set the order id,

tracking id, and order status.

Those are the fields that I will set

according to business rules on the domain service.

For now, I only mapped the input values

that I get from the clients and created an order object.

Remember, entities are mutable,

so, that means I can update this order object later

with the correct status

and with order and tracking id values.

If I return to create order methods,

I will call the

create order command to order methods

in the data mapper and get the order entity.

Now, I have the order entity objects

so, I will call the order domain service,

validate, and initiate order methods.

This method will take order and restaurant entities

to be used in the business logic in domain service.

As you see, the validate and initiate order methods

returns an order created domain event.

Now, if I validated and initiated order in the domain core,

I can save it using the order repository.

For this, I will create a private methods here,

save order, and parse the order as parameter,

and return to save order object.

Here, I will simply call order repository save methods

and if the returned order is null,

I will drive a domain exception.

Otherwise, I will log an info message

and return the order object.

Let's log an error message before this exception.

Now, in create order methods

I will call save order methods.

Okay, to return a response to client

I need to convert the saved order

into create order response.

To do that, I will add a new method,

order to create order response

into data mapper class.

In these methods, I will add order object as the parameter.

Here, I will create create order response

using builder pattern.

And, set tracking id

and, order status

and, I will then return the create order response object.

Now, I can log an info message

and return the create order response object.

As you can see, I skipped the event firing step.

After creating the order, I want to fire an event

to trigger payment process in payment service.

So, let's think about this.

I have saved the order into persistent store

but, also, I need to fire the order created events

that returned from the domain core module.

Remember that, during the design lectures, I mentioned,

saving into local database

and firing the event operations should be automatic.

To make these operations automatic

I will use Saga along with Outbox Patterns.

But now, at least, I want to make sure

that I saved the order into the local database first,

and then fired the event.

'Cause, the other way around,

first firing the events

and then trying to save into database,

could lead an inconsistent state easily

as mentioned previously.

So, before publishing an event,

if I want to make sure that the changes are committed

into the persistent store in the local database,

I have two options here.

Let's create a new Git branch here

named publish event option one.

For this first, I will run git init command

and create a Git repository here.

If you don't have Git installed,

please check the setup section

and install Git in your machine.

After running git init command

I will run git add dot command to add my changes

into my local Git repository.

And then, I will simply use git commit command

with minus am options and giving a message.

With this commands I will commit my changes

into local repository.

Then, I will use git checkout minus b command

and give the publish-event-option-1 as the branch name.

So, here I will create a new branch with this name

and switch to this new branch.

Here, I will publish the events

after the transactional methods returns.

To do that, I need to refactor this code a bit.

Let's continue with that in the next lecture.

