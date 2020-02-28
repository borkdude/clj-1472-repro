(ns clj-1472-repro.core
  (:gen-class)
  (:require [clojure.spec.alpha :as s]))

(defn -main [& _args]
  (println "Is 1 a valid int?:" (s/valid? int? 1)))
