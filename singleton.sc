//> using dep io.kubernetes:client-java:21.0.1
import io.kubernetes.client.openapi.apis.CoreV1Api
import io.kubernetes.client.util.Config

import io.kubernetes.client.openapi._
import scala.jdk.CollectionConverters._

val client = Config.defaultClient()
Configuration.setDefaultApiClient(client)

val namespace = sys.env.getOrElse("TARGET_NAMESPACE", "default")

val api = new CoreV1Api()
val list = api.listNamespacedPod(namespace).execute()

list.getItems().asScala.foreach { pod =>
  println(pod.getMetadata.getName)
}
