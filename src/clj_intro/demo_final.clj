(ns clj-intro.demo
  (:require [clj-java-decompiler.core :as dec]))

;; hi! I'm a comment!

;; 1. Intro
;; hello world, syntax, REPL, basic functions

(defn hello
  "says hello world to someone"
  [name]
  (println (str "hello " name)))

(hello "world")


;; 2. Data Structures
;; persistent data structures, structural sharing, equality, values

(def v1 [1 2 3])

v1

(def v2 (conj v1 4))

v2

(def v3 (conj v1 4))

(= v2 v3)
(identical? v2 v3)

(def list1 (list 1 2 3))

list1

(conj list1 4)

(def list2 (conj list1 4))
list2
(def list3 (conj list1 5))

(= list2 list3)
(rest list2)
(= (rest list2) (rest list3))

(identical? (rest list2) (rest list3))

;; 3. Factorial & Fibonacci
;; loop/recur, lazy sequences, TCO, destructuring

(defn fact [n]
  (if (= n 0)
    1
    (*' n (fact (- n 1)))))

(fact 5)

(fact 12000)

(dec/decompile
  (loop [n 5
         r 1]
    (if (= n 0)
      r
      (recur (- n 1) (*' r n)))))


(defn foo [x]
  (when (not= x 0)
    (println x)
    (recur (- x 1))))

(foo 5)

(defn fact [n]
  (reduce *' (range 1 (inc n))))

(fact 12000)

;; 4. Polymorphism
;; protocols, host interop

(Integer/parseInt "123")

(slurp (new java.io.File "/dev/null"))


(doto (java.util.HashMap.)
  (.put "foo" 1)
  (.put "bar" 2)
  (println))

(type {"foo" 1 "bar" 2})

(= {"foo" 1 "bar" 2}
   (doto (java.util.HashMap.)
     (.put "foo" 1)
     (.put "bar" 2)))



(defprotocol IConcat
  (ccat [this that]))

(ccat "foo" "bar")

(extend-type java.lang.String
  IConcat
  (ccat [this that]
    (str this that)))

(ccat "foo" "bar")

(type (into-array [1 2]))

(type (into-array [1 2]))

(extend-type (type (into-array [1]))
  IConcat
  (ccat [this that]
    (concat (seq this) (seq that)))
  )

(ccat (into-array ["a" "f"]) (into-array [3 4]))
;; Example 1: Parsing logkeys logs