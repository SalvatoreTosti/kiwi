(ns kiwi.run)
  (gen-class
   :name kiwi.run.testy
   :prefix "-"
   :methods [[testyWest [] void]])
   ;:implements [java.awt.datatransfer.ClipboardOwner])

(defn -testyWest [this]
  (println "testy west!"))
