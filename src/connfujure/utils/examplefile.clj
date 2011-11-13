(ns connfujure.utils.examplefile
  (import java.io.BufferedReader)
  (import java.io.FileReader))

(defn connect [TOKEN]
  (BufferedReader. (FileReader. "test/resources/test.txt")))