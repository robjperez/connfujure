(ns connfujure.rss
  (require connfujure.event)
  (import connfujure.event.Event))

(defrecord Rss [name from content])

(defn rss-from-message [{content :title {from :id} :actor {name :bare_uri} :backchat}]
  (Rss. name from content))
 
(defprotocol IRssChannel
  (on-new [this post] "Handler for rss")
 )


(defn rss-event [message]
  (if (map? message)
    (let [{ {source :source }:backchat } message ]
      (if (= "WEBFEED" (.toUpperCase source))
        (Event. :rss (rss-from-message message) on-new)))))

