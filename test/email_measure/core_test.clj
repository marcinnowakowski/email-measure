(ns email-measure.core-test
  (:require [clojure.test :refer :all]
            [email-measure.core :refer :all]))

(deftest indent->parenthis-zzz
  (testing (str "=          Simple hierarchy test           =\n") 
  (is (= (indent->parenthis (str "a" "\n>b" "\n>>c" "\n>d" "\n>>e" "\nf")) 
    (str "<a\n" "<b \n" "<c \n" ">d \n" "<e \n" ">>f")))))

;;==============================================
(defmacro t
  "General test generator"
  [test-comment question funtion-to-test input expected-output]
  (list 'deftest (gensym funtion-to-test)
     (list 'testing test-comment
       (list 'is 
         (list question
           (list funtion-to-test input)
           expected-output
         )
       )
     )
   )
)

;;(defn my-function [])
;;(defn test-comment [&[args]] (println args))
;;(macroexpand '(t "comment" = my-function "input" "expected output"))

;;==============================================
(defmacro t=
  "Equal test generator"
  [test-comment funtion-to-test input expected-output]
  (list 't test-comment '= funtion-to-test input expected-output)
)

;;==============================================
(t= 
  (str
  "\n"
  "============================================\n"
  "=          Simple hierarchy test           =\n"
  "============================================\n"
  )
  
  indent->parenthis

  (str
  "a" 
  "\n>b"
  "\n>>c" 
  "\n>d" 
  "\n>>e" 
  "\nf"
  )
  
  (str
  "<a\n"
  "<b \n"
  "<c \n"
  ">d \n"
  "<e \n"
  ">>f"
  )
)