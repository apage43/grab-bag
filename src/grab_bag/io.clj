(ns grab-bag.io
  (:require [clojure.java.io :as io]))

(defn byteslurp
  "Like slurp but returns a byte[]"
  [file]
  (let [bs (transient [])]
    (with-open [istream (io/input-stream file)]
      (loop [b (.read istream)]
        (if (neg? b) (byte-array (persistent! bs))
          (do (conj! bs (.byteValue b)) (recur (.read istream))))))))
