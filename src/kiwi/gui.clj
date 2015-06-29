(ns kiwi.gui
  (:gen-class)
  (:require
   [seesaw.core :as seesaw]
   ))

(defn build-content []
  (let [text (seesaw/text
              :text "test"
              :margin 10)
        ]
    text))

(defn display [content]
  "General display function, based on lecture slides, builds Jframe for program."
  (let [window (seesaw/frame
                :title "Fuji"
                :on-close :exit
                :content content
                :width 425 ;850
                :height 550)] ;1100)
    (seesaw/show! window)))

(defn run []
    (display (build-content)))
