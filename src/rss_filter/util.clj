(ns rss-filter.util
  (:require [com.rpl.specter :as s]))

(def ITEM #(= :item (:tag %)))

(def TITLE
  [:content (s/filterer #(= :title (:tag %))) s/FIRST
   :content s/FIRST])
