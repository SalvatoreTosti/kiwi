(ns kiwi.run
  (:import java.awt.datatransfer.Clipboard)
  (:import java.awt.datatransfer.Transferable)
  (:import java.awt.datatransfer.ClipboardOwner))
  (gen-class
   :name kiwi.run.testy
   :prefix "-"
   :methods [[testyWest [] void]]
   :implements [java.awt.datatransfer.ClipboardOwner])

(defn -testyWest [this]
  (println "testy west!"))

(defn -lostOwnership [this clipboard contents]
                 (println "lost ownership!"))
                 ;(. Thread (sleep 200))
                 ;(clip/contents! (clip/contents) this)
                 ;this)))
