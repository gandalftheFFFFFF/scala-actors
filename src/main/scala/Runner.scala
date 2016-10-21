import scala.math.BigInt
import akka.actor.ActorSystem
import akka.actor.Actor
import akka.actor.Props
import akka.routing.{ ActorRefRoutee, RoundRobinRoutingLogic, Router }
import akka.actor.ActorRef

case class Computation(n: Int)
case class Answer(answer: Int)
case class Terminated(a: Actor)

class Calculator extends Actor {
  val worker = context.system.actorOf(Props[Worker], "worker")
  var router = {
    val routees = Vector.fill(3) {
      val r = context.actorOf(Props[Worker])
      context watch r
      ActorRefRoutee(r)
    }
    Router(RoundRobinRoutingLogic(), routees)
  }
  def receive = {
    case c: Computation => router.route(c, sender()) 
    case Answer(a) => println("42 is the answer!")
  }
}

class Worker extends Actor {
  def receive = {
    case Computation(n) =>
      println("Answer f(" + n + ") = " + f(n) + "\n")
  }

  def f(n: Int): BigInt = {
    def help(n: Int, sum: BigInt): BigInt = n match {
      case 1 => sum
      case _ => help(n - 1, sum * n)
    }
    help(n, 1)
  }
}
    
object Runner extends App {
  val system = ActorSystem("TestSystem")

  val testActor = system.actorOf(Props[Calculator], name = "testActor")

  
  val first = testActor ! Computation(1234) 
  val second = testActor ! Computation(1000)
  val third = testActor ! Computation(200)
  val fourth = testActor ! Computation(10)
  val answer = testActor ! Answer(42)
}
