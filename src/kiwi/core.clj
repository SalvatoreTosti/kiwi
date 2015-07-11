(ns kiwi.core
  (:gen-class)
  (:require
   [seesaw.core :as seesaw]
   [seesaw.bind :as bind]
   [seesaw.clipboard :as clip]
   [kiwi.gui :as gui])
  (:import java.awt.Toolkit)
  (:import java.awt.datatransfer.Clipboard)
  (:import java.awt.datatransfer.DataFlavor)
  (:import java.awt.datatransfer.FlavorEvent)
  (:import java.awt.datatransfer.FlavorListener)
  (:import java.awt.datatransfer.Transferable)
  (:import java.awt.datatransfer.UnsupportedFlavorException)
  )

(defn get-current-clipping [clipboard]
  (str (. (. clipboard (getContents nil) ) getTransferData (. DataFlavor stringFlavor))))

(defn clipboard-watch
  [clip-hist key identity old new]
  (when-not (= new old)
      (swap! clip-hist conj new)))
      ;(println "something new in clipboard: " old " => " new))
    ;(println "nothing new in clipboard: " old " => " new)))

(defn test-and-update
  [prev-clipboard]
   (do
    (. Thread (sleep 550))
     (reset! prev-clipboard (clip/contents))
     (recur prev-clipboard)))

(defn -main
  [& args]
  (let [clipboard (. (Toolkit/getDefaultToolkit)
                     (getSystemClipboard))
        ;curr-clipboard (get-current-clipping clipboard)  ;curr-clipboard (atom (. clipboard (getContents nil)))
        prev-clipboard (atom  "")
        clip-hist (atom (vector nil))
        clip-container (agent [])
        active-list (seesaw/listbox)]
    (add-watch prev-clipboard :clip (partial clipboard-watch clip-hist))
    (gui/run clip-hist active-list)
    (bind/bind (bind/transform (clip/contents)) (bind/b-send clip-container conj))
    (bind/bind clip-hist (bind/property active-list :model))
    (test-and-update prev-clipboard)))
     ;(while true
      ;(repeatedly
       ;(. Thread (sleep 550))
       ;(try
       ;  (reset! prev-clipboard (clip/contents)) ;(get-current-clipping clipboard))
       ;  (catch Exception e (println (.getMessage e)))
       ;  )
    ;(dorun
    ; (repeatedly
    ;  (test-and-update prev-clipboard)

