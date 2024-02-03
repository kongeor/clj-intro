(ns clj-intro.interop
  (:import (clojure.lang IPersistentCollection)))

;; exceptions

(Integer/parseInt "foo")

(->> (range 5)
     (pmap #(do
             (Thread/sleep 1000)
             (println %)
             %)))


;; type hints

(set! *warn-on-reflection* true)


(defn my-len [x]
  (.length x))

(my-len "123")

(my-len [1 2 3 4])

(defn my-len2 [^String x]
  (.length x))

(my-len2 "asdf")

(my-len2 [1 2 3 4])


(defn my-len3
  ([^String x]
   (.length x))
  ([^IPersistentCollection coll]
   (.length coll)))

;; classes and member access

(System/getProperty "java.vm.version")

(.nextInt (java.util.Random.))

;; java primitives

;; coercions

;; optimization