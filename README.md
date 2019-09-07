![Logo](logo.png)

[![CircleCI](https://circleci.com/gh/andreshj87/Nyuzuk.svg?style=svg)](https://circleci.com/gh/andreshj87/Nyuzuk) [![Codacy Badge](https://api.codacy.com/project/badge/Grade/4444fcaff2c847bf92757f839c138c46)](https://app.codacy.com/app/andreshj87/Nyuzuk?utm_source=github.com&utm_medium=referral&utm_content=andreshj87/Nyuzuk&utm_campaign=Badge_Grade_Dashboard) [![Codacy Badge](https://api.codacy.com/project/badge/Coverage/7e32dafe20574c42afc2ae4437278db0)](https://www.codacy.com/app/andreshj87/Nyuzuk?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=andreshj87/Nyuzuk&amp;utm_campaign=Badge_Coverage)

# Nyuzuk

Android app written in Kotlin that displays news articles - powered by [News API](https://newsapi.org)

## How to run it?
In order to make it work, make sure to get your free API key from https://newsapi.org and add it to a new file `config/secrets.properties` like this:
```plain
API_KEY=%your-api-key%
```

Do a gradle sync and everything should be ready.

## Architecture
It's designed based in the classic 3-layer **clean architecture**.

![](https://raw.githubusercontent.com/ImangazalievM/CleanArchitectureManifest/master/images/CleanArchitectureLayers.png)

Every layer is represented as module, thus achieving separation of concerns.
Also, it is important to note that domain layer is defined as a pure-kotlin non-android module, in order to avoid dependencies with the android framework.

### domain layer
This is where all of our **domain entities** and **business logic** is.

This layer does not need to know anything about the other 2, being totally independent in this sense. Other layers are responsible for mapping models to be naturally understood by the `domain` layer.

Use cases contain business logic that can be used by the `presentation` layer, and make use of repositories that are implemented in the `data` layer.

I'm using [coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html) [flow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-flow/) to be able to execute asynchronous code.

## data layer
This is where our repository implementations and data sources are.

Repositories are responsible for deciding whether to retrieve data from local or remote datasources.
I'm using [Room](https://developer.android.com/jetpack/androidx/releases/room) as database for the local datasource and Retrofit for the remote datasource.

## presentation layer
In this layer I'm using the MVVM pattern, using [Jetpack ViewModels](https://developer.android.com/topic/libraries/architecture/viewmodel) and exposing a [LiveData](https://developer.android.com/topic/libraries/architecture/livedata) object that the view (activities or fragments) is observing and can react updating the UI accordingly.

## other
I'm using [koin](https://github.com/InsertKoinIO/koin) for dependency injection and, even if the learning curve is super smooth making it really easy to setup, you better watch out for any potential runtime crash while trying to resolve dependencies.

It's worth mentioning that I'm using the `Either` class from [Λrrow](https://github.com/arrow-kt/arrow) library, since it allows a very natural way of handling errors.
