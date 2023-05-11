# API Description

To provide seamless navigation and a modern user interface, the app utilizes the Navigation Controller to allow users to switch between different pages with a bottom nav bar.

Fragments have been employed to divide the UI into reusable components so they can be independently managed and updated.

I utilised ViewModels to store and manage UI-related data and handle the communication between fragments, ensuring separation of concerns and data-persistence.

Retrofit Instance for GET requests to the API with Gson to map the response to a [custom object](https://git.cardiff.ac.uk/c21048229/notttj-21048229-mob-dev-portfolio/-/blob/main/4-application/app/src/main/java/uk/ac/cardiff/c21048229/mycommute/retrofit/SearchModel.java). This lets the app use callback methods for better reliability and performance.

Notification Channels are utilized to provide daily notifications, ensuring that users receive important updates about train times and delays.

RecyclerView to efficiently display of large data sets (train services/stations) by optimising memory usage by recycling views, providing smooth scrolling and fast performance.

For the onboarding welcome activity, I used the ViewPager2 component to display intuitive swipeable screens displaying the features of the app.

To store the lists of stations and their CRS values, I created a [custom XML file](https://git.cardiff.ac.uk/c21048229/notttj-21048229-mob-dev-portfolio/-/blob/main/4-application/app/src/main/res/xml/stations.xml) to ensure that the location selector fragment can quickly retrieve all the station data [at runtime](https://developer.android.com/reference/android/content/res/Resources#getXml(int)).
