(ns clj-intro.renaming
  (:require [clojure.string :as str])
  (:import (java.lang Character$UnicodeBlock)))

(map ^[char] Character$UnicodeBlock/of
     (last (file-seq (java.io.File. "/home/kostas/temp/magia"))))

(def uni-of ^[char] Character$UnicodeBlock/of)

(defn split-and-get [n idx]
  (let [parts (split-with (fn [c]
                            (and
                              (not= Character$UnicodeBlock/HALFWIDTH_AND_FULLWIDTH_FORMS (uni-of c))
                              (not= Character$UnicodeBlock/HIGH_SURROGATES (uni-of c))
                              (not= Character$UnicodeBlock/MISCELLANEOUS_SYMBOLS (uni-of c))
                              (not= Character$UnicodeBlock/DINGBATS (uni-of c))))
                          n)]
    (println parts)
    (rest (nth parts idx))))


(def magia-name

  (->> (file-seq (java.io.File. "/home/kostas/temp/magia"))
       (map java.io.File/getName)
       (filter #(str/includes? % "15"))
       (first)))

(map (fn [c]
       [c (uni-of c) (= Character$UnicodeBlock/HALFWIDTH_AND_FULLWIDTH_FORMS (uni-of c))]) magia-name)


(defn simplify-name [episode-name]
  (let [part-1 (split-and-get episode-name 1)
        name (str/trim (str/join (split-and-get part-1 0)))]
    (if (str/ends-with? name ".mp4")
      name
      (str name ".mp4"))
    ))

(type (file-seq (java.io.File. "/home/kostas/temp/magia-work")))

(doseq [n (vec (file-seq (java.io.File. "/home/kostas/temp/magia-work")))]
  (when (.isFile n)
    (let [simple-name (simplify-name (.getName n))]
      (.renameTo n (java.io.File. (str "/home/kostas/temp/magia-work/" simple-name))))))



(let [[_ meh] (split-with (fn [c]
                            (not= Character$UnicodeBlock/HALFWIDTH_AND_FULLWIDTH_FORMS (uni-of c)))
                          (.getName (last (file-seq (java.io.File. "/home/kostas/temp/magia")))))]
  (rest meh))


(Character$UnicodeBlock/of \c)

(.getName (last (file-seq (java.io.File. "/home/kostas/temp/magia"))))
