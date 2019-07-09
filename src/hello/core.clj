(ns hello.core
  (:require [clojure.string :refer [includes?]]
            [clojure.core.reducers :as r])
  (:gen-class))

(defn get-words [file-name]
  (with-open [rdr (clojure.java.io/reader file-name)]
    (doall (line-seq rdr))))

(def english-words (set (get-words "/home/agold/Prog/wordlists/english")))

(def french-words (set (get-words "/home/agold/Prog/wordlists/french")))

#_(def german-words (set (get-words "/home/agold/Prog/wordlists/ngerman")))

(def german-words (set (get-words "/home/agold/Prog/wordlists/german")))

(def spanish-words (set (get-words "/home/agold/Prog/wordlists/spanish")))

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

(defn reversed-pairs
  "return lazy seq of vector pairs of [word rev-word] if rev-word is in dict"
  [dict]
  (sort-by (comp count first)
           (filter (complement nil?)
                   (map #(word-to-reversed-pair % dict) dict))))

(defn list-palindromes
  "list palindromes in dict sorted by length"
  [dict]
  (sort-by count (filter palindrome? dict)))

(defn words-from-frag
  "find all words in dict containing frag"
  [dict frag]
  (sort-by count (filter #(includes? % frag) dict)))

(defn with-dict
  "bind specified dict and call function f"
  [dkey f & args]
  (if (and
       (keyword? dkey)
       (contains? dicts dkey))
    (let [dict (dkey dicts)]
      (apply f dict args))
    (println "error" dkey " is not a recognized dictionary")))
; example: (with-dict :eng words-from-frag "marge")

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!" args))
