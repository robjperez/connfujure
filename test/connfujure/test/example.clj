(ns connfujure.test.example
  (use connfujure.dsl)
  )

(def token "<your token here>")
(def apl
  (conference
   (sms
    (on-new [{:keys [from to content]}] (println (str from " sends " content " to " to))))
   (rss
    (on-new [{:keys [from content]}] (println (str from " publishes " content))))))


(defn -main [token]
  (run-application token apl))