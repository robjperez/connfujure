(ns connfujure.handler
  (use [connfujure.client :only (do-request)])
  (use [connfujure.sms :only (sms-event)])
  (use [connfujure.rss :only (rss-event)])
  (use [connfujure.twitter :only (twitter-event)])
  (use [connfujure.voice :only (voice-event)])
  (require connfujure.event)
  (import connfujure.event.Event))

(defn dispatch-event [message]
  (or
   (sms-event message)
   (rss-event message)
   (twitter-event message)
   (voice-event message)
      ))

(defn lazy-event-seq [TOKEN] (filter identity (map #(dispatch-event %) (do-request TOKEN))))

(def channel-runner (agent nil))

(defn execute [handler {:keys [channel data fun]}]
  (let [channel-handler (channel handler)]
    (if channel-handler
      (fun (channel handler) data)))) 
   
(defn listen-events [TOKEN handler]
  (dorun (map (fn [event] (send channel-runner (fn [_] (execute handler event)))) (lazy-event-seq TOKEN))))
 