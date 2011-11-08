(ns connfujure.dsl
  (require [connfujure.sms ]))

(defn get-protocol [channel]
  (cond
   (instance? connfujure.sms.ISmsChannel channel ) :sms))

(defn connection [& channels]
    (apply assoc {}
           (mapcat
            (fn [channel] [(get-protocol channel ) channel])
            channels)))





(defmacro listen-channel [forms protocol]
  (let [ formated-forms
        (map
         (fn [[verb params method]]
           (list verb (vec (concat ['_] params)) method)) forms)]
    `(reify 
       ~protocol
       ~@formated-forms)))

(defmacro sms [& forms] `(listen-channel ~forms connfujure.sms/ISmsChannel )) 
     