(ns kiwi.gui
  (:gen-class)
  (:require
   [seesaw.core :as seesaw]
   ))

(defn update-gui [atom-list active-list]
  (seesaw/config! active-list
                        :model @atom-list))

(defn display [content]
  "General display function, based on Roger Whitney's lecture slides, builds Jframe for program."
  (let [window (seesaw/frame
                :title "Kiwi"
                :on-close :exit
                :content content
                :width 425 ;850
                :height 550)] ;1100)
    (seesaw/native!)
    (seesaw/show! window)))

(defn run [atom-list active-list]
    (display (update-gui atom-list active-list)))
