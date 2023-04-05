# Ads Application

Ads Application is an application that lets you browse ads and favourite them for offline browsing.


The app consists of two basic screen and a BottomNavigation component. 
The first screen displays all the ads from the API, with the possibility of adding them to favourites.
The second screen displays all the user's favourite ads. It's possible to remove each by a swiping gesture.

Both screens take advantage of the LazyColumn component to ensure a good user experience while scrolling.

## Solution

This solution adopts the MVVM architecture, taking advantage of the most widely used Gradle dependencies in Android development:

- Jetpack Compose
- Kotlin coroutines
- Hilt (Dagger) for DI
- Navigation components
- Room Database
- Retrofit
- Coil

The purpose of using these dependencies was to drastically reduce the time in writing boilerplate code, and having an easily readable and stable product.
Some experimental features from Jetpack Compose were also used.
Another benefit of this architecture is scalability. New features can easily be added in isolation and with ease without damaging the rest of the code. Existing features can easily be improved.

## Future Improvements

The LazyColumns could be further improved by using the Paging3 dependencies and ensuring a good pagination.
The app lacks a proper ui test base entirely. 
Some unit tests were developed at the early stages of the development of this app, but the global code coverage needs to be improvement with more time.

## Known bugs

There is currently a bug with the swiping gesture in favourites. 
This bug needs proper investigation, but at first glance it looked like a bug in the Jetpack Compose related library.
Feel free to suggest fixes or tips about this.