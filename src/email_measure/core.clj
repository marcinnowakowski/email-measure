(ns email-measure.core
  (:gen-class))

(require '[clojure.string :as str])

;; ok... it's not that easy to program in clojure after leaving code for a while
;; lets see how it is going to be now?
;; ass(sic!) i remember this function is not really used in my application
;; but lets leave it for my derivatives(?)
;; lets keep this code more difficult...;--) 
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

;; now to the core... this should return total data map 
;; best in json format
;; i wish it will contain... let see what?;--)
;; ahh... definitelly we have to cut out headers and special parts
;; by the way we talk here about grabled email
;; you don't know what a brabled-grabled email is... so what?
;; use google... or [in advance i appology my swear words... 
;; i am a noob in swearing...] fuck off.
;; clojure programmers swear ass... well but more gently
;;
;; after a bit of thinking i see...
;; this function mainly removes identation
;; and provide pair map
;;
;; {element level indentation |int| ->
;; text accociated with this indentation level |str| }
;;
(defn map%indent->level-string
  [s]
  (let [;; << *1*
    pom-map%indent->level-string(fn [acc-level-map elem]
      (let [ ;; << *2*
        elem-indent-count (count (re-find  (re-pattern "^[>]*") elem))
        acc-level (get acc-level-map elem-indent-count)
        trimmed-elem 
          (if (= (first elem) \s) 
            (subs elem (+ elem-indent-count 1))
            (subs elem elem-indent-count)
          )
        ] ;; *2*
        (if acc-level
          (assoc acc-level-map elem-indent-count (str/join "\n" (list acc-level trimmed-elem)))
          (assoc acc-level-map elem-indent-count trimmed-elem)
        )
      );; *2* >>
    ) ;; pom-map%indent->level-string
    ] ;; *1*
    (reduce 
      pom-map%indent->level-string
      {} 
      (str/split s #"\n")
    )
  )  ;; *1* >>
)

;; this should be the main function 
;; providing measure we want.
(defn measure
  [garbled-email]
  (let [
    level-map (map%indent->level-string garbled-email)
    level-keys (sort (keys level-map))
    pom-measure (fn [level-idx]
      (let [
        level (get level-map level-idx)
        extract-date-sender-from-level (fn [level]
          (let [
            reg #"On (.*)[,][ ](.*\n?.*) wrote:"
            ]
            (into 
             (zipmap 
               [:on-wrote-header :on-wrote-date :on-wrote-from] 
               (vec (re-find (re-matcher reg level)))
             )
             {:content level}
            )
          )
        )
        ]
        (list (extract-date-sender-from-level level))
      )
    )
;; initial level handling will be a small disturbance...
;; let leave it for later
;;    read-level-header (fn [level-idx]
;;      (let [
;;        level (get level-map level-idx)
;;        reg-date #"Date: ([^\n]*)"
;;        reg-from #"From: ([^\n]*)"
;;        ]
;;        (list 
;;          (get (re-find (re-matcher reg-date level)) 1)
;;          (get (re-find (re-matcher reg-from level)) 1)
;;        ) 
;;      )  
;;    )
  ]
    (zipmap (vec level-keys) (vec (map pom-measure level-keys)) )
  )               
)

;; From: marcin.nowakowski@gazeta.pl Marcin Nowakowski - ( ... ) 
;; To:  
;; Date: Sat, 22 Nov 2014 04:21:36 +0000

;; maybe useful later
;;remove-line-breaks (fn [s] 
;;  (apply str (filter #(= % \n) s))
;;)

(defn ->csv [level-map]
  (let[
    level-keys (sort (keys level-map))
    pom->csv (fn [level-idx]
      (let[
        level (get level-map level-idx)
        on-wrote-header (get level :on-wrote-header)
        on-wrote-date (get level :on-wrote-date)
        on-wrote-from (get level :on-wrote-from)
        content (get level :content)
        ]
        (do (println "\n\n***lol" level-idx "\n***level:" level "\n***on-wrote-header:" on-wrote-header)  
        (str
          ;; providing informatioj when previous message was dispatched
          on-wrote-date
          " "
          ;; providing information who dispatched previous message
          on-wrote-from
          " "
          ;; counting message length without headers
          (- (count content) (count on-wrote-header) )
          "\n"
        )
        )
      )
    )
    
  ]
   ;; performing iteration over the map
   (do (println (map pom->csv level-keys))
   (reduce (fn [elem acc] (do (print "x" elem) (str acc elem)) ) (map pom->csv level-keys) "")
   )
  )
)


(defn measure-io
  [input-file-name output-file-name]
  (do
    (println "in measure-io")
    (spit output-file-name (-> input-file-name slurp measure ->csv))
  )
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
