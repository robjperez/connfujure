(ns connfujure.sms
  (require connfujure.event)
  (import connfujure.event.Event )
  (require connfujure.message)
  (import connfujure.message.Message))

(defrecord Sms [from to content])

(defn create-message [{:keys [ appId from to message]}]
  (Message. appId message from to :sms ))


(defprotocol ISmsChannel
  (on-new [this post] "callback for sms-event"))

(defn sms-event [message]
  (if (vector? message)
    (let [[type content] message ]
      (if (= (.toUpperCase type) "SMS")
        (let [data (create-message content) ]
          (Event. :sms data on-new ))))))