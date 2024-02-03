(ns clj-intro.special-forms)

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

;; throw

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
