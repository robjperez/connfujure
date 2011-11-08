(ns connfujure.dsl
  (require [connfujure.sms]))

(defn get-protocol [channel]
  (cond
   (instance? connfujure.sms.ISmsChannel channel ) :sms))

(defn connection [& channels]
    (apply assoc {}
           (mapcat
            (fn [channel] [(get-protocol channel ) channel])
            channels)))




(defmacro sms [& forms]
  (let [ formated-forms
        (map
         (fn [[verb params method]]
           (list (symbol (str "do-"  verb)) (vec (concat [ '_ ] params)) method)) forms)]
    `(reify 
       connfujure.sms/ISmsChannel
       ~@formated-forms)))
     