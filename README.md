# Warehouse Android Test

## Overview

This project is a technical test for The Warehouse Senior Android Developer interview, restructured from a Java version. The project is built using the latest Android Studio (Ladybug Feature Drop | 2024.2.2) and leverages technologies such as Kotlin, MVVM architecture, Kotlin Flow, Retrofit, and Jetpack DataStore. It implements functionality to search The Warehouse products and display product details. Additionally, it provides the necessary unit tests and instrumented tests.

## Features

- **Product Search:**  
  Users can search for The Warehouse products using keywords. The search results are displayed in a list. The refactored logic uses paging to handle large datasets: 20 items are loaded at a time and when the user scrolls to the bottom, the subsequent 20 items are fetched. Additionally, a debouncing mechanism prevents duplicate requests, and the pull-to-refresh feature has been removed as it did not match user expectations in most search scenarios.
- **Product Detail:**  
  Tapping on a product displays detailed information, including:
    - An image gallery showing multiple product images.
    - Product name, price, barcode, description, product ID, and sold online status.
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


