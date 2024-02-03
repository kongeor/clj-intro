(ns clj-intro.macros)

;; > Clojure has a programmatic macro system which allows the compiler
;; > to be extended by user code. Macros can be used to define syntactic
;; > constructs which would require primitives or built-in support in other languages.

(def x -2)

(when (pos? x)
  (println "positive!")
  (* x x))

(macroexpand
  '(when (pos? x)
     (println "positive!")
     (* x x)))