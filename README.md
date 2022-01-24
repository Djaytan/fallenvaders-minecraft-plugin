# fallenvaders-minecraft-core-mod

![Target](https://img.shields.io/badge/mod-Minecraft-blueviolet)
![Minecraft version](https://img.shields.io/badge/version-1.18.1-blue)
[![CircleCI](https://circleci.com/gh/FallenVaders/fallenvaders-minecraft-core-mod/tree/main.svg?style=svg)](https://circleci.com/gh/FallenVaders/fallenvaders-minecraft-core-mod/tree/main)

FallenVaders' Minecraft core mod

## Gradle Wrapper

A wrapper script of `gradle` has been included in the project: `gradlew` for Linux and `gradlew.bat` for Windows. His
behavior is exactly the same as the original command `gradle` and don't need to be installed.

Shortly, this is recommended to use this wrapper instead of your local version of gradle. It simplifies the environment
setup for contributors by avoiding manual installation. Furthermore, it permits to share a same gradle setup among
environments which lead to fewer issues.

For more information, refer you to: https://docs.gradle.org/current/userguide/gradle_wrapper.html

## Build the mod

Simply run this command:

```shell
./gradlew build
```

The mod will be created under the repository `build/libs`.

## Fabric

The project use Fabric as mod loader and API. For more information, go here: https://fabricmc.net/

## MultiMC

MultiMC is a great Minecraft launcher which is used for developments.

To install it, follow these steps here (Windows):
https://fabricmc.net/wiki/player:tutorials:install_multimc:windows

## Cautions

This mod must contains only common features for client and server sides.

For specific client side features, they must be implemented [here](https://github.com/FallenVaders/fallenvaders-minecraft-client-mod).
For specific server side features, go [here](https://github.com/FallenVaders/fallenvaders-minecraft-server-mod).

Implementing server side features in this mod can expose server to security issues.
