(ns connfujure.dsl
  (require [connfujure.rss])
  (require [connfujure.sms])
  (require [connfujure.twitter])
  (require [connfujure.voice])
  
  (use [connfujure.handler :only (listen-events)])) 

(defn get-protocol [channel]
  (cond
   (instance? connfujure.sms.ISmsChannel channel ) :sms
   (instance? connfujure.rss.IRssChannel channel ) :rss
   (instance? connfujure.twitter.ITwitterChannel channel) :twitter
   (instance? connfujure.voice.IVoiceChannel channel) :voice
   )
  )

(defn run-application [token conference]  (listen-events token conference))

(defn conference [& channels]
    (apply assoc {}
           (mapcat
            (fn [channel] [(get-protocol channel ) channel])
            channels)))

(defmacro listen-channel [forms protocol]
  (let [ formated-forms
        (map
         (fn [[verb params method]] 
           (list verb (vec (concat ['_] params)) method)) 
          forms)]
    `(reify
       ~protocol
       
       ~@formated-forms)))

(defmacro sms [& forms] `(listen-channel ~forms connfujure.sms/ISmsChannel ))
(defmacro rss [& forms] `(listen-channel ~forms connfujure.rss/IRssChannel ))
(defmacro twitter [& forms] `(listen-channel ~forms connfujure.twitter/ITwitterChannel))
(defmacro call [& forms] `(listen-channel ~forms connfujure.voice/IVoiceChannel))
