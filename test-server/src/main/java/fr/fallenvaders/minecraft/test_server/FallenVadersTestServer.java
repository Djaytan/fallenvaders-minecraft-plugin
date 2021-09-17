/*
 *  This file is part of the FallenVaders distribution (https://github.com/FallenVaders).
 *  Copyright © 2021 Loïc DUBOIS-TERMOZ.
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, version 3.
 *
 *  This program is distributed in the hope that it will be useful, but
 *  WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 *  General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package fr.fallenvaders.minecraft.test_server;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

public class FallenVadersTestServer {

  public static final Logger LOGGER = Logger.getAnonymousLogger();

  public static final String SERVER_JAR_NAME = "papermc-server.jar";

  public static final String[] SERVER_LAUNCH_COMMAND =
      new String[] {"java", "-server", "-Xms512M", "-Xmx2G", "-jar", SERVER_JAR_NAME, "nogui"};

  public static final String[] DEBUG_SERVER_LAUNCH_COMMAND =
      new String[] {
        "java",
        "-server",
        "-Xms512M",
        "-Xmx2G",
        "-agentlib:jdwp=transport=dt_socket,server=n,suspend=n,address=5005",
        "-jar",
        SERVER_JAR_NAME,
        "nogui"
      };

  public static void main(String[] args) {
    String[] serverLaunchCommand = SERVER_LAUNCH_COMMAND;
    if (args.length > 0) {
      if ("debug".equalsIgnoreCase(args[0])) {
        serverLaunchCommand = DEBUG_SERVER_LAUNCH_COMMAND;
      } else {
        LOGGER.severe("Wrong program arguments: only \"debug\" is allowed.");
        System.exit(-1);
      }
    }
    ProcessBuilder pb = new ProcessBuilder(serverLaunchCommand);
    pb.directory(new File("server/"));
    pb.inheritIO();
    try {
      LOGGER.info("Launching test server...");
      LOGGER.info(String.join(" ", pb.command()));
      Process p = pb.start();
      try {
        p.waitFor();
      } catch (InterruptedException e) {
        LOGGER.severe("Server interrupted!");
        e.printStackTrace();
      }
    } catch (IOException e) {
      LOGGER.severe("Failed to launch test server! Maybe wrong default working directory setup?");
      e.printStackTrace();
    }
  }
}
