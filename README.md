<h1 align="center"> Kotlin Repositories</h1>

<p align="center">
  <a href="https://android-arsenal.com/api?level=23"><img alt="API" src="https://img.shields.io/badge/API-23%2B-brightgreen.svg?style=flat"/></a>
</p>

<p align="center">  
Android App that lists Kotlin Github repositories
</p>

<p align="center">
<img src="/screenshots/Screen_1.png" width="200"/> <img src="/screenshots/Screen_2.png" width="200"/> <img src="/screenshots/Screen_3.png" width="200"/> <img src="/screenshots/Screen_4.png" width="200"/>
</p>

## Download
Go to the [Releases](https://github.com/yalematta/kotlin-repositories/releases) to download the lastest APK.

## API
By using API for Repositories (it is one of the internal GitHub API’s),
<br />
I have decided to use [GitHub Search API](https://developer.github.com/v3/search/#search-repositories) and sort the repositories by their stars.

## Tech stack
- Minimum SDK level 23
- [Kotlin](https://kotlinlang.org/) based + [Coroutines](https://github.com/Kotlin/kotlinx.coroutines) for asynchronous.
- Dagger-Hilt (alpha) for dependency injection.
- JetPack
  - LiveData - notify domain layer data to views.
  - Lifecycle - dispose of observing data when lifecycle state changes.
  - ViewModel - UI related data holder, lifecycle aware.
  - Navigation Component - handle everything needed for in-app navigation.
  - Data Binding - declaratively bind observable data to UI elements.
- Architecture
  - MVVM Architecture (View - DataBinding - ViewModel - Model)
  - Repository pattern
- [Glide](https://github.com/bumptech/glide) - loading images.
- [Retrofit2 & OkHttp3](https://github.com/square/retrofit) - construct the REST APIs and paging network data.
- [Material-Components](https://github.com/material-components/material-components-android) - Material design components like ripple animation, cardView.


