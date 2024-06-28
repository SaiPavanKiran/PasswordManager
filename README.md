# Password Manager Android Application

## Project Overview
The Password Manager Android Application allows users to securely store their usernames and passwords locally on their devices. The stored data is protected using the AES encryption algorithm, ensuring that user credentials remain safe and private. The application features a user-friendly interface for managing credentials.

### Key Features
- **Secure Storage:** All usernames and passwords are stored locally using AES encryption.
- **Add Credentials:** Users can add new account details (Account Name, Username, Password) using a bottom sheet form.
- **View and Manage Credentials:** Users can view, edit, or delete their saved credentials from a list.
- **Biometric Authentication:** Users must authenticate using their fingerprint to access the application.
- **Password Strength Indicator:** Indicates the strength of the entered password below the password field.
- **Password Generator:** A button in the add bottom sheet generates a strong password for the user.

## Main Warning Note ⚠️
> **Warning:** Users must enroll at least one fingerprint in the device settings to enter this application. This requirement will be made optional in future releases.

## Prerequisites
Before you begin, ensure you have met the following requirements:
- **Android Studio:** version 2024.1.1 or higher
- **Kotlin:** version 2.0.0 or higher
- **Jetpack Compose:** version 2.0.0
- **Android Device/Emulator:** running Android version 8 or higher
- **Room Database:** for data storage

## Setup Instructions
Follow these steps to set up the project on your local machine:

1. **Clone the Repository:**
    ```sh
    git clone https://github.com/SaiPavanKiran/PasswordManager.git
    ```

2. **Open the Project in Android Studio:**
    - Launch Android Studio.
    - Select `Open an existing project`.
    - Navigate to the cloned repository and select it.

3. **Configure SDK and Dependencies:**
    - Ensure you have the appropriate SDK version installed.
    - Sync the project to download all necessary dependencies by clicking on `File` -> `Sync Project with Gradle Files`.

## Build Instructions
To build and run the project, follow these steps:

1. **Clean and Rebuild the Project:**
    - Navigate to `Build` -> `Clean Project`.
    - Then, go to `Build` -> `Rebuild Project`.

2. **Run the Project:**
    - Select the target device/emulator from the available options.
    - Click the `Run` button or navigate to `Run` -> `Run 'app'`.

## Usage Instructions
Once the project is set up and running, you can use the application as follows:

1. **Launching the App:**
    - After building the project, launch the app on your device or emulator.

2. **Navigating the App:**
    - **Home Screen:** Displays a list of all user-saved credentials.
    - **Add Credentials:** Tap the plus button at the bottom to open a bottom sheet where you can add a new account name, username, and password. The passwords are saved using AES encryption.
    - **View and Manage Credentials:**
        - Tap on the forward button on any list item to open a bottom sheet with the corresponding details.
        - In this bottom sheet, you will find two buttons:
            - **Edit (Blue Button):** Allows you to edit the account name, username, and password. After editing, click the update button to save changes.
            - **Delete (Red Button):** Deletes the selected credential from the list.
              
## Videos

<p align="center">
  <img src="https://github.com/SaiPavanKiran/PasswordManager/assets/170409295/b42e9954-803c-4522-b83a-88e230a64482" alt="Prompting User Fingerprint" width="250" />
    &nbsp;
    &nbsp;
    &nbsp;
  <img src="https://github.com/SaiPavanKiran/PasswordManager/assets/170409295/8f3fe5ad-1ced-41f5-81e0-355b69be9004" alt="Adding New User Detail" width="250" />
</p>

<p align="center">
  <strong>Prompting User Fingerprint</strong>
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    <strong>Adding New User Detail</strong>
</p>

<br/>

<p align="center">
  <img src="https://github.com/SaiPavanKiran/PasswordManager/assets/170409295/5c8f93f4-2b08-416c-9adf-5f08c89186c8" alt="Viewing List Item Details" width="250" />
    &nbsp;
    &nbsp;
    &nbsp;
  <img src="https://github.com/SaiPavanKiran/PasswordManager/assets/170409295/aeed88b6-4bd8-4e3c-a32a-2657c290ae33" alt="Deleting a List Item" width="250" />
</p>

<p align="center">
  <strong>Viewing List Item Details</strong>
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    <strong>Deleting a List Item</strong>
</p>
   
## Additional Information

### Troubleshooting
- **App crashes on launch:** Ensure all dependencies are properly configured, the device/emulator meets the required specifications, and verify if you gave the Android manifest permissions properly.
- **Issues with data encryption/decryption:** Verify that the encryption keys are correctly managed and stored.

### Known Issues
- Performance may degrade on older devices with lower processing power.
- List items with extremely long usernames or passwords may not display correctly.

### Future Releases
- Making biometric authentication optional in the app settings.

### Contributing
We welcome contributions to the project. To contribute, follow these steps:

1. **Fork the Repository:**
    - Navigate to the project repository on GitHub and click the `Fork` button.

2. **Create a New Branch:**
    ```sh
    git checkout -b feature/your-feature
    ```

3. **Commit Your Changes:**
    ```sh
    git commit -m 'Add your feature'
    ```

4. **Push to the Branch:**
    ```sh
    git push origin feature/your-feature
    ```

5. **Open a Pull Request:**
    - Go to the original repository on GitHub.
    - Click the `New Pull Request` button.
    - Provide a detailed description of your changes and submit the pull request.

### Contact Information
For any questions or support, please contact:
- **Name:** Sai Pavan Kiran
- **GitHub:** [SaiPavanKiran](https://github.com/SaiPavanKiran)
