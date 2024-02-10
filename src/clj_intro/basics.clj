(ns clj-intro.basics)

;; The syntax for Clojure is like Lisp and is very simple:
;; code is made up of expressions which are evaluated to some value.

;; basic expressions

5
"hi"
[1 2 3]
(+ 1 2)
(if true "yes" "no")
(println "hello!")

;; sub-expressions

(+ 1
   (* 2 3)
   (/ 10 2))

;; doc/source

;; scalars

nil
true
false

;; numbers

1        ; integer
1N       ; arbitrary-precision integer
1.2      ; float/double/decimal
1.2M     ; arbitrary-precision decimal
1.2e4    ; scientific notation
1.2e4M   ; sci notation of arbitrary-precision decimal

0x3a     ; hex literal (58 in decimal)
1/3      ; Rational number, or "ratio".
\a       ; The character "a".
"hi"     ; A string.
"a
multiline
string"

#"^foo\d?$"   ; A regular expression.
:foo          ; A keyword.

;; A symbol is an object that represents the name of something.

'foo   ; A symbol.

;; ---------------------
;; Data Structures

;; nice literal syntax

;; vectors
[1 2 3]
["foo" 1 :bar]

;; hash-maps
{:a 1 :b 2}

;; sets (unordered)
#{1 2 3}

;; linked lists (less often used)
;; notice the quote (')

'(1 2 3)

;; nesting works as you would expect

#{:a
  [1 2 3]
  {:foo 11 :bar 12}
  #{"shirt" "coat" "hat"}}

;; ^ all those are concrete data types

;; ------
;; Abstractions

;; Some of the Clojure abstractions are:
;; * Collection (Lists, vectors, maps, and sets are all collections.)
;; * Sequential (Lists and vectors are ordered collections.)
;; * Associative (Hashmaps associate keys with values. Vectors associate numeric indices with values.)
;; * Indexed (Vectors, for example, can be quickly indexed into.)


;; --------------
;; Functions calls

;; (my-func arg1 arg2 arg3)

;; nested calls

#_(my-func (my-func2 arg1
                     arg2)
           (other-func arg-a
                       (foo-bar arg-x
                                arg-y
                                (+ arg-xx
                                   arg-yy
                                   arg-zz))
                       arg-b))

;; ----------------
;; Macros and Special Forms

;; note on macros TODO

;; special forms see ./special_forms.clj

;; -----------------
;; Namespaces
;; Functions for Creating Data Structures
;; Functions For Working With Data Structures
;; see ./data_structures.clj

;; Regular Expressions
;; Functions For Working With Strings
;;

;; Values, Immutability, and Persistence


;; ---------------
;; Truthiness

;; nil and false are falsey

(if   0 :t :f)  ; ⇒ :t
(if  "" :t :f)  ; ⇒ :t
(if  [] :t :f)  ; ⇒ :t
(if  {} :t :f)  ; ⇒ :t
(if #{} :t :f)  ; ⇒ :t


;; ---------------------
;; Vars

(def the-answer 42)

;; The thing being defined here (behind the scenes) is officially
;; called a Var. The symbol "the-answer" refers to that var which
;; itself refers to the value 42:

;; the-answer (a symbol) → a var → 42 (a value).


;; locals don't involve vars at all: those symbols refer directly to their values.

;; -----------------
;; Functions: Defining Your Own

(def my-func
  (fn [a b]
    (println "adding them!")
    (+ a b)))

;; or just

(defn my-func
  "Docstring goes here."
  [a b]
  (println "adding them!")
  (+ a b))

;; --------------
;; Side-effects

;; TODO

;; -----------------
;; Destructuring

;; TODO
