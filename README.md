# Leo TV Show App - A TV Show App made by Leo :D#

Architecture: MVVM + Repository Pattern + Kotlin flow

Data Sources:
Remote: TVMaze Rest API

What does the app do:
It consists in a Tv Show guide with 3 main features: to search shows, to search people involved in tv and maintain a favorite list
of shows.

How to run:
This app was designed for devices running android API 21 to 32. It was build using Android Studio Bumblebee | 2021.1.1 Patch 2.
This project uses gradle-7.2. Just open the project and left click on android studio's "green play button".

Trade-Offs:

- UX improvements.
- Improve network handling checking network on device.
- Write tests.
- Not implemented accessibility features.

3rd party libraries:

- Jetpack navigation component and Safe args
- Jetpack paging 3 library
- Room Database
- Retrofit for network layer
- Glide for loading images
- Hilt for Dependency Injection
- Dependencies for test as hamcrest, roboletric, espresso.

Used some base code from following sources:
Result class
https://github.com/android/architecture-samples