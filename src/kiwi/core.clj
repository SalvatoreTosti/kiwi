(ns kiwi.core
  (:gen-class)
  (:require
   [seesaw.core :as seesaw]
   [seesaw.bind :as bind]
   [seesaw.clipboard :as clip]
   [seesaw.dnd :as dnd]
   [kiwi.gui :as gui]
   [kiwi.run :as run])
  (:import java.awt.Toolkit)
  (:import java.awt.datatransfer.Clipboard)
  (:import java.awt.datatransfer.ClipboardOwner)
  (:import java.awt.datatransfer.DataFlavor)
  (:import java.awt.datatransfer.FlavorEvent)
  (:import java.awt.datatransfer.FlavorListener)
  ;(:import java.awt.datatansfer.Transferable)
  (:import java.awt.datatransfer.UnsupportedFlavorException))

(defn vector-remove
  [vect position]
  "zero-based item removal from a vector."
    (cond
     ;(zero? position) (subvec vect 1)
     (= position (count vect)) (subvec vect 0 (dec (count vect)))
     :else
     (vec (concat (subvec vect 0 (dec position)) (subvec vect position (count vect))))
     ))

(defn move-to-head
  [vect position]
  "Moves element from position in vect to front of vector, 0 based."
  (let [select (vect position)]
    (->> (vector-remove vect (inc position))
        (into (vector select))
  )))

(defn vec-contains? [vect x]
  "Determines if x is an element of vect."
  (pos? (.indexOf vect x)))

(defn front-conj-no-dup
  "Adds a new element to the front of a coll, will return current coll if element is already present."
  [coll x]
  (let [index  (.indexOf coll x)]
    (if (>= index 0)
      coll
      (into (vector x) coll))))

(defn handle-new-clipping
  "Examines state of clipboard and history, then adds or ignores new clipping.
  TODO: not entirely satisfied with this function, should be revisited."
  [clip-hist new]
  (let [full (>= (count clip-hist) 5)
        duplicate (vec-contains? clip-hist new)]
    (cond
     (and full (not duplicate))  ;duplicate must be explicitly checked here to avoid dropping, then attempting to insert a duplicate.
       (-> (drop-last clip-hist)
           (front-conj-no-dup new)
           (vec))
     (and (not full))
        (front-conj-no-dup clip-hist new)
     :else
       clip-hist)))

(defn clipboard-watch
  "Watches for changes in current clipping, then dispatches to handle-new-clipping to evaluate action."
  [*clip-hist key identity old new]
  (when-not (= new old)
    (println "Something new! " new " old: " old)
     (swap! *clip-hist front-conj-no-dup new)))

(defn test-and-update
  "Updates "
  [*curr-clipboard off-atom]
  (if off-atom nil) ;;To be implemented as a signal the GUI can pass to cease execution of main listen loop.
   (do
     (. Thread (sleep 550))
     (swap! *curr-clipboard (constantly (str (clip/contents))))
     (println (clip/contents))
     (recur *curr-clipboard off-atom)))

(defn -main
  [& args]
  (let [*curr-clipboard (atom  (clip/contents))
        *clip-hist (atom (vector @*curr-clipboard))
        active-list (seesaw/listbox)]
    (seesaw/listen active-list
                   :mouse-clicked
                   (fn[e] (clip/contents! (str (seesaw/selection active-list))))
                   :focus-lost (fn[e]
                                 (seesaw/selection! active-list nil)
                                 )
                   )

    (add-watch *curr-clipboard :clip (partial clipboard-watch *clip-hist))
    (gui/run *clip-hist active-list)
    (bind/bind *clip-hist (bind/property active-list :model))
    (test-and-update *curr-clipboard false)))
