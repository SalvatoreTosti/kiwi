(ns kiwi.gui
  (:gen-class)
  (:require
   [seesaw.core :as seesaw]
   ))

(defn update-gui [*list active-list]
  (seesaw/config! active-list :model @*list))

(defn display [content]
  "General display function, based on Roger Whitney's lecture slides, builds Jframe for program."
  (let [window (seesaw/frame
                :title "Kiwi"
                :on-close :exit
                :content content
                :width 425 ;850
                :height 550)] ;1100)
    ;(seesaw/native!)
    (seesaw/show! window)))

(defn run [*list active-list]
    (display (update-gui *list active-list)))
