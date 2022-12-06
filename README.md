# Assignment from Mobile Computing
Please note that, the language used in application is Slovak, but application is easy to use :)


# Screens

- Registration 
- Login 
- Pub & Bar & Restaurant list
- Pub/Bar/Restaurant detail 
- Friends
- Nearby pub/bar/restaurant screen
- Add friends / Remove friends
- Pub / Bar / Restaurant list within a radius of 500 meters

## What have been used?

- [Fragments](https://developer.android.com/guide/fragments) 
- [Navigation](https://developer.android.com/guide/navigation)
- [RecyclerViews](https://developer.android.com/jetpack/androidx/releases/recyclerview)
- [ViewModels](https://developer.android.com/reference/android/arch/lifecycle/ViewModel)
- [DataBinding](https://developer.android.com/topic/libraries/data-binding)
- [RoomDatabase](https://developer.android.com/training/data-storage/room)
- [Coroutines](https://developer.android.com/kotlin/coroutines)
- [Retrofit](https://www.digitalocean.com/community/tutorials/retrofit-android-example-tutorial)
- [Location services](https://developer.android.com/reference/android/location/Location)
- [Overpass API](https://wiki.openstreetmap.org/wiki/Overpass_API)

## Registration screen details
- contains inputs for username, password, repeat password and buttons for registration and login
- after clicking on login button, user is redirected to Login screen
- after clicking on registration button, user is being registered and is redirected to first fragment after successful login/registration - Pub & Bar & Restaurant list

## Login screen details
- contains inputs for username, password, buttons for login and registration
- after clicking on login button, user is redirected to Pub & Bar & Restaurant list if login details are correct
- after clicking on registration button, user is redirected to Registration screen

## Pub / Bar / Restaurant screen details
- contains list of pubs/bars/restaurants, where at least one user is checked in
- each row contains pub/bar/restaurant name, number of people checked in and the distance of the pub/bar/restaurant
- user is able to refresh the screen, sort pubs/bars/restaurants, add friends etc.

## Pub/Bar/Restaurant screen details
- contains different information about pub / bar / restaurant

## Nearby Pub / Bar / Restaurant screen details
- user has to have GPS turned on to find the neariest pub / bar / restaurant
- pub / bar / restaurant is found in a radius of 500 meters

## Friend list screen details
- contains list of friends, who have added user between their friends 
- under each friend's name is the pub / bar / restaurant, where they have last checked in (if they did so)

## Friend add / remove screen details
- contains input for filling the username of a friend and button for adding or deleting the friend


## Some random meme :P
![enter image description here](https://i.imgflip.com/73dyim.jpg)