# Learning Scala Actors
===
This is a project about learning how to use Scala Actors.

In Runner.scala, I've followed Scala's own routing example and created an actor who routes messages to other actors.

It is interesting to play around with the size of the router (number of workers), and the routing logic.

## The actors
I've defined two actors: `Calculator` and `Worker`.

### Calculator
This actor holds the router and is the first to receive a message. If the message is a `Computation` it sends a message to a `Worker` in the routing queue.

### Worker
This actor does the computation and prints the result


## Use case
I figure this could be used as an example of a simple parallel execution program. For example in the context of executing BIRT reports!

