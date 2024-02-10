(ns clj-intro.polymorphism)

;; protocols

;; expression problem

(defprotocol IAdd
  (add [x y]))

(add 1 2)

(extend-type java.lang.Long
  IAdd
  (add [this other]
    (+ this other)))

(add 1 2)

(add "foo" "bar")

(extend-type java.lang.String
  IAdd
  (add [this other]
    (str this other)))

(add "foo" "bar")

(add "foo" 1)

(add 1 "foo")

(type [])

(extend-type clojure.lang.PersistentVector
  IAdd
  (add [this other]
    (mapv + this other)))

(add [1 2 3] [10 20 30])

;; always use dates in examples!

(defprotocol IsoDate
  (to-iso-string [this]))

(extend-protocol IsoDate
  java.util.Date
  (to-iso-string [^java.util.Date this]
    (to-iso-string (.toLocalDateTime
                     (.atZone
                       (java.time.Instant/ofEpochMilli (.getTime this))
                       (java.time.ZoneId/systemDefault)))))

  java.time.LocalDateTime
  (to-iso-string [this]
    (.format java.time.format.DateTimeFormatter/ISO_LOCAL_DATE_TIME this))

  java.time.Instant
  (to-iso-string [this]
    (to-iso-string (java.util.Date/from this)))

  Number
  (to-iso-string [^Number this]
    (to-iso-string (java.time.Instant/ofEpochMilli this))))

(to-iso-string (java.util.Date.))

(to-iso-string (System/currentTimeMillis))

(to-iso-string (java.time.Instant/now))

;; multi-methods