(ns connfujure.sms)

(defrecord Sms [from to body])

(defprotocol ISmsChannel
  (on-new [this ^Sms post] "callback for sms-event"))