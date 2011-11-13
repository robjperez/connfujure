(ns connfujure.utils.httpclient
  (import org.apache.http.impl.client.DefaultHttpClient)
  (import org.apache.http.client.methods.HttpGet)
  (import org.apache.http.conn.scheme.Scheme)
  (import org.apache.http.conn.ssl.SSLSocketFactory)
  (import org.apache.http.conn.ssl.TrustStrategy)
  (import java.security.KeyStore)
  (import java.io.InputStreamReader)
  (import java.io.BufferedReader)
  )


(def ^{:private true} insecure-socket-factory
  (doto (SSLSocketFactory. (reify TrustStrategy
                             (isTrusted [_ _ _] true)))
    (.setHostnameVerifier SSLSocketFactory/ALLOW_ALL_HOSTNAME_VERIFIER)))

(defn connect [TOKEN]
  (let [client (DefaultHttpClient.)
        method (HttpGet. "https://stream.connfu.com/connfu-stream-testing-emc2")]
    (do
      (-> client
           (.getConnectionManager)
           (.getSchemeRegistry)
           (.register (Scheme. "https" 443 insecure-socket-factory)))
      (doto method
        (.setHeader "authorization" (str "Backchat " TOKEN)))
      (-> (.execute client method)
          (.getEntity )
          (.getContent )
          (InputStreamReader.)
          (BufferedReader.)))))