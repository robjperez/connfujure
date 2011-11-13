(ns connfujure.rss
  (require connfujure.event)
  (import connfujure.event.Event)
  (require connfujure.message)
  (import connfujure.message.Message))


(defn create-message [{content :title {from :id} :actor {id :bare_uri} :backchat}]
  (Message. id content from nil :rss))
 
(defprotocol IRssChannel
  (on-new [this post] "Handler for rss")
 )


(defn rss-event [message]
  (if (map? message)
    (let [{ {source :source }:backchat } message ]
      (if (= "WEBFEED" (.toUpperCase source))
        (Event. :rss (create-message message) on-new)))))

