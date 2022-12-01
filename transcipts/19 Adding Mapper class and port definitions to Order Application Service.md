Instructor: After creating DTO classes

in the previous lesson,

now I will create a mapper package

to hold the data mapper class.

Here I will create a class order data mapper

and mark it with sprint components annotation.

This will be a sprint component

so that I can inject and use it from the service classes.

I will let the required mapper methods here

while implementing the services.

Create, the last package I will create is the ports package.

This package will hold the ports

in the hexagonal architecture.

Remember, each of these ports

will have the corresponding adapters

either in the domain layer

or in one of the infrastructure layers.

I will create two sub packages

in the ports package, inputs and outputs.

As you remember, we have two types of ports

in hexagonal architecture.

Input ports are the interfaces

that's implemented in the domain layer

and used by the clients of the domain layer

and the output ports are the interfaces

that's implemented in the infrastructure layers

like data access or messaging modules

and used by the domain layer

to reach to those infrastructure layers.

In the input package,

I will create a sub package service

and create all the replication service interface.

Here I will add two methods.

First one is create order

and this one takes create order commands as parameter

and return create order response.

Second methods is track order

which takes track order query as parameters

and return track order response.

Here for the parameters,

I will also add valid annotation

to enable the validation annotations.

Remember, I put some validation annotations

on the fields in each data transfer object.

I have to use this annotation here

not in the implementation

because of to be in specification.

And methods preconditions

as represented by parameter constrains

must not be strengthened in the subtypes as you see here.

If I use valid annotation in the implementation,

I will get constraints declaration exception

which is true according to the be in specification.

You can see a discussion about this topic here.

Now I'm still in the input package.

Here I will create a message listener sub package

and add payments and restaurant approval packages.

On payment package,

I will create an interface payment response message listener

and add two methods,

payments completed and payments canceled.

As a parameter I use payment response for both methods.

This payment canceled methods can be called

in case a payment is failed

because of a business logic invariant

but it can be a response to the payment cancel request

as part of the saga rollback operation.

I will go into the details of these implementation

then completing this saga pattern.

Right now, I will let also

a restaurant approval response message listener interface

and add order approach

and order rejected methods.

As parameter to these methods,

I use restaurant approval response DTO objects

that I have created in the DTO package.

So, I have created three input ports.

One is order application service

that will be used by the client of this application,

like the postman calls

that I will make to initiate an order.

The second and third input ports are the message listeners

for payments and restaurant approvals.

Remember, I have mentioned

that domain event listeners

are special type of application services

and they triggered by domain events,

not by the clients.

As you will see when I implement

the payment in a restaurant services,

I will raise domain events on those services

and it'll trigger the message listeners here

in the order service.

Now, I will create output ports.

For that, first I will create a sub package repository.

Here I will create all repository interface.

First methods I will add here is save methods

that takes an order and return an order.

Here the order class is the entity class

that I created in the domain core module.

So as you see, I passed the domain entity to do repositories

and it'll be the repository implementations responsibility

to convert this order entity objects

into JPA entity objects and save into the database.

Then I add find by tracking ID methods plus tracking ID

as the parameter and return an optional order.

Here I use optional because

I may or may not find an order with that tracking ID.

Second repository interface is restaurant repository.

Here I will add find restaurant information methods

with restaurant as the parameter

and return an optional restaurant object with details.

Here I will pass the ID of the products

in the restaurant objects

and I expect to return the details of the products,

including the name and price.

Finally, I will add customer repository interface

and add find customer methods

with UUID customer ID parameter,

and return an optional customer object.

Remember, those repository interfaces will be implemented

in the data access layers with the adapters.

I will come to these implementations in the coming sections.

Finally, I will create message publisher package

on output package

and create payments and restaurant approval sub packages.

At this point, I will go to common domain module

and under the event package,

I will create publisher package

and then create domain event publisher interface.

Here I will use a generic type T

that extends domain events interface

and I will add publish methods with the parameter of type T.

Then I will return to order replication service.

For payments, here I will create

order created payment request message publisher interface.

I will simply extend the domain event publisher interface

that I just created

and as the generic type, I will use order created events.

Then I will also create

order canceled payment request message publisher interface.

For this interface,

I will use order canceled event as the events type.

Similarly, in the restaurant approval package,

I will create

order paid restaurants request message publisher interface.

In this interface, I will extend again

the domain event publisher interface

with generic type or through page events.

So here I have created three message publishers

to publish the possible three type of events

in the order domain logic.

So actually I use a single domain event publisher interface

with a publish method,

but I also give meaningful names

for the specific publishers using different interfaces

so that I can understand why a publisher is used

by the name following the domain driven design.

Right now I will start creating the implementation classes

of order replication service.

Let's do that in the next lecture.

