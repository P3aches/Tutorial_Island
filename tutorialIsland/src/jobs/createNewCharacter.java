package jobs;

import java.awt.List;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.io.Writer;
import java.util.*;
import java.util.concurrent.Delayed;

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
		CREATE_ACCOUNT(596,60),
		EMAIL_ADDRESS(673,43),
		EMAIL_ADDRESS_RETYPE(673,113),
		BAD_EMAIL(673,29),
		PASSWORD_RETYPE(673,74),
		PASSWORD(673,84),
		AGE(673,49),
		CONTINUE(673,69);		
		
		private int id, childId;
		private WIDGETS(int id,int childId)
		{
			this.id = id;
			this.childId = childId;
		}		
	};
	ArrayList<Boolean> checks = new ArrayList<>(Arrays.asList(false,false,false,false,false,false,false)); 
	Keyboard input = new Keyboard();
	String userName = new String("clay740");
	String emailEnding = new String("@gmail.com");
	String email = new String();
	String password = new String("password1234");
	String age = new String("22");
	Widget frontPage = new Widget(WIDGETS.CREATE_ACCOUNT.id);	
	Widget createAccount = new Widget(WIDGETS.EMAIL_ADDRESS.id);
	private int count = 0;
	
	@Override
	public boolean activate() {
		// TODO Auto-generated method stub
		if(!Game.isLoggedIn())
		{
			return true;
		}
		else
			return false;
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub

		if(frontPage.getChild(WIDGETS.CREATE_ACCOUNT.childId).visible() && checks.get(0)==false)
		{
			checks.set(0, frontPage.getChild(WIDGETS.CREATE_ACCOUNT.childId).click(true));
			Task.sleep(200);
		}
		else if(email!=null && checks.get(1)==false)
		{
			email=userName+emailEnding;
			checks.set(1, createAccount.getChild(WIDGETS.EMAIL_ADDRESS.childId).click(true));
			input.sendText(email+"\t", false);
			Task.sleep(400); 
		}
		else if(createAccount.getChild(WIDGETS.BAD_EMAIL.childId).visible())
		{
			createAccount.getChild(WIDGETS.EMAIL_ADDRESS.childId).click(true);
			input.sendKey((char)KeyEvent.VK_END);
			
			for(int i = 0; i< email.length()+50;i++)
			{
				input.sendKey((char) KeyEvent.VK_BACK_SPACE);
			}
			//reset to enter in email
			checks.set(1, false);
			userName= userName+count;
			
		}
		else if(checks.get(2)==false)
		{
			email=userName+emailEnding;
			checks.set(2, createAccount.getChild(WIDGETS.EMAIL_ADDRESS_RETYPE.childId).click(true));
			input.sendText(email+"\t", false);
			Task.sleep(300);
		}
		else if(checks.get(3)==false)
		{
			checks.set(3, createAccount.getChild(WIDGETS.PASSWORD.childId).click(true));
			input.sendText(password+"\t", false,100,200);
			Task.sleep(300);
		}		
		else if(checks.get(4)==false)
		{
			checks.set(4, createAccount.getChild(WIDGETS.PASSWORD_RETYPE.childId).click(true));
			input.sendText(password+"\t", false,100,200);
			Task.sleep(300);
		}
		else if(checks.get(5)==false)
		{
			checks.set(5, createAccount.getChild(WIDGETS.AGE.childId).click(true));
			input.sendText(age+"\t", false,100,200);
			Task.sleep(300);
		}
		else if(checks.get(6)==false)
		{
			checks.set(6, createAccount.getChild(WIDGETS.CONTINUE.childId).click(true));
		}
		
		
		
		
		
		System.out.print("Did this work?:"+ checks.get(0)+","+checks.get(1)+"\n");
		
		
	}
	

}
