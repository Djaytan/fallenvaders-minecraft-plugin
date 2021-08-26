package fr.fallenvaders.minecraft.test_server;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

public class FallenVadersTestServer {

    public static final Logger LOGGER = Logger.getAnonymousLogger();

    public static final String SERVER_JAR_NAME = "papermc-server.jar";

    public static final String[] SERVER_LAUNCH_COMMAND = new String[]{
        "java",
        "-server",
        "-Xms512M",
        "-Xmx2G",
        "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005",
        "-jar",
        SERVER_JAR_NAME,
        "nogui"
    };

    public static void main(String[] args) {
        ProcessBuilder pb = new ProcessBuilder(SERVER_LAUNCH_COMMAND);
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
