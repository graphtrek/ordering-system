Ali: Hi everyone.

As we have discussed previously,

having an isolated domain layer,

along with ports and adapters,

it leads to an easily testable application.

In this lecture, I will create some tests

to see how you can test your domain logic

while mocking the XML dependencies.

I will use Spring Boot's test library with Mockito

to write unit tests for the order service domain logic.

First, I will go to base pom.xml file

and add Mockito core dependency

With the current latest version which is 4.3.1.

I will use scope test for this dependency

as it is only required for tests.

I will also move the version to property section.

Now I will go to order application service module

and add this Mockito core dependency into the pom.xml file.

And next to this, I will also add

Spring Boot starter test dependency with test scope.

So these two dependencies are the required ones

to write unit tests.

Now, before starting to write tests,

I will update the orderDataMapper class,

and add string message parameter

to the orderToCreateOrderResponse method.

This way, I will have this successful message

in my CreateOrderResponse,

and I will test this message as well.

I will then change the order create command handler,

CreateOrder method,

and add a successful message as the second parameter

to the orderToCreateOrderResponse method call.

Now I will first create com.food.ordering.system,

order service domain package under test java folder.

Then I will create orderTestConfiguration class,

and mark this class with Spring Boot application annotation,

and use BasePackages com.food.ordering.system.

I have added this annotation to start a Spring context

and inject the beans to test my test classes easily.

As we don't have the Spring Boot main class yet in the code,

I only implemented the core domain logic.

In this configuration,

I will create my Spring beans as mock beans.

Let's start with publishers.

I will create a method

OrderCreatedPaymentRequestMessagePublisher

and return mock OrderCreatedPaymentRequestMessagePublisher

object using Mockito mock method.

I will use Maven reload

to load to new Mockito library classes.

Here I need to use bean annotation

to create a Spring bean from this mock object.

Similarly I will create

OrderCancelledPaymentRequestMessagePublisher

and mock this object.

And then I will create

OrderPaidRestaurantRequestMessagePublisher method

to mock this interface using the Mockito mock method.

I will then add OrderRepository method,

and create a mock bean from the orderRepository interface.

I will create two more mock beans

for CustomerRepository and RestaurantRepository.

Remember these are the output ports

that will be implemented by adapters in XML modules,

so I need to mock them here to test the business logic.

Finally, I'll also create a real bean

for OrderDomainService.

I will return OrderDomainServiceImpl class

so that it will be created and injected

to my test classes when I autowire it.

Note that this OrderDomainService

is an order domain core and it is plain old Java object

because I didn't use Spring dependency

and annotations in the domain core module.

So to be able to inject this domain service here,

I need to create a Spring bean for that.

I will do the same in the application bean configuration

when implementing the order container module

in the coming lectures.

Great, let's create the test class,

OrderApplicationServiceTest.

Here I'll let Spring Boot test annotation

and include OrderTestConfiguration class in this annotation.

So this test will basically use

the OrderTestConfiguration class to create the mock beans.

I will also use TestInstance annotation

with per class option to create a single instance

of this test class.

By default, for each test method,

a new instance of the class is created.

In that case, to be able to use beforeAll method,

you need to use a static method.

And to initialize the fields in this beforeAll method,

you need to use static for each field.

But I want to use beforeAll without the static modifiers,

and for this, here I use per class test instance

that will allow this.

I will autowire OrderApplicationService

and OrderDataMapper here.

Those are the real Spring beans,

so they will be injected

including all dependent Spring beans.

And then I will autowire OrderDataMapper

which is also a real Spring component.

And I will also autowire OrderRepository,

CustomerRepository, and the RestaurantRepository.

Those are actually will be the mock beans

because when I use @Autowired,

Spring will check the bean definitions

in the OrderTestConfiguration class

and see them as the mock beans.

Now for different test scenarios,

I will create the global fields.

I will let three CreateOrderCommands,

one for successful create order, and two for wrong input.

I'll also add customer ID,

restaurant ID,

product ID,

and order ID fields.

For UUID fields, I will give some random value

and I will add a BigDecimal price field with value 200.

Before writing test methods,

I will add an init method with beforeAll annotation.

So this code will initialize the input fields

and define the mock beans' behaviors

before running the test.

Here I set the three CreateOrderCommand fields.

First one has correct input.

The second CreateOrderCommand has a wrong total price.

And the third one has a wrong product price.

These objects aligns with the JSON inputs

that the application will receive from the REST alliance

as you can see here.

Then here I have a customer entity with CustomerId,

and then have a restaurantResponse with restaurantId,

products list and restaurant active fields.

And I create an order entity from the CreateOrderCommand

and set a random OrderId.

Now let's look at the create order data flow

starting from application service.

As you see, I have a method on the customerRepository

to get the customer object.

Since I have a mock customerRepository,

I need to mock findCustomer method,

so I use Mockito when method,

and if findCustomer is called,

I'll tell to return optional customer object,

the object that I created above.

Similarly I have findRestaurantInformation method call

in the create order flow,

and for this I will use Mockito when method

and return optional restaurant response object.

And when orderRepository.save method

is called for any order,

I will return the order object that I created here.

So I have created three Mockito constructs

for the repositories here as you can see.

All right, I will continue with creating

the test methods in the next lecture.