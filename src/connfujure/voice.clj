(ns connfujure.voice
  (require connfujure.event)
  (import connfujure.event.Event)
  (require connfujure.message)
  (import connfujure.message.Message))


(defn voice-to-message  [{:keys [from to newTopic ]}]
  (Message. 1234 newTopic  from to :voice))

(defprotocol IVoiceChannel
  (on-join [this post] "voice on join callback")
  (on-leave [this post] "voice on leave callback")
  (on-new-topic [this call] "Event for new topic" ))

(defn voice-event [message [type content]]
  (if (vector? message)
    (let [[type content] message
          type (.toUpperCase type)]
      (if (#{"JOIN" "LEAVE" "NEW_TOPIC" } type)
                (let [ fun (case type
                 "JOIN" on-join
                 "LEAVE" on-leave
                 "NEW_TOPIC" on-new-topic)]
                  (Event. :void (voice-to-message content) fun))))))