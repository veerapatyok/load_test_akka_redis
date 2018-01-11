import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.stream.ActorMaterializer
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport
import redis.RedisClient

import scala.concurrent.{ExecutionContextExecutor, Future}
import scala.util.{Failure, Success}

object MainServer extends App with FailFastCirceSupport {
  implicit val system: ActorSystem = ActorSystem("load_test_redis")
  implicit val materializer: ActorMaterializer = ActorMaterializer()
  implicit val executionContext: ExecutionContextExecutor = system.dispatcher

  val redis = RedisClient()
  val route: Route = (get & pathPrefix("redis" / IntNumber) & pathEndOrSingleSlash) { count =>
    val pub = (1 to count).map(key => redis.set(key.toString, 0))

    onComplete(Future.sequence(pub)) {
      case Success(_) => complete("data" -> "success")
      case Failure(e) => complete(400, e.getMessage)
    }
  }

  Http().bindAndHandle(route, "0.0.0.0", 9000)
}
