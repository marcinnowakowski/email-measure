(ns email-measure.core-test
  (:require [clojure.test :refer :all]
            [email-measure.core :refer :all]))

;;==============================================
(defmacro t
  "General test generator"
  [test-comment question funtion-to-test input expected-output]
  (list 'deftest (gensym 'funtion-to-test)
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

;;(defn function-to-test [])
;;(defn test-comment [&[args]] (println args))
;;(macroexpand '(t "comment" = function-to-test "input" "expected output"))

;;==============================================
(defn t=
  "Equal test generator"
  [test-comment funtion-to-test input expected-output]
  (t test-comment = funtion-to-test input expected-output)
)

;;==============================================
(t= "simple hierarchy test" indent-to-parenthis "a \n>b \n>>c \n>d \n>>e \nf" "<a\n <b \n<c \n>d \n<e \n>>f")
