(ns rss-filter.util
  (:require [com.rpl.specter :as s]))

(def ITEM #(= :item (:tag %)))

(def TITLE #(= :title (:tag %)))

(def ITEM-TITLE
  [:content (s/filterer TITLE) s/FIRST
   :content s/FIRST])
