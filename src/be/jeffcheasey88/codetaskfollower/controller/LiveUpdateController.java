package be.jeffcheasey88.codetaskfollower.controller;

import be.jeffcheasey88.codetaskfollower.model.Model;
import dev.peerat.framework.Locker;
import dev.peerat.framework.dependency.Injection;

public class LiveUpdateController{
	
	@Injection private Locker<Model> modelLocker;

}
