(ns email-measure.core-test
  (:require [clojure.test :refer :all]
            [email-measure.core :refer :all]))

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
  
  indent->parenthesis

  (str
  "a" 
  "\n>b"
  "\n>>c" 
  "\n>d" 
  "\n>>e" 
  "\nf"
  )
  
  (str
  "a\n"
  "<b\n"
  "<c\n"
  ">d\n"
  "<e\n"
  ">>f"
  )
)

;;==============================================
(t= 
  (str
  "\n"
  "============================================\n"
  "=              Level map test              =\n"
  "============================================\n"
  )
  
  indent->level-map

  (str
  "a1\n" 
  ">b1\n"
  "a2\n" 
  ">>>d1\n" 
  ">>c1\n" 
  ">b2\n"
  "a3\n"
  ">>>d2"
  )
  
  {0 "a1\na2\na3"
   1 "b1\nb2"
   2 "c1"
   3 "d1\nd2"}
)