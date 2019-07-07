(ns hello.core
  (:require [clojure.string :refer [includes?]])
  (:gen-class))

(defn get-words [file-name]
  (with-open [rdr (clojure.java.io/reader file-name)]
    (doall (line-seq rdr))))

(def english-words (set (get-words "/Users/agold/Prog/wordlists/english")))

(def french-words (set (get-words "/Users/agold/Prog/wordlists/french")))

#_(def german-words (set (get-words "/Users/agold/Prog/wordlists/ngerman")))

(def german-words (set (get-words "/Users/agold/Prog/wordlists/german")))

(def spanish-words (set (get-words "/Users/agold/Prog/wordlists/spanish")))

(def dicts {:eng english-words :ger german-words :fr french-words :sp spanish-words})

(defn goldie [s]
  (println "Hello" s))

(defn reverse-word [w]
  (apply str (reverse w)))

(defn palindrome? [w]
  (= w (reverse-word w)))

(defn is-word? [w dict]
  (contains? dict w))

(defn reversibles [dict]
  (filter #(is-word? (reverse-word %) dict) dict))

(defn word-to-reversed-pair [w dict]
  (let [wr (reverse-word w)]
    (if (is-word? wr dict)
      [w wr]
      nil)))

(defn reversed-pairs [dict]
  (sort (filter (complement nil?)
                (map #(word-to-reversed-pair % dict) dict))))

(defn list-palindromes [dict]
  (sort (filter palindrome? dict)))

(defn words-from-frag
  "find all words in dict containing frag"
  [dict frag]
  (sort (filter #(includes? % frag) dict)))

(defn with-dict
  "bind specified dict and call function f"
   [dkey f & args]
   (let [dict (dkey dicts)]
     (apply f dict args)))
; example: (with-dict :eng words-from-frag "marge")

(defn compare-by-count
  [a b]
  (compare (count a) (count b)))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!" args))
