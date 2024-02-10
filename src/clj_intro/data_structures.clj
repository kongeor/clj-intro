(ns clj-intro.data-structures)

;; covers:
;; * Laziness
;; * Bread and Butter Functions
;; *
;; go to

;; https://clojure.org/reference/data_structures

;; ------------------
;; example land!

;; map

(map inc [10 11 12])

(map str [10 11 12])

(map (fn [x] (str ">" x "<")) [10 11 12])

(map
  (fn [x y] (+ x y))
  [1 2 3]
  [10 20 30 40])

(map
  +
  [  1   2   3   ]
  [ 10  20  30 40]
  [100 200 300   ])


;; filter

(filter odd? (range 10))

(remove odd? (range 10))

;; apply (unpacks)

(max 1 5 2 8)

(apply max [1 5 2 8])

;; TIL

(apply max 4 55 [1 5 222 8 3])

;; for (similar to Python's list comprehensions)

(for [x (range 10)]
  x)

(for [x (range 3)
      y (range 3)]
  [x y])

(for [x (range 5)
      y (range 5)
      :when (> x y)]
  [x y])

;; reduce

(reduce
  (fn [acc x]
    (+ acc x))
  0
  (range 5))

(reduce + 100 (range 5))

(reduce + (range 5))

;; TIL

(reductions + (range 5))

;; partial, comp, and iterate

(let [plus5 (partial + 5)]
  (plus5 10))

(let [plus5 (partial + 5)
      times4 (fn [x] (* x 4))
      f (comp plus5 times4)]
  (map f [1 2 3]))

;; oh no my REPL!

#_(iterate inc 5)

(take 5 (iterate inc 5))

;; Welcome to Lazy town!

;; all you fibonaccis are belong to us!

(defn fibo [a]
  (condp = a
    0 0
    1 1
    (+ (fibo (dec a)) (fibo (- a 2)))))

(map fibo (range 20))

(map second (take 10 (iterate (fn [[a b]]
                                [b (+ a b)]) [0 1])))

#_(lazy-seq)
