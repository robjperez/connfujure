(ns connfujure)
(load "dispatcher")
(load "listener")

(defn check-token [TOKEN]
  )

(defn application [TOKEN funs]
  (println "Starting application with token " TOKEN)
  (check-token TOKEN)
  (dispatcher/start funs)
  (listener/start funs)  
  ;((:voice funs) {:do #((:leave %1) "call")})
)