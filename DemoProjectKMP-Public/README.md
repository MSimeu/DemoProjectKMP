# DemoProjectKMP

### Project Info

DemoProjectKMP is a Kotlin Multiplatform (KMP) project that showcases the usage of KMP to build a shared module for both Android and iOS applications. The app uses the [API](https://api-football-standings.azharimm.dev/leagues) to display information about each professional football league available.

### Technologies Used

- **Language**: Kotlin
- **Framework**: Kotlin Multiplatform, Android, SwiftUI
- **Database**: SQLite (using SQLDelight)
- **Network**: Ktor

### Initial Configuration

To set up the project initially, follow these steps:

1. Clone the repository:
   ```bash
   git clone https://github.com/MSimeu/DemoProjectKMP.git
   git clone https://github.com/menes-simeu_volvo/DemoProjectKMP.git
   ```
2. Navigate to the project directory:
   ```bash
   cd DemoProjectKMP
   ```
3. Open the project in Android Studio.


### Step-by-Step Guide for the Workshop

The workshop will guide you through the process of transforming the Android and iOS projects into a KMP project. We'll start with the `Start` folder, which contains the initial Android and iOS projects, and end with the `End` folder, which contains the final KMP project.

#### Step 1: Create a New Project Using the [KMP Template](https://kmp.jetbrains.com/)

For convenience, we will use a KMP template powered by jetbrain available at https://kmp.jetbrains.com/. This template will help us set up the initial project structure quickly.

1. Go to [KMP Template](https://kmp.jetbrains.com/).
2. Enter the project name, for example, "Kmp_Footballmatches".
3. Select the platforms: Android and iOS.
4. Choose the option "Do not share UI (use only SwiftUI)".
5. Download and extract the Zip file
6. Open the project with Android Studio

This will generate a project structure with a shared module for your business logic.

#### Step 2: Migrate the Data Layer

1. **Copy Data Folder**: From the `Start` Android project, copy the `data` folder and place it into the `commonMain` source set of the shared module.

2. **Set Up Ktor**: Since Retrofit is not compatible with KMP, we'll use Ktor for networking.

   - In the `lib.versions.toml` file, add the following:
   
```TOML
     
     [versions]
     ktor-client = "2.3.9"

     [libraries]
     ktor-client-core = { module = "io.ktor:ktor-client-core", version.ref = "ktor-client" }
     ktor-client-darwin = { module = "io.ktor:ktor-client-darwin", version.ref = "ktor-client" }
     ktor-client-encoding = { module = "io.ktor:ktor-client-encoding", version.ref = "ktor-client" }
     ktor-client-logging = { module = "io.ktor:ktor-client-logging", version.ref = "ktor-client" }
     ktor-client-okhttp = { module = "io.ktor:ktor-client-okhttp", version.ref = "ktor-client" }
     ktor-client-android = { module = "io.ktor:ktor-client-android", version.ref = "ktor-client" }
     ktor-serialization-kotlinx-json = { module = "io.ktor:ktor-serialization-kotlinx-json", version.ref = "ktor-client" }
     ktor-client-content-negotiation = { module = "io.ktor:ktor-client-content-negotiation", version.ref = "ktor-client" }
     
```

   - In the `build.gradle.kts` file, add the necessary dependencies:
   
     ```kotlin
     plugins {
         // Other plugins
         kotlin("plugin.serialization") version "2.0.0"
     }

     kotlin {
        // Other plugins
         sourceSets {
            commonMain.dependencies {
                implementation(libs.ktor.client.core)
                implementation(libs.ktor.client.logging)
                implementation(libs.ktor.serialization.kotlinx.json)
                implementation(libs.ktor.client.content.negotiation)
                implementation(libs.ktor.client.encoding)
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")
            }
            commonTest.dependencies {
                implementation(libs.kotlin.test)
            }

            androidMain.dependencies {
                implementation(libs.ktor.client.okhttp)
                implementation(libs.ktor.client.android)
            }

            iosMain.dependencies {
                implementation(libs.ktor.client.darwin)
            }
        }
     }
     ```

3. **Remove Retrofit Service**: Remove the `ApiRetrofitService.kt` file and replace it with `ApiKtorService.kt`.

4. **Create HttpClient**: Create a new file `HttpClient.kt` and add the following:
   ```kotlin
   expect val client: HttpClient
   ```

   Then, add the missing actual declarations for iOS and Android:

   - **Android**:
   
     ```kotlin
     actual val client: HttpClient
         get() = HttpClient(OkHttp) {
             install(HttpTimeout) {
                 socketTimeoutMillis = 60_000
                 requestTimeoutMillis = 60_000
             }
             install(ContentNegotiation) {
                 json(Json {
                     prettyPrint = true
                     isLenient = true
                     ignoreUnknownKeys = true
                 })
             }
             install(Logging) {
                 logger = Logger.DEFAULT
                 level = LogLevel.ALL
                 logger = object : Logger {
                     override fun log(message: String) {
                         Log.d("KtorClient", message)
                     }
                 }
             }
         }
     ```

   - **iOS**:
   
     ```kotlin
     actual val client: HttpClient
         get() = HttpClient(Darwin) {
             install(ContentNegotiation) {
                 json(Json {
                     prettyPrint = true
                     isLenient = true
                     ignoreUnknownKeys = true
                 })
             }
             install(Logging) {
                 level = BODY
             }
         }
     ```
     

5. **Model Classes**: Update your model classes to use `kotlinx.serialization.Serializable`:

   ```kotlin
   import kotlinx.serialization.Serializable

   @Serializable
   data class LeagueResponse(
       val data: List<League>
   )

   @Serializable
   data class League(
       val id: String,
       val name: String,
       val abbr: String,
       val logos: Logos
   )

   @Serializable
   data class Logos(
       val light: String,
       val dark: String
   )
   ```


#### Step 3: Move the Domain Layer

1. **Copy Domain Files**: From the `Start` Android project, locate the `domain` package and copy it into the shared module. Ensure that the package location matches in the shared module.

2. **Update Package Location**: Update the package declaration in the copied domain files to match the new location in the shared module.

3. **Refactor LeagueRepositoryImpl**: In the `LeagueRepositoryImpl` class, you can retain the Ktor implementation. Uncomment the Ktor implementation and ensure that it imports the correct `ApiKtorService`.

   ```kotlin
   class LeagueRepositoryImpl : LeagueRepository {
       private val apiService = ApiKtorService()

       override suspend fun getLeagues(): List<League> {
           return withContext(Dispatchers.IO) {
               apiService.getLeagues().data
           }
       }
   }
   ```

By migrating the domain code, you consolidate your business logic into a single shared module, making it accessible to both Android and iOS platforms. Additionally, using Ktor for networking ensures consistent behavior across platforms.

#### Step 4: Migrate the Presentation Layer for Android

1. **Copy Presentation Files**: Navigate to the Start Android project and copy the presentation files (excluding `App.kt` and `MainActivity.kt`) into the Compose App module where your main activity is located.

2. **Update Imports**: Replace all `material3` imports with `material`.

3. **Remove ColorScheme Code**: Remove any code related to `ColorScheme` for now.

4. **Add Coil Dependency**: Ensure that you add the Coil dependency to your project. Add the following line to your `build.gradle.kts` file:

   ```kotlin
   implementation("io.coil-kt:coil-compose:2.0.0-rc01")
   ```

   This will enable you to use Coil for image loading in your Compose UI.

5. **Update AndroidManifest.xml**:
   - Rename the `MainActivity` if necessary to match the new Compose activity name.
   - Add the internet permission if your app requires internet access. Add the following line within the `<manifest>` tag:

   ```xml
   <uses-permission android:name="android.permission.INTERNET" />
   ```

Migrating the presentation layer involves transferring UI-related files and dependencies from the Android project to the Compose module. Ensure that your AndroidManifest.xml is updated accordingly, especially if there are changes to activity names or if your app requires internet access.

Finally, test your Android app to ensure that it behaves as expected.

#### Step 4: Migrate the Presentation Layer for iOS

1. **Copy Presentation Files**: From the Start iOS project, copy the relevant presentation files into the `iosApp.xcodeproj` within Xcode. Ensure that you include all necessary SwiftUI views and controllers.

2. **Run Gradle Tasks**: Run the Gradle tasks `compileKotlinIosArm64` and/or `compileKotlinIosX64` to compile the Kotlin code for iOS.

3. **Update Fetch Leagues Method**: Replace the existing method for fetching leagues with the following code snippet:

    ```swift
    private func fetchLeagues() {
        GetLeaguesUseCase(repository: repository).invoke { [weak self] result, error in
            DispatchQueue.main.async {
                if let league = result {
                    self?.uiState = .success(league)
                } else {
                    self?.uiState = .failure(error?.localizedDescription ?? "Error")
                }
            }
        }
    }
    ```

    This code snippet utilizes a use case to fetch leagues from the repository and updates the UI state accordingly.

4. **Update Content View**: In the `ContentView` or relevant SwiftUI view, ensure that you add `id: \.self` to list items. This helps SwiftUI identify individual list items correctly for updates.

Migrating the presentation layer for iOS involves transferring SwiftUI views and controllers from the Start iOS project to the Xcode project.

Finally, test your iOS app in Xcode to ensure that it behaves as expected.

### Contact

- **Mobile Developers**:
  - [Menes SIMEU]([menes.simeu@volvo.com])
 