(ns clj-intro.musicbrainz
  (:require [clojure.data.json :as json]))

(comment
  (json/read-str (slurp "https://musicbrainz.org/ws/2/release?query=artist:beatles%20AND%20country:GB&fmt=json") :key-fn keyword))

(def data (json/read-str (slurp "https://musicbrainz.org/ws/2/release?query=artist:beatles%20AND%20country:GB&20ANDstatus:Official&limit=100&fmt=json") :key-fn keyword))

(-> data
    :releases
    first)

{:date "1963-09-06",
 :barcode "",
 :artist-credit [{:name "The Beatles",
                  :artist {:id "b10bbbfc-cf9e-42e0-be17-e2c3e1d2600d",
                           :name "The Beatles",
                           :sort-name "Beatles, The",
                           :disambiguation "UK rock band, “The Fab Four”"}}],
 :packaging "Cardboard/Paper Sleeve",
 :label-info [{:catalog-number "GEP 8880", :label {:id "df7d1c7f-ef95-425f-8eef-445b3d7bcbd9", :name "Parlophone"}}],
 :title "The Beatles’ Hits",
 :track-count 4,
 :packaging-id "f7101ce3-0384-39ce-9fde-fbbd0044d35f",
 :disambiguation "serif",
 :status "Official",
 :id "00dafa47-bdce-4d96-b8af-af0859ee008f",
 :score 100,
 :count 1,
 :status-id "4e304316-386d-3409-af2e-78857eec5cfe",
 :release-events [{:date "1963-09-06",
                   :area {:id "8a754a16-0027-3a29-b6d7-2b40ea0481ed",
                          :name "United Kingdom",
                          :sort-name "United Kingdom",
                          :iso-3166-1-codes ["GB"]}}],
 :text-representation {:language "eng", :script "Latn"},
 :country "GB",
 :media [{:format "7\" Vinyl", :disc-count 0, :track-count 4}],
 :release-group {:id "794a5fcd-4098-3519-b4b6-e66707f4cbc3",
                 :type-id "6d0c5bf6-7a33-3420-a519-44fc63eedebf",
                 :primary-type-id "6d0c5bf6-7a33-3420-a519-44fc63eedebf",
                 :title "The Beatles’ Hits",
                 :primary-type "EP",
                 :secondary-types ["Compilation"],
                 :secondary-type-ids ["dd2a21e1-0c00-3729-a7a0-de60b84eb5d1"]}}

(-> data
    :releases
    count)

(->> data
     :releases
     (map :title)
     (into #{}))

(->> data
     :releases
     (map (juxt :title :date)))

(->> data
     :releases
     (mapcat :media))

(->> data
     :releases
     (sort-by :track-count #(compare %2 %1))
     (map #(select-keys % [:title :date :track-count]))
     (take 5)
     )