(ns grab-bag.time)

(defn now-ms
  "Now, in milliseconds."
  [] (System/currentTimeMillis))

(defn rate-limited
  "Wrap `f' to create a fn that will call f at most once every minwait
  milliseconds."
  [minwait f]
  (let [timeatom (atom 0)]
    (fn [& args]
      (locking timeatom
                (let [at-least (+ @timeatom minwait)
                      now (now-ms)]
                  (when (< now at-least)
                    (Thread/sleep (- at-least now)))
                  (reset! timeatom (now-ms))))
      (apply f args))))

