(ns connfujure.voice)

(defrecord Voice[from to])

(defprotocol IVoiceChannel
  (on-join [this ^Voice post] "voice on join callback")
  (on-leave [this ^Voice post] "voice on leave callback"))