(ns connfujure.sms
  (require connfujure.event)
  (import connfujure.event.Event ))

(defrecord Sms [from to content])

(defn sms-from-message [{:keys [from to message]}]
  (Sms. from to message))


(defprotocol ISmsChannel
  (on-new [this post] "callback for sms-event"))

(defn sms-event [message]
  (if (vector? message)
    (let [[type content] message ]
      (if (= (.toUpperCase type) "SMS")
        (let [data (sms-from-message content) ]
          (Event. :sms data on-new ))))))