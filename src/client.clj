(ns client)
(load "lib/connfujure")

(def TOKEN "THETOKEN")

(connfujure/application 
  TOKEN 
  {:voice (fn [conference] 
            ((:do conference) {:join (fn [call] 
                                         (println "Call joined"))
                               :leave (fn [call]
                                         (println "Call left"))
                              }))
   :sms (fn [conference]
            ((:do conference) {:new (fn [sms]
                                       (print "New Message" sms))
                              }))                   
  }
)


