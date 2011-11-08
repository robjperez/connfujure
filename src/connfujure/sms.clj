(ns connfujure.sms)

(defrecord Sms [from to body])

(defprotocol ISmsChannel
  (do-new [this ^Sms post] "callback for sms-event"))