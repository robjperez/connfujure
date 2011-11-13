(ns client)
(use 'connfujure.core)
(use 'connfujure.dsl)

(def TOKEN "THETOKEN")

(application TOKEN
             (conference
               (sms
                 (on-new [message]
                         (println (str "New message from: " (:from message) "\n"
                                       "to: " (:to message) "\n"
                                       "body: " (:body message) "\n"
                                       ))))
               (voice
                 (on-join [{call :call}]
                          (println "Join"))
                 (on-leave [{call :call}]
                           (println "Leave")))))