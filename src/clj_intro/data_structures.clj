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

;; all you fibonaccis are belong to us!

;; fibo, take 1

(defn fibo [a]
  (condp = a
    0 1
    1 1
    (+ (fibo (dec a)) (fibo (- a 2)))))

;; oh no...
(map fibo (range 60))

;; fibo, take 2

(declare memo-fibo)

(defn fibo2 [a]
  (condp = a
    0 1
    1 1
    (+ (memo-fibo (dec a)) (memo-fibo (- a 2)))))

(def memo-fibo (memoize fibo2))

(map fibo2 (range 60))

;; oh no
(map fibo2 (range 600))

;; fibo, take 3

(declare memo-fibo3)

(defn fibo3 [a]
  (condp = a
    0 1
    1 1N  ;;  <- notice the N
    (+ (memo-fibo3 (dec a)) (memo-fibo3 (- a 2)))))

(def memo-fibo3 (memoize fibo3))

;; :muscle:
(last (map fibo3 (range 600)))

;; fibo, take 4

(defn fibo4 [n]
  (first (last (take (inc n) (iterate (fn [[a b]]
                                        [b (+ a b)]) [0 1N])))))

(time (fibo4 600))

(defn fibo5
  ([]
   (fibo5 1 1N))
  ([a b]
   (lazy-seq (cons a (fibo5 b (+ a b))))))


(last (take 600 (fibo5)))

;; lazy seqs

;; realization

(let [s (map inc (range 10))]
  #_(println s)
  (realized? s))

;; chunking

(let [s (take 5 (map #(do (println %)
                          (inc %)) (range 40)))]
  s)

;; sequences

(partition-all 3 (range 9))

(apply map vector (partition-all 3 (range 9)))

(split-with (partial > 5) (range 10))

(get (System/getProperties) "java.vm.version")

(re-seq #"\d+" (get (System/getProperties) "java.vm.version"))
