(ns rss-filter.main
  (:require ;; REST API
            [org.httpkit.server :as http]
            [reitit.ring :as ring]

            ;; Data transformation
            [clojure.data.xml :as xml]

            ;; Logging
            [clojure.tools.logging :as log]

            ;; Feed specific
            [rss-filter.cortex :as cortex])
  (:gen-class))


;; >> Application

(defn feed-handler [{:keys [url filterer]}]
  (fn [{:keys [uri]}]
    {:status 200
     :headers {"Content-Type" "application/xml"}
     :body (-> url
               slurp
               xml/parse-str
               filterer
               xml/emit-str)}))

(defn env [key]
  (or (System/getenv key)
      (throw (Exception. (format "Environment variable %s not set" key)))))

(defn make-routes []
  [["/feed"
    ["/cortex" {:get (feed-handler
                       {:url (env "CORTEX_FEED_URL")
                        :filterer cortex/filterer})}]]])

(defn log-request [handler]
  (fn [{:keys [request-method uri] :as request}]
    (log/info (format "%s request for %s" request-method uri))
    (handler request)))

(defn make-app []
  (ring/ring-handler
    (ring/router
      (make-routes))
    (ring/create-default-handler)
    {:middleware [log-request]}))



;; >> Server

(defonce server (atom nil))

(defn start []
  (reset! server (http/run-server (make-app) {:port 8080})))

(defn stop []
  (when-not (nil? @server)
    (@server :timeout 100)
    (reset! server nil)))

(defn -main [& _args]
  (log/info "Starting server")
  (start))

(comment
  (-main))
