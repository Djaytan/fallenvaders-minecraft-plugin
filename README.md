# fallenvaders-minecraft-core-mod

![Target](https://img.shields.io/badge/mod-Minecraft-blueviolet)
![Minecraft version](https://img.shields.io/badge/version-1.17.1-blue)
![CircleCI](https://img.shields.io/circleci/build/github/FallenVaders/mc-fallenvaders?token=6c195a3a2f200bed4f2d9ea090309af8ed1d2c62)

FallenVaders core mod

# Gradle Wrapper

A wrapper script of `gradle` has been included in the project: `gradlew` for Linux and `gradlew.bat` for Windows. His
behavior is exactly the same as the original command `gradle` and don't need to be installed.

Shortly, this is recommended to use this wrapper instead of your local version of gradle. It simplifies the environment
setup for contributors by avoiding manual installation. Furthermore, it permits to share a same gradle setup among
environments which lead to fewer issues.

For more information, refer you to: https://docs.gradle.org/current/userguide/gradle_wrapper.html

# Build the mod

Simply run this command:

```shell
./gradlew build
```

The mod will be created under the repository `build/libs`.

# Fabric

The project use Fabric as mod loader and API. For more information, go here: https://fabricmc.net/

# MultiMC

MultiMC is a great Minecraft launcher which is used for developments.

To install it, follow these steps here (Windows):
https://fabricmc.net/wiki/player:tutorials:install_multimc:windows
