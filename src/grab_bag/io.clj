(ns grab-bag.io
  (:require [clojure.java.io :as io]))

(defn byteslurp
  "Like slurp but returns a byte[]"
  [file]
  (let [baos (java.io.ByteArrayOutputStream.)]
    (io/copy (io/file file) baos)
    (.toByteArray baos)))
