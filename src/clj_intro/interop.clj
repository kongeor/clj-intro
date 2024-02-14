(ns clj-intro.interop
  (:import (clojure.lang IPersistentCollection)))

; exceptions

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


;; -----------------
;; ALPHA LAND!
;; 1.12alpha6 stuff

;; method values

(comment
  ;; does not work because of different arities
  (map Integer/decode ["1" "2"]))

;; that works!
(map Integer/decode ["1" "2"])

(map ^[String ] Integer/parseInt ["1" "2"])

;; java field descriptors: /home/kostas/projects/clj-intro/src/clj_intro/interop.clj

(defn str->bytes [s]
  (bytes (byte-array (map byte s))))

(str->bytes "hoho")

;; pre alpha6 style

(defn my-str-new-2 [^"[B" b]
  (String. b))

(my-str-new-2 (str->bytes "foo"))

(defn my-str-new [b]
  (^[byte*] String/new b))

(my-str-new (str->bytes "foo"))

;; streams ...

(java.util.Arrays/stream ^"[Ljava.lang.String;" (to-array ["foo" "bar"]))

(-> (java.util.stream.Stream/of (to-array ["12.43" "1.123" "0.54"]))
    (.map (reify java.util.function.Function
            (apply [this x]
              (parse-double x))))
    ;; not working yet
    #_(.map parse-double)
    ;; not working yet
    ;; https://clojure.atlassian.net/browse/CLJ-2799
    #_(.map ^[double] Math/round)
    (.collect (java.util.stream.Collectors/toList)))

;; seq interop

(stream-seq! (java.util.stream.Stream/of (to-array ["12.43" "1.123" "0.54"])))

;; why would you do that?
;; performance ... see https://clojure.atlassian.net/browse/CLJ-2791
;; Implement Spliterable on PersistentVector

(type (range 10))

;; used to work but now more performant
(-> (range 10)
    (.stream)
    (.mapToInt
      (reify java.util.function.ToIntFunction
        (applyAsInt [_ i]
          (.intValue ^Long i))))
    (.sum ))

