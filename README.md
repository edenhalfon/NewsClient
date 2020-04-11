# NewsClient
This project fetch articles from NewsApi and show them in RecyclerView and than opening it inside another activity with WebView.

# Technologies & Methodologies
  * ModelView View Model (MVVM) - using LiveData and AndroidViewModel.
  * Room as persistent storage in order to support no connection situations.
  * Retrofit for network client.
  * Tests - Unit Tests & Espresso for UI tests and Junit. 


![Project Structure](https://codelabs.developers.google.com/codelabs/android-room-with-a-view-kotlin/img/a7da8f5ea91bac52.png)

The project also support WebView caching and showing articles although there is no connection.
