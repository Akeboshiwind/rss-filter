# RSS Feed Filterer

Personal RSS feed filterer.
Currently only customisable via code.

See `main.clj` for REST API and feed configuration.


## Configuration

Supports the following Environment Variables:

Var name | Description | Required?
--- | --- | ---
`CORTEX_FEED_URL` | The url for the RelayFM crossover feed. This feed will be filtered to only those that reference cortex. | ✅


## Running

(Remember to supply all the correct environment variables)
```bash
$ docker run -itu0 -p 8080:8080 ghcr.io/akeboshiwind/rss-filter:latest
```

## Development

There is a `bb.edn` with some built-in tasks.
(Requires `clojure cli`, `babashka` & `docker` to be installed).

Start a repl:
```bash
$ bb dev:repl
```


## Release

Releases are controlled by tags.
Push a tag with `vX.Y.Z` to release a new container.
