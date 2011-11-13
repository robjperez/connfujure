(ns connfujure.core)
(use 'connfujure.sms)

(defn application [TOKEN & rules]
  (let [s (:sms (first rules))]
    (on-new s (connfujure.sms.Sms. "hola" "a" "b"))))
