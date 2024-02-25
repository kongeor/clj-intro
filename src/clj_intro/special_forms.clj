(ns clj-intro.special-forms
  (:require [clj-java-decompiler.core :as dec]))

(def x 1)

(defn add
  "Adds two numbers"
  [x y]
  (+ x y))

(add 1 2)

(if true 1 2)

(if nil 1 2)

;; truthiness


;; let

(let [x 1
      y 2]
  (add x y))

;; loop

;; recur

(defn factorial [n]
  (if (= n 0)
    1N
    (* n (factorial (dec n)))))

(factorial 5)

(factorial 100)

(factorial 100000)

;; take 2

(defn factorial2 [n]
  (if (= n 0)
    1N
    (* n (recur (dec n)))))

#_(factorial2 100000)

;; take 3

(defn factorial3 [n]
  (loop [n n
         r 1N]
    (if (= n 0)
      r
      (recur (dec n) (* r n)))))

(factorial3 100000)

(dec/decompile (loop [n 1
         r 1N]
    (if (= n 0)
      r
      (recur (dec n) (* r n)))))


;; throw

(throw (IllegalArgumentException. "oh no you don't"))

(throw (ex-info "boom" {}))


;; try/catch/finally

(/ 1 0)


;; finally! is interesting

(try
  (/ 1 0)
  #_(catch java.lang.ArithmeticException e
    ::arithemtic-exception)
  (finally
    (println "boom!")
    -1
    ))

;; destructing, sequential & associative
