(ns connfujure.utils.uriconn
  (import java.io.BufferedReader)
  (import java.io.InputStreamReader)
  (import java.net.URL)
  (import javax.net.ssl.SSLContext)
  (import javax.net.ssl.HttpsURLConnection)
  (import javax.net.ssl.X509TrustManager)
  (import java.security.cert.X509Certificate)
  (import javax.net.ssl.HostnameVerifier)
  )

(def ^{:private true} verifier
  (reify
    HostnameVerifier
    (verify [_ _ _] true)))

(def ^{:private true} trust-manager
  (reify
    X509TrustManager
    (checkClientTrusted [_ _ _] )
    (checkServerTrusted [_ _ _] )
    (getAcceptedIssuers [_] (make-array X509Certificate 0)))) 
                                   
(def ^{:private true} insecure-socket-factory
  (let [ context (SSLContext/getInstance "TLS") ]
    (do
      (doto context
        (.init nil (into-array [trust-manager]) nil))
      (.getSocketFactory context))))

(defn do-request [TOKEN]
  (let [url (URL. "https://stream.connfu.com/connfu-stream-testing-emc2")
        conn (.openConnection url)]
    (do
      (HttpsURLConnection/setDefaultHostnameVerifier verifier)
      (HttpsURLConnection/setDefaultSSLSocketFactory insecure-socket-factory)
      (doto conn
        (.setRequestProperty "Authorization" (str  "Backchat "  TOKEN))
        (.setDoOutput true)
        (.setDoInput true))
      (-> conn (.getInputStream) (InputStreamReader.) (BufferedReader.)))))
      
        