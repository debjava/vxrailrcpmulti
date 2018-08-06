package com.ddlab.rcp.views;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import org.alicebot.ab.Bot;
import org.alicebot.ab.Chat;
import org.alicebot.ab.MagicBooleans;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.osgi.framework.Bundle;

public class DDRobotBot {
  private static final boolean TRACE_MODE = false;
  private static String botName = "super";
  private Chat chatSession = null;

  public DDRobotBot() {

    String resourcesPath = getResourcesPath();
    System.out.println(resourcesPath);
    MagicBooleans.trace_mode = TRACE_MODE;
    Bot bot = new Bot(botName, resourcesPath);
    chatSession = new Chat(bot);
    bot.brain.nodeStats();
  }

  public String getBotResponse(String userInputText) {
    String response = null;
    try {
      response = chatSession.multisentenceRespond(userInputText);
    } catch (Exception ex) {
      ex.printStackTrace();
      response =
          "Probably I am unable to understand so I am unable to answer, you can search in google.";
    }
    return response;
  }

  private String getResourcesPath() {
    Bundle bundle = Platform.getBundle("vxraildemo3");
    URL fileURL = bundle.getEntry("resources");
    File file = null;
    try {
      URL resolvedFileURL = FileLocator.toFileURL(fileURL);
      // We need to use the 3-arg constructor of URI in order to properly escape file system chars
      URI resolvedURI = new URI(resolvedFileURL.getProtocol(), resolvedFileURL.getPath(), null);
      file = new File(resolvedURI);
    } catch (URISyntaxException e1) {
      e1.printStackTrace();
    } catch (IOException e1) {
      e1.printStackTrace();
    }
    return file.getAbsolutePath();
  }

  //  private String getResourcesPath1() {
  //    return new Path("E:/RCP-Demo-2018/vxrailrcpmulti/resources").toOSString(); // Working ok
  //  }
}
