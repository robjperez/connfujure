(ns connfujure.call
  (require connfujure.event)
  (import connfujure.event.Event))

(defrecord Call [from to content])
(defn call-from-message [{:keys [from to newTopic ]}]
  (Call. from to newTopic))

(defprotocol ICallChannel
  (on-join [this call] "Event for join to call")
  (on-leave [this call] "Event for leaving a call")
  (on-new-topic [this call] "Event for new topic" ))

(defn call-event [message [type content]]
  (if (vector? message)
    (let [[type content] message
          type (.toUpperCase type)]
      (if (#{"JOIN" "LEAVE" "NEW_TOPIC" } type)
                (let [ fun (case type
                 "JOIN" on-join
                 "LEAVE" on-leave
                 "NEW_TOPIC" on-new-topic)]
                  (Event. :call content fun))))))