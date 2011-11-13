(ns connfujure.twitter
  (use connfujure.event)
  (import connfujure.event.Event))



(defrecord Tweet [from to content])
(defn tweet-from-message [{{from :id content :content {to :user_mentions } :entities } :object}]
  (Tweet. from to content))

(defprotocol ITwitterChannel
  (on-new [this tweet] "event handler for new tweets"))

(defn twitter-event [message]
  (if (vector? message)
    (let [{ {source :source} :backchat} message]
      (if (= (.toUpperCase source)  "TWITTER")
        (let [data (tweet-from-message message)]
          (Event. :twitter data on-new ))))))

