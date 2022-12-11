Instructor: Now I will continue

with OrderTrack command handler implementation.

Here I will inject DataMapper and OrderRepository.

I will explicitly state

that this will be a read only transaction

on TrackOrder methods.

So I use a transactional annotation which reads

on the option here.

I will not have any business logic in this class.

There is just an existence check.

So no domain service call is necessary here.

And I will simply use the repository methods

and return from this methods for simple.

So let's call find by tracking ID on orderRepository object.

I need to get the tracking ID

from the TrackOrderQuery object.

I will simply call the tracking ID constructor

and get the order tracking ID fields

from TrackOrderQuery object.

Here I will get an optional order.

And if this optional object is empty,

I will log a message, and throw OrderNotFoundException.

So let's create a new exception class.

OrderNotFoundException next to the order domain exception,

and throw it here.

I will create OrderNotFoundException

and extend the domain exception class.

Then I will throw this OrderNotFoundException

in this method.

I will create a new method on DataMapper,

order to TrackOrderResponse,

and map the order object to TrackOrderResponse object

by setting tracking ID, order status, and failure messages.

Then to return the TrackOrderResponse

in track order methods,

I will call the order to TrackOrderResponse methods

and convert the order object to TrackOrderResponse.

Right, we have completed the order replication service.

Now I will also implement the domain event listener parts.

First I will create payment response

message listener input class,

and implement PaymentResponseMessageListener interface.

I'll use service, validated, and Slf4j annotations.

Then similarly,

I will create RestaurantApprovalResponseMessageListener

input class and implement

RestaurantApprovalResponseMessageListener interface.

Again, I will add the same service, validated,

and Slfj4 annotations.

As mentioned,

these listener implementations will be triggered

by the domain events from other bonded contexts,

payment and restaurant service.

As I used command handler in the order application service,

here I will use two different classes

to handle these listeners,

and actually they will complete the saga pattern

in our architecture.

So for now I will park here,

and continue with the implementation

of these domain event listeners in the SAGA section.

Great.

Leaving the listener implementations to do SAGA section.

I have completed the implementation

of order service domain layer.

Let's write some tests on the next lecture

to test the functionality of the domain layer.

Then we can continue

with the Kafka section to integrate the services with Kafka.

So I will see you in the next lecture.

