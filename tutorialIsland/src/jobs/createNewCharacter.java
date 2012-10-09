package jobs;

import tutorialIsland.TutotialIsland;
import tutorialIsland.TutotialIsland.States;

import java.awt.List;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.security.cert.Certificate;
import java.util.*;
import java.util.concurrent.Delayed;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.activation.MailcapCommandMap;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLPeerUnverifiedException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import org.powerbot.core.script.job.Task;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.Game;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.input.Keyboard;
import org.powerbot.game.api.methods.input.Mouse;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.util.Timer;
import org.powerbot.game.api.wrappers.node.SceneObject;
import org.powerbot.game.api.wrappers.widget.Widget;
import org.powerbot.game.api.wrappers.widget.WidgetChild;

public class createNewCharacter extends Node{

	public enum WIDGETS 
	{
		CREATE_ACCOUNT(596,60,true),
		EMAIL_ADDRESS(673,43,true),
		EMAIL_ADDRESS_RETYPE(673,113,true),
		BAD_EMAIL(673,29,true),
		PASSWORD_RETYPE(673,74,true),
		PASSWORD(673,84,true),
		AGE(673,49,true),
		CONTINUE(673,69,true);		
		
		private int id, childId;
		private boolean check;
		private WIDGETS(int id,int childId,boolean check)
		{
			this.id = id;
			this.childId = childId;
			this.check = check;
		}	
		public void setCheck(boolean check)
		{
			this.check = check;
		}
	}

	String userName = new String("clay740");
	String emailEnding = new String("@sharklasers.com");
	String emailStart = "";
	String email = new String();
	String password = new String("password1234");
	String age = new String("22");
	Widget frontPage = new Widget(WIDGETS.CREATE_ACCOUNT.id);	
	Widget createAccount = new Widget(WIDGETS.EMAIL_ADDRESS.id);
	TutotialIsland state = new TutotialIsland();

	private int count = 0;
	
	@Override
	public boolean activate() {
		// TODO Auto-generated method stub
		if(!Game.isLoggedIn()&& state.currentState== TutotialIsland.States.SIGNUP_NEW_CHARACTER)
		{
			return true;
		}
		else
		{
			state.setCurrentState(tutorialIsland.TutotialIsland.States.SELECT_CHARACTER);
			return false;			
		}
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub

		if(frontPage.getChild(WIDGETS.CREATE_ACCOUNT.childId).visible() && WIDGETS.CREATE_ACCOUNT.check==false)
		{
			WIDGETS.CREATE_ACCOUNT.setCheck(frontPage.getChild(WIDGETS.CREATE_ACCOUNT.childId).click(true));
			Task.sleep(400);
		}
		else if(WIDGETS.EMAIL_ADDRESS.check==false)
		{
			String parse,input = new String();
			parse="";
			try{
				URL webMail = new URL("https://www.guerrillamail.com/");
				HttpsURLConnection  connection  = (HttpsURLConnection) webMail.openConnection();
				BufferedReader br = new BufferedReader(
						new InputStreamReader(connection.getInputStream())); 
				
				while((input = br.readLine()) != null){
					parse = parse+"\n"+input;
				}
				//printout  parsed html
				System.out.println(parse);
				
				String inboxID = "(inbox-id\">)(.+?)(<)";
				//create pattern object
				Pattern pattern = Pattern.compile(inboxID);
				//create matcher object
				Matcher match = pattern.matcher(parse);
				if(match.find())
				{
					emailStart = match.group(2);
					System.out.println(match.group());
				}
				else
				{
					System.out.println("Did not find email.");
				}
				email=emailStart+emailEnding;
				WIDGETS.EMAIL_ADDRESS.setCheck(createAccount.getChild(WIDGETS.EMAIL_ADDRESS.childId).click(true));
				Keyboard.sendText(email+"\t", false);
				Task.sleep(400);
			}
			catch(MalformedURLException e){
				System.err.println(e);
			}
			catch (IOException e) {
				System.err.println(e);
			}
			 
		}
		else if(createAccount.getChild(WIDGETS.BAD_EMAIL.childId).visible())
		{
			createAccount.getChild(WIDGETS.EMAIL_ADDRESS.childId).click(true);
			Keyboard.sendKey((char)KeyEvent.VK_END);
			
			for(int i = 0; i< email.length()+50;i++)
			{
				Keyboard.sendKey((char) KeyEvent.VK_BACK_SPACE);
			}
			//reset to enter in email
			WIDGETS.EMAIL_ADDRESS.setCheck(false);
			userName= userName+count;
			
		}
		else if(WIDGETS.EMAIL_ADDRESS_RETYPE.check==false)
		{
			WIDGETS.EMAIL_ADDRESS_RETYPE.setCheck(createAccount.getChild(WIDGETS.EMAIL_ADDRESS_RETYPE.childId).click(true));
			Keyboard.sendText(email+"\t", false);
			Task.sleep(300);
		}
		else if(WIDGETS.PASSWORD.check==false)
		{
			WIDGETS.PASSWORD.setCheck(createAccount.getChild(WIDGETS.PASSWORD.childId).click(true));
			Keyboard.sendText(password+"\t", false,100,200);
			Task.sleep(300);
		}		
		else if(WIDGETS.PASSWORD_RETYPE.check==false)
		{
			WIDGETS.PASSWORD_RETYPE.setCheck(createAccount.getChild(WIDGETS.PASSWORD_RETYPE.childId).click(true));
			Keyboard.sendText(password+"\t", false,100,200);
			Task.sleep(300);
		}
		else if(WIDGETS.AGE.check==false)
		{
			WIDGETS.AGE.setCheck(createAccount.getChild(WIDGETS.AGE.childId).click(true));
			Keyboard.sendText(age+"\t", false,100,200);
			Task.sleep(300);
		}
		else if(WIDGETS.CONTINUE.check==false)
		{
			WIDGETS.CONTINUE.setCheck(createAccount.getChild(WIDGETS.CONTINUE.childId).click(true));
		}

		
		
		
		
		System.out.print("Did this work?:Create Account:"+ WIDGETS.CREATE_ACCOUNT.check+",Email:"+WIDGETS.EMAIL_ADDRESS.check+"\n");
		
		
	}
	

}
