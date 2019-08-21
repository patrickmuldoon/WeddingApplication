# WeddingApplication
Microservice implementation using Eureka and Zuul for my wedding app.

AuthServer to handle login requests and pass back JWT. Also handles registrationRequests.

ImageServer to handle image requests (CRUD operations) for Images and PhotoAlbums.

ZuulProxyServer is the gateway that takes requests and routes them to the designated service. Validates Token and user roles for resources that have access restricted

EurekaServer is the base server for the zuulProxy to attatch itself to
