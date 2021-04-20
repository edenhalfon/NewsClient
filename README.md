# NewsClient
This project fetch articles from NewsApi and show them in RecyclerView and than opening it inside another activity with WebView.

# Technologies & Methodologies
  * Model View View-Model (MVVM) - using LiveData and AndroidViewModel.
  * Room as persistent storage in order to support no connection situations.
  * Retrofit for network client.
  * Tests - Unit Tests & Espresso for UI tests and Junit. 


![Project Structure](https://res.cloudinary.com/practicaldev/image/fetch/s--UxW69rdg--/c_limit%2Cf_auto%2Cfl_progressive%2Cq_auto%2Cw_880/https://dev-to-uploads.s3.amazonaws.com/i/yxk8akawka6rctphk0cq.png)

The project also support WebView caching and showing articles although there is no connection.
