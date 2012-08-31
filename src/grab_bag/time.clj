(ns grab-bag.time)

(defn now [] (System/currentTimeMillis))

(defn rate-limited-promises
  "Wrap the passed function `f` and allow it to be called no more than once
  every `minwait` milliseconds. Instead of the result, return a promise."
  [minwait f]
  (let [timeagent (agent 0)]
    (fn [& args]
      (let [result (promise)]
        (send-off timeagent (fn [last-time]
                              (let [current (now)
                                    at-least (+ last-time minwait)]
                                (when (< current at-least)
                                  (Thread/sleep (- at-least current))))
                              (deliver result (apply f args))
                              (now)))
        result))))

(defn rate-limited
  "Like (rate-limited-promises) but will deref the promise and always return the
   result, blocking until it is available."
  [minwait f]
  (comp deref (rate-limited-promises minwait f)))
