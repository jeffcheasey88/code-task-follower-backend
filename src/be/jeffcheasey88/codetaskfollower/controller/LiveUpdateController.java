package be.jeffcheasey88.codetaskfollower.controller;

import be.jeffcheasey88.codetaskfollower.configuration.Mapper;
import dev.peerat.framework.HttpWriter;
import dev.peerat.framework.Locker;
import dev.peerat.framework.dependency.Injection;
import dev.peerat.framework.json.JsonMap;
import dev.peerat.framework.routes.Route;
import be.jeffcheasey88.codetaskfollower.dto.ModelUpdateDto;

public class LiveUpdateController{
	
	@Injection("modelUpdater") private Locker<ModelUpdateDto> modelLocker;

	@Route(path = "/models", needLogin = true, websocket = true)
	public void liveUpdate(HttpWriter writer){
		modelLocker.listen(model -> {
			JsonMap result = (JsonMap) Mapper.toJson(model.getModel());
			model.fillJson(result);
			writer.write(result.toString());
			writer.flush();
		});
	}
}
