(ns rss-filter.cortex
  (:require [com.rpl.specter :as s]
            [rss-filter.util :as u]))

(def CORTEX-ITEM
  [:content s/ALL
   :content s/ALL
   u/ITEM
   (s/not-selected?
     u/ITEM-TITLE
     #(re-matches #".*[cC]ortex.*" %))])

(defn filterer [feed]
  (s/setval CORTEX-ITEM s/NONE
    feed))
