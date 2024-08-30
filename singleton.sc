//> using dep io.kubernetes:client-java:21.0.1
import io.kubernetes.client.openapi.models.V1LeaseSpec
import io.kubernetes.client.openapi.models.V1ObjectMeta
import io.kubernetes.client.openapi.models.V1Lease
import io.kubernetes.client.openapi.apis.CoordinationV1Api
import io.kubernetes.client.openapi.apis.CoreV1Api
import io.kubernetes.client.util.Config

import io.kubernetes.client.openapi._
import scala.jdk.CollectionConverters._

val client = Config.defaultClient()
Configuration.setDefaultApiClient(client)

val namespace = sys.env.getOrElse("TARGET_NAMESPACE", "default")
val podName = sys.env.getOrElse("POD_NAME", throw new IllegalArgumentException("POD_NAME env var is required"))

val coordApi = new CoordinationV1Api()
val lease = V1Lease()
  .metadata(
    new V1ObjectMeta()
      .name("singleton-lease")
  ).spec(
    new V1LeaseSpec()
      .holderIdentity(podName)
      .leaseDurationSeconds(30)
  )

coordApi.createNamespacedLease(namespace, lease)
