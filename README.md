#Password Manager Android Application
##Project Overview
The Password Manager Android Application allows users to securely store their usernames and passwords locally on their devices. The stored data is protected using the AES encryption algorithm, ensuring that user credentials remain safe and private.

##Prerequisites
Before you begin, ensure you have met the following requirements:
*Android Studio: version 2024.1.1 or higher
*Kotlin: version 2.0.0 or higher
*Jetpack Compose: version 2.0.0
*Android Device/Emulator: running Android version 8 or higher
*Room Database: for data storage

##Setup Instructions
Follow these steps to set up the project on your local machine:

1.Clone the Repository:
git clone https://github.com/SaiPavanKiran/PasswordManager.git

2.Open the Project in Android Studio:
*Launch Android Studio.
*Select Open an existing project.
*Navigate to the cloned repository and select it.

3.Configure SDK and Dependencies:
*Ensure you have the appropriate SDK version installed.
*Sync the project to download all necessary dependencies by clicking on File -> Sync Project with Gradle Files.

##Build Instructions
To build and run the project, follow these steps:

1.Clean and Rebuild the Project:
*Navigate to Build -> Clean Project.
*Then, go to Build -> Rebuild Project.

2.Run the Project:
*Select the target device/emulator from the available options.
*Click the Run button or navigate to Run -> Run 'app'.

##Usage Instructions
Once the project is set up and running, you can use the application as follows:

1.Launching the App:
*After building the project, launch the app on your device or emulator.

2.Navigating the App:
*Home Screen: Displays a list of all user-saved credentials.
*Add Credentials: Tap the plus button at the bottom to open a bottom sheet where you can add a new username and password.
*Edit or Delete Credentials: Tap on the forward symbol on any list item to open a bottom sheet with the corresponding details. Here, you can either edit the details and update them or   delete them using the delete button.

##Additional Information
###Troubleshooting
*App crashes on launch: Ensure all dependencies are properly configured, and the device/emulator meets the required specifications.
*Issues with data encryption/decryption: Verify that the encryption keys are correctly managed and stored.

###Known Issues
*Performance may degrade on older devices with lower processing power.
*List items with extremely long usernames or passwords may not display correctly.

###Contributing
We welcome contributions to the project. To contribute, follow these steps:

1.Fork the Repository:

*Navigate to the project repository on GitHub and click the Fork button.

2.Create a New Branch:
git checkout -b feature/your-feature

3.Commit Your Changes:

git commit -m 'Add your feature'

4.Push to the Branch:

git push origin feature/your-feature

5.Open a Pull Request:
*Go to the original repository on GitHub.
*Click the New Pull Request button.
*Provide a detailed description of your changes and submit the pull request.

