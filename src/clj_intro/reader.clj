(ns clj-intro.reader)

;; getting the hello world out of the way

(println "Hello World!")

; > Clojure is a homoiconic language, which is a fancy term describing the fact
; > that Clojure programs are represented by Clojure data structures.

;; oh no! we said homoiconic too soon!

;; all your parenthesis are belong to us!
;; xkcd

;; not exhaustive, see https://clojure.org/reference/reader

;; ---------
;; literals

123

true

"boom!"

\c

:my-keyword

##NaN

;; ---------
;; symbols

>=

;; dot .

java.util.Random

;; lists, vectors, maps, sets

(1 2 3)

;; oh, no! ...

;; say hi! to quote (')

'(1 2 3)

;; more on that a bit later

;; vectors

[1 2 3]

;; maps

{:a 1 :b 2}

;; say goodbye to commas ....

;; sets

#{1 2 3}

;; deref

(future 1)

(deref (future 1))

@(future 1)

;; dispatch #

#"([a-z]+)-([0-9]+)"

(re-matches #"([a-z]+)-([0-9]+)" "asdf-123")

