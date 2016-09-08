# Photo Server

Handles photo uploads and processes EXIF metadata.

## Requirements

  - `scala`
  - `sbt`
  - `rabbitmq`
  - `redis`

## Usage

Setup credentials:

```
cp photo-service/conf/application.conf{.sample,}
```

(set values for `s3.accessKey` and `s3.secretKey`)

Start all the services:

```
sbt photoService/run
sbt exifService/run
sbt exifWorker/run
```

Upload a photo:

From the browser: `http://localhost:9000` (you will be asked to run evolutions on the first request).

From the command line:

```
curl -v -F json='{"user":"jorge","description":"cool"}' -F photo=@/path/to/photo http://localhost:9000/photo/
```

Retrieve the photo:

```
curl http://localhost:9000/photo/<id>
```

## Design

The project is split in 3 components:

  - **photo-service** handles photo upload and retrieval
  - **exif-service** serves exif metadata
  - **exif-worker** processes parsing of EXIF metadata from uploaded photos

### Photo Service

  - Uses in memory H2 for storage (requires running evolutions on initial request).
  - On file upload, pushes a message to the rabbitmq queue 'uploaded_photo'.

### EXIF Worker

  - Consumes rabbitmq messages, parses and stores EXIF metadata in redis.

### EXIF Service

  - Serves EXIF information from redis.

## Implementation Notes

  - For separation of concerns, the exif worker and service have been separated into two different components.
  They use redis as the data store.
  - Runtime dependency injection with Guice is used extensively as Play 2.4+ heavily relies on it and it makes testing
  easier.

## Known Issues

  - IO operations are blocking the play dispatch thread.
  - There is collision of filenames in S3 for images with the same name.

### Future Work

  - Do not block on service requests, uploads and database access (use futures) and async controllers.
  - Add unit tests for models
  - Add system tests (test all the components together)
  - Use unique keys for S3 to avoid file name collision.
