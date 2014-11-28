(ns email-measure.core
  (:gen-class))

(require '[clojure.string :as str])

(defn indent->parenthesis
  [s]
  (let [;; << *1*
    pom-ind->par (fn [acc elem]
      (let [ ;; << *2*
        acc-lines (first acc)
        acc-indent-count (second acc)
        elem-indent-count (count (re-find  (re-pattern "^[>]*") elem))
        indent-count-difference (- acc-indent-count elem-indent-count)
        compute-parenthis (fn [indent-count-difference]
          (if (< 0 indent-count-difference)
            (str/join (repeat indent-count-difference ">"))
            (str/join (repeat (- 0 indent-count-difference) "<"))
          )
        ) ;; compute-parenthis
        ] ;; *2*
        (list 
          (conj
            acc-lines
            (str 
              (compute-parenthis indent-count-difference) 
              (subs elem elem-indent-count)
            )
          )
          elem-indent-count
        )
      );; *2* >>
    ) ;; pom-ind->par
    ] ;; *1*
    (str/join "\n" (reverse (first
      (reduce 
        pom-ind->par 
        (list () 0) 
        (str/split s #"\n")
      )
    )))
  )  ;; *1* >>
)

(defn indent->level-map
  [s]
  (let [;; << *1*
    pom-indent->level-map(fn [acc-level-map elem]
      (let [ ;; << *2*
        elem-indent-count (count (re-find  (re-pattern "^[>]*") elem))
        acc-level (get acc-level-map elem-indent-count)
        trimmed-elem (subs elem elem-indent-count)
        ] ;; *2*
        (if acc-level
          (assoc acc-level-map elem-indent-count (str/join "\n" (list acc-level trimmed-elem)))
          (assoc acc-level-map elem-indent-count trimmed-elem)
        )
      );; *2* >>
    ) ;; pom-indent->level-map
    ] ;; *1*
    (reduce 
      pom-indent->level-map 
      {} 
      (str/split s #"\n")
    )
  )  ;; *1* >>
)

(defn measure
  [garbled-email]
  (indent->level-map garbled-email)
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
    (measure-io "resources/iulian_1.gar" "resources/iulian_1.csv")
    "The End"
  )
)
