(ns clj-intro.logkeys
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(def logs "2024-02-24 20:09:58+0200 > <Enter>\n2024-02-24 20:09:58+0200 > <Enter><RShft>$$ <BckSp><BckSp><BckSp>\n2024-02-24 20:10:00+0200 > <Enter><RShft>$<BckSp><BckSp><RShft>## <RShft>extracting<LCtrl><BckSp><RShft><#+17><RShft><#+9><RShft>r<BckSp><RShft>real worl<LCtrl><BckSp><BckSp><RShft>example 1<LShft>: <RShft>parsing logkeys logs<Esc>o\n")

(str/split-lines logs)

;; spit with limit
(->> logs
     (str/split-lines)
     (map #(str/split % #">" 2)))

;; throw away the timestamp
(->> logs
     (str/split-lines)
     (map #(str/split % #">" 2))
     (map second)
     (map str/trim))

;; now we need to find a regex to parse each lines and get the characters

;; grab a line
(def log-line  "<Enter><RShft>$<BckSp><BckSp><RShft>## <RShft>extracting<LCtrl><BckSp><RShft><#+17><RShft><#+9><RShft>r<BckSp><RShft>real worl<LCtrl><BckSp><BckSp><RShft>example 1<LShft>: <RShft>parsing logkeys logs<Esc>o")

;; try 1
(re-seq #"(<[A-Z].*>)" log-line)

;; hm, this doesn't work

;; try 2

(def log-token "<Enter>")

;; this works
(re-matches #"<Enter>" log-token)

;; this also works
(re-matches #"<[A-Z]nter>" log-token)

;; and this works too
(re-matches #"<[A-Z].*>" log-token)

;; let's try the line again, hm, very greedy?
(re-seq #"(<[A-Z].*>)(.*)" log-line)

;; let's try the line again, aha
(re-seq #"(<[A-Z][^>.]*>)" log-line)

;; let's try the line again, we have a winner
(re-seq #"(<[A-Z][^>.]*>|\p{ASCII})" log-line)

;; let's create a function

(defn parse-line [line]
  (map second (re-seq #"(<[A-Z][^>.]*>|\p{ASCII})" line)))

;; try
(parse-line log-line)

;; and finally we can check things

(frequencies (parse-line log-line))

;; connect things
(->> logs
     (str/split-lines)
     (map #(str/split % #">" 2))
     (map second)
     (map str/trim)
     (mapcat parse-line))

;; let's create a function
(defn parse-logs [logs]
  (->> logs
       (str/split-lines)
       (map #(str/split % #">" 2))
       (map second)
       (map str/trim)
       (mapcat parse-line)))

;; try
(parse-logs logs)

;; try again
(frequencies (parse-logs logs))

;; sort
(sort-by second (frequencies (parse-logs logs)))

;; sort desc
(sort-by second > (frequencies (parse-logs logs)))

;; let's create the character range
(range (int \a) (int \z))

;;
(def a-z (set (map str (map char (range (int \a) (int \z))))))

;; let's revisit our function
(defn parse-logs [logs]
  (->> logs
       (str/split-lines)
       (map #(str/split % #">" 2))
       (map second)
       (map str/trim)
       (mapcat parse-line)
       (remove a-z)))

(sort-by second > (frequencies (parse-logs logs)))

;; now let's revisit the logs

(def logs "Logging started ...\n\n2024-02-24 19:37:35+0200 > \n\nLogging stopped at 2024-02-24 20:03:26+0200\n\nLogging started ...\n\n2024-02-24 20:03:26+0200 > asdf<RShft>C<BckSp><BckSp><BckSp><BckSp><BckSp><BckSp>\n")

;; let's filter out everything not starting with 2
;; we can revisit in 3000 AD
(->> logs
     (str/split-lines)
     (filter #(str/starts-with? % "2"))
     (map #(str/split % #">" 2)))

;; that looks like it!
(defn parse-logs [logs]
  (->> logs
       (str/split-lines)
       (filter #(str/starts-with? % "2"))
       (map #(str/split % #">" 2))
       (map second)
       (map str/trim)
       (mapcat parse-line)
       (remove a-z)))

;; and that's our stats
(defn char-stats [chars]
  (sort-by second > (frequencies chars)))

;; can I get the logs

(comment
  (slurp "/var/log/logkeys.log"))

;; thankfully no ...

;; now do some cp and chown to do things
;; ➜  ~ sudo cp /var/log/logkeys.log .
;; ➜  ~ sudo chown kostas:kostas logkeys.log

(slurp "/home/kostas/logkeys.log")

;; yup!

(char-stats (parse-logs (slurp "/home/kostas/logkeys.log")))

;; =>
;(["<Esc>" 247]
; ["<LShft>" 245]
; [" " 243]
; ["<BckSp>" 146]
; ["<RShft>" 108]
; ["<Enter>" 99]
; ["<LAlt>" 87]
; [";" 55]
; ["(" 51]
; ["#" 40]
; ["<LCtrl>" 39]
; [">" 35]
; ["-" 33]
; [":" 33]
; ["<" 28]
; ["+" 27]
; ["1" 26]
; ["z" 20]
; ["," 18]
; ["<Tab>" 17]
; ["." 17]
; ["/" 16]
; ["%" 12]
; ["[" 12]
; ["2" 12]
; ["\"" 11]
; ["'" 9]
; ["C" 9]
; ["]" 8]
; ["\\" 6]
; [")" 5]
; ["*" 5]
; ["4" 4]
; ["7" 4]
; ["5" 4]
; ["3" 3]
; ["0" 3]
; ["^" 3]
; ["$" 3]
; ["9" 2]
; ["8" 2]
; ["<Right>" 2]
; ["<LMeta>" 2]
; ["!" 1]
; ["<End>" 1]
; ["|" 1]
; ["?" 1]
; ["<Left>" 1]
; ["<Down>" 1])


;; oh no! there's a bug! can you spot it?

;; don't forget to

;; ➜  ~ rm logkeys.log

;; and maybe stop the logkeys service too?


;; or maybe just
;; https://github.com/kernc/logkeys/blob/master/docs/Logfiles.md
(io/resource "logkeys_example.log")

(slurp (io/resource "logkeys_example.log"))

(char-stats (parse-logs (slurp (io/resource "logkeys_example.log"))))
