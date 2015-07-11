(ns kiwi.gui
  (:gen-class)
  (:require
   [seesaw.core :as seesaw]
   ))

(defn update-gui [atom-list active-list]
  (seesaw/config! active-list
                        :model @atom-list)
  )

#_(defn build-content [atom-list active-list]
  (let [text (seesaw/text
              :text "test"
              :margin 10)
        (seesaw/config! active-list
                        :model @atom-list)
        ]
    display-list))

(defn display [content]
  "General display function, based on lecture slides, builds Jframe for program."
  (let [window (seesaw/frame
                :title "Fuji"
                :on-close :exit
                :content content
                :width 425 ;850
                :height 550)] ;1100)
    (seesaw/show! window)))

(defn run [atom-list active-list]
    (display (update-gui atom-list active-list)))
