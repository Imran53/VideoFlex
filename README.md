# VideoFlex

<img src="https://github.com/Imran53/VideoFlex/assets/53171337/f64e482b-e556-49b3-b792-a18a8326b32d" alt="Video Icon" width="100" height="100">

VideoFlex is an Android application that enables seamless video streaming using Firebase Realtime Database and ExoPlayer. The app allows users to access a collection of videos stored in Firebase and displays them in a RecyclerView with their respective titles. When a video is selected, it plays in a video player with landscape mode support. Users can conveniently navigate within the video by seeking 10 seconds forward or backward and also have the ability to adjust the playback speed. Additionally, VideoFlex incorporates a search bar feature, allowing users to easily search for specific videos within the collection.

## Key Features

- Seamless video streaming using Firebase Realtime Database and ExoPlayer.
- Display of videos in a RecyclerView with titles.
- Landscape mode support for immersive viewing experience.
- Convenient navigation with 10-second seek options.
- Playback speed adjustment for personalized experience.
- Search bar for easy video discovery.

## Screenshots


<div align="center">
  <img src="https://github.com/Imran53/VideoFlex/assets/53171337/0350cbb1-426b-4dd8-800a-48c4a6530dcc" alt="Image 1" width="200" height="400">
  <img src="https://github.com/Imran53/VideoFlex/assets/53171337/529c0193-146f-45b2-9fc0-c11f9ef7258f" alt="Image 2" width="200" height="400">
  <img src="https://github.com/Imran53/VideoFlex/assets/53171337/b8e3d07e-e10d-4051-8b53-952822a552a5" alt="Image 3" width="200" height="400">
</div>

## Landscape Mode

<div align="center">
  <img src="https://github.com/Imran53/VideoFlex/assets/53171337/49b8435e-967b-4a74-a16d-6c7ac0de4b5e" alt="Image 4" width="810" height="400">
</div>


## Video Preview

![videoflex](https://github.com/Imran53/VideoFlex/assets/53171337/b2744dab-3f74-4aeb-be82-74db1207434d)


## Getting Started

To get started with VideoFlex, follow these steps:

1. Clone the repository: `git clone https://github.com/Imran53/VideoFlex.git`
2. Open the project in Android Studio.
3. Set up your Firebase project and configure the Firebase Realtime Database.
4. Update the necessary Firebase and ExoPlayer dependencies in the `build.gradle` file.
5. Run the app on an Android device or emulator.

Make sure to replace `[repository URL]` with the actual URL of your GitHub repository.


## Dependencies

VideoFlex relies on the following dependencies:

- com.google.android.exoplayer:exoplayer:2.14.2
- com.google.android.exoplayer:exoplayer-core:2.14.2
- com.google.android.exoplayer:exoplayer-ui:2.14.2
- com.google.android.exoplayer:exoplayer-dash:2.14.2
- com.google.android.exoplayer:exoplayer-hls:2.14.2
- com.google.android.exoplayer:exoplayer-smoothstreaming:2.14.2
- com.google.firebase:firebase-storage:20.2.1
- com.google.firebase:firebase-core:21.1.1
- com.squareup.picasso:picasso:2.71828
- com.firebaseui:firebase-ui-database:7.2.0
- com.google.firebase:firebase-database:20.2.2

Please refer to the `build.gradle` file for a complete list of dependencies and their versions. Replace `[version]` with the actual versions you are using in your project.



## Contributing

Contributions to VideoFlex are welcome! If you find any issues or have suggestions for improvement, please submit an issue or a pull request.

## License

This project is licensed under the [MIT License](LICENSE).

