package com.ddlab.rcp.views;

import java.io.File;

import org.alicebot.ab.Bot;
import org.alicebot.ab.Chat;
import org.alicebot.ab.MagicBooleans;
import org.eclipse.core.runtime.Path;

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
	  }
	  catch(Exception ex) {
		  ex.printStackTrace();
		  response = "Probably I am unable to understand so I am unable to answer, you can search in google.";
	  }
	  return response;
  }

  private String getResourcesPath() {
    File currDir = new File("E:/RCP-Demo-2018/vxrailrcpmulti/resources");
//    String resourcesPath = currDir.getAbsolutePath();
//    return resourcesPath;
    
    return new Path("E:/RCP-Demo-2018/vxrailrcpmulti/resources").toOSString();
    
//    return currDir.getAbsolutePath();
  }
}
