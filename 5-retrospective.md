# Retrospective

During the development of MyCommute, I was able to accomplish most of what I set out to do. The app successfully provides features such as daily commute notifications, a comprehensive train timetable, localization options, all with integration with the RTT API to provide accurate train times.

I learnt how to use key Android APIs and third-party libraries, such as utilizing fragments, Retrofit, RecyclerView, and other components to create a user-friendly and feature-rich app.

I initially planned to implement offline caching for saved commutes and timetables, but I instead focused on ensuring the app worked without bugs and fitted the [Android Accessibility Standards](https://stuff.mit.edu/afs/sipb/project/android/docs/guide/topics/ui/accessibility/checklist.html).

I also have my API key in plain-text within the source-code, which is bad security practice- ideally I should store it outside of the git repository.

In future development I would also like to implement a user service with Firebase to sync user data across devices and provide SMS notifications for daily commutes. I would also like to explore additional features such as journey planning, Google Maps integration and other transportation modes. I would also invest in implementing [material design 3](https://m3.material.io/) for a more modern UI that is consistent with other Material Design apps.
