(ns rss-filter.cortex
  (:require [com.rpl.specter :as s]
            [rss-filter.util :as u]))

(def NOT-CORTEX-ITEM
  [:content s/ALL
   :content s/ALL
   u/ITEM
   (s/not-selected?
     u/ITEM-TITLE
     #(re-matches #".*[cC]ortex.*" %))])

(def FEED-TITLE
  [:content s/ALL
   :content s/ALL
   u/TITLE
   :content
   s/FIRST])

(def FEED-DESCRIPTION
  [:content s/ALL
   :content s/ALL
   #(= :description (:tag %))
   :content
   s/FIRST])

(defn filterer [feed]
  (->> feed
       (s/setval NOT-CORTEX-ITEM s/NONE)
       (s/setval FEED-TITLE "Cortex Crossover")
       (s/setval FEED-DESCRIPTION "RelayFM Crossover Feed, filtered for only Cortex episodes by rss-feed.")))

(comment
  (def url "")
  (def data (-> url slurp clojure.data.xml/parse-str))

  (->> data
       #_
       (s/select [:content s/ALL
                  :content s/ALL
                  u/TITLE
                  :content
                  s/FIRST])
       filterer))
