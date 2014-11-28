(defproject email-measure "0.1.0-SNAPSHOT"
  :description "Email measure can be used to asses information transfer in email communication."
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.6.0"]]
  :main ^:skip-aot email-measure.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
