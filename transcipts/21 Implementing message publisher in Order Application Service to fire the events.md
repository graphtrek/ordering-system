now I will refactor the application service code.

Please be sure you have initialized a Git repository,

and switched to do branch, publish event, option one.

First, I will create a new class, order create helper.

I will annotate this class with component annotation

and add SLF4J annotation.

Here, I will add five fields:

order domain service,

order repository,

customer repository,

and restaurant repository,

and finally, the data mapper.

Then, I will inject these fields

using constructor injection.

And then, I will add a new methods, persist order,

with order create command parameter,

and return a order created event.

I will make these methods transactional

and copy the order persist code here

from the order create methods.

I will get three private methods also here.

And persist order into database

and return the order created event.

Here, for save order, I won't use the return order anymore.

Let's also add an info log message

before returning to order created event here.

Then, in the order command handler class,

I will delete the private methods,

and simply inject the order create helper,

and then call persist order methods.

Here, I will only leave the order data mapper

in the constructor next to the order create helper.

Now, let's call the order create helper persist methods,

and get the order created event.

I will use the order object inside the domain event here,

in the order to create order response methods.

Then, I will log an info message before return statements.

Now, I can inject order created

payment request message publisher.

And in the create order methods,

I can call the published methods

with the order created events parameter.

I will remove the transactional annotation

from the create order methods,

because I already made

the persist order methods transactional,

as you see here.

This will make sure that,

after saving the order into persistent store,

I published the order created event.

Note that the implementation

of this publisher will be in order messaging module,

that we will implement in the coming lectures,

but in the domain layer,

I don't need to think

about these details of this publishing,

like using Kafka or some other solution.

These are the details,

and I will implement them in the order messaging module.

Here, since I used the default Spring proxy AOP,

all AOP functionality provided by Spring,

including transactional,

will only be taken into account

if the call goes through the proxy.

That means the annotated methods should be invoked

from another beam.

That's why I created a new components

order create helper here,

so in the order create command handler,

if I create a new methods with transactional annotation,

and call from the create order methods,

the transactional annotation will not function.

Also, the methods with transactional annotation

must be public, otherwise the transactional annotation

will not function again.

Note that if you use AspectJ mods,

instead of Spring Proxy AOP,

you won't have this limitation.

However, to use that, you need

to include and configure the AspectJ library.

All right.

Here, I also want to show you

an alternate approach using Spring Framework

for the event publishing operation.

There is a transactional event listener annotation

in Spring, which listens an event

that is fired from a transactional method,

and it only process the events

if the transactional operation is completed successfully.

Let's implement this also,

although this is not required in this case,

you may be in some situations

that you can use this way of event handling

in your applications.

For this, I will run Git add dot command,

and then commit my local changes using Git commit command.

Then, I will use Git checkout master command

to switch back to the master branch.

Here in the master branch,

now I will create a new branch,

publish event option two,

so I will use Git checkout minus B publish event option two.

Now in this new Git branch,

I will create a new class,

application domain event publisher.

I will make this class a Spring-managed bin

using the components annotation,

and also I will add SLF4J annotation.

Then, I will implement application events publisher aware

from Spring Framework

and override set application events publisher methods.

And also, I will implement

the domain event publisher interface

from the common domain module.

As the generic type,

I will use order created events

and override the publish methods.

Then, I will create a field

with type application event publisher,

and set it in the set application event publisher methods.

Then, in the published methods implementation

of domain event publisher,

I will call to publish events methods

on application event publisher object.

Let's also log an information message here,

after publishing the message.

This publisher can be used

for any type of events in the order service.

Now, I will create another class,

order created event application listener.

I will mark this class as a component,

and add SLF4J annotation.

And then, inject payment request message publisher fields

using constructor injection.

Then, I will create a methods, process,

and set the order created event as parameter.

In these methods

I will use transactional event listener annotation,

and with this annotation,

these methods will only be called

when the method

that publishes the application event publisher

is completed, and the transaction is committed.

So, I will go to order create command handler,

and inject application domain event publisher.

And then, call the publish methods on these objects

in the create order methods.

I have the listener

of these application domain event publisher,

which is the process methods

in the order created events application listener,

and since I used the transactional event listener,

its listener methods will only process

when they create order method's completed,

and the transaction is committed.

Great, after explaining transactional event

listener of Spring,

let's switch back to do master branch.

I will commit to changes here,

and then check out to master branch.

I will continue with the first approach,

instead of using the transactional event listener,

as the first approach doesn't have

the additional application published step,

and it simply uses the method calls.

So, I will use Git merge publish event option one command

onto master branch

to merge the changes into the master branch.

All right, so I will continue

with the implementation of order track commands handler

in the next lecture.

