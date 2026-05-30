#!/bin/sh
set -eu

if [ -n "${AIVEN_CA_CERT:-}" ]; then
  truststore="/tmp/lin-ragagent-cacerts"
  certificate="/tmp/lin-ragagent-aiven-ca.pem"

  cp "$JAVA_HOME/lib/security/cacerts" "$truststore"
  printf '%s\n' "$AIVEN_CA_CERT" > "$certificate"
  keytool -importcert \
    -trustcacerts \
    -noprompt \
    -alias lin-ragagent-aiven-ca \
    -file "$certificate" \
    -keystore "$truststore" \
    -storepass changeit
  rm -f "$certificate"

  export JAVA_TOOL_OPTIONS="${JAVA_TOOL_OPTIONS:-} -Djavax.net.ssl.trustStore=$truststore -Djavax.net.ssl.trustStorePassword=changeit"
fi

exec java -jar /app/app.jar
