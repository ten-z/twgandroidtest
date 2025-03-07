# Warehouse Android Test

## Overview

This project is a technical test for The Warehouse Senior Android Developer interview, refactored from a Java version. The project is built using the latest Android Studio (Ladybug Feature Drop | 2024.2.2) and utilizes technologies such as Kotlin, MVVM architecture, Kotlin Flow, Retrofit, and Jetpack DataStore. It implements functionality to search The Warehouse products and display product details. Additionally, it provides the necessary unit tests and instrumented tests.

## Features

- **Product Search:**  
  Users can search for The Warehouse products using keywords, and the results are displayed in a list. The refactored logic continues to implement paging to manage large datasets, loading 20 items at a time and fetching the next set when the user reaches the bottom. A debouncing mechanism has also been introduced to prevent duplicate requests. Additionally, from my experience, the pull-to-refresh feature may not align with user expectations in most search scenarios, so it has been removed.
- **Product Detail:**  
  Tapping on a product displays detailed information, including:
    - An image gallery showing multiple product images.
    - Product name, price, description, product ID, and sold online status.
    - A clearance icon is shown when promotions are available.
- **API**:
    - Uses API endpoints provided by The Warehouse.
    - **Login API**: Called in `MainActivity` to authenticate as a guest.
    - **Search API**: Called in `SearchResultActivity` to retrieve search results.
- **Modern Architecture**: The project follows the MVVM pattern, separating UI and data layers. The UI layer is further divided into views (activities, etc.) and viewModels, while the data layer is split into DataRepository and DataSource.
- **State Management**: Utilizes Kotlin Flow to manage asynchronous data and UI state.
- **Local Storage**: Uses Jetpack DataStore as a modern replacement for SharedPreferences.
- **Testing**: Contains unit tests using JUnit and instrumentation tests with Espresso.

## Screenshots

### Search Screen
<img src="https://github.com/ten-z/twgandroidtest/blob/main/Screenshot_20250307_221342.png?raw=true" width="300" alt="Search Screen" />

### Search Results
<img src="https://github.com/ten-z/twgandroidtest/blob/main/Screenshot_20250307_221406.png?raw=true" width="300" alt="Search Results" />

### Product Detail
<img src="https://github.com/ten-z/twgandroidtest/blob/main/Screenshot_20250307_221421.png?raw=true" width="300" alt="Product Detail" />


