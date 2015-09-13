(defproject kiwi "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [seesaw "1.4.4"]]
  :plugins [[lein-kibit "0.1.2"]]
  :main ^:skip-aot kiwi.core
  :aot [kiwi.run]
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
