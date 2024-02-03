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

;; multi-methods