(ns connfujure.client
  (use connfujure.utils.httpclient)
  (use [ clojure.data.json :only (read-json)]))

(defn parse-data [data]
  (if (not (empty? data))
    (read-json data)))
        

(defn do-request [ TOKEN]
  (let [conn (connect TOKEN)]
      (filter #(not (nil? %)) (map parse-data (repeatedly #(.readLine conn))))))

