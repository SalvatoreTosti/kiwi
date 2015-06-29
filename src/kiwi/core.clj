(ns kiwi.core
  (:gen-class)
  (:require
   [seesaw.core :as seesaw]
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

(defn updated-clipboard? [prev curr]
  (not= prev curr))

(defn update-clip-hist [curr clip-hist]
  (conj curr clip-hist)
  ;(map #(println " " + %) clip-hist)
  )

(defn -main
  [& args]
  (let [clipboard (. (Toolkit/getDefaultToolkit)
                     (getSystemClipboard))
        curr-clipboard (get-current-clipping clipboard)  ;curr-clipboard (atom (. clipboard (getContents nil)))
        prev-clipboard (atom  "")
        clip-hist (atom (vector nil))
        ]

     (gui/run)
     (while true
       ;(println (updated-clipboard? @prev-clipboard (get-current-clipping clipboard)))
       (println  (. clipboard (isDataFlavorAvailable (. DataFlavor stringFlavor))))
         (. Thread (sleep 250))
        (try
         (when (and
                (updated-clipboard? @prev-clipboard (get-current-clipping clipboard))
                ;(and
                 (. clipboard (isDataFlavorAvailable (. DataFlavor stringFlavor)))
                 ;(.) )
                )
           (do
             (swap! clip-hist conj (get-current-clipping clipboard))
             (swap! prev-clipboard (constantly (get-current-clipping clipboard)))
             (println (last @clip-hist))
             )
           )
         (catch Exception e (println (str "caught exception: " (.getMessage e)))))
       ))
  )


  ;((String) clipboard.getContents(null).getTransferData(DataFlavor.stringFlavor)))
