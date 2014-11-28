(ns email-measure.core
  (:gen-class))

(require '[clojure.string :as str])

(defn indent-to-parenthis
  [s]
  (str/split s #"\n")
  )

(defn measure
  [s]
  (println "lol")
  "TODO: not implemented yet"
)

(defn measure-io
  [input-file-name output-file-name]
  (spit output-file-name (-> input-file-name slurp measure))
)

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (do
    ;; checking current directory
    (println "Current directory:" (System/getProperty "user.dir"))
    ;; measure message parameters and produce results
    (measure-io "resources/iulian_1.gra" "resources/iulian_1.res")
    "The End"
  )
)
