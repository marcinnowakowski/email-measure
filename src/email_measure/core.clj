(ns email-measure.core
  (:gen-class))

(require '[clojure.string :as str])

(defn indent->parenthis
  [s]
  ;; << *1*
  [pom_ind->par (fn [acc elem]
    (let [ ;; << *2*
      acc-lines first(acc)
      acc-indent-count second(acc)
      get-indent (fn [elem]
	      (let [;; << *3*
		      init-section-count (count (re-find  (re-pattern "^[>]*") elem))
		      ];; *3*
		      (list init-section-count (subs elem init-section-count))
	      );; *3* >>
      ) ;; get-indent
      elem-indent-count (-> elem get-indent count)
      compute-parenthis (fn [acc-indent-count elem-indent-count]
        (let [;; << *3*
          indent (- acc-indent-count elem-indent-count)
          ];; *3*
        );; *3* >>
        if (> 0 indent)
        (str (repeat indent ">") )
        (str (repeat indent "<") )
      ) ;; insert-parenthis
      ];; *2*
      (list 
        (conj 
          acc-lines 
          (str 
            (compute-parenthis acc-indent-count elem-indent-count) 
            (subs elem elem-indent-count) ) )
    );; *2* >>
  ) ;; pom_ind->par
  ] ;; *1* >>
  (reduce 
    pom_ind->par 
    '(() 0) 
    (str/split s #"\n")
  ) 
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
    (measure-io "resources/iulian_1.gar" "resources/iulian_1.csv")
    "The End"
  )
)
